package net.sleeplessdev.lib.util;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.IStringSerializable;

import java.util.Locale;

public enum BlockMaterial implements IStringSerializable {

    CARPET(Material.CARPET, SoundType.CLOTH, 0.1F, 0.5F),
    CIRCUITS(Material.CIRCUITS, SoundType.STONE, 0.0F, 0.0F),
    CLOTH(Material.CLOTH, SoundType.CLOTH, 0.8F, 4.0F),
    GLASS(Material.GLASS, SoundType.GLASS, 0.3F, 1.5F),
    GLOWSTONE(Material.GLASS, SoundType.GLASS, 0.3F, 1.5F),
    GRASS(Material.GRASS, SoundType.PLANT, 0.6F, 3F),
    GROUND(Material.GROUND, SoundType.GROUND, 0.5F, 2.5F),
    ICE(Material.ICE, SoundType.GLASS, 0.5F, 2.5F),
    LADDER(Material.WOOD, SoundType.LADDER, 0.4F, 2.0F),
    LEAVES(Material.LEAVES, SoundType.PLANT, 0.2F, 1.0F),
    METAL(Material.IRON, SoundType.METAL, 5.0F, 30.0F),
    PLANT(Material.PLANTS, SoundType.PLANT, 0.0F, 0.0F),
    QUARTZ(Material.ROCK, SoundType.STONE, 0.8F, 4.0F),
    ROCK(Material.ROCK, SoundType.STONE, 1.5F, 30.0F),
    SAND(Material.SAND, SoundType.SAND, 0.5F, 2.5F),
    SNOW(Material.SNOW, SoundType.SNOW, 0.2F, 1.0F),
    VINE(Material.VINE, SoundType.PLANT, 0.2F, 1.0F),
    WOOD(Material.WOOD, SoundType.WOOD, 2.0F, 15.0F);

    private final Material material;
    private final SoundType sound;
    private final float hardness;
    private final float resistance;

    BlockMaterial(Material material, SoundType sound, float hardness, float resistance) {
        this.material = material;
        this.sound = sound;
        this.hardness = hardness;
        this.resistance = resistance;
    }

    public Material getMaterial() {
        return material;
    }

    public SoundType getSound() {
        return sound;
    }

    public float getHardness() {
        return hardness;
    }

    public float getResistance() {
        return resistance;
    }

    @Override
    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }

}
