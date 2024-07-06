package net.archasmiel.thaumcraft.core.recipe;

import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.NotNull;

public interface ArcaneWorkBenchRecipe extends Recipe<CraftingInput> {
    @Override
    default @NotNull RecipeType<?> getType() {
        return TCRecipeRegister.ARCANE_WORK_BENCH;
    }

    float getCost();
}
