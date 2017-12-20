package net.sleepless.lib.event;

import net.minecraft.block.Block;
import net.minecraftforge.registries.IForgeRegistry;

public final class BlockRegistryEvent extends ForgeRegistryEvent<Block> {

    protected BlockRegistryEvent(IForgeRegistry<Block> registry) {
        super(registry);
    }

    public static final class Post extends ForgeRegistryEvent.Post<Block> {
        protected Post(IForgeRegistry<Block> registry) {
            super(registry);
        }
    }

}
