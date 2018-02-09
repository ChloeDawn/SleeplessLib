package net.sleeplessdev.lib.client.render;

import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.util.EnumFacing;

public interface CardinalSelectionBox extends ExtendedSelectionBox {

    PropertyEnum<EnumFacing> getFacingProperty();

}
