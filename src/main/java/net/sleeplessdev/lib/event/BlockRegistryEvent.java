package net.sleeplessdev.lib.event;

import com.google.common.base.CaseFormat;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.sleeplessdev.lib.SleeplessLib;
import net.sleeplessdev.lib.base.ModContainers;

import java.util.Objects;

public final class BlockRegistryEvent extends ForgeRegistryEvent<Block> {

    protected BlockRegistryEvent(IForgeRegistry<Block> registry) {
        super(registry);
    }

    @Override
    public void register(Block entry) {
        if ("block.null".equals(entry.getUnlocalizedName()) || "tile.null".equals(entry.getUnlocalizedName())) {
            if (SleeplessLib.isDeobfuscatedEnvironment()) {
                SleeplessLib.LOGGER.warn("Block <{}> is missing an unlocalized name!", entry.getRegistryName());
                SleeplessLib.LOGGER.warn("Attempting to define one from the block's registry name path");
                SleeplessLib.LOGGER.info("This warning will not be displayed outside of your dev environment");
            }
            entry.setUnlocalizedName(Objects.requireNonNull(entry.getRegistryName()).getResourcePath());
        }
        super.register(entry);
    }

    public void register(Class<? extends TileEntity> blockEntity, String key) {
        ResourceLocation id = ModContainers.applyActiveDomain(key);
        GameRegistry.registerTileEntity(blockEntity, id.toString());
    }

    public void register(Class<? extends TileEntity> blockEntity) {
        String name = blockEntity.getSimpleName();
        String key = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name);
        register(blockEntity, key);
    }

    public void registerAll(Class<? extends TileEntity>... blockEntities) {
        for (Class<? extends TileEntity> blockEntity : blockEntities) {
            register(blockEntity);
        }
    }

    public static final class Post extends ForgeRegistryEvent.Post<Block> {
        protected Post(IForgeRegistry<Block> registry) {
            super(registry);
        }
    }

}
