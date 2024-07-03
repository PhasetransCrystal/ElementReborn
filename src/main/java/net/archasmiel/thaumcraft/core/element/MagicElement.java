package net.archasmiel.thaumcraft.core.element;

import net.archasmiel.thaumcraft.Thaumcraft;
import net.archasmiel.thaumcraft.util.IResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MagicElement {
    private final String name;
    private final ResourceLocation texture;
    private final int blend;
    private final int color;
    private final Iterable<MagicElement> components;

    public MagicElement(String name, int color, ResourceLocation image, int blend, MagicElement... components) {
            this.name = name;
            this.components = List.of(components);
            this.color = color;
            this.texture = image;
            this.blend = blend;
    }

    public MagicElement(String name, int color, MagicElement... components) {
        this(name, color, IResourceLocation.create("thaumcraft", "textures/aspects/" + name.toLowerCase() + ".png"), 1,components);
    }

    public MagicElement(String name, int color,int blend, MagicElement... components) {
        this(name, color,IResourceLocation.create("thaumcraft", "textures/aspects/" + name.toLowerCase() + ".png"), blend,components);
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

    public Component getTranslationText() {
        return Component.translatable("tc.aspect." + this.name).withColor(this.color);
    }

    public Iterable<MagicElement> getComponents() {
        return this.components;
    }

    public ResourceLocation getTexture() {
        return this.texture;
    }

    public boolean equalsName(String name) {
        return this.name.equals(name);
    }
    public int getBlend() {
        return this.blend;
    }

    public boolean isPrimal() {
        return this.getComponents() == null || ((Collection<MagicElement>) this.getComponents()).size() != 2;
    }

    public boolean isCompound() {
        return !this.isPrimal();
    }

}
