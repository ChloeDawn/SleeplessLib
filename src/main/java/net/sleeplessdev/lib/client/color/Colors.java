package net.sleeplessdev.lib.client.color;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.sleeplessdev.lib.client.Client;

@SideOnly(Side.CLIENT)
public final class Colors {

    public static final ColorProvider BIOME_COLOR = new ColorProvider() {
        @Override
        public int getColor(IBlockState state, IBlockAccess world, BlockPos pos, int tintIndex) {
            return BiomeColorHelper.getFoliageColorAtPos(world, pos);
        }

        @Override
        public int getColor(ItemStack stack, int tintIndex) {
            return Client.getPlayer()
                    .map(player -> {
                        World world = player.world;
                        BlockPos pos = new BlockPos(player);
                        return BiomeColorHelper.getFoliageColorAtPos(world, pos);
                    }).orElse(getColor(tintIndex));
        }

        @Override
        public int getColor(int tintIndex) {
            return ColorizerFoliage.getFoliageColorBasic();
        }
    };

    private Colors() {}

}

