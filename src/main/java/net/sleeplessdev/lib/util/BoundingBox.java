package net.sleeplessdev.lib.util;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public final class BoundingBox {

    private final ImmutableMap<EnumFacing, AxisAlignedBB> aabbMap;

    private BoundingBox(AxisAlignedBB aabbNorth, Predicate<EnumFacing> filter) {
        final Map<EnumFacing, AxisAlignedBB> aabbMap = new HashMap<>();
        for (EnumFacing facing : EnumFacing.values()) {
            if (filter.test(facing)) {
                aabbMap.put(facing, rotateAABB(aabbNorth, facing));
            }
        }
        this.aabbMap = ImmutableMap.copyOf(aabbMap);
    }

    public static BoundingBox of(AxisAlignedBB aabb, Predicate<EnumFacing> filter) {
        return new BoundingBox(aabb, filter);
    }

    public static BoundingBox of(AxisAlignedBB aabb) {
        return of(aabb, facing -> true);
    }

    public static BoundingBox of(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, Predicate<EnumFacing> filter) {
        return of(new AxisAlignedBB(minX / 16.0, minY / 16.0, minZ / 16.0, maxX / 16.0, maxY / 16.0, maxZ / 16.0), filter);
    }

    public static BoundingBox of(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        return of(minX, minY, minZ, maxX, maxY, maxZ, facing -> true);
    }

    public static BoundingBox of(Vec3d min, Vec3d max, Predicate<EnumFacing> filter) {
        return of(min.x, min.y, min.z, max.x, max.y, max.z, filter);
    }

    public static BoundingBox of(Vec3d min, Vec3d max) {
        return of(min.x, min.y, min.z, max.x, max.y, max.z);
    }

    public AxisAlignedBB get(EnumFacing facing) {
        return aabbMap.getOrDefault(facing, Block.FULL_BLOCK_AABB);
    }

    public AxisAlignedBB getDown() {
        return get(EnumFacing.DOWN);
    }

    public AxisAlignedBB getUp() {
        return get(EnumFacing.UP);
    }

    public AxisAlignedBB getNorth() {
        return get(EnumFacing.NORTH);
    }

    public AxisAlignedBB getSouth() {
        return get(EnumFacing.SOUTH);
    }

    public AxisAlignedBB getWest() {
        return get(EnumFacing.WEST);
    }

    public AxisAlignedBB getEast() {
        return get(EnumFacing.EAST);
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

}
