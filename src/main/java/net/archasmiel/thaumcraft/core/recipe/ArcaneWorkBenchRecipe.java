package net.archasmiel.thaumcraft.core.recipe;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.NotNull;

public interface ArcaneWorkBenchRecipe extends Recipe<ArcaneWorkBenchInput> {
    @Override
    default @NotNull RecipeType<?> getType() {
        return RecipeTypeRegister.ARCANE_WORK_BENCH;
    }
}
