package net.sleeplessdev.lib.util;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

import java.util.EnumMap;

public class BoundingBox {

    private final ImmutableMap<EnumFacing, AxisAlignedBB> aabbMap;

    private BoundingBox(AxisAlignedBB aabbNorth) {
        final EnumMap<EnumFacing, AxisAlignedBB> aabbMap = new EnumMap<>(EnumFacing.class);
        if (aabbNorth != null) for (EnumFacing facing : EnumFacing.VALUES) {
            aabbMap.put(facing, rotateAABB(aabbNorth, facing));
        }
        this.aabbMap = Maps.immutableEnumMap(aabbMap);
    }

    public static BoundingBox of(AxisAlignedBB aabb) {
        return new BoundingBox(aabb);
    }

    public static BoundingBox of(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        return of(new AxisAlignedBB(minX / 16.0, minY / 16.0, minZ / 16.0, maxX / 16.0, maxY / 16.0, maxZ / 16.0));
    }

    public static BoundingBox of(Vec3d min, Vec3d max) {
        return of(min.x, min.y, min.z, max.x, max.y, max.z);
    }

    public static BoundingBox ofSingleton(AxisAlignedBB aabb) {
        return new SingletonBoundingBox(aabb);
    }

    public static BoundingBox ofSingleton(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        return ofSingleton(new AxisAlignedBB(minX / 16.0, minY / 16.0, minZ / 16.0, maxX / 16.0, maxY / 16.0, maxZ / 16.0));
    }

    public static BoundingBox ofSingleton(Vec3d min, Vec3d max) {
        return ofSingleton(min.x, min.y, min.z, max.x, max.y, max.z);
    }

    public AxisAlignedBB getDefault() {
        return get(EnumFacing.NORTH);
    }

    public AxisAlignedBB get(EnumFacing facing) {
        return aabbMap.getOrDefault(facing, Block.FULL_BLOCK_AABB);
    }

    private AxisAlignedBB rotateAABB(AxisAlignedBB aabbNorth, EnumFacing facing) {
        double minX = aabbNorth.minX, minY = aabbNorth.minY, minZ = aabbNorth.minZ;
        double maxX = aabbNorth.maxX, maxY = aabbNorth.maxY, maxZ = aabbNorth.maxZ;
        switch (facing) {
            case DOWN: return new AxisAlignedBB(1 - maxX, minZ, 1 - maxY, 1 - minX, maxZ, 1 - minY);
            case UP: return new AxisAlignedBB(minX, 1 - maxZ, minY, maxX, 1 - minZ, maxY);
            case SOUTH: return new AxisAlignedBB(1 - maxX, minY, 1 - maxZ, 1 - minX, maxY, 1 - minZ);
            case WEST: return new AxisAlignedBB(minZ, minY, minX, maxZ, maxY, maxX);
            case EAST: return new AxisAlignedBB(1 - maxZ, minY, 1 - maxX, 1 - minZ, maxY, 1 - minX);
        }
        return aabbNorth;
    }

    /**
     * A micro-optimization to avoid map lookups for single-facing AABBs
     */
    private static class SingletonBoundingBox extends BoundingBox {
        private final AxisAlignedBB aabbNorth;

        private SingletonBoundingBox(AxisAlignedBB aabbNorth) {
            super(null);
            this.aabbNorth = aabbNorth;
        }

        @Override
        public AxisAlignedBB getDefault() {
            return aabbNorth;
        }

        @Override
        public AxisAlignedBB get(EnumFacing facing) {
            return aabbNorth;
        }
    }

}
