package net.sleeplessdev.lib.event;

import com.google.common.base.Equivalence;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.sleeplessdev.lib.SleeplessLib;

import java.util.Collection;
import java.util.Map;

@Mod.EventBusSubscriber(modid = SleeplessLib.ID)
final class EventBusManager {

    @SideOnly(Side.CLIENT)
    private static Multimap<IItemColor, Item> itemColorMap = ArrayListMultimap.create();

    @SideOnly(Side.CLIENT)
    private static Multimap<IBlockColor, Block> blockColorMap = ArrayListMultimap.create();

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

        Multimap<Equivalence.Wrapper<ItemStack>, String> ores = ArrayListMultimap.create();
        MinecraftForge.EVENT_BUS.post(new OreRegistryEvent(ores));
        OreRegistryEvent.construct(ores);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    protected static void onModelRegistry(net.minecraftforge.client.event.ModelRegistryEvent event) {
        MinecraftForge.EVENT_BUS.post(new net.sleeplessdev.lib.event.ModelRegistryEvent(event));
        MinecraftForge.EVENT_BUS.post(new ColorRegistryEvent(itemColorMap, blockColorMap));
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    protected static void onItemColorHandler(ColorHandlerEvent.Item event) {
        for (Map.Entry<IItemColor, Collection<Item>> entry : itemColorMap.asMap().entrySet()) {
            Item[] items = entry.getValue().toArray(new Item[0]);
            event.getItemColors().registerItemColorHandler(entry.getKey(), items);
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    protected static void onBlockColorHandler(ColorHandlerEvent.Block event) {
        for (Map.Entry<IBlockColor, Collection<Block>> entry : blockColorMap.asMap().entrySet()) {
            Block[] blocks = entry.getValue().toArray(new Block[0]);
            event.getBlockColors().registerBlockColorHandler(entry.getKey(), blocks);
        }
    }

}
