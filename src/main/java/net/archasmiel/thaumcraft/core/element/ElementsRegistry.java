package net.archasmiel.thaumcraft.core.element;

import net.archasmiel.thaumcraft.Thaumcraft;
import net.archasmiel.thaumcraft.util.IResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class ElementsRegistry {
    public static final ResourceLocation ELEMENTS_KEY_PATH = IResourceLocation.create("elements");
    public static final ResourceKey<Registry<MagicElement>> ELEMENTS_KEY = ResourceKey.createRegistryKey(ELEMENTS_KEY_PATH);
    public static final Registry<MagicElement> REGISTRY_ELEMENTS = new RegistryBuilder<>(ELEMENTS_KEY).sync(true).create();

    public static final DeferredRegister<MagicElement> elements = DeferredRegister.create(REGISTRY_ELEMENTS, Thaumcraft.MODID);

    public static MagicElement register(String name, MagicElement element) {
        elements.register(name, () -> element);
        return element;
    }

    public static MagicElement register(String modId, String name, MagicElement element) {
        DeferredRegister.create(REGISTRY_ELEMENTS, modId).register(name, () -> element);
        return element;
    }

    public static MagicElement getMagicElement(ResourceLocation resourceLocation) {
        MagicElement element = REGISTRY_ELEMENTS.get(resourceLocation);
        if (element == null) {
            throw new IllegalArgumentException("Element not found in registry: " + resourceLocation);
        }
        return element;
    }

    public static MagicElement getMagicElement(String resourceLocation) {
        MagicElement element = REGISTRY_ELEMENTS.get(ResourceLocation.parse(resourceLocation));
        if (element == null) {
            throw new IllegalArgumentException("Element not found in registry: " + resourceLocation);
        }
        return element;
    }

}
