package net.sleeplessdev.lib.client.color;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nullable;

public interface ColorProvider extends IBlockColor, IItemColor {

    @Override
    default int colorMultiplier(IBlockState state, @Nullable IBlockAccess world, @Nullable BlockPos pos, int tintIndex) {
        return getColorValue();
    }

    @Override
    default int colorMultiplier(ItemStack stack, int tintIndex) {
        return getColorValue();
    }

    default int getColorValue() {
        return 0;
    }

}
