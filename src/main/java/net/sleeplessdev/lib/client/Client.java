package net.sleeplessdev.lib.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Optional;
import java.util.UUID;

@SideOnly(Side.CLIENT)
public final class Client {

    private Client() {}

    public static Minecraft getInstance() {
        return FMLClientHandler.instance().getClient();
    }

    public static Optional<EntityPlayer> getPlayer() {
        return Optional.ofNullable(getInstance().player);
    }

    public static Optional<World> getWorld() {
        return  Optional.ofNullable(getInstance().world);
    }

    public static RayTraceResult getMouseOver() {
        RayTraceResult result = getInstance().objectMouseOver;
        if (result != null) return result;
        return new RayTraceResult(RayTraceResult.Type.MISS, Vec3d.ZERO, EnumFacing.UP, BlockPos.ORIGIN);
    }

    public static UUID getPlayerUUID() {
        return getInstance().getSession().getProfile().getId();
    }

    public static boolean isFancyGraphics() {
        return Blocks.LEAVES.getBlockLayer() != BlockRenderLayer.SOLID;
    }

    public static BlockColors getBlockColors() {
        return Client.getInstance().getBlockColors();
    }

    public static ItemColors getItemColors() {
        return Client.getInstance().getItemColors();
    }

}
