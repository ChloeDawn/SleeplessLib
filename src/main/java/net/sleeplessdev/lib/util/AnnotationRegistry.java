package net.sleeplessdev.lib.util;

import com.google.common.collect.SetMultimap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.discovery.ASMDataTable.ASMData;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.sleeplessdev.lib.util.annotation.TabHolder;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public final class AnnotationRegistry {

    private static final Map<ModContainer, Map<String, CreativeTabs>> TABS_MAP = new LinkedHashMap<>();

    private AnnotationRegistry() {}

    @Nullable
    public static CreativeTabs getTabFor(ModContainer container) {
        if (TABS_MAP.containsKey(container)) {
            return TABS_MAP.get(container).values().stream().findFirst().orElse(null);
        }
        return null;
    }

    public static void collectData(FMLPreInitializationEvent event) {
        for (ModContainer container : Loader.instance().getActiveModList()) {
            SetMultimap<String, ASMData> data = event.getAsmData().getAnnotationsFor(container);
            if (data == null) continue;
            if (TABS_MAP.isEmpty() && data.containsKey(TabHolder.class.getCanonicalName())) {
                TABS_MAP.put(container, getAllFor(data, TabHolder.class, CreativeTabs.class));
            }
        }
    }

    private static <T> Map<String, T> getAllFor(SetMultimap<String, ASMData> data, Class<?> target, Class<T> type) {
        Map<String, T> map = new HashMap<>();
        for (ASMData entry : data.get(target.getCanonicalName())) {
            try {
                Class<?> clazz = Class.forName(entry.getClassName());
                Object obj = clazz.getDeclaredField(entry.getObjectName()).get(null);
                if (type.isAssignableFrom(obj.getClass())) {
                    map.put(entry.getObjectName(), type.cast(obj));
                }
            } catch (IllegalAccessException | ClassNotFoundException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

}
