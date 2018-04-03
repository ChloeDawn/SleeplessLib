package net.sleeplessdev.lib.event;

import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public final class ItemRegistryEvent extends ForgeRegistryEvent<Item> {

    protected ItemRegistryEvent(IForgeRegistry<Item> registry) {
        super(registry);
    }

    public static final class Post extends ForgeRegistryEvent.Post<Item> {
        protected Post(IForgeRegistry<Item> registry) {
            super(registry);
        }
    }

}
