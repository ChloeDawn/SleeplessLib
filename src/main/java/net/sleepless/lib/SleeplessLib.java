package net.sleepless.lib;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.sleepless.lib.util.annotation.AnnotationRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = SleeplessLib.ID, name = SleeplessLib.NAME, version = SleeplessLib.VERSION)
public final class SleeplessLib {

    public static final String ID = "sleeplesslib";
    public static final String NAME = "Sleepless Lib";
    public static final String VERSION = "%VERSION%";

    public static final Logger LOGGER = LogManager.getLogger(NAME);

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        AnnotationRegistry.collectData(event);
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {}

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {}

}
