package net.sleeplessdev.lib.util;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.function.Supplier;

public class CreativeTab extends CreativeTabs {

    private final String label;
    private final Supplier<ItemStack> icon;

    public CreativeTab(String label, Supplier<ItemStack> icon) {
        super("");
        this.label = label;
        this.icon = icon;
    }

    public CreativeTab(Supplier<ItemStack> icon) {
        this(DomainHelper.getActiveModId(), icon);
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

}
