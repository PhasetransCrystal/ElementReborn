package net.archasmiel.thaumcraft.core.element;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.archasmiel.thaumcraft.Thaumcraft;
import net.archasmiel.thaumcraft.util.IResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class MagicElement {
    private final String name;
    private final ResourceLocation texture;
    private final int blend;
    private final int color;
    private final Iterable<MagicElement> components;

    public MagicElement(String name, int color, ResourceLocation texture, int blend, MagicElement... components) {
        this.name = name;
        this.components = List.of(components);
        this.color = color;
        this.texture = texture;
        this.blend = blend;
    }

    public MagicElement(String name, int color, MagicElement... components) {
        this(name, color, IResourceLocation.create("thaumcraft", "textures/aspects/" + name.toLowerCase(Locale.ROOT) + ".png"), 1, components);
    }

    public MagicElement(String name, int color, int blend, MagicElement... components) {
        this(name, color, IResourceLocation.create("thaumcraft", "textures/aspects/" + name.toLowerCase(Locale.ROOT) + ".png"), blend, components);
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


    @Nullable
    public static MagicElement loadFromJson(JsonObject json) {
        if (!MagicElement.checkJson(json)) return null;
        String name = json.get("name").getAsString();
        ResourceLocation texture = ResourceLocation.parse(json.get("texture").getAsString());
        int color = json.get("color").getAsInt();
        int blend = json.get("blend").getAsInt();
        List<MagicElement> components = new ArrayList<>();
        if (json.has("components")) {
            for (JsonElement component : json.get("components").getAsJsonArray()) {
                components.add(ElementsRegistry.getElementByName(component.getAsString()));
            }
        }
        return new MagicElement(name, color, texture, blend, components.toArray(new MagicElement[2]));
    }

    public static boolean checkJson(JsonObject json) {
        if (!json.has("name") || !json.has("texture") || !json.has("color") || !json.has("blend")) {
            Thaumcraft.LOGGER.error("Invalid JSON for MagicElement: " + json);
            return false;
        }
        return true;
    }
}
