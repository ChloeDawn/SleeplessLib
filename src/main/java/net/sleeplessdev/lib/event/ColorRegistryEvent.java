package net.sleeplessdev.lib.event;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.sleeplessdev.lib.client.color.ColorProvider;

public final class ColorRegistryEvent extends Event {

    private final BlockColors blockColors;
    private final ItemColors itemColors;

    protected ColorRegistryEvent(BlockColors blockColors, ItemColors itemColors) {
        this.blockColors = blockColors;
        this.itemColors = itemColors;
    }

    @SideOnly(Side.CLIENT)
    public void register(IItemColor color, Item... items) {
        itemColors.registerItemColorHandler(color, items);
    }

    @SideOnly(Side.CLIENT)
    public void register(IBlockColor color, Block... blocks) {
        blockColors.registerBlockColorHandler(color, blocks);
    }

    @SideOnly(Side.CLIENT)
    public void register(ColorProvider provider, Block block, Item item) {
        blockColors.registerBlockColorHandler(provider, block);
        itemColors.registerItemColorHandler(provider, item);
    }

    @SideOnly(Side.CLIENT)
    public void register(ColorProvider provider, Block block) {
        register(provider, block, Item.getItemFromBlock(block));
    }

    @SideOnly(Side.CLIENT)
    public void register(ColorProvider provider, Item item) {
        register(provider, Block.getBlockFromItem(item), item);
    }

}
