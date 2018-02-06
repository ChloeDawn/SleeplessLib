package net.sleeplessdev.lib.client.color;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.sleeplessdev.lib.client.Client;

@SideOnly(Side.CLIENT)
public final class Colors {

    public static final ColorProvider BIOME_COLOR = new ColorProvider() {
        @Override
        public int colorMultiplier(IBlockState state, IBlockAccess world, BlockPos pos, int tintIndex) {
            if (world != null && pos != null) {
                return BiomeColorHelper.getFoliageColorAtPos(world, pos);
            }
            return ColorizerGrass.getGrassColor(0.5D, 1.0D);
        }

        @Override
        public int colorMultiplier(ItemStack stack, int tintIndex) {
            return Client.getPlayer()
                    .map(p -> BiomeColorHelper.getFoliageColorAtPos(p.world, new BlockPos(p)))
                    .orElse(ColorizerGrass.getGrassColor(0.5D, 1.0D));
        }
    };

    private Colors() {}

}

