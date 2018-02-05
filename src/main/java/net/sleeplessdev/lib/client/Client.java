package net.sleeplessdev.lib.client;

import net.minecraft.client.Minecraft;
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

public final class Client {

    private Client() {}

    @SideOnly(Side.CLIENT)
    public static Minecraft getInstance() {
        return FMLClientHandler.instance().getClient();
    }

    @SideOnly(Side.CLIENT)
    public static Optional<EntityPlayer> getPlayer() {
        return Optional.ofNullable(getInstance().player);
    }

    @SideOnly(Side.CLIENT)
    public static Optional<World> getWorld() {
        return  Optional.ofNullable(getInstance().world);
    }

    @SideOnly(Side.CLIENT)
    public static RayTraceResult getMouseOver() {
        RayTraceResult result = getInstance().objectMouseOver;
        if (result != null) return result;
        return new RayTraceResult(RayTraceResult.Type.MISS, Vec3d.ZERO, EnumFacing.UP, BlockPos.ORIGIN);
    }

    @SideOnly(Side.CLIENT)
    public static UUID getPlayerUUID() {
        return getInstance().getSession().getProfile().getId();
    }

    public static boolean isFancyGraphics() {
        return Blocks.LEAVES.getBlockLayer() != BlockRenderLayer.SOLID;
    }

}
