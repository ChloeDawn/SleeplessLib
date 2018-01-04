package net.sleepless.lib.util.serializable;

import net.minecraft.nbt.NBTTagCompound;

public interface ISerializable {

    void serialize(NBTTagCompound nbt);

    void deserialize(NBTTagCompound nbt);

    default boolean syncToClient() {
        return false;
    }

}
