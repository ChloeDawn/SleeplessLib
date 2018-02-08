package net.sleeplessdev.lib.client.render;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;

/**
 * This interface provides custom selection box rendering for a block, determined by the passed type.
 * It is designed to be implemented on an {@link Block} class and will not function anywhere else.
 * Note that whilst this interface is implemented on your block, the only rendered selection boxes
 * will be the ones passed in {@link Block#addCollisionBoxToList}. Rendering of other selection boxes
 * is cancelled on {@link DrawBlockHighlightEvent}
 */
public interface CustomSelectionBox {

    /**
     * Determines how the selection box should be rendered.
     * {@link RenderType#UNIFIED} will unify all collision bounding boxes into a single box.
     * {@link RenderType#MULTI} will render all individual collision bounding boxes.
     * @param state The actual state of the block.
     * @param world The current world the block is in.
     * @param pos   The current position of the block.
     * @return The render type to be used.
     */
    RenderType getRenderType(IBlockState state, IBlockAccess world, BlockPos pos);

    /**
     * Determines the minimum bounds of the selection box for {@link RenderType#UNIFIED}
     * The rendered bounding box expands around this as it collects additional bounding boxes.
     * @param state The actual state of the block.
     * @param world The current world the block is in.
     * @param pos   The current position of the block.
     * @return The minimum range for the selection box.
     */
    default AxisAlignedBB getMinimumRange(IBlockState state, IBlockAccess world, BlockPos pos) {
        return new AxisAlignedBB(0.5D, 0.5D, 0.5D, 0.5D, 0.5D, 0.5D);
    }

    enum RenderType {
        UNIFIED,
        MULTI
    }

}
