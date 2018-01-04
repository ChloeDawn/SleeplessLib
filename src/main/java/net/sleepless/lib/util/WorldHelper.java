package net.sleepless.lib.util;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

public final class WorldHelper {

    private WorldHelper() {}

    public static Optional<TileEntity> getTileEntity(World world, BlockPos pos) {
        return getTileEntity(world, pos, TileEntity.class);
    }

    @SuppressWarnings("unchecked")
    public static <T extends TileEntity> Optional<T> getTileEntity(World world, BlockPos pos, Class<T> clazz) {
        if (isBlockLoaded(world, pos)) {
            TileEntity tile = world.getTileEntity(pos);
            if (tile != null && clazz.isAssignableFrom(tile.getClass())) {
                return Optional.of((T) tile);
            }
        }
        return Optional.empty();
    }

    public static boolean isBlockLoaded(World world, BlockPos pos) {
        return pos.getY() >= 0 && pos.getY() <= 255 && world.isBlockLoaded(pos);
    }

}
