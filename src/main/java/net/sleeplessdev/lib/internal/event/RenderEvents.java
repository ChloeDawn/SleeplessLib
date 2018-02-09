package net.sleeplessdev.lib.internal.event;

import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.sleeplessdev.lib.SleeplessLib;
import net.sleeplessdev.lib.base.OrdinalFacing;
import net.sleeplessdev.lib.client.render.ExtendedBoundingBox;
import net.sleeplessdev.lib.client.render.OrientableBoundingBox;
import net.sleeplessdev.lib.math.BoundingBox;

import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = SleeplessLib.ID, value = Side.CLIENT)
final class RenderEvents {

    private RenderEvents() {}

    @SubscribeEvent
    protected static void onDrawBlockHighlight(DrawBlockHighlightEvent event) {
        if (event.getTarget() == null) return;
        if (event.getTarget().typeOfHit != RayTraceResult.Type.BLOCK) return;

        EntityPlayer player = event.getPlayer();
        World world = player.world;
        BlockPos pos = event.getTarget().getBlockPos();
        IBlockState state = world.getBlockState(pos);

        if (!(state.getBlock() instanceof ExtendedBoundingBox)) return;

        state = state.getActualState(world, pos);

        ExtendedBoundingBox iface = (ExtendedBoundingBox) state.getBlock();

        List<AxisAlignedBB> boxes = new ArrayList<>();

        double offsetX = player.lastTickPosX + (player.posX - player.lastTickPosX) * event.getPartialTicks();
        double offsetY = player.lastTickPosY + (player.posY - player.lastTickPosY) * event.getPartialTicks();
        double offsetZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * event.getPartialTicks();

        GlStateManager.pushMatrix();
        GlStateManager.disableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(
                GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.glLineWidth(2.0F);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);

        double x = pos.getX() - offsetX;
        double y = pos.getY() - offsetY;
        double z = pos.getZ() - offsetZ;

        GlStateManager.translate(x + 0.5D, y + 0.5D, z + 0.5D);

        if (!(iface instanceof OrientableBoundingBox)) {
            for (BoundingBox box : iface.getBoundingBoxes(state, world, pos)) {
                boxes.add(box.getDefault());
            }
        } else {
            OrientableBoundingBox orientable = (OrientableBoundingBox) iface;
            PropertyEnum<OrdinalFacing> property = orientable.getFacingProperty();
            OrdinalFacing facing = state.getValue(property);

            if (!state.getPropertyKeys().contains(property)) {
                String p = property.toString();
                String s = state.toString();
                throw new IllegalStateException("Could not locate " + p + " in " + s + "!");
            }

            if (!facing.isCardinal()) {
                GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
            }

            for (BoundingBox box : iface.getBoundingBoxes(state, world, pos)) {
                boxes.add(box.get(facing.getCardinal()));
            }
        }

        GlStateManager.translate(-0.5D, -0.5D, -0.5D);

        for (AxisAlignedBB box : boxes) {
            RenderGlobal.drawSelectionBoundingBox(box.grow(0.002D), 0.0F, 0.0F, 0.0F, 0.4F);
        }

        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.glLineWidth(0.2F);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.popMatrix();

        event.setCanceled(true);
    }

}
