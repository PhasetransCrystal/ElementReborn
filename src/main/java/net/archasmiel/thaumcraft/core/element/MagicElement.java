package net.archasmiel.thaumcraft.core.element;

import net.archasmiel.thaumcraft.Thaumcraft;
import net.archasmiel.thaumcraft.util.IResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import java.util.ArrayList;
import java.util.Collection;

public class MagicElement {
    private String modId = Thaumcraft.MODID;
    private final String name;
    private final ResourceLocation texture;
    private final int blend;
    private final int color;
    private final MagicElement[] components;

    // Auto Registering Constructor
    public MagicElement(String name, int color, MagicElement[] components, ResourceLocation image, int blend) {
        if (ElementsRegistry.elements.getRegistry().get().containsKey(IResourceLocation.create(name))) {
            throw new IllegalArgumentException(name + " already registered!");
        } else {
            this.name = name;
            this.components = components;
            this.color = color;
            this.texture = image;
            this.blend = blend;
            ElementsRegistry.register(name, this);
        }
    }

    public MagicElement(String modId ,String name, int color, MagicElement[] components, ResourceLocation image, int blend) {
        if (ElementsRegistry.elements.getRegistry().get().containsKey(IResourceLocation.create(modId,name))) {
            throw new IllegalArgumentException(name + " already registered!");
        } else {
            this.modId = modId;
            this.name = name;
            this.components = components;
            this.color = color;
            this.texture = image;
            this.blend = blend;
            ElementsRegistry.register(modId, name, this);
        }
    }


    public MagicElement(String name, int color, MagicElement[] components) {
        this(name, color, components, IResourceLocation.create("thaumcraft", "textures/aspects/" + name.toLowerCase() + ".png"), 1);
    }

    public MagicElement(String name, int color, MagicElement[] components, int blend) {
        this(name, color, components, IResourceLocation.create("thaumcraft", "textures/aspects/" + name.toLowerCase() + ".png"), blend);
    }

    public MagicElement(String name, int color, int blend) {
        this(name, color, null, blend);
    }

    public int getColor() {
        return this.color;
    }

    public String getName() {
        return this.name;
    }

    public ResourceLocation getResourceLocation() {
        return IResourceLocation.create(this.modId,this.name);
    }


    public Component getTranslationText() {
        return Component.translatable("tc.aspect." + this.name).withColor(this.color);
    }


    public MagicElement[] getComponents() {
        return this.components;
    }

    public ResourceLocation getTexture() {
        return this.texture;
    }

    public static MagicElement getAspect(ResourceLocation resourceLocation) {
        MagicElement element = ElementsRegistry.elements.getRegistry().get().get(resourceLocation);
        if (element == null) {
            throw new IllegalArgumentException("Unknown aspect: " + resourceLocation + "Because could not find it in registry");
        }
        return element;
    }

    public int getBlend() {
        return this.blend;
    }

    public boolean isPrimal() {
        return this.getComponents() == null || this.getComponents().length != 2;
    }

    public static ArrayList<MagicElement> getPrimalMagicElements() {
        ArrayList<MagicElement> primals = new ArrayList<>();
        Collection<MagicElement> allMagicElements = ElementsRegistry.elements.getRegistry().get().stream().toList();

        for (MagicElement magicElement : allMagicElements) {
            if (magicElement.isPrimal()) {
                primals.add(magicElement);
            }
        }

        return primals;
    }

    public static ArrayList<MagicElement> getCompoundAspects() {
        ArrayList<MagicElement> compounds = new ArrayList<>();
        Collection<MagicElement> allMagicElements = ElementsRegistry.elements.getRegistry().get().stream().toList();

        for (MagicElement magicElement : allMagicElements) {
            if (!magicElement.isPrimal()) {
                compounds.add(magicElement);
            }
        }

        return compounds;
    }
}
