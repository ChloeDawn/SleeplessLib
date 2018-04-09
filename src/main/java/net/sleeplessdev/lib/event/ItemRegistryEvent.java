package net.sleeplessdev.lib.event;

import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;
import net.sleeplessdev.lib.SleeplessLib;

import java.util.Objects;

public final class ItemRegistryEvent extends ForgeRegistryEvent<Item> {

    protected ItemRegistryEvent(IForgeRegistry<Item> registry) {
        super(registry);
    }

    @Override
    public void register(Item entry) {
        if ("item.null".equals(entry.getUnlocalizedName())) {
            if (SleeplessLib.isDeobfuscatedEnvironment()) {
                SleeplessLib.LOGGER.warn("Item <{}> is missing an unlocalized name!", entry.getRegistryName());
                SleeplessLib.LOGGER.warn("Attempting to define one from the item's registry name path");
                SleeplessLib.LOGGER.info("This warning will not be displayed outside of your dev environment");
            }
            entry.setUnlocalizedName(Objects.requireNonNull(entry.getRegistryName()).getResourcePath());
        }
        super.register(entry);
    }

    public static final class Post extends ForgeRegistryEvent.Post<Item> {
        protected Post(IForgeRegistry<Item> registry) {
            super(registry);
        }
    }

}
