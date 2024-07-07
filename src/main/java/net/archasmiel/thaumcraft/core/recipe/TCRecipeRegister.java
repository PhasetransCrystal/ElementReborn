package net.archasmiel.thaumcraft.core.recipe;

import net.archasmiel.thaumcraft.Thaumcraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TCRecipeRegister {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, Thaumcraft.MODID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, Thaumcraft.MODID);

    public static final RecipeType<ArcaneWorkBenchRecipe> ARCANE_WORK_BENCH = registerType("arcane_work_bench");
    public static final RecipeSerializer<ShapedArcaneWorkBenchRecipe> SHAPED_ARCANE_WORK_BENCH_SERIALIZER = registerSerializer("shaped_arcane_work_bench", new ShapedArcaneWorkBenchRecipe.Serializer());
    public static final RecipeSerializer<ShapelessArcaneWorkBenchRecipe> SHAPELESS_ARCANE_WORK_BENCH_SERIALIZER = registerSerializer("shapeless_arcane_work_bench", new ShapelessArcaneWorkBenchRecipe.Serializer());


    public static <T extends Recipe<?>> RecipeType<T> registerType(final String name) {
        RecipeType<T> recipeType = new RecipeType<T>() {
            @Override
            public String toString() {
                return name;
            }
        };

        RECIPE_TYPES.register(name, () -> recipeType);
        return recipeType;
    }


    public static <T extends RecipeSerializer<?>> T registerSerializer(String name, T serializer) {
        RECIPE_SERIALIZERS.register(name, () -> serializer);
        return serializer;
    }

}
