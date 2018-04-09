package net.sleeplessdev.lib;

import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = SleeplessLib.ID, name = SleeplessLib.NAME, version = SleeplessLib.VERSION)
public final class SleeplessLib {

    public static final String ID = "sleeplesslib";
    public static final String NAME = "Sleepless Lib";
    public static final String VERSION = "%VERSION%";

    public static final Logger LOGGER = LogManager.getLogger(NAME);

    private static boolean deobfuscatedEnvironment;

    public static boolean isDeobfuscatedEnvironment() {
        return deobfuscatedEnvironment;
    }

    @Mod.EventHandler
    public void onPreInitialization(FMLPreInitializationEvent event) {
        Object o = Launch.blackboard.get("fml.deobfuscatedEnvironment");
        if (o instanceof Boolean) {
            deobfuscatedEnvironment = (boolean) o;
        } else LOGGER.error("Failed to retrieve environment state from launch blackboard!");
    }

    @Mod.EventHandler
    public void onInitialization(FMLInitializationEvent event) {}

    @Mod.EventHandler
    public void onPostInitialization(FMLPostInitializationEvent event) {}

}
