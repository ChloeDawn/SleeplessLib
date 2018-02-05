package net.sleeplessdev.lib.base;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

import java.util.Optional;

public final class ModContainers {

    private ModContainers() {}

    public static Optional<ModContainer> getActiveContainer() {
        return Optional.ofNullable(Loader.instance().activeModContainer());
    }

    public static String getActiveModId() {
        return getActiveContainer().map(ModContainer::getModId).orElseThrow(
                () -> new IllegalStateException("Cannot retrieve modid from a null mod container!")
        );
    }

    public static ResourceLocation applyActiveDomain(String path) {
        return new ResourceLocation(getActiveModId(), path);
    }

    public static ResourceLocation applyActiveDomain(ResourceLocation name) {
        return applyActiveDomain(name.getResourcePath());
    }

}
