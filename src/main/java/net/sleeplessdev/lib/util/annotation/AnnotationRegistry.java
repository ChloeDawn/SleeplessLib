package net.sleeplessdev.lib.util.annotation;

import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public final class AnnotationRegistry {

    private static final Map<Class<?>, Set<ASMDataTable.ASMData>> ASM_DATA_MAP = new LinkedHashMap<>();

    private AnnotationRegistry() {}

    public static void collectData(FMLPreInitializationEvent event) {
        ASMDataTable data = event.getAsmData();
        ASM_DATA_MAP.put(TabHolder.class, data.getAll(TabHolder.class.getName()));
    }

}
