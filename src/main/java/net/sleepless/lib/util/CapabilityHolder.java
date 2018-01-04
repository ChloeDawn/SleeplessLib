package net.sleepless.lib.util;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public final class CapabilityHolder {

    private static final Function<Capability<?>, String> ERROR_GLOBAL = capability ->
            String.format("A global capability for <%s> has already been registered!",
                    capability.getName()
            );

    private static final BiFunction<EnumFacing, Capability<?>, String> ERROR_SIDE = (side, capability) ->
            String.format("A sided capability for <%s> has already been registered to side <%s>",
                    capability.getName(), side.getName()
            );

    private final CapabilityMap global;
    private final SidedCapabilityMap sided;

    private CapabilityHolder(CapabilityMap global, SidedCapabilityMap sided) {
        this.global = global;
        this.sided = sided;
    }

    public static CapabilityHolder.Builder create() {
        return new CapabilityHolder.Builder();
    }

    public final boolean test(Capability<?> capability, @Nullable EnumFacing side) {
        return sided.containsKey(side) && sided.get(side).containsKey(capability)
                || side == null && global.containsKey(capability);
    }

    @SuppressWarnings("unchecked")
    public final <V> V get(Capability<V> capability, @Nullable EnumFacing side) {
        if (sided.containsKey(side) && sided.get(side).containsKey(capability)) {
            Map<Capability<?>, Supplier<?>> capabilities = sided.get(side);
            Supplier<V> supplier = (Supplier<V>) capabilities.get(capability);
            return capability.cast(supplier.get());
        } else if (side == null && global.containsKey(capability)) {
            Supplier<V> supplier = (Supplier<V>) global.get(capability);
            return capability.cast(supplier.get());
        }
        return null;
    }

    public static final class Builder {
        private final CapabilityMap global = new CapabilityMap();
        private final SidedCapabilityMap sided = new SidedCapabilityMap();

        private Builder() {}

        public final <V> Builder add(Capability<V> capability, Supplier<V> instance, @Nullable EnumFacing side) {
            if (side == null) {
                if (!global.containsKey(capability)) {
                    global.put(capability, instance);
                } else throw new IllegalStateException(ERROR_GLOBAL.apply(capability));
            } else if (!global.containsKey(capability)) {
                sided.putIfAbsent(side, new CapabilityMap());
                if (!sided.get(side).containsKey(capability)) {
                    sided.get(side).put(capability, instance);
                } else throw new IllegalStateException(ERROR_SIDE.apply(side, capability));
            } else throw new IllegalStateException(ERROR_GLOBAL.apply(capability));
            return this;
        }

        public CapabilityHolder build() {
            return new CapabilityHolder(global, sided);
        }
    }

    private static final class CapabilityMap extends LinkedHashMap<Capability<?>, Supplier<?>> {}

    private static final class SidedCapabilityMap extends LinkedHashMap<EnumFacing, CapabilityMap> {}

}
