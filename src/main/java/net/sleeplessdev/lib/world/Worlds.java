package net.sleeplessdev.lib.world;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Optional;

public final class Worlds {

    private Worlds() {}

    public static IBlockState getBlockState(IBlockAccess world, BlockPos pos) {
        return isBlockLoaded(world, pos) ? world.getBlockState(pos) : Blocks.AIR.getDefaultState();
    }

    public static Optional<TileEntity> getBlockEntity(IBlockAccess world, BlockPos pos) {
        return getBlockEntity(world, pos, TileEntity.class);
    }

    @SuppressWarnings("unchecked")
    public static <T extends TileEntity> Optional<T> getBlockEntity(IBlockAccess world, BlockPos pos, Class<T> clazz) {
        if (isBlockLoaded (world, pos)) {
            final TileEntity tile = world.getTileEntity(pos);
            if (tile != null && clazz.isAssignableFrom(tile.getClass())) {
                return Optional.of((T) tile);
            }
        }
        return Optional.empty();
    }

    public static boolean isBlockLoaded(IBlockAccess world, BlockPos pos) {
        if (world instanceof World) {
            final World w = (World) world;
            return w.isValid(pos) && w.isBlockLoaded(pos);
        }
        return pos.getY() >= 0 && pos.getY() < 256
                && pos.getX() >= -30000000
                && pos.getZ() >= -30000000
                && pos.getX() < 30000000
                && pos.getZ() < 30000000;
    }

}
