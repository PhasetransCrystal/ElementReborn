package net.archasmiel.thaumcraft.item.gen.base;

import net.archasmiel.thaumcraft.core.element.MagicElement;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TCItemProperties {
    public final String name;
    public final String modid;
    public final List<Component> tooltip = new ArrayList<>();
    public final Map<MagicElement,Integer> scanningElements = new HashMap<>();

    public TCItemProperties(String name, String modid) {
        this.name = name;
        this.modid = modid;
    }

    public TCItemProperties(String name) {
        this(name, "thaumcraft");
    }

}


