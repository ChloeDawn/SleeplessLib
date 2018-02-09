package net.sleeplessdev.lib.client.render;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.sleeplessdev.lib.math.BoundingBox;

import java.util.List;

public interface ExtendedBoundingBox {

    List<BoundingBox> getBoundingBoxes(IBlockState state, IBlockAccess world, BlockPos pos);

}
