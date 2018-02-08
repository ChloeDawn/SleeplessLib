package net.sleeplessdev.lib.internal.event;

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
import net.sleeplessdev.lib.client.render.CustomSelectionBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mod.EventBusSubscriber(modid = SleeplessLib.ID)
final class RenderEvents {

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    protected static void onDrawBlockHighlight(DrawBlockHighlightEvent event) {
        if (event.getTarget() != null && event.getTarget().typeOfHit == RayTraceResult.Type.BLOCK) {
            World world = event.getPlayer().world;
            BlockPos pos = event.getTarget().getBlockPos();
            IBlockState state = world.getBlockState(pos).getActualState(world, pos);

            if (state.getBlock() instanceof CustomSelectionBox) {
                CustomSelectionBox icsb = ((CustomSelectionBox) state.getBlock());
                EntityPlayer player = event.getPlayer();

                List<AxisAlignedBB> boxes = new ArrayList<>();
                AxisAlignedBB entityBox = player.getEntityBoundingBox().grow(6.0D);
                state.addCollisionBoxToList(world, pos, entityBox, boxes, player, true);

                if (boxes.isEmpty()) return;
                if (icsb.getRenderType(state, world, pos) == CustomSelectionBox.RenderType.UNIFIED) {
                    AxisAlignedBB actualBox = icsb.getMinimumRange(state, world, pos).offset(pos);
                    for (AxisAlignedBB box : boxes) actualBox = actualBox.union(box);
                    boxes = Collections.singletonList(actualBox);
                }

                GlStateManager.disableAlpha();
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(
                        GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                        GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO
                );
                GlStateManager.glLineWidth(2.0F);
                GlStateManager.disableTexture2D();
                GlStateManager.depthMask(false);
                double offsetX = player.lastTickPosX + (player.posX - player.lastTickPosX) * event.getPartialTicks();
                double offsetY = player.lastTickPosY + (player.posY - player.lastTickPosY) * event.getPartialTicks();
                double offsetZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * event.getPartialTicks();

                for (AxisAlignedBB box : boxes) {
                    AxisAlignedBB target = box.grow(0.002D).offset(-offsetX, -offsetY, -offsetZ);
                    RenderGlobal.drawSelectionBoundingBox(target, 0.0F, 0.0F, 0.0F, 0.4F);
                }

                GlStateManager.depthMask(true);
                GlStateManager.enableTexture2D();
                GlStateManager.disableBlend();
                GlStateManager.enableAlpha();
                event.setCanceled(true);
            }
        }
    }

}
