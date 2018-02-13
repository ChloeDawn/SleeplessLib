package net.sleeplessdev.lib.serializable;

import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public final class SerializerStorage {

    private final List<Supplier<ISerializable>> serializers;

    private SerializerStorage(List<Supplier<ISerializable>> serializers) {
        this.serializers = serializers;
    }

    public static SerializerStorage.Builder create() {
        return new SerializerStorage.Builder();
    }

    public void serializeAll(NBTTagCompound nbt, boolean client) {
        if (!client) {
            for (Supplier<ISerializable> s : serializers) {
                ISerializable i = s.get();
                Objects.requireNonNull(i);
                i.serialize(nbt);
            }
        } else for (Supplier<ISerializable> s : serializers) {
            ISerializable i = s.get();
            Objects.requireNonNull(i);
            if (i.syncToClient()) i.serialize(nbt);
        }
    }

    public void deserializeAll(NBTTagCompound nbt, boolean client) {
        if (!client) {
            for (Supplier<ISerializable> s : serializers) {
                ISerializable i = s.get();
                Objects.requireNonNull(i);
                i.deserialize(nbt);
            }
        } else for (Supplier<ISerializable> s : serializers) {
            ISerializable i = s.get();
            Objects.requireNonNull(i);
            if (i.syncToClient()) i.deserialize(nbt);
        }
    }

    public void serializeAll(NBTTagCompound nbt) {
        serializeAll(nbt, false);
    }

    public void deserializeAll(NBTTagCompound nbt) {
        deserializeAll(nbt, false);
    }

    public static final class Builder {
        private final List<Supplier<ISerializable>> serializables = new ArrayList<>();

        private Builder() {}

        public Builder add(Supplier<ISerializable> serializable) {
            serializables.add(serializable);
            return this;
        }

        public Builder addAll(Supplier<ISerializable>... serializables) {
            this.serializables.addAll(Arrays.asList(serializables));
            return this;
        }

        public Builder addAll(Collection<Supplier<ISerializable>> serializables) {
            this.serializables.addAll(serializables);
            return this;
        }

        public SerializerStorage build() {
            return new SerializerStorage(serializables);
        }
    }

}
