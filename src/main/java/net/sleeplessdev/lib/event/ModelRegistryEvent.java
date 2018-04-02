package net.sleeplessdev.lib.event;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.IContextSetter;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Objects;

public final class ModelRegistryEvent extends Event implements IContextSetter {

    protected ModelRegistryEvent(net.minecraftforge.client.event.ModelRegistryEvent event) {}

    @SideOnly(Side.CLIENT)
    public void register(Item item, int meta, ModelResourceLocation mrl) {
        Objects.requireNonNull(item.getRegistryName());
        ModelLoader.setCustomModelResourceLocation(item, meta, mrl);
    }

    @SideOnly(Side.CLIENT)
    public void register(Item item, ModelResourceLocation mrl) {
        register(item, 0, mrl);
    }

    @SideOnly(Side.CLIENT)
    public void register(Item item, int meta, String variant) {
        Objects.requireNonNull(item.getRegistryName());
        register(item, meta, new ModelResourceLocation(item.getRegistryName(), variant));
    }

    @SideOnly(Side.CLIENT)
    public void register(Item item, String variant) {
        Objects.requireNonNull(item.getRegistryName());
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
    public void register(Block block, int meta, ModelResourceLocation mrl) {
        Item item = Item.getItemFromBlock(block);
        if (item != Items.AIR) {
            Objects.requireNonNull(item.getRegistryName());
            ModelLoader.setCustomModelResourceLocation(item, meta, mrl);
        }
    }

    @SideOnly(Side.CLIENT)
    public void register(Block block, ModelResourceLocation mrl) {
        Item item = Item.getItemFromBlock(block);
        if (item != Items.AIR) {
            register(item, 0, mrl);
        }
    }

    @SideOnly(Side.CLIENT)
    public void register(Block block, int meta, String variant) {
        Item item = Item.getItemFromBlock(block);
        if (item != Items.AIR) {
            Objects.requireNonNull(item.getRegistryName());
            register(item, meta, new ModelResourceLocation(item.getRegistryName(), variant));
        }
    }

    @SideOnly(Side.CLIENT)
    public void register(Block block, String variant) {
        Item item = Item.getItemFromBlock(block);
        if (item != Items.AIR) {
            Objects.requireNonNull(item.getRegistryName());
            register(item, 0, new ModelResourceLocation(item.getRegistryName(), variant));
        }
    }

    @SideOnly(Side.CLIENT)
    public void register(Block block) {
        Item item = Item.getItemFromBlock(block);
        if (item != Items.AIR) {
            register(item, "normal");
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerAll(Block block, ModelResourceLocation... mrls) {
        Item item = Item.getItemFromBlock(block);
        if (item != Items.AIR) {
            registerAll(item, mrls);
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerAll(Block block, String... variants) {
        Item item = Item.getItemFromBlock(block);
        if (item != Items.AIR) {
            registerAll(item, variants);
        }
    }

    @SideOnly(Side.CLIENT)
    public void register(Item item, ItemMeshDefinition meshDefinition) {
        Objects.requireNonNull(item.getRegistryName());
        ModelLoader.setCustomMeshDefinition(item, meshDefinition);
    }

    @SideOnly(Side.CLIENT)
    public void register(Block block, IStateMapper mapper) {
        Objects.requireNonNull(block.getRegistryName());
        ModelLoader.setCustomStateMapper(block, mapper);
    }

    @SideOnly(Side.CLIENT)
    public <V extends TileEntity> void register(Class<V> blockEntity, TileEntitySpecialRenderer<V> renderer) {
        Objects.requireNonNull(renderer);
        ClientRegistry.bindTileEntitySpecialRenderer(blockEntity, renderer);
    }

}
