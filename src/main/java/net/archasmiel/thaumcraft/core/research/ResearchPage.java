package net.archasmiel.thaumcraft.core.research;

import net.archasmiel.thaumcraft.core.crafting.IArcaneRecipe;
import net.archasmiel.thaumcraft.core.element.StorageElements;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ResearchPage {
    public PageType type;
    public String text;
    public String research;
    public ResourceLocation image;
    public StorageElements aspects;
    public Object recipe;
    public ItemStack recipeOutput;

    public ResearchPage(String text) {
        this.type = ResearchPage.PageType.TEXT;
        this.text = null;
        this.research = null;
        this.image = null;
        this.aspects = null;
        this.recipe = null;
        this.recipeOutput = null;
        this.text = text;
    }

    public Component getTranslatedText() {
        return Component.translatable(this.text == null ? "" : this.text);
    }

    public enum PageType {
        TEXT,
        TEXT_CONCEALED,
        IMAGE,
        CRUCIBLE_CRAFTING,
        ARCANE_CRAFTING,
        ASPECTS,
        NORMAL_CRAFTING,
        INFUSION_CRAFTING,
        COMPOUND_CRAFTING,
        INFUSION_ENCHANTMENT,
        SMELTING;

        PageType() {
        }
    }
}