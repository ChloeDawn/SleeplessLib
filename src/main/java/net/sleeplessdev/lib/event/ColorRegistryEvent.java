package net.sleeplessdev.lib.event;

import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.sleeplessdev.lib.client.color.ColorProvider;

import java.util.Arrays;

public final class ColorRegistryEvent extends Event {

    @SideOnly(Side.CLIENT)
    private final Multimap<IItemColor, Item> itemColorMap;

    @SideOnly(Side.CLIENT)
    private final Multimap<IBlockColor, Block> blockColorMap;

    protected ColorRegistryEvent(Multimap<IItemColor, Item> itemColorMap, Multimap<IBlockColor, Block> blockColorMap) {
        this.itemColorMap = itemColorMap;
        this.blockColorMap = blockColorMap;
    }

    @SideOnly(Side.CLIENT)
    public void register(IItemColor color, Item... items) {
        itemColorMap.putAll(color, Arrays.asList(items));
    }

    @SideOnly(Side.CLIENT)
    public void register(IBlockColor color, Block... blocks) {
        blockColorMap.putAll(color, Arrays.asList(blocks));
    }

    @SideOnly(Side.CLIENT)
    public void register(ColorProvider provider, Block block, Item item) {
        itemColorMap.put(provider, item);
        blockColorMap.put(provider, block);
    }

}
