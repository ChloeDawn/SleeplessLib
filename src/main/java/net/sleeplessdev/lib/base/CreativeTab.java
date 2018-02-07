package net.sleeplessdev.lib.base;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.function.Supplier;

public class CreativeTab extends CreativeTabs {

    private final String label;
    private final Supplier<ItemStack> icon;

    private boolean hasSearchBar;

    public CreativeTab(String label, Supplier<ItemStack> icon) {
        super("");
        this.label = label;
        this.icon = icon;
    }

    public CreativeTab(Supplier<ItemStack> icon) {
        this(ModContainers.getActiveModId(), icon);
    }

    public CreativeTab setHasSearchBar(boolean hasSearchBar) {
        this.hasSearchBar = hasSearchBar;
        return this;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public final String getTranslatedTabLabel() {
        return "tab." + label + ".label";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public final ItemStack getTabIconItem() {
        return icon.get();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getBackgroundImageName() {
        return hasSearchBar ? "item_search.png" : "items.png";
    }

    @Override
    public boolean hasSearchBar() {
        return hasSearchBar;
    }

}
