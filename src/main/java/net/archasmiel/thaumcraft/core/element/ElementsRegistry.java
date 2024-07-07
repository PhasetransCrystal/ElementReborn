package net.archasmiel.thaumcraft.core.element;

import net.archasmiel.thaumcraft.util.IResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

import javax.annotation.Nullable;
import java.util.List;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class ElementsRegistry {
    public static final ResourceLocation ELEMENTS_KEY_PATH = IResourceLocation.create("elements");
    public static final ResourceKey<Registry<MagicElement>> ELEMENTS_KEY = ResourceKey.createRegistryKey(ELEMENTS_KEY_PATH);
    public static final Registry<MagicElement> REGISTRY_ELEMENTS = new RegistryBuilder<>(ELEMENTS_KEY).sync(true).create();


    @Nullable
    public static MagicElement getElement(ResourceLocation resourceLocation) {
        return REGISTRY_ELEMENTS.get(resourceLocation);
    }

    @Nullable
    public static MagicElement getElement(String resourceLocation) {
        return REGISTRY_ELEMENTS.get(ResourceLocation.parse(resourceLocation));
    }

    @Nullable
    public static MagicElement getElementByName(String name) {
        return REGISTRY_ELEMENTS.stream().filter((magicElement -> magicElement.equalsName(name))).findFirst().orElse(null);
    }

    public static List<MagicElement> getPrimalElements() {
        return REGISTRY_ELEMENTS.stream().filter(MagicElement::isPrimal).toList();
    }

    public static List<MagicElement> getCompoundElements() {
        return REGISTRY_ELEMENTS.stream().filter((MagicElement::isCompound)).toList();
    }

    @SubscribeEvent
    public static void registerRegistry(NewRegistryEvent event) {
        event.register(REGISTRY_ELEMENTS);
    }

}
