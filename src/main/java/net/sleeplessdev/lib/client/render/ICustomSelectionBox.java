package net.sleeplessdev.lib.client.render;

import net.minecraft.block.Block;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This interface provides methods for passing bounding boxes and the render type of the block's selection box.
 * It is designed to be implemented on an {@link Block} class and will not function anywhere else.
 * Note that whilst this interface is implemented on your block, the only rendered selection boxes will be
 * the ones passed in {@link ICustomSelectionBox#getBoundingBoxes(List, IBlockState, World, BlockPos)}.
 * Rendering of other selection boxes are cancelled on {@link DrawBlockHighlightEvent} by the interface.
 */
@Mod.EventBusSubscriber(modid = SleeplessLib.ID)
public interface ICustomSelectionBox {

    /**
     * Collects all bounding boxes to the passed {@link List<AxisAlignedBB>} for rendering.
     * This method will be removed once I find a way to handle this directly through the state collision boxes.
     * @param boxes The list of bounding boxes.
     * @param state The actual state of the block.
     * @param world The current world the block is in.
     * @param pos   The current position of the block.
     */
    void getBoundingBoxes(List<AxisAlignedBB> boxes, IBlockState state, World world, BlockPos pos);

    /**
     * Determines how the selection box should be rendered.
     * {@link SelectionRenderType#SINGLE} will merge all bounding boxes into one unified bounding box.
     * {@link SelectionRenderType#MULTI} will render all individual bounding boxes.
     * @param state The actual state of the block.
     * @param world The current world the block is in.
     * @param pos   The current position of the block.
     * @return The render type to be used.
     */
    SelectionRenderType getRenderType(IBlockState state, World world, BlockPos pos);

    /**
     * Determines the minimum bounds of the selection box for {@link SelectionRenderType#SINGLE}
     * The rendered bounding box expands around this as it collects additional bounding boxes.
     * @param state The actual state of the block.
     * @param world The current world the block is in.
     * @param pos   The current position of the block.
     * @return The minimum range for the selection box.
     */
    AxisAlignedBB getMinimumRange(IBlockState state, World world, BlockPos pos);

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    static void onDrawBlockHighlight(DrawBlockHighlightEvent event) {
        if (event.getTarget() != null && event.getTarget().typeOfHit == RayTraceResult.Type.BLOCK) {
            World world = event.getPlayer().world;
            BlockPos pos = event.getTarget().getBlockPos();
            IBlockState state = world.getBlockState(pos).getActualState(world, pos);
            if (state.getBlock() instanceof ICustomSelectionBox) {
                ICustomSelectionBox iscb = ((ICustomSelectionBox) state.getBlock());
                EntityPlayer player = event.getPlayer();
                SelectionRenderType type = iscb.getRenderType(state, world, pos);
                float pTicks = event.getPartialTicks();
                GlStateManager.disableAlpha();
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(
                        GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                        GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO
                );
                GlStateManager.glLineWidth(2.0F);
                GlStateManager.disableTexture2D();
                GlStateManager.depthMask(false);
                double offsetX = player.lastTickPosX + (player.posX - player.lastTickPosX) * pTicks;
                double offsetY = player.lastTickPosY + (player.posY - player.lastTickPosY) * pTicks;
                double offsetZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * pTicks;
                for (AxisAlignedBB box : type.getBoxesFor(iscb, state, world, pos)) {
                    AxisAlignedBB target = box.grow(0.002D).offset(pos).offset(-offsetX, -offsetY, -offsetZ);
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

    enum SelectionRenderType {

        SINGLE {
            @Override
            protected List<AxisAlignedBB> getBoxesFor(ICustomSelectionBox icsb, IBlockState state, World world, BlockPos pos) {
                if (!Cache.SINGLE.containsKey(state)) {
                    List<AxisAlignedBB> boxes = new ArrayList<>();
                    icsb.getBoundingBoxes(boxes, state, world, pos);
                    AxisAlignedBB actualBox = icsb.getMinimumRange(state, world, pos);
                    for (AxisAlignedBB box : boxes) actualBox = actualBox.union(box);
                    Cache.SINGLE.put(state, actualBox);
                }
                return Collections.singletonList(Cache.SINGLE.get(state));
            }
        },

        MULTI {
            @Override
            protected List<AxisAlignedBB> getBoxesFor(ICustomSelectionBox icsb, IBlockState state, World world, BlockPos pos) {
                if (!Cache.MULTI.containsKey(state)) {
                    List<AxisAlignedBB> boxes = new ArrayList<>();
                    icsb.getBoundingBoxes(boxes, state, world, pos);
                    Cache.MULTI.put(state, boxes);
                }
                return Cache.MULTI.get(state);
            }
        };

        protected abstract List<AxisAlignedBB> getBoxesFor(ICustomSelectionBox icsb, IBlockState state, World world, BlockPos pos);
    }

    final class Cache {
        private static final Map<IBlockState, AxisAlignedBB> SINGLE = new HashMap<>();
        private static final Map<IBlockState, List<AxisAlignedBB>> MULTI = new HashMap<>();

        private Cache() {}
    }

}
