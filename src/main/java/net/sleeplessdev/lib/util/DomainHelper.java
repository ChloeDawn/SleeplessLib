package net.sleeplessdev.lib.util;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.sleeplessdev.lib.SleeplessLib;

import java.util.Optional;

public final class DomainHelper {

    private DomainHelper() {}

    public static Optional<ModContainer> getActiveContainer() {
        return Optional.ofNullable(Loader.instance().activeModContainer());
    }

    public static String getActiveModId() {
        getActiveContainer().ifPresent(ModContainer::getModId);
        new IllegalStateException(
                "Cannot retrieve modid from a null mod container!"
        ).printStackTrace();
        return SleeplessLib.ID;
    }

    public static ResourceLocation applyActiveDomain(String path) {
        return new ResourceLocation(getActiveModId(), path);
    }

    public static ResourceLocation applyActiveDomain(ResourceLocation name) {
        return applyActiveDomain(name.getResourcePath());
    }

}
