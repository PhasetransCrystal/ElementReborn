package net.archasmiel.thaumcraft.inventory.menu;

import net.archasmiel.thaumcraft.block.entity.ArcaneWorkBenchBlockEntity;
import net.archasmiel.thaumcraft.core.recipe.ArcaneWorkBenchRecipe;
import net.archasmiel.thaumcraft.core.recipe.TCRecipeRegister;
import net.archasmiel.thaumcraft.inventory.TCInventoryRegister;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.CraftingTableBlock;
import org.apache.http.client.HttpClient;
import org.jetbrains.annotations.Nullable;

public class ArcaneWorkBenchMenu extends AbstractContainerMenu {

    private final Container container;
    private final RecipeType<? extends ArcaneWorkBenchRecipe> recipeType;
    private final Inventory playerInventory;
    private final ContainerLevelAccess access;

    public ArcaneWorkBenchMenu(int p_38963_, Inventory p_38964_
    ) {
        this(TCInventoryRegister.ARCANE_WORKBENCH, TCRecipeRegister.ARCANE_WORK_BENCH, p_38963_, p_38964_, new SimpleContainer(11),ContainerLevelAccess.NULL);
    }

    public ArcaneWorkBenchMenu(int syncId, Inventory inventory , Container container,ContainerLevelAccess access
    ) {
        this(TCInventoryRegister.ARCANE_WORKBENCH, TCRecipeRegister.ARCANE_WORK_BENCH, syncId, inventory, container,access);
    }
    public ArcaneWorkBenchMenu(
            @Nullable MenuType<?> menuType,
            RecipeType<? extends ArcaneWorkBenchRecipe> recipeType,
            int syncId,
            Inventory playerInventory,
            Container container,
            ContainerLevelAccess access
    ) {
        super(menuType, syncId);
        this.playerInventory = playerInventory;
        this.recipeType = recipeType;
        this.container = container;
        this.access = access;
    }

    @Override
    public ItemStack quickMoveStack(Player p_39391_, int p_39392_) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(p_39392_);
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (p_39392_ == 0) {
                this.access.execute((p_39378_, p_39379_) -> itemstack1.getItem().onCraftedBy(itemstack1, p_39378_, p_39391_));
                if (!this.moveItemStackTo(itemstack1, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (p_39392_ >= 10 && p_39392_ < 46) {
                if (!this.moveItemStackTo(itemstack1, 1, 10, false)) {
                    if (p_39392_ < 37) {
                        if (!this.moveItemStackTo(itemstack1, 37, 46, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.moveItemStackTo(itemstack1, 10, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.moveItemStackTo(itemstack1, 10, 46, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(p_39391_, itemstack1);
            if (p_39392_ == 0) {
                p_39391_.drop(itemstack1, false);
            }
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(Player p_38874_) {
        return true;
    }
}
