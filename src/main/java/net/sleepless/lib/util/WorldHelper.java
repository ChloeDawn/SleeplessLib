package net.sleepless.lib.util;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public final class WorldHelper {

    private WorldHelper() {}

    @Nullable
    public static TileEntity getBlockEntity(World world, BlockPos pos) {
        return getBlockEntity(world, pos, TileEntity.class);
    }

    @Nullable
    public static <T extends TileEntity> T getBlockEntity(World world, BlockPos pos, Class<T> clazz) {
        if (isBlockLoaded(world, pos)) {
            TileEntity tile = world.getTileEntity(pos);
            if (tile != null && clazz.isAssignableFrom(tile.getClass())) {
                return (T) tile;
            }
        }
        return null;
    }

    public static boolean isBlockLoaded(World world, BlockPos pos) {
        return pos.getY() >= 0 && pos.getY() <= 255 && world.isBlockLoaded(pos);
    }

}
