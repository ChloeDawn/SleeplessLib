package net.sleeplessdev.lib.serializable;

import net.minecraft.nbt.NBTTagCompound;

public interface ISerializable {

    void serialize(NBTTagCompound nbt);

    void deserialize(NBTTagCompound nbt);

    default boolean syncToClient() {
        return false;
    }

}
