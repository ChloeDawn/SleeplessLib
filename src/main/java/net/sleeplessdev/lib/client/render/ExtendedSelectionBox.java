package net.sleeplessdev.lib.client.render;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;

import java.util.List;

public interface ExtendedSelectionBox {

    @Deprecated // Use new method below that passes hit vector coordinates
    void getSelectionBoundingBoxes(IBlockState state, IBlockAccess world, BlockPos pos, List<AxisAlignedBB> boxes);

    @SuppressWarnings("deprecation")
    default void getSelectionBoundingBoxes(IBlockState state, IBlockAccess world, BlockPos pos, Vec3d hitVec, List<AxisAlignedBB> boxes) {
        getSelectionBoundingBoxes(state, world, pos, boxes);
    }

}
