package net.sleeplessdev.lib.client.render;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.vecmath.Vector3f;

public interface OrientableSelectionBox extends ExtendedSelectionBox {

    Vector3f getRotationAngles(IBlockState state, IBlockAccess world, BlockPos pos);

}
