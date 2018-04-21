package net.sleeplessdev.lib.item;

import com.google.common.base.Equivalence;
import net.minecraft.item.ItemStack;

import java.util.Objects;

public final class ItemStacks {

    private static final Equivalence<ItemStack> EQUIVALENCE = new Equivalence<ItemStack>() {
        @Override
        protected boolean doEquivalent(ItemStack left, ItemStack right) {
            if (left.isEmpty() && right.isEmpty()) {
                return true;
            }
            if (left.getItem() != right.getItem()) {
                return false;
            }
            if (left.getHasSubtypes() && left.getMetadata() != right.getMetadata()) {
                return false;
            }
            return Objects.equals(left.getTagCompound(), right.getTagCompound());
        }

        @Override
        protected int doHash(ItemStack stack) {
            if (stack.isEmpty()) return 0;
            int hash = stack.getItem().hashCode();
            hash = (31 * hash) + (stack.getHasSubtypes() ? stack.getMetadata() : 0);
            hash = (31 * hash) + Objects.hashCode(stack.getTagCompound());
            return hash;
        }
    };

    private ItemStacks() {}

    public static Equivalence<ItemStack> equivalence() {
        return EQUIVALENCE;
    }

    public static int hashCode(ItemStack stack) {
        return EQUIVALENCE.hash(stack);
    }

    public static boolean areEquivalent(ItemStack left, ItemStack right) {
        return EQUIVALENCE.equivalent(left, right);
    }

}
