package net.ssorangecaty.elementreborn.element;


import net.ssorangecaty.elementreborn.ElementReborn;
import net.ssorangecaty.elementreborn.core.element.ElementsRegistry;
import net.ssorangecaty.elementreborn.core.element.MagicElement;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ERMagicElements {
    public static final DeferredRegister<MagicElement> ELEMENTS = DeferredRegister.create(ElementsRegistry.REGISTRY_ELEMENTS, ElementReborn.MODID);

    public static final MagicElement WIND = register("wind", new MagicElement("wind", 16777086, null,1));
    public static final MagicElement EARTH = register("earth", new MagicElement("earth", 5685248, null,1));
    public static final MagicElement FIRE = register("fire", new MagicElement("fire", 16734721, null,1));
    public static final MagicElement WATER = register("water", new MagicElement("water", 3986684, null,1));
    public static final MagicElement ORDER = register("order", new MagicElement("order", 14013676, null,1,EARTH,WIND));
    public static final MagicElement CHAOS = register("chaos", new MagicElement("chaos", 4210752, null,771,WATER,FIRE));

    public static final MagicElement[] DEFAULT_ELEMENTS = new MagicElement[]{WIND, EARTH, FIRE, WATER, ORDER, CHAOS};

    public static MagicElement register(String name, MagicElement element) {
        ELEMENTS.register(name, () -> element);
        return element;
    }

    public static void registerElements() {
    }
}
