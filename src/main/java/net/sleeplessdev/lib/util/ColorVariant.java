package net.sleeplessdev.lib.util;

import net.minecraft.block.material.MapColor;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.IStringSerializable;

import java.util.Locale;

public enum ColorVariant implements IStringSerializable {

    BLACK(MapColor.BLACK, EnumDyeColor.BLACK, 0x1D1D21),
    RED(MapColor.RED, EnumDyeColor.RED, 0xB02E26),
    GREEN(MapColor.GREEN, EnumDyeColor.GREEN, 0x5E7C16),
    BROWN(MapColor.BROWN, EnumDyeColor.BROWN, 0x835432),
    BLUE(MapColor.BLUE, EnumDyeColor.BLUE, 0x3C44AA),
    PURPLE(MapColor.PURPLE, EnumDyeColor.PURPLE, 0x8932B8),
    CYAN(MapColor.CYAN, EnumDyeColor.CYAN, 0x169C9C),
    LIGHT_GRAY(MapColor.SILVER, EnumDyeColor.SILVER, 0x9D9D97),
    GRAY(MapColor.GRAY, EnumDyeColor.GRAY, 0x474F52),
    PINK(MapColor.PINK, EnumDyeColor.PINK, 0xF38BAA),
    LIME(MapColor.LIME, EnumDyeColor.LIME, 0x80C71F),
    YELLOW(MapColor.YELLOW, EnumDyeColor.YELLOW, 0xFED83D),
    LIGHT_BLUE(MapColor.LIGHT_BLUE, EnumDyeColor.LIGHT_BLUE, 0x3AB3DA),
    MAGENTA(MapColor.MAGENTA, EnumDyeColor.MAGENTA, 0xC74EBD),
    ORANGE(MapColor.GOLD, EnumDyeColor.ORANGE, 0xF9801D),
    WHITE(MapColor.SNOW, EnumDyeColor.WHITE, 0xF9FFFE);

    public static final ColorVariant[] VALUES = ColorVariant.values();

    private final MapColor mapColor;
    private final EnumDyeColor dyeColor;
    private final int colorValue;

    ColorVariant(MapColor mapColor, EnumDyeColor dyeColor, int colorValue) {
        this.mapColor = mapColor;
        this.dyeColor = dyeColor;
        this.colorValue = colorValue;
    }

    public MapColor getMapColor() {
        return mapColor;
    }

    public EnumDyeColor getDyeColor() {
        return dyeColor;
    }

    public int getColorValue() {
        return colorValue;
    }

    @Override
    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }

}
