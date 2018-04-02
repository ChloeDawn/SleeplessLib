package net.sleeplessdev.lib.item;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.sleeplessdev.lib.block.SimpleSlabBlock;

public class SimpleSlabItem extends ItemBlock {

    private final SimpleSlabBlock slab;

    public SimpleSlabItem(Block slab) {
        super(slab);
        if (slab instanceof SimpleSlabBlock) {
            this.slab = (SimpleSlabBlock) slab;
        } else throw new IllegalStateException(
                "SimpleSlabItem cannot be applied to "
                + slab.getClass().getCanonicalName()
        );
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && player.canPlayerEdit(pos.offset(facing), facing, stack)) {
            if (tryPlace(player, stack, world, pos, facing)) {
                return EnumActionResult.SUCCESS;
            }
            if (tryPlace(player, stack, world, pos.offset(facing), null)) {
                return EnumActionResult.SUCCESS;
            }
            return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
        }
        return EnumActionResult.FAIL;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack) {
        IBlockState state = world.getBlockState(pos);
        return state.getBlock() == slab
                && (side == EnumFacing.UP && slab.isLower(state)
                || side == EnumFacing.DOWN && slab.isUpper(state))
                || world.getBlockState(pos.offset(side)).getBlock() == this.slab
                || super.canPlaceBlockOnSide(world, pos, side, player, stack);
    }

    private boolean tryPlace(EntityPlayer player, ItemStack stack, World world, BlockPos pos, EnumFacing side) {
        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() != slab) return false;
        if (slab.isDouble(state)) return false;
        if (side == null || side == EnumFacing.UP && slab.isLower(state) || side == EnumFacing.DOWN && slab.isUpper(state)) {
            IBlockState dSlab = slab.getDouble();
            AxisAlignedBB aabb = dSlab.getCollisionBoundingBox(world, pos);
            if (aabb == null) return false;
            if (world.checkNoEntityCollision(aabb.offset(pos)) && world.setBlockState(pos, dSlab)) {
                SoundType sound = dSlab.getBlock().getSoundType(dSlab, world, pos, player);
                world.playSound(player, pos, sound.getPlaceSound(), SoundCategory.BLOCKS,
                        (sound.getVolume() + 1.0F) / 2.0F, sound.getPitch() * 0.8F);
                stack.shrink(1);
            }
            return true;
        }
        return false;
    }

}
