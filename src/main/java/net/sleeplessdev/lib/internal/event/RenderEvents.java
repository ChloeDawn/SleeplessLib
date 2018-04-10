package net.sleeplessdev.lib.internal.event;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.sleeplessdev.lib.SleeplessLib;
import net.sleeplessdev.lib.client.render.ExtendedSelectionBox;
import net.sleeplessdev.lib.client.render.OrientableSelectionBox;

import javax.vecmath.Vector3f;
import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = SleeplessLib.ID, value = Side.CLIENT)
final class RenderEvents {

    private RenderEvents() {}

    @SubscribeEvent
    protected static void onDrawBlockHighlight(DrawBlockHighlightEvent event) {
        if (event.getTarget() == null) return;
        if (event.getTarget().typeOfHit != Type.BLOCK) return;

        final EntityPlayer player = event.getPlayer();
        final BlockPos pos = event.getTarget().getBlockPos();
        final IBlockState state = player.world.getBlockState(pos);

        if (!(state.getBlock() instanceof ExtendedSelectionBox)) return;

        event.setCanceled(true);

        final ExtendedSelectionBox iface = (ExtendedSelectionBox) state.getBlock();
        final List<AxisAlignedBB> boxes = new ArrayList<>();
        final Vec3d hitVec = event.getTarget().hitVec.subtract(
                pos.getX(), pos.getY(), pos.getZ()
        );

        iface.getSelectionBoundingBoxes(state, player.world, pos, hitVec, boxes);

        if (boxes.isEmpty()) return;

        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(
                SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA,
                SourceFactor.ONE, DestFactor.ZERO
        );
        GlStateManager.glLineWidth(2.0F);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);

        final float ticks = event.getPartialTicks();
        final double x = pos.getX() - (player.lastTickPosX + ((player.posX - player.lastTickPosX) * ticks));
        final double y = pos.getY() - (player.lastTickPosY + ((player.posY - player.lastTickPosY) * ticks));
        final double z = pos.getZ() - (player.lastTickPosZ + ((player.posZ - player.lastTickPosZ) * ticks));

        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder buffer = tessellator.getBuffer();

        GlStateManager.translate(x + 0.5D, y + 0.5D, z + 0.5D);

        if (iface instanceof OrientableSelectionBox) {
            // TODO Migrate to matrix transformations and pass data into buffer builder
            final OrientableSelectionBox oiface = (OrientableSelectionBox) iface;
            final Vector3f angle = oiface.getRotationAngles(state, player.world, pos);
            GlStateManager.rotate(angle.x, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(angle.y, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(angle.z, 0.0F, 0.0F, 1.0F);
        }

        GlStateManager.translate(-0.5D, -0.5D, -0.5D);

        buffer.begin(3, DefaultVertexFormats.POSITION_COLOR);

        for (AxisAlignedBB box : boxes) {
            final double minX = box.minX - 0.002D;
            final double minY = box.minY - 0.002D;
            final double minZ = box.minZ - 0.002D;
            final double maxX = box.maxX + 0.002D;
            final double maxY = box.maxY + 0.002D;
            final double maxZ = box.maxZ + 0.002D;
            RenderGlobal.drawBoundingBox(buffer, minX, minY, minZ, maxX, maxY, maxZ, 0.0F, 0.0F, 0.0F, 0.4F);
        }

        tessellator.draw();

        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

}
