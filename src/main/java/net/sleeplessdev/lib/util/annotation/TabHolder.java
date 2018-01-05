package net.sleeplessdev.lib.util.annotation;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;

/**
 * Apply this to a {@link CreativeTabs} field instance to have all registered items and blocks inserted into it.
 * Insertion is only applicable to items and blocks that have no pre-set creative tab.
 */
public @interface TabHolder {

    /**
     * Modid is required if this annotation is not used in an {@link Mod} annotated class.
     * @return The owner modid of this creative tab.
     */
    String modid() default "";

}
