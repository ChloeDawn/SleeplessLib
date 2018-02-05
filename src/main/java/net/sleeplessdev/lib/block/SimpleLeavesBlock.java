package net.sleeplessdev.lib.block;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.sleeplessdev.lib.base.BlockMaterial;
import net.sleeplessdev.lib.base.ColorVariant;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class SimpleLeavesBlock extends BlockLeaves {

    private final Material material;
    private final MapColor mapColor;

    public SimpleLeavesBlock(BlockMaterial material, ColorVariant color) {
        this.material = material.getMaterial();
        this.mapColor = color.getMapColor();
        setHardness(material.getHardness());
        setResistance(material.getResistance());
        setSoundType(material.getSound());
    }

    public SimpleLeavesBlock(BlockMaterial material) {
        this.material = material.getMaterial();
        this.mapColor = this.material.getMaterialMapColor();
        setHardness(material.getHardness());
        setResistance(material.getResistance());
        setSoundType(material.getSound());
    }

    public SimpleLeavesBlock() {
        this(BlockMaterial.LEAVES);
    }

    public abstract ItemStack getSapling(IBlockState state, IBlockAccess world, BlockPos pos);

    @Override
    @Deprecated
    public Material getMaterial(IBlockState state) {
        return material;
    }

    @Override
    @Deprecated
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return mapColor;
    }

    @Override
    @Deprecated
    public IBlockState getStateFromMeta(int meta) {
        boolean checkDecay = (meta & 1) == 0;
        boolean decayable = (meta >> 1) == 0;
        return getDefaultState()
                .withProperty(CHECK_DECAY, checkDecay)
                .withProperty(DECAYABLE, decayable);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int checkDecay = (state.getValue(CHECK_DECAY) ? 1 : 0) & 1;
        int decayable = (state.getValue(DECAYABLE) ? 1 : 0) << 1;
        return checkDecay | decayable;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(this);
    }

    @Override
    public final BlockPlanks.EnumType getWoodType(int meta) {
        return BlockPlanks.EnumType.OAK;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        Random rand = world instanceof World ? ((World) world).rand : new Random();
        int chance = getSaplingDropChance(state);

        if (fortune > 0) {
            chance -= 2 << fortune;
            if (chance < 10) chance = 10;
        }

        if (rand.nextInt(chance) == 0) {
            ItemStack sapling = getSapling(state, world, pos);
            if (!sapling.isEmpty()) drops.add(sapling);
        }

        chance = 200;
        if (fortune > 0) {
            chance -= 10 << fortune;
            if (chance < 40) chance = 40;
        }

        captureDrops(true);
        if (world instanceof World) {
            dropApple((World) world, pos, state, chance);
        }
        drops.addAll(captureDrops(false));
    }

    @Override
    public List<ItemStack> onSheared(ItemStack stack, IBlockAccess world, BlockPos pos, int fortune) {
        return Collections.singletonList(new ItemStack(this));
    }

}
