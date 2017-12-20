package net.sleepless.lib.event;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.sleepless.lib.SleeplessLib;

@Mod.EventBusSubscriber(modid = SleeplessLib.ID)
final class EventBusManager {

    private EventBusManager() {}

    @SubscribeEvent
    protected static void onBlockRegistry(RegistryEvent.Register<Block> event) {
        MinecraftForge.EVENT_BUS.post(new BlockRegistryEvent(event.getRegistry()));
    }

    @SubscribeEvent
    protected static void onItemRegistry(RegistryEvent.Register<Item> event) {
        MinecraftForge.EVENT_BUS.post(new BlockRegistryEvent.Post(ForgeRegistries.BLOCKS));
        MinecraftForge.EVENT_BUS.post(new ItemRegistryEvent(event.getRegistry()));
    }

    @SubscribeEvent
    protected static void onBiomeRegistry(RegistryEvent.Register<Biome> event) {
        MinecraftForge.EVENT_BUS.post(new ItemRegistryEvent.Post(ForgeRegistries.ITEMS));
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    protected static void onModelRegistry(net.minecraftforge.client.event.ModelRegistryEvent event) {
        MinecraftForge.EVENT_BUS.post(new net.sleepless.lib.event.ModelRegistryEvent());
    }

}
