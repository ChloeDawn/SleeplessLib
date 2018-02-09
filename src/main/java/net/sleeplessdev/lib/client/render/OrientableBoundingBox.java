package net.sleeplessdev.lib.client.render;

import net.minecraft.block.properties.PropertyEnum;
import net.sleeplessdev.lib.base.OrdinalFacing;

public interface OrientableBoundingBox extends ExtendedBoundingBox {

    PropertyEnum<OrdinalFacing> getFacingProperty();

}
