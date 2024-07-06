package net.archasmiel.thaumcraft.core.recipe;

import net.archasmiel.thaumcraft.Thaumcraft;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RecipeTypeRegister {

    public static final DeferredRegister<RecipeType<? extends Recipe<?>>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, Thaumcraft.MODID);
    public static final RecipeType<ArcaneWorkBenchRecipe> ARCANE_WORK_BENCH = register("arcane_work_bench");

    public static <T extends Recipe<?>> RecipeType<T> register(final String name) {
        RecipeType<T> recipeType = new RecipeType<T>() {
            @Override
            public String toString() {
                return name;
            }
        };

        RECIPE_TYPES.register(name, () -> recipeType);
        return recipeType;
    }


}
