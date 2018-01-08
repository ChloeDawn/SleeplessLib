package net.sleeplessdev.lib.util.serializable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

public final class SerializableBlockPos implements ISerializable {

    private final BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

    private final String key;

    private SerializableBlockPos(String key, BlockPos pos) {
        this.pos.setPos(pos);
        this.key = key;
    }

    public static SerializableBlockPos create(String name, BlockPos pos) {
        return new SerializableBlockPos(name, pos);
    }

    public static SerializableBlockPos create(String name) {
        return new SerializableBlockPos(name, BlockPos.ORIGIN);
    }

    public static SerializableBlockPos create(BlockPos pos) {
        return new SerializableBlockPos("pos", pos);
    }

    public static SerializableBlockPos create() {
        return new SerializableBlockPos("pos", BlockPos.ORIGIN);
    }

    public BlockPos getPos() {
        return pos.toImmutable();
    }

    public SerializableBlockPos setPos(Vec3i vec) {
        pos.setPos(vec);
        return this;
    }

    public SerializableBlockPos setPos(int x, int y, int z) {
        pos.setPos(x, y, z);
        return this;
    }

    public SerializableBlockPos setPos(double x, double y, double z) {
        pos.setPos(x, y, z);
        return this;
    }

    @Override
    public void serialize(NBTTagCompound nbt) {
        NBTTagCompound pos = new NBTTagCompound();
        pos.setInteger("x", this.pos.getX());
        pos.setInteger("y", this.pos.getY());
        pos.setInteger("z", this.pos.getZ());
        nbt.setTag(key, pos);
    }

    @Override
    public void deserialize(NBTTagCompound nbt) {
        NBTTagCompound pos = nbt.getCompoundTag(key);
        int x = pos.getInteger("x");
        int y = pos.getInteger("y");
        int z = pos.getInteger("z");
        this.pos.setPos(x, y, z);
    }

}
