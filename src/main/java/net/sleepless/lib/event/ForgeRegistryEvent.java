package net.sleepless.lib.event;

import com.google.common.collect.ImmutableList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

class ForgeRegistryEvent<V extends IForgeRegistryEntry<V>> extends Event {

    private final IForgeRegistry<V> registry;

    protected ForgeRegistryEvent(IForgeRegistry<V> registry) {
        this.registry = registry;
    }

    public void register(V entry) {
        registry.register(entry);
    }

    public void register(V entry, ResourceLocation name) {
        registry.register(entry.setRegistryName(name));
    }

    public V retrieve(ResourceLocation name) {
        return registry.getValue(name);
    }

    public ResourceLocation retrieve(V entry) {
        return registry.getKey(entry);
    }

    public ImmutableList<V> retrieveAll() {
        return ImmutableList.copyOf(registry.getValues());
    }

    static class Post<V extends IForgeRegistryEntry<V>>  extends Event {
        private final IForgeRegistry<V> registry;

        protected Post(IForgeRegistry<V> registry) {
            this.registry = registry;
        }

        public V retrieve(ResourceLocation name) {
            return registry.getValue(name);
        }

        public ResourceLocation retrieve(V entry) {
            return registry.getKey(entry);
        }

        public ImmutableList<V> retrieveAll() {
            return ImmutableList.copyOf(registry.getValues());
        }
    }

}
