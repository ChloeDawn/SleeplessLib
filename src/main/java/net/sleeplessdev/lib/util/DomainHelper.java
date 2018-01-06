package net.sleeplessdev.lib.util;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.sleeplessdev.lib.SleeplessLib;

import javax.annotation.Nullable;

public final class DomainHelper {

    private DomainHelper() {}

    @Nullable
    public static ModContainer getActiveContainer() {
        return Loader.instance().activeModContainer();
    }

    public static String getActiveModId() {
        ModContainer c = getActiveContainer();
        if (c != null) return c.getModId();
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
