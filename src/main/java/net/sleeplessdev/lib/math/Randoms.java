package net.sleeplessdev.lib.math;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;

import java.util.Random;

public final class Randoms {

    private Randoms() {}

    public static Random withSeed(long seed) {
        final Random rand = new Random();
        rand.setSeed(seed);
        return rand;
    }

    public static Random ofCoordinate(Vec3i vec) {
        return withSeed(MathHelper.getCoordinateRandom(
                vec.getX(), vec.getY(), vec.getZ()
        ));
    }

}
