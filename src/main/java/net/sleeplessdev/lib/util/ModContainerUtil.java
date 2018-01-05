package net.sleeplessdev.lib.util;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.sleeplessdev.lib.SleeplessLib;

public final class ModContainerUtil {

    private ModContainerUtil() {}

    public static String getActiveModId() {
        ModContainer container = Loader.instance().activeModContainer();
        if (container == null) {
            SleeplessLib.LOGGER.warn("Null active mod container! Falling back to modid <{}>.", SleeplessLib.ID);
            return SleeplessLib.ID;
        } else return container.getModId();
    }

    public static ResourceLocation applyActiveContainer(String path) {
        return new ResourceLocation(getActiveModId(), path);
    }

    public static ResourceLocation applyActiveContainer(ResourceLocation name) {
        return applyActiveContainer(name.getResourcePath());
    }

}
