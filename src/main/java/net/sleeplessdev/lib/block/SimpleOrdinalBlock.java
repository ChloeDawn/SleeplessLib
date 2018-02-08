package net.sleeplessdev.lib.block;

import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.sleeplessdev.lib.base.BlockMaterial;
import net.sleeplessdev.lib.base.ColorVariant;
import net.sleeplessdev.lib.base.OrdinalFacing;

public abstract class SimpleOrdinalBlock extends SimpleBlock {

    public static final PropertyEnum<OrdinalFacing> FACING = PropertyEnum.create("facing", OrdinalFacing.class);

    public SimpleOrdinalBlock(BlockMaterial material) {
        super(material);
    }

    public SimpleOrdinalBlock(BlockMaterial material, ColorVariant color) {
        super(material, color);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).ordinal();
    }

    @Override
    @Deprecated
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FACING, OrdinalFacing.VALUES[meta]);
    }

    @Override
    @Deprecated
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        OrdinalFacing facing = state.getValue(FACING);
        switch (rot) {
            case CLOCKWISE_90:
                return state.withProperty(FACING, facing.rotate90());
            case CLOCKWISE_180:
                return state.withProperty(FACING, facing.rotate180());
            case COUNTERCLOCKWISE_90:
                return state.withProperty(FACING, facing.rotate270());
        }
        return state;
    }

    @Override
    @Deprecated
    public IBlockState withMirror(IBlockState state, Mirror mirror) {
        OrdinalFacing facing = state.getValue(FACING);
        EnumFacing.Axis axis = facing.getCardinal().getAxis();

        if (mirror == Mirror.LEFT_RIGHT && axis == EnumFacing.Axis.X) {
            return state.withProperty(FACING, facing.rotate180());
        }

        if (mirror == Mirror.FRONT_BACK && axis == EnumFacing.Axis.Z) {
            return state.withProperty(FACING, facing.rotate180());
        }

        return state;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public boolean rotateBlock(World world, BlockPos pos, EnumFacing axis) {
        return axis.getAxis().isVertical() && world.setBlockState(pos, world.getBlockState(pos).cycleProperty(FACING));
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return getDefaultState().withProperty(FACING, OrdinalFacing.getFacingFromEntity(placer).rotate180());
    }

}
