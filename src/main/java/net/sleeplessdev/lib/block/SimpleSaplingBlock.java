package net.sleeplessdev.lib.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.sleeplessdev.lib.base.BlockMaterial;
import net.sleeplessdev.lib.base.ColorVariant;
import net.sleeplessdev.lib.base.ModContainers;

import java.util.Random;

public abstract class SimpleSaplingBlock extends BlockBush implements IGrowable {

    public static final PropertyInteger GROWTH_STAGE = PropertyInteger.create("stage", 0, 1);
    protected static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.8D, 0.9D);

    public SimpleSaplingBlock(BlockMaterial material, ColorVariant color) {
        super(material.getMaterial(), color.getMapColor());
        setHardness(material.getHardness());
        setResistance(material.getResistance());
        setSoundType(material.getSound());
        setCreativeTab(null);
    }

    public SimpleSaplingBlock(BlockMaterial material) {
        super(material.getMaterial(), material.getMaterial().getMaterialMapColor());
        setHardness(material.getHardness());
        setResistance(material.getResistance());
        setSoundType(material.getSound());
        setCreativeTab(null);
    }

    public SimpleSaplingBlock() {
        super(Material.PLANTS);
        setSoundType(SoundType.PLANT);
        setCreativeTab(null);
    }

    public abstract IBlockState getTreeWood(IBlockState state, IBlockAccess world, BlockPos pos);

    public abstract IBlockState getTreeLeaves(IBlockState state, IBlockAccess world, BlockPos pos);

    private void tryGrowTree(World world, BlockPos pos, IBlockState state, Random rand) {
        if (state.getValue(GROWTH_STAGE) == 0) {
            world.setBlockState(pos, state.cycleProperty(GROWTH_STAGE), 4);
        } else generateTree(world, pos, state, rand);
    }

    public void generateTree(World world, BlockPos pos, IBlockState state, Random rand) {
        if (!TerrainGen.saplingGrowTree(world, rand, pos)) return;

        IBlockState wood = getTreeWood(state, world, pos);
        IBlockState leaves = getTreeLeaves(state, world, pos);

        WorldGenerator generator = new WorldGenTrees(true, 4, wood, leaves, false);

        world.setBlockToAir(pos);

        if (!generator.generate(world, rand, pos)) {
            world.setBlockState(pos, state, 4);
        }
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if (!world.isRemote) {
            super.updateTick(world, pos, state, rand);
            if (world.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0) {
                tryGrowTree(world, pos, state, rand);
            }
        }
    }

    @Override
    @Deprecated
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return SAPLING_AABB;
    }

    @Override
    @Deprecated
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(GROWTH_STAGE, meta & 1);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(GROWTH_STAGE);
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
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, GROWTH_STAGE);
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World world, Random rand, BlockPos pos, IBlockState state) {
        return (double) world.rand.nextFloat() < 0.45D;
    }

    @Override
    public void grow(World world, Random rand, BlockPos pos, IBlockState state) {
        tryGrowTree(world, pos, state, rand);
    }

}
