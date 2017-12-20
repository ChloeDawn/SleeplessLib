package net.sleepless.lib;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = SleeplessLib.ID, name = SleeplessLib.NAME, version = SleeplessLib.VERSION)
public final class SleeplessLib {

    public static final String ID = "sleeplesslib";
    public static final String NAME = "Sleepless Library";
    public static final String VERSION = "%VERSION%";

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {}

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {}

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {}

}
