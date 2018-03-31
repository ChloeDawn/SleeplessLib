package net.sleeplessdev.lib.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.sleeplessdev.lib.base.BlockMaterial;
import net.sleeplessdev.lib.base.ColorVariant;
import net.sleeplessdev.lib.base.ModContainers;

public abstract class SimpleLogBlock extends BlockLog {

    private final Material material;
    private final MapColor mapColor;

    private boolean hasBarkItem = false;

    public SimpleLogBlock(BlockMaterial material, ColorVariant color) {
        this.material = material.getMaterial();
        this.mapColor = color.getMapColor();
        setHardness(material.getHardness());
        setResistance(material.getResistance());
        setSoundType(material.getSound());
        setCreativeTab(null);
    }

    public SimpleLogBlock(BlockMaterial material) {
        this.material = material.getMaterial();
        this.mapColor = this.material.getMaterialMapColor();
        setHardness(material.getHardness());
        setResistance(material.getResistance());
        setSoundType(material.getSound());
        setCreativeTab(null);
    }

    public Block setHasBarkItem(boolean hasBark) {
        this.hasBarkItem = hasBark;
        return this;
    }

    public boolean hasBarkItem() {
        return hasBarkItem;
    }

    @Override
    public boolean rotateBlock(World world, BlockPos pos, EnumFacing axis) {
        return world.setBlockState(pos, world.getBlockState(pos).cycleProperty(LOG_AXIS));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumAxis axis = EnumAxis.values()[meta % EnumAxis.values().length];
        return getDefaultState().withProperty(LOG_AXIS, axis);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(LOG_AXIS).ordinal();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, LOG_AXIS);
    }

    public boolean isBark(IBlockState state) {
        return hasBarkItem && state.getValue(LOG_AXIS) == EnumAxis.NONE;
    }

    public boolean isBark(int meta) {
        return hasBarkItem && meta > 0;
    }

    @Override
    @Deprecated
    public Material getMaterial(IBlockState state) {
        return material;
    }

    @Override
    @Deprecated
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return mapColor;
    }

    @Override
    public final Block setUnlocalizedName(String name) {
        return super.setUnlocalizedName(ModContainers.getActiveModId() + "." + name);
    }

    @Override // TODO Remove in 1.13
    public String getUnlocalizedName() {
        return super.getUnlocalizedName().replace("tile.", "block.");
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (hasBarkItem) {
            items.add(new ItemStack(this, 1, 0));
            items.add(new ItemStack(this, 1, 1));
        } else items.add(new ItemStack(this));
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(this, 1, isBark(state) ? 1 : 0);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return getDefaultState().withProperty(LOG_AXIS, isBark(meta) ? EnumAxis.NONE : EnumAxis.fromFacingAxis(side.getAxis()));
    }

}
