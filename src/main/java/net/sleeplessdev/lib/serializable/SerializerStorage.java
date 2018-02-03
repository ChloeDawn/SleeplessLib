package net.sleeplessdev.lib.serializable;

import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public final class SerializerStorage {

    private final List<ISerializable> serializers;

    private SerializerStorage(List<ISerializable> serializers) {
        this.serializers = serializers;
    }

    public static SerializerStorage.Builder create() {
        return new SerializerStorage.Builder();
    }

    public ISerializable get(int index) {
        return serializers.get(index);
    }

    public void serializeAll(NBTTagCompound nbt, boolean client) {
        if (!client) {
            for (ISerializable s : serializers) {
                s.serialize(nbt);
            }
        } else for (ISerializable s : serializers) {
            if (s.syncToClient()) s.serialize(nbt);
        }
    }

    public void deserializeAll(NBTTagCompound nbt, boolean client) {
        if (!client) {
            for (ISerializable s : serializers) {
                s.deserialize(nbt);
            }
        } else for (ISerializable s : serializers) {
            if (s.syncToClient()) s.deserialize(nbt);
        }
    }

    public void serializeAll(NBTTagCompound nbt) {
        serializeAll(nbt, false);
    }

    public void deserializeAll(NBTTagCompound nbt) {
        deserializeAll(nbt, false);
    }

    public static final class Builder {
        private final List<ISerializable> serializables = new ArrayList<>();

        private Builder() {}

        public Builder add(ISerializable serializable) {
            serializables.add(serializable);
            return this;
        }

        public Builder addAll(ISerializable... serializables) {
            this.serializables.addAll(Arrays.asList(serializables));
            return this;
        }

        public Builder addAll(Collection<ISerializable> serializables) {
            this.serializables.addAll(serializables);
            return this;
        }

        public SerializerStorage build() {
            return new SerializerStorage(serializables);
        }
    }

}
