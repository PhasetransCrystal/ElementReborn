package net.archasmiel.thaumcraft.core.crafting;

import net.archasmiel.thaumcraft.core.element.StorageElements;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

public interface IArcaneRecipe extends Recipe<TCRecipeInput> {

    int getRecipeSize();

    StorageElements getElements();

    StorageElements getAspects(InventoryMenu inv);

    String getResearch();
}
