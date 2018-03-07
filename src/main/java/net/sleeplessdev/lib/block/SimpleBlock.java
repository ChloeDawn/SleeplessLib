package net.sleeplessdev.lib.block;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.sleeplessdev.lib.base.BlockMaterial;
import net.sleeplessdev.lib.base.ColorVariant;
import net.sleeplessdev.lib.base.ModContainers;
import net.sleeplessdev.lib.math.RayTracing;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class SimpleBlock extends Block {

    private boolean fullBlock = true;
    private boolean opaqueBlock = true;

    public SimpleBlock(BlockMaterial material, ColorVariant color) {
        super(material.getMaterial(), color.getMapColor());
        setHardness(material.getHardness());
        setResistance(material.getResistance());
        setSoundType(material.getSound());
        lightOpacity = fullBlock ? 255 : 0;
    }

    public SimpleBlock(BlockMaterial material) {
        super(material.getMaterial(), material.getMaterial().getMaterialMapColor());
        setHardness(material.getHardness());
        setResistance(material.getResistance());
        setSoundType(material.getSound());
        lightOpacity = fullBlock ? 255 : 0;
    }

    public final void setFullBlock(boolean fullBlock) {
        this.fullBlock = fullBlock;
    }

    public final void setOpaqueBlock(boolean opaqueBlock) {
        this.opaqueBlock = opaqueBlock;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Override
    @Deprecated
    public boolean isFullCube(IBlockState state) {
        return fullBlock;
    }

    @Override
    @Deprecated
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return fullBlock ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
    }

    @Override
    @Deprecated
    public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entity, boolean isActualState) {
        List<AxisAlignedBB> boxes = new ArrayList<>();
        getCollisionBoxes(state, world, pos, boxes);
        for (AxisAlignedBB box : boxes) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, box);
        }
    }

    @Override
    @Deprecated
    public boolean isOpaqueCube(IBlockState state) {
        return opaqueBlock;
    }

    @Override
    @Deprecated
    @Nullable
    public RayTraceResult collisionRayTrace(IBlockState state, World world, BlockPos pos, Vec3d start, Vec3d end) {
        List<AxisAlignedBB> boxes = new ArrayList<>();
        getCollisionBoxes(state, world, pos, boxes);

        if (boxes.size() <= 1) {
            AxisAlignedBB box = !boxes.isEmpty() ? boxes.get(0) : Block.FULL_BLOCK_AABB;
            return rayTrace(pos, start, end, box);
        }

        return RayTracing.rayTraceMultiAABB(boxes, pos, start, end).orElse(null);
    }

    @Override
    public final Block setUnlocalizedName(String name) {
        return super.setUnlocalizedName(ModContainers.getActiveModId() + "." + name);
    }

    @Override // TODO Remove in 1.13
    public String getUnlocalizedName() {
        return super.getUnlocalizedName().replace("tile.", "block.");
    }

    public void getCollisionBoxes(IBlockState state, IBlockAccess world, BlockPos pos, List<AxisAlignedBB> boxes) {
        boxes.add(state.getBoundingBox(world, pos));
    }

}
