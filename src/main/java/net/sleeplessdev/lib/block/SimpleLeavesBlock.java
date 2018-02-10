package net.sleeplessdev.lib.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.sleeplessdev.lib.base.BlockMaterial;
import net.sleeplessdev.lib.base.ColorVariant;
import net.sleeplessdev.lib.base.ModContainers;
import net.sleeplessdev.lib.client.Client;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class SimpleLeavesBlock extends BlockLeaves {

    private final Material material;
    private final MapColor mapColor;

    public SimpleLeavesBlock(BlockMaterial material, ColorVariant color) {
        this.material = material.getMaterial();
        this.mapColor = color.getMapColor();
        setHardness(material.getHardness());
        setResistance(material.getResistance());
        setSoundType(material.getSound());
        setCreativeTab(null);
    }

    public SimpleLeavesBlock(BlockMaterial material) {
        this.material = material.getMaterial();
        this.mapColor = this.material.getMaterialMapColor();
        setHardness(material.getHardness());
        setResistance(material.getResistance());
        setSoundType(material.getSound());
        setCreativeTab(null);
    }

    public SimpleLeavesBlock() {
        this(BlockMaterial.LEAVES);
    }

    public abstract ItemStack getSapling(IBlockState state, IBlockAccess world, BlockPos pos);

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
    @Deprecated
    public IBlockState getStateFromMeta(int meta) {
        boolean checkDecay = (meta & 1) == 0;
        boolean decayable = (meta >> 1) == 0;
        return getDefaultState()
                .withProperty(CHECK_DECAY, checkDecay)
                .withProperty(DECAYABLE, decayable);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int checkDecay = (state.getValue(CHECK_DECAY) ? 1 : 0) & 1;
        int decayable = (state.getValue(DECAYABLE) ? 1 : 0) << 1;
        return checkDecay | decayable;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, CHECK_DECAY, DECAYABLE);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return getDefaultState().withProperty(CHECK_DECAY, false).withProperty(DECAYABLE, false);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(this);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return !Client.isFancyGraphics();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return Client.isFancyGraphics() ? BlockRenderLayer.CUTOUT_MIPPED : BlockRenderLayer.SOLID;
    }

    @Override
    public final BlockPlanks.EnumType getWoodType(int meta) {
        return BlockPlanks.EnumType.OAK;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        Random rand = world instanceof World ? ((World) world).rand : new Random();
        int chance = getSaplingDropChance(state);

        if (fortune > 0) {
            chance -= 2 << fortune;
            if (chance < 10) chance = 10;
        }

        if (rand.nextInt(chance) == 0) {
            ItemStack sapling = getSapling(state, world, pos);
            if (!sapling.isEmpty()) drops.add(sapling);
        }

        chance = 200;
        if (fortune > 0) {
            chance -= 10 << fortune;
            if (chance < 40) chance = 40;
        }

        captureDrops(true);
        if (world instanceof World) {
            dropApple((World) world, pos, state, chance);
        }
        drops.addAll(captureDrops(false));
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        if (!Client.isFancyGraphics()) return super.shouldSideBeRendered(state, world, pos, side);
        return world.getBlockState(pos.offset(side)).getBlock() == this
                || super.shouldSideBeRendered(state, world, pos, side);
    }

    @Override
    public List<ItemStack> onSheared(ItemStack stack, IBlockAccess world, BlockPos pos, int fortune) {
        return Collections.singletonList(new ItemStack(this));
    }

    @Override
    public final Block setUnlocalizedName(String name) {
        return super.setUnlocalizedName(ModContainers.getActiveModId() + "." + name);
    }

    @Override // TODO Remove in 1.13
    public String getUnlocalizedName() {
        return super.getUnlocalizedName().replace("tile.", "block.");
    }

}
