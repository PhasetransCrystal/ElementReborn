package net.archasmiel.thaumcraft.block.entity;

import net.archasmiel.thaumcraft.block.TCBlockEntityRegister;
import net.archasmiel.thaumcraft.core.recipe.ArcaneWorkBenchRecipe;
import net.archasmiel.thaumcraft.core.recipe.TCRecipeRegister;
import net.archasmiel.thaumcraft.inventory.menu.ArcaneWorkBenchMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class ArcaneWorkBenchBlockEntity extends BaseContainerBlockEntity implements Container, StackedContentsCompatible {
    public static final int WAND_ROD_SLOT = 10;
    public static final int RESULT_SLOT = 0;
    protected NonNullList<ItemStack> items = NonNullList.withSize(11, ItemStack.EMPTY);

    public ArcaneWorkBenchBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(TCBlockEntityRegister.ARCANE_WORKBENCH.get(), blockPos, blockState);
    }

    @NotNull
    @Override
    public Component getDefaultName() {
        return Component.literal("Arcane Workbench");
    }

    @NotNull
    @Override
    public NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    public void setItems(@NotNull NonNullList<ItemStack> items) {
        this.items = items;
    }


    @NotNull
    @Override
    public AbstractContainerMenu createMenu(int syncId, @NotNull Inventory playerInventory) {
        return new ArcaneWorkBenchMenu(syncId, playerInventory, this, ContainerLevelAccess.create(playerInventory.player.level(), this.getBlockPos()));
    }

    @Override
    public int getContainerSize() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemstack : items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public @NotNull ItemStack getItem(int slot) {
        if (slot < 0 || slot >= items.size()) {
            return ItemStack.EMPTY;
        }
        return items.get(slot);
    }

    @Override
    public void setItem(int slot, @NotNull ItemStack stack) {
        if (slot < 0 || slot >= items.size()) return;
        items.set(slot, stack);
    }

    @Override
    public void clearContent() {
        items.clear();
    }

    @Override
    public void fillStackedContents(@NotNull StackedContents p_40281_) {
        for (ItemStack itemstack : items) {
            p_40281_.accountStack(itemstack);
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider p_338309_) {
        super.loadAdditional(tag, p_338309_);
        ContainerHelper.loadAllItems(tag, this.items, p_338309_);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider p_324280_) {
        super.saveAdditional(tag, p_324280_);
        ContainerHelper.saveAllItems(tag, this.items, p_324280_);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ArcaneWorkBenchBlockEntity entity) {
        for (RecipeHolder<CraftingRecipe> recipe : level.getRecipeManager().getAllRecipesFor(RecipeType.CRAFTING))
            if (recipe.value().matches(CraftingInput.of(3, 3, entity.items), level)) {
                entity.items.set(RESULT_SLOT, recipe.value().getResultItem(null).copy());
                entity.setChanged();
            }
        for (RecipeHolder<ArcaneWorkBenchRecipe> recipe : level.getRecipeManager().getAllRecipesFor(TCRecipeRegister.ARCANE_WORK_BENCH))
            if (recipe.value().matches(CraftingInput.of(3, 3, entity.items), level)) {
                entity.items.set(RESULT_SLOT, recipe.value().getResultItem(null).copy());
                entity.setChanged();
            }
    }
}