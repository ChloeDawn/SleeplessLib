package net.sleeplessdev.lib.serializable;

import net.minecraft.nbt.NBTTagCompound;
import net.sleeplessdev.lib.base.ColorVariant;

public final class SerializableColor implements ISerializable {

    private final String key;

    private ColorVariant color;

    private SerializableColor(String key, ColorVariant color) {
        this.key = key;
        this.color = color;
    }

    public static SerializableColor create(String key, ColorVariant color) {
        return new SerializableColor(key, color);
    }

    public static SerializableColor create(String key) {
        return new SerializableColor(key, ColorVariant.WHITE);
    }

    public static SerializableColor create(ColorVariant color) {
        return new SerializableColor("color", color);
    }

    public static SerializableColor create() {
        return new SerializableColor("color", ColorVariant.WHITE);
    }

    public ColorVariant getColor() {
        return color;
    }

    public void setColor(ColorVariant color) {
        this.color = color;
    }

    @Override
    public void serialize(NBTTagCompound nbt) {
        nbt.setInteger(key, color.ordinal());
    }

    @Override
    public void deserialize(NBTTagCompound nbt) {
        color = ColorVariant.VALUES[nbt.getInteger(key)];
    }

}
