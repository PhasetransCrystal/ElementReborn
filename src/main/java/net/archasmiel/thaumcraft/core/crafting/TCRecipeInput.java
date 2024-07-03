package net.archasmiel.thaumcraft.core.crafting;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.RecipeInput;

public interface TCRecipeInput extends RecipeInput {
    Player getPlayer();
}
