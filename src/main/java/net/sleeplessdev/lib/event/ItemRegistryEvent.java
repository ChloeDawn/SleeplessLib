package net.sleeplessdev.lib.event;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.sleeplessdev.lib.SleeplessLib;

import java.util.Objects;

public final class ItemRegistryEvent extends ForgeRegistryEvent<Item> {

    protected ItemRegistryEvent(IForgeRegistry<Item> registry) {
        super(registry);
    }

    @Override
    public void register(Item entry) {
        if (entry instanceof ItemBlock && entry.getRegistryName() == null) {
            ResourceLocation name = Objects.requireNonNull(((ItemBlock) entry).getBlock().getRegistryName());
            SleeplessLib.LOGGER.debug("Attempting to inherit name from Block <{}> for registered ItemBlock", name);
            entry.setRegistryName(name);
        }
        super.register(entry);
    }

    @Override
    public void register(Item entry, ResourceLocation name) {
        if ("item.null".equals(entry.getUnlocalizedName())) {
            SleeplessLib.LOGGER.debug("Item <{}> is missing an unlocalized name, creating one from registry name", name);
            entry.setUnlocalizedName(name.getResourcePath());
        }
        super.register(entry, name);
    }

    public static final class Post extends ForgeRegistryEvent.Post<Item> {
        protected Post(IForgeRegistry<Item> registry) {
            super(registry);
        }
    }

}
