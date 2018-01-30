package net.sleeplessdev.lib.block;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.sleeplessdev.lib.util.BlockMaterial;
import net.sleeplessdev.lib.util.ColorVariant;

import java.util.ArrayList;
import java.util.List;

public class SimpleBlock extends Block {

    public SimpleBlock(BlockMaterial material, ColorVariant color) {
        super(material.getMaterial(), color.getMapColor());
        setHardness(material.getHardness());
        setResistance(material.getResistance());
        setSoundType(material.getSound());
    }

    public SimpleBlock(BlockMaterial material) {
        super(material.getMaterial(), material.getMaterial().getMaterialMapColor());
        setHardness(material.getHardness());
        setResistance(material.getResistance());
        setSoundType(material.getSound());

    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Override
    @Deprecated
    public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> boxes, Entity entity, boolean isActualState) {
        List<AxisAlignedBB> collectedBoxes = new ArrayList<>();
        getCollisionBoxes(collectedBoxes, state, world, pos);
        for (AxisAlignedBB box : collectedBoxes) {
            addCollisionBoxToList(pos, entityBox, boxes, box);
        }
    }

    public void getCollisionBoxes(List<AxisAlignedBB> boxes, IBlockState state, World world, BlockPos pos) {
        boxes.add(state.getBoundingBox(world, pos));
    }

    @Override // TODO Remove in 1.13
    public String getUnlocalizedName() {
        return super.getUnlocalizedName().replace("tile.", "block.");
    }

    @Override
    public BlockStateContainer getBlockState() {
        return super.getBlockState();
    }

}
