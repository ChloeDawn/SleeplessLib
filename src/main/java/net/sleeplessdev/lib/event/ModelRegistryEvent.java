package net.sleeplessdev.lib.event;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.IContextSetter;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.Validate;

public final class ModelRegistryEvent extends Event implements IContextSetter {

    protected ModelRegistryEvent(net.minecraftforge.client.event.ModelRegistryEvent event) {}

    @SideOnly(Side.CLIENT)
    public void register(Item item, int meta, ModelResourceLocation mrl) {
        Validate.notNull(item.getRegistryName());
        ModelLoader.setCustomModelResourceLocation(item, meta, mrl);
    }

    @SideOnly(Side.CLIENT)
    public void register(Item item, ModelResourceLocation mrl) {
        register(item, 0, mrl);
    }

    @SideOnly(Side.CLIENT)
    public void register(Item item, int meta, String variant) {
        Validate.notNull(item.getRegistryName());
        register(item, meta, new ModelResourceLocation(item.getRegistryName(), variant));
    }

    @SideOnly(Side.CLIENT)
    public void register(Item item, String variant) {
        Validate.notNull(item.getRegistryName());
        register(item, 0, new ModelResourceLocation(item.getRegistryName(), variant));
    }

    @SideOnly(Side.CLIENT)
    public void register(Item item) {
        register(item, "inventory");
    }

    @SideOnly(Side.CLIENT)
    public void registerAll(Item item, ModelResourceLocation... mrls) {
        for (int i = 0; i < mrls.length; i++) {
            register(item, i, mrls[i]);
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerAll(Item item, String... variants) {
        for (int i = 0; i < variants.length; i++) {
            register(item, i, variants[i]);
        }
    }

    @SideOnly(Side.CLIENT)
    public void register(Item item, ItemMeshDefinition meshDefinition) {
        Validate.notNull(item.getRegistryName());
        ModelLoader.setCustomMeshDefinition(item, meshDefinition);
    }

    @SideOnly(Side.CLIENT)
    public void register(Block block, IStateMapper mapper) {
        Validate.notNull(block.getRegistryName());
        ModelLoader.setCustomStateMapper(block, mapper);
    }

}
