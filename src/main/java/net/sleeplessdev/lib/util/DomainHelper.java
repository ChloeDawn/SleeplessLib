package net.sleeplessdev.lib.util;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.sleeplessdev.lib.SleeplessLib;

import java.util.function.Function;

public final class DomainHelper {

    private static final Function<ModContainer, String> DOMAIN_FUNCTION = container -> {
        if (container != null) return container.getModId();
        new IllegalStateException(
                "Cannot retrieve modid from a null mod container!"
        ).printStackTrace();
        return SleeplessLib.ID;
    };

    private static final Function<String, ResourceLocation> RL_FUNCTION = string ->
            new ResourceLocation(DOMAIN_FUNCTION.apply(getActiveContainer()), string);

    private DomainHelper() {}

    public static ModContainer getActiveContainer() {
        return Loader.instance().activeModContainer();
    }

    public static String getActiveModId() {
        return DOMAIN_FUNCTION.apply(getActiveContainer());
    }

    public static ResourceLocation applyActiveDomain(String path) {
        return RL_FUNCTION.apply(path);
    }

    public static ResourceLocation applyActiveDomain(ResourceLocation name) {
        return applyActiveDomain(name.getResourcePath());
    }

}
