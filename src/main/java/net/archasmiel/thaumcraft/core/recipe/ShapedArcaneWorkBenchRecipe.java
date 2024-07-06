package net.archasmiel.thaumcraft.core.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ShapedArcaneWorkBenchRecipe implements ArcaneWorkBenchRecipe{
    final ShapedRecipePattern pattern;
    final ItemStack result;
    final String group;
    final float cost;

    public ShapedArcaneWorkBenchRecipe(ShapedRecipePattern pattern, ItemStack result, String group, float cost) {
        this.pattern = pattern;
        this.result = result;
        this.group = group;
        this.cost = cost;
    }


    @Override
    public float getCost() {
        return cost;
    }

    @Override
    public boolean canCraftInDimensions(int p_44161_, int p_44162_) {
        return p_44161_ >= this.pattern.width() && p_44162_ >= this.pattern.height();
    }

    public boolean matches(@NotNull CraftingInput p_345040_, @NotNull Level p_44167_) {
        return this.pattern.matches(p_345040_);
    }

    public @NotNull ItemStack assemble(@NotNull CraftingInput p_345201_, HolderLookup.@NotNull Provider p_335688_) {
        return this.getResultItem(p_335688_).copy();
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider p_336125_) {
        return this.result.copy();
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return TCRecipeRegister.SHAPED_ARCANE_WORK_BENCH_SERIALIZER;
    }


    public static class Serializer implements RecipeSerializer<ShapedArcaneWorkBenchRecipe> {
        public static final MapCodec<ShapedArcaneWorkBenchRecipe> CODEC = RecordCodecBuilder.mapCodec(
                p_340778_ -> p_340778_.group(
                                ShapedRecipePattern.MAP_CODEC.forGetter(p_311733_ -> p_311733_.pattern),
                                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(p_311730_ -> p_311730_.result),
                                Codec.STRING.optionalFieldOf("group", "").forGetter(p_311729_ -> p_311729_.group),
                                Codec.FLOAT.fieldOf("cost").forGetter(p_311734_ -> p_311734_.cost)
                        )
                        .apply(p_340778_, ShapedArcaneWorkBenchRecipe::new)
        );
        public static final StreamCodec<RegistryFriendlyByteBuf, ShapedArcaneWorkBenchRecipe> STREAM_CODEC = StreamCodec.of(
                ShapedArcaneWorkBenchRecipe.Serializer::toNetwork, ShapedArcaneWorkBenchRecipe.Serializer::fromNetwork
        );

        @Override
        public @NotNull MapCodec<ShapedArcaneWorkBenchRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, ShapedArcaneWorkBenchRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static ShapedArcaneWorkBenchRecipe fromNetwork(RegistryFriendlyByteBuf p_319998_) {
            String s = p_319998_.readUtf();
            ShapedRecipePattern shapedrecipepattern = ShapedRecipePattern.STREAM_CODEC.decode(p_319998_);
            ItemStack itemstack = ItemStack.STREAM_CODEC.decode(p_319998_);
            float f = p_319998_.readFloat();
            return new ShapedArcaneWorkBenchRecipe(shapedrecipepattern, itemstack,s,f);
        }

        private static void toNetwork(RegistryFriendlyByteBuf p_320738_, ShapedArcaneWorkBenchRecipe p_320586_) {
            p_320738_.writeUtf(p_320586_.group);
            ShapedRecipePattern.STREAM_CODEC.encode(p_320738_, p_320586_.pattern);
            ItemStack.STREAM_CODEC.encode(p_320738_, p_320586_.result);
            p_320738_.writeFloat(p_320586_.cost);
        }
    }
}
