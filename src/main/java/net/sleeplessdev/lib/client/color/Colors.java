package net.sleeplessdev.lib.client.color;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerGrass;
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
        public int colorMultiplier(IBlockState state, IBlockAccess world, BlockPos pos, int tintIndex) {
            return getBiomeColor(world, pos);
        }

        @Override
        public int colorMultiplier(ItemStack stack, int tintIndex) {
            return getBiomeColor();
        }
    };

    private Colors() {}

    public static BlockColors getBlockColors() {
        return Client.getInstance().getBlockColors();
    }

    public static ItemColors getItemColors() {
        return Client.getInstance().getItemColors();
    }

    public static int getBiomeColor(IBlockAccess world, BlockPos pos) {
        if (world != null && pos != null) {
            return BiomeColorHelper.getGrassColorAtPos(world, pos);
        }
        return ColorizerGrass.getGrassColor(0.5D, 1.0D);
    }

    public static int getBiomeColor() {
        return Client.getPlayer()
                .map(player -> {
                    World world = player.world;
                    BlockPos pos = new BlockPos(player);
                    return getBiomeColor(world, pos);
                }).orElse(ColorizerGrass.getGrassColor(0.5D, 1.0D));
    }

}

