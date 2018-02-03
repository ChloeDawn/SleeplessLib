package net.sleeplessdev.lib.world;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Optional;

public final class Worlds {

    private Worlds() {}

    public static Optional<TileEntity> getBlockEntity(IBlockAccess world, BlockPos pos) {
        return getBlockEntity(world, pos, TileEntity.class);
    }

    @SuppressWarnings("unchecked")
    public static <T extends TileEntity> Optional<T> getBlockEntity(IBlockAccess world, BlockPos pos, Class<T> clazz) {
        if (!(world instanceof World) || isBlockLoaded ((World) world, pos)) {
            TileEntity tile = world.getTileEntity(pos);
            if (tile != null && clazz.isAssignableFrom(tile.getClass())) {
                return Optional.of((T) tile);
            }
        }
        return Optional.empty();
    }

    public static boolean isBlockLoaded(World world, BlockPos pos) {
        return world.isValid(pos) && world.isBlockLoaded(pos);
    }

}
