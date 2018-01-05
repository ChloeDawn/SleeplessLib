package net.sleeplessdev.lib.event;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.sleeplessdev.lib.SleeplessLib;
import net.sleeplessdev.lib.util.ModContainerUtil;

public final class ItemRegistryEvent extends ForgeRegistryEvent<Item> {

    protected ItemRegistryEvent(IForgeRegistry<Item> registry) {
        super(registry);
    }

    @Override
    public void register(Item entry) {
        if (entry instanceof ItemBlock && entry.getRegistryName() == null) {
            SleeplessLib.LOGGER.warn("Registry name for ItemBlock was null, attempting to inherit name from Block parent.");
            entry.setRegistryName(Block.getBlockFromItem(entry).getRegistryName());
        }
        super.register(entry);
    }

    @Override
    public void register(Item entry, ResourceLocation name) {
        if ("item.null".equals(entry.getUnlocalizedName())) {
            String modid = name.getResourceDomain();
            String path = name.getResourcePath();
            entry.setUnlocalizedName(modid + "." + path);
        }
        super.register(entry, name);
    }

    @Override
    public void register(Item entry, String name) {
        if ("item.null".equals(entry.getUnlocalizedName())) {
            String modid = ModContainerUtil.getActiveModId();
            entry.setUnlocalizedName(modid + "." + name);
        }
        super.register(entry, name);
    }

    public static final class Post extends ForgeRegistryEvent.Post<Item> {
        protected Post(IForgeRegistry<Item> registry) {
            super(registry);
        }
    }

}
