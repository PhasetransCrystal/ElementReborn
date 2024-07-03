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

    public ResearchPage(String research, String text) {
        this.type = ResearchPage.PageType.TEXT;
        this.text = null;
        this.research = null;
        this.image = null;
        this.aspects = null;
        this.recipe = null;
        this.recipeOutput = null;
        this.type = ResearchPage.PageType.TEXT_CONCEALED;
        this.research = research;
        this.text = text;
    }
/*
    public ResearchPage(IRecipe recipe) {
        this.type = ResearchPage.PageType.TEXT;
        this.text = null;
        this.research = null;
        this.image = null;
        this.aspects = null;
        this.recipe = null;
        this.recipeOutput = null;
        this.type = ResearchPage.PageType.NORMAL_CRAFTING;
        this.recipe = recipe;
        this.recipeOutput = recipe.func_77571_b();
    }

    public ResearchPage(IRecipe[] recipe) {
        this.type = ResearchPage.PageType.TEXT;
        this.text = null;
        this.research = null;
        this.image = null;
        this.aspects = null;
        this.recipe = null;
        this.recipeOutput = null;
        this.type = ResearchPage.PageType.NORMAL_CRAFTING;
        this.recipe = recipe;
    }

    public ResearchPage(IArcaneRecipe... recipe) {
        this.type = ResearchPage.PageType.TEXT;
        this.text = null;
        this.research = null;
        this.image = null;
        this.aspects = null;
        this.recipe = null;
        this.recipeOutput = null;
        this.type = ResearchPage.PageType.ARCANE_CRAFTING;
        this.recipe = recipe;
    }

    public ResearchPage(CrucibleRecipe[] recipe) {
        this.type = ResearchPage.PageType.TEXT;
        this.text = null;
        this.research = null;
        this.image = null;
        this.aspects = null;
        this.recipe = null;
        this.recipeOutput = null;
        this.type = ResearchPage.PageType.CRUCIBLE_CRAFTING;
        this.recipe = recipe;
    }

    public ResearchPage(InfusionRecipe[] recipe) {
        this.type = ResearchPage.PageType.TEXT;
        this.text = null;
        this.research = null;
        this.image = null;
        this.aspects = null;
        this.recipe = null;
        this.recipeOutput = null;
        this.type = ResearchPage.PageType.INFUSION_CRAFTING;
        this.recipe = recipe;
    }

    public ResearchPage(List recipe) {
        this.type = ResearchPage.PageType.TEXT;
        this.text = null;
        this.research = null;
        this.image = null;
        this.aspects = null;
        this.recipe = null;
        this.recipeOutput = null;
        this.type = ResearchPage.PageType.COMPOUND_CRAFTING;
        this.recipe = recipe;
    }

    public ResearchPage(IArcaneRecipe recipe) {
        this.type = ResearchPage.PageType.TEXT;
        this.text = null;
        this.research = null;
        this.image = null;
        this.aspects = null;
        this.recipe = null;
        this.recipeOutput = null;
        this.type = ResearchPage.PageType.ARCANE_CRAFTING;
        this.recipe = recipe;
        this.recipeOutput = recipe.getRecipeOutput();
    }

    public ResearchPage(CrucibleRecipe recipe) {
        this.type = ResearchPage.PageType.TEXT;
        this.text = null;
        this.research = null;
        this.image = null;
        this.aspects = null;
        this.recipe = null;
        this.recipeOutput = null;
        this.type = ResearchPage.PageType.CRUCIBLE_CRAFTING;
        this.recipe = recipe;
        this.recipeOutput = recipe.getRecipeOutput();
    }

    public ResearchPage(ItemStack input) {
        this.type = ResearchPage.PageType.TEXT;
        this.text = null;
        this.research = null;
        this.image = null;
        this.aspects = null;
        this.recipe = null;
        this.recipeOutput = null;
        this.type = ResearchPage.PageType.SMELTING;
        this.recipe = input;
        this.recipeOutput = FurnaceRecipes.func_77602_a().func_151395_a(input);
    }

    public ResearchPage(InfusionRecipe recipe) {
        this.type = ResearchPage.PageType.TEXT;
        this.text = null;
        this.research = null;
        this.image = null;
        this.aspects = null;
        this.recipe = null;
        this.recipeOutput = null;
        this.type = ResearchPage.PageType.INFUSION_CRAFTING;
        this.recipe = recipe;
        if (recipe.getRecipeOutput() instanceof ItemStack) {
            this.recipeOutput = (ItemStack)recipe.getRecipeOutput();
        } else {
            this.recipeOutput = recipe.getRecipeInput();
        }

    }

    public ResearchPage(InfusionEnchantmentRecipe recipe) {
        this.type = ResearchPage.PageType.TEXT;
        this.text = null;
        this.research = null;
        this.image = null;
        this.aspects = null;
        this.recipe = null;
        this.recipeOutput = null;
        this.type = ResearchPage.PageType.INFUSION_ENCHANTMENT;
        this.recipe = recipe;
    }*/

    public ResearchPage(ResourceLocation image, String caption) {
        this.type = ResearchPage.PageType.TEXT;
        this.text = null;
        this.research = null;
        this.image = null;
        this.aspects = null;
        this.recipe = null;
        this.recipeOutput = null;
        this.type = ResearchPage.PageType.IMAGE;
        this.image = image;
        this.text = caption;
    }

    public ResearchPage(StorageElements se) {
        this.type = ResearchPage.PageType.TEXT;
        this.text = null;
        this.research = null;
        this.image = null;
        this.aspects = null;
        this.recipe = null;
        this.recipeOutput = null;
        this.type = ResearchPage.PageType.ASPECTS;
        this.aspects = se;
    }

    public Component getTranslatedText() {
        return Component.translatable(this.text == null ? "" : this.text);
    }

    public static enum PageType {
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

        private PageType() {
        }
    }
}