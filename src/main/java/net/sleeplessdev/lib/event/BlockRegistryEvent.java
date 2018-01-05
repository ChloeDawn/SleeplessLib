package net.sleeplessdev.lib.event;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;

public final class BlockRegistryEvent extends ForgeRegistryEvent<Block> {

    protected BlockRegistryEvent(IForgeRegistry<Block> registry) {
        super(registry);
    }

    @Override
    public void register(Block entry, ResourceLocation name) {
        if ("block.null".equals(entry.getUnlocalizedName())) {
            String modid = name.getResourceDomain();
            String path = name.getResourcePath();
            entry.setUnlocalizedName(modid + "." + path);
        }
        super.register(entry, name);
    }

    public static final class Post extends ForgeRegistryEvent.Post<Block> {
        protected Post(IForgeRegistry<Block> registry) {
            super(registry);
        }
    }

}
