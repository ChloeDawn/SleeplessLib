package net.sleeplessdev.lib.event;

import com.google.common.base.Equivalence;
import com.google.common.base.Equivalence.Wrapper;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.IContextSetter;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Collection;
import java.util.Map.Entry;

public final class OreRegistryEvent extends Event implements IContextSetter {

    private static final Equivalence<ItemStack> STACK_EQV = new Equivalence<ItemStack>() {
        @Override
        protected boolean doEquivalent(ItemStack a, ItemStack b) {
            return ItemStack.areItemStackShareTagsEqual(a, b);
        }

        @Override
        @SuppressWarnings("ConstantConditions")
        protected int doHash(ItemStack stack) {
            int result = stack.getItem().getRegistryName().hashCode();
            result = 31 * result + stack.getItemDamage();
            if (stack.hasTagCompound()) {
                NBTTagCompound nbt = stack.getTagCompound();
                result = 31 * result + nbt.hashCode();
            }
            return result;
        }
    };

    private final Multimap<Wrapper<ItemStack>, String> ores = ArrayListMultimap.create();

    protected OreRegistryEvent() {}

    protected void construct() {
        for (Entry<Wrapper<ItemStack>, Collection<String>> entry : ores.asMap().entrySet()) {
            ItemStack stack = entry.getKey().get();
            if (stack != null && !stack.isEmpty()) {
                for (String ore : entry.getValue()) {
                    OreDictionary.registerOre(ore, stack);
                }
            }
        }
    }

    private void register(Wrapper<ItemStack> wrapped, String ore) {
        if (!ores.containsEntry(wrapped, ore)) {
            ores.put(wrapped, ore);
        }
    }

    public void register(ItemStack stack, String ore) {
        register(STACK_EQV.wrap(stack), ore);
    }

    public void registerAll(ItemStack stack, String... ores) {
        for (String ore : ores) {
            register(STACK_EQV.wrap(stack), ore);
        }
    }

    public void registerAll(ItemStack stack, Collection<String> ores) {
        for (String ore : ores) {
            register(STACK_EQV.wrap(stack), ore);
        }
    }

}
