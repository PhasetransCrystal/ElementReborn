package net.archasmiel.thaumcraft.core.material;

import com.google.common.base.Suppliers;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public enum IToolMaterial implements Tier {
    THAUMIUM(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 400,7.0F, 2.0F, 22, () -> Ingredient.of(Items.DIAMOND)),
    VOID(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 150,8.0F, 3.0F, 10, () -> Ingredient.of(Items.NETHERITE_INGOT)),
    ELEMENTAL(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1500,10.0F, 3.0F, 18, () -> Ingredient.of(Items.NETHERITE_INGOT));
    private final TagKey<Block> incorrectBlocksForDrops;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final Supplier<Ingredient> repairIngredient;

    private IToolMaterial(TagKey<Block> incorrectBlocksForDrops, int uses, float speed, float damage, int enchantmentValue, Supplier<Ingredient> repairIngredient) {
        this.incorrectBlocksForDrops = incorrectBlocksForDrops;
        this.uses = uses;
        this.speed = speed;
        this.damage = damage;
        this.enchantmentValue = enchantmentValue;
        this.repairIngredient = Suppliers.memoize(repairIngredient::get);
    }

    @Override
    public int getUses() {
        return this.uses;
    }

    @Override
    public float getSpeed() {
        return this.speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.damage;
    }

    @Override
    public @NotNull TagKey<Block> getIncorrectBlocksForDrops() {
        return this.incorrectBlocksForDrops;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public @NotNull Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    public static TagKey<Block> getTagFromTier(IToolMaterial tier) {
        return switch (tier) {
            case THAUMIUM, ELEMENTAL -> BlockTags.NEEDS_DIAMOND_TOOL;
            case VOID -> Tags.Blocks.NEEDS_NETHERITE_TOOL;
        };
    }

    @Nullable
    public TagKey<Block> getTag() {
        return getTagFromTier(this);
    }


}
