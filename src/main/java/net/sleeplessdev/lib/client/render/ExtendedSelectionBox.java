package net.sleeplessdev.lib.client.render;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import java.util.List;

public interface ExtendedSelectionBox {

    void getSelectionBoundingBoxes(IBlockState state, IBlockAccess world, BlockPos pos, List<AxisAlignedBB> boxes);

}
