package net.sleeplessdev.lib;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.sleeplessdev.lib.block.SimpleLeavesBlock;
import net.sleeplessdev.lib.client.color.Colors;
import net.sleeplessdev.lib.event.BlockRegistryEvent;
import net.sleeplessdev.lib.event.ColorRegistryEvent;
import net.sleeplessdev.lib.event.ItemRegistryEvent;
import net.sleeplessdev.lib.event.ModelRegistryEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = SleeplessLib.ID, name = SleeplessLib.NAME, version = SleeplessLib.VERSION)
@Mod.EventBusSubscriber(modid = SleeplessLib.ID)
public final class SleeplessLib {

    public static final String ID = "sleeplesslib";
    public static final String NAME = "Sleepless Lib";
    public static final String VERSION = "%VERSION%";

    public static final Logger LOGGER = LogManager.getLogger(NAME);

    @GameRegistry.ObjectHolder("sleeplesslib:leaves")
    public static final Block LEAF_BLOCK = Blocks.AIR;

    @GameRegistry.ObjectHolder("sleeplesslib:leaves")
    public static final Item LEAF_ITEM = Items.AIR;

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {}

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {}

    @SubscribeEvent
    public static void onBlockRegistry(BlockRegistryEvent event) {
        event.register(new SimpleLeavesBlock() {
            @Override
            public ItemStack getSapling(IBlockState state, IBlockAccess world, BlockPos pos) {
                return ItemStack.EMPTY;
            }
        }, "leaves");
    }

    @SubscribeEvent
    public static void onItemRegistry(ItemRegistryEvent event) {
        event.register(new ItemBlock(LEAF_BLOCK), "leaves");
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onModelRegistry(ModelRegistryEvent event) {
        event.register(LEAF_ITEM, "normal");
        event.register(LEAF_BLOCK, new StateMap.Builder().ignore(BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE).build());
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onColorRegistry(ColorRegistryEvent event) {
        event.register(Colors.BIOME_COLOR, LEAF_BLOCK, LEAF_ITEM);
    }

}
