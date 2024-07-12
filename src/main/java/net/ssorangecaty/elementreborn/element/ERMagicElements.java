package net.ssorangecaty.elementreborn.element;


import net.ssorangecaty.elementreborn.ElementReborn;
import net.ssorangecaty.elementreborn.core.element.ElementsRegistry;
import net.ssorangecaty.elementreborn.core.element.MagicElement;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ERMagicElements {
    public static final DeferredRegister<MagicElement> ELEMENTS = DeferredRegister.create(ElementsRegistry.REGISTRY_ELEMENTS, ElementReborn.MODID);

    // 风
    public static final MagicElement WIND = register("wind", new MagicElement("wind", 16777086, 1));
    // 地
    public static final MagicElement EARTH = register("earth", new MagicElement("earth", 5685248, 1));
    // 火
    public static final MagicElement FIRE = register("fire", new MagicElement("fire", 16734721, 1));
    // 水
    public static final MagicElement WATER = register("water", new MagicElement("water", 3986684, 1));
    // 秩序
    public static final MagicElement ORDER = register("order", new MagicElement("order", 14013676, 1));
    // 混沌
    public static final MagicElement CHAOS = register("chaos", new MagicElement("chaos", 4210752, 771));

    public static final MagicElement[] DEFAULT_ELEMENTS = new MagicElement[]{WIND, EARTH, FIRE, WATER, ORDER, CHAOS};

    public static MagicElement register(String name, int color, MagicElement... components) {
        MagicElement element = new MagicElement(name, color, components);
        ELEMENTS.register(name, () -> element);
        return element;
    }

    public static MagicElement register(String name, int color, int blend, MagicElement... components) {
        MagicElement element = new MagicElement(name, color, blend, components);
        ELEMENTS.register(name, () -> element);
        return element;
    }

    public static MagicElement register(String name, MagicElement element) {
        ELEMENTS.register(name, () -> element);
        return element;
    }

    public static void registerElements() {
    }
}
