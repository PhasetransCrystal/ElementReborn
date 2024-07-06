package net.archasmiel.thaumcraft.core.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ShapelessArcaneWorkBenchRecipe implements ArcaneWorkBenchRecipe{
    final String group;
    final ItemStack result;
    final NonNullList<Ingredient> ingredients;
    private final boolean isSimple;
    final float cost;

    public ShapelessArcaneWorkBenchRecipe(String p_249640_, ItemStack p_252071_, NonNullList<Ingredient> p_250689_, float cost) {
        this.group = p_249640_;
        this.result = p_252071_;
        this.ingredients = p_250689_;
        this.cost = cost;
        this.isSimple = p_250689_.stream().allMatch(Ingredient::isSimple);
    }

    public boolean matches(CraftingInput p_346123_, @NotNull Level p_44263_) {
        if (p_346123_.ingredientCount() != this.ingredients.size()) {
            return false;
        } else if (!isSimple) {
            var nonEmptyItems = new java.util.ArrayList<ItemStack>(p_346123_.ingredientCount());
            for (var item : p_346123_.items())
                if (!item.isEmpty())
                    nonEmptyItems.add(item);
            return net.neoforged.neoforge.common.util.RecipeMatcher.findMatches(nonEmptyItems, this.ingredients) != null;
        } else {
            return p_346123_.size() == 1 && this.ingredients.size() == 1
                    ? this.ingredients.getFirst().test(p_346123_.getItem(0))
                    : p_346123_.stackedContents().canCraft(this, null);
        }
    }

    public @NotNull ItemStack assemble(@NotNull CraftingInput p_345555_, HolderLookup.@NotNull Provider p_335725_) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return false;
    }

    @Override
    public @NotNull String getGroup() {
        return this.group;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return TCRecipeRegister.SHAPELESS_ARCANE_WORK_BENCH_SERIALIZER;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider p_335606_) {
        return this.result;
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    @Override
    public float getCost() {
        return this.cost;
    }


    public static class Serializer implements RecipeSerializer<ShapelessArcaneWorkBenchRecipe> {
        private static final MapCodec<ShapelessArcaneWorkBenchRecipe> CODEC = RecordCodecBuilder.mapCodec(
                p_340779_ -> p_340779_.group(
                                Codec.STRING.optionalFieldOf("group", "").forGetter(p_301127_ -> p_301127_.group),
                                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(p_301142_ -> p_301142_.result),
                                Ingredient.CODEC_NONEMPTY
                                        .listOf()
                                        .fieldOf("ingredients")
                                        .flatXmap(
                                                p_301021_ -> {
                                                    Ingredient[] aingredient = p_301021_.toArray(Ingredient[]::new); // Neo skip the empty check and immediately create the array.
                                                    if (aingredient.length == 0) {
                                                        return DataResult.error(() -> "No ingredients for shapeless recipe");
                                                    } else {
                                                        return aingredient.length > ShapedRecipePattern.getMaxHeight() * ShapedRecipePattern.getMaxWidth()
                                                                ? DataResult.error(() -> "Too many ingredients for shapeless recipe. The maximum is: %s".formatted(ShapedRecipePattern.getMaxHeight() * ShapedRecipePattern.getMaxWidth()))
                                                                : DataResult.success(NonNullList.of(Ingredient.EMPTY, aingredient));
                                                    }
                                                },
                                                DataResult::success
                                        )
                                        .forGetter(p_300975_ -> p_300975_.ingredients),
                                Codec.FLOAT.fieldOf("cost").forGetter(p_301135_ -> p_301135_.cost)
                        )
                        .apply(p_340779_, ShapelessArcaneWorkBenchRecipe::new)
        );
        public static final StreamCodec<RegistryFriendlyByteBuf, ShapelessArcaneWorkBenchRecipe> STREAM_CODEC = StreamCodec.of(
                ShapelessArcaneWorkBenchRecipe.Serializer::toNetwork, ShapelessArcaneWorkBenchRecipe.Serializer::fromNetwork
        );

        @Override
        public @NotNull MapCodec<ShapelessArcaneWorkBenchRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, ShapelessArcaneWorkBenchRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static ShapelessArcaneWorkBenchRecipe fromNetwork(RegistryFriendlyByteBuf p_319905_) {
            String s = p_319905_.readUtf();
            int i = p_319905_.readVarInt();
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i, Ingredient.EMPTY);
            nonnulllist.replaceAll(p_319735_ -> Ingredient.CONTENTS_STREAM_CODEC.decode(p_319905_));
            ItemStack itemstack = ItemStack.STREAM_CODEC.decode(p_319905_);
            float f = p_319905_.readFloat();
            return new ShapelessArcaneWorkBenchRecipe(s, itemstack, nonnulllist,f);
        }

        private static void toNetwork(RegistryFriendlyByteBuf p_320371_, ShapelessArcaneWorkBenchRecipe p_320323_) {
            p_320371_.writeUtf(p_320323_.group);
            p_320371_.writeVarInt(p_320323_.ingredients.size());
            p_320371_.writeFloat(p_320323_.cost);

            for (Ingredient ingredient : p_320323_.ingredients) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(p_320371_, ingredient);
            }

            ItemStack.STREAM_CODEC.encode(p_320371_, p_320323_.result);
        }
    }
}
