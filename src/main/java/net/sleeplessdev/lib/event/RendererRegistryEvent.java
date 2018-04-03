package net.sleeplessdev.lib.event;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.IContextSetter;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Objects;

public final class RendererRegistryEvent extends Event implements IContextSetter {

    protected RendererRegistryEvent(ModelRegistryEvent event) {}

    @SideOnly(Side.CLIENT)
    public <V extends TileEntity> void register(Class<V> blockEntityClass, TileEntitySpecialRenderer<V> blockEntityRenderer) {
        Objects.requireNonNull(blockEntityRenderer);
        ClientRegistry.bindTileEntitySpecialRenderer(blockEntityClass, blockEntityRenderer);
    }

    @SideOnly(Side.CLIENT)
    public <T extends Entity> void register(Class<T> entityClass, IRenderFactory<? super T> renderFactory) {
        Objects.requireNonNull(renderFactory);
        RenderingRegistry.registerEntityRenderingHandler(entityClass, renderFactory);
    }

}
