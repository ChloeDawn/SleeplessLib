package net.sleeplessdev.lib.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.sleeplessdev.lib.base.BlockMaterial;
import net.sleeplessdev.lib.base.ColorVariant;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class SimpleConnectingBlock extends SimpleBlock {

    public static final ImmutableMap<EnumFacing, PropertyBool> SIDES = Arrays.stream(EnumFacing.values())
            .collect(Maps.toImmutableEnumMap(Function.identity(), f -> PropertyBool.create(f.getName())));

    private final Predicate<EnumFacing> sidePredicate;

    public SimpleConnectingBlock(BlockMaterial material, ColorVariant color, Predicate<EnumFacing> sidePredicate) {
        super(material, color);
        this.sidePredicate = sidePredicate;
    }

    public SimpleConnectingBlock(BlockMaterial material, Predicate<EnumFacing> sidePredicate) {
        super(material);
        this.sidePredicate = sidePredicate;
    }

    public SimpleConnectingBlock(BlockMaterial material, ColorVariant color) {
        super(material, color);
        this.sidePredicate = s -> true;
    }

    public SimpleConnectingBlock(BlockMaterial material) {
        super(material);
        this.sidePredicate = s -> true;
    }

    protected abstract boolean canConnectTo(EnumFacing side, IBlockAccess world, BlockPos pos);

    @Override
    @Deprecated
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos origin) {
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(origin);
        for (EnumFacing side : EnumFacing.VALUES) {
            if (!sidePredicate.test(side)) continue;
            pos.move(side);
            PropertyBool key = SIDES.get(side);
            boolean value = canConnectTo(side, world, pos);
            state = state.withProperty(key, value);
            pos.move(side.getOpposite());
        }
        return super.getActualState(state, world, origin);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        BlockStateContainer.Builder builder = new BlockStateContainer.Builder(this);
        for (EnumFacing side : EnumFacing.VALUES) {
            if (!sidePredicate.test(side)) continue;
            builder.add(SIDES.get(side));
        }
        return builder.build();
    }

}
