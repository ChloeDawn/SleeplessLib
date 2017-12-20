package net.sleepless.lib.event;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.Validate;

@SideOnly(Side.CLIENT)
public final class ModelRegistryEvent extends Event {

    protected ModelRegistryEvent() {}

    public void register(Item item, int meta, ModelResourceLocation mrl) {
        Validate.notNull(item.getRegistryName());
        ModelLoader.setCustomModelResourceLocation(item, meta, mrl);
    }

    public void register(Item item, ModelResourceLocation mrl) {
        register(item, 0, mrl);
    }

    public void register(Item item, int meta, String variant) {
        register(item, meta, new ModelResourceLocation(item.getRegistryName(), variant));
    }

    public void register(Item item, String variant) {
        register(item, 0, new ModelResourceLocation(item.getRegistryName(), variant));
    }

    public void register(Item item) {
        register(item, "inventory");
    }

    public void registerAll(Item item, ModelResourceLocation... mrls) {
        for (int i = 0; i < mrls.length; i++) {
            register(item, i, mrls[i]);
        }
    }

    public void registerAll(Item item, String... variants) {
        for (int i = 0; i < variants.length; i++) {
            register(item, i, variants[i]);
        }
    }

    public void register(Item item, ItemMeshDefinition meshDefinition) {
        ModelLoader.setCustomMeshDefinition(item, meshDefinition);
    }

    public void register(Block block, IStateMapper mapper) {
        ModelLoader.setCustomStateMapper(block, mapper);
    }

}
