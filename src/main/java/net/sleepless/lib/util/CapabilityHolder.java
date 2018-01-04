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

    private final Map<Capability<?>, Supplier<?>> globalCapabilities = new LinkedHashMap<>();
    private final Map<EnumFacing, Map<Capability<?>, Supplier<?>>> sidedCapabilities = new LinkedHashMap<>();

    private CapabilityHolder() {}

    public static CapabilityHolder create() {
        return new CapabilityHolder();
    }

    public final <V> void add(Capability<V> capability, Supplier<V> instance, @Nullable EnumFacing side) {
        if (side == null) {
            if (!globalCapabilities.containsKey(capability)) {
                globalCapabilities.put(capability, instance);
            } else throw new IllegalStateException(ERROR_GLOBAL.apply(capability));
        } else if (!globalCapabilities.containsKey(capability)) {
            sidedCapabilities.putIfAbsent(side, new LinkedHashMap<>());
            if (!sidedCapabilities.get(side).containsKey(capability)) {
                sidedCapabilities.get(side).put(capability, instance);
            } else throw new IllegalStateException(ERROR_SIDE.apply(side, capability));
        } else throw new IllegalStateException(ERROR_GLOBAL.apply(capability));
    }

    public final boolean test(Capability<?> capability, @Nullable EnumFacing side) {
        return sidedCapabilities.containsKey(side) & sidedCapabilities.get(side).containsKey(capability)
                || side == null && globalCapabilities.containsKey(capability);
    }

    @SuppressWarnings("unchecked")
    public final <V> V get(Capability<V> capability, @Nullable EnumFacing side) {
        if (sidedCapabilities.containsKey(side) && sidedCapabilities.get(side).containsKey(capability)) {
            Map<Capability<?>, Supplier<?>> capabilities = sidedCapabilities.get(side);
            Supplier<V> supplier = (Supplier<V>) capabilities.get(capability);
            return capability.cast(supplier.get());
        } else if (side == null && globalCapabilities.containsKey(capability)) {
            Supplier<V> supplier = (Supplier<V>) globalCapabilities.get(capability);
            return capability.cast(supplier.get());
        }
        return null;
    }

}
