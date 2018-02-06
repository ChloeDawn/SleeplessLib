package net.sleeplessdev.lib.client.color;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nullable;

public abstract class ColorProvider implements IBlockColor, IItemColor {

    @Override
    public final int colorMultiplier(IBlockState state, @Nullable IBlockAccess world, @Nullable BlockPos pos, int tintIndex) {
        return world != null && pos != null ? getColor(state, world, pos, tintIndex) : getColor(tintIndex);
    }

    @Override
    public final int colorMultiplier(ItemStack stack, int tintIndex) {
        return !stack.isEmpty() ? getColor(stack, tintIndex) : getColor(tintIndex);
    }

    public abstract int getColor(IBlockState state, IBlockAccess world, BlockPos pos, int tintIndex);

    public abstract int getColor(ItemStack stack, int tintIndex);

    public abstract int getColor(int tintIndex);

}
