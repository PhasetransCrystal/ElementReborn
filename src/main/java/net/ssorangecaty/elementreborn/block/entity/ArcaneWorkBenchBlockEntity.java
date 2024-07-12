package net.ssorangecaty.elementreborn.block.entity;

import net.ssorangecaty.elementreborn.block.TCBlockEntityRegister;
import net.ssorangecaty.elementreborn.core.element.MagicElement;
import net.ssorangecaty.elementreborn.core.element.StorageElements;
import net.ssorangecaty.elementreborn.core.recipe.ArcaneWorkBenchRecipe;
import net.ssorangecaty.elementreborn.core.recipe.TCRecipeRegister;
import net.ssorangecaty.elementreborn.core.wands.WandRod;
import net.ssorangecaty.elementreborn.data.TCDataComponentRegister;
import net.ssorangecaty.elementreborn.element.TCMagicElements;
import net.ssorangecaty.elementreborn.inventory.menu.ArcaneWorkBenchMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
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
    public static final int RESULT_SLOT = 9;
    protected NonNullList<ItemStack> items = NonNullList.withSize(11, ItemStack.EMPTY);
    private int users = 0;
    private float needCost = 0;
    private boolean isDefaultCraftingRecipe = true;

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
        this.addUsers();
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

    public @NotNull CraftingInput getCraftingInput() {
        NonNullList<ItemStack> itemList = NonNullList.withSize(9, ItemStack.EMPTY);
        for (int i = 0; i < 9; i++) {
            itemList.set(i, items.get(i));
        }
        return CraftingInput.of(3, 3, itemList);
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
        if (level.isClientSide || entity.isNobodyUse() || entity.items.stream().allMatch(ItemStack::isEmpty)) return;
        ItemStack previous = entity.items.get(RESULT_SLOT), current = ItemStack.EMPTY;
        entity.items.set(RESULT_SLOT, ItemStack.EMPTY);
        CraftingInput input = entity.getCraftingInput();
        for (RecipeHolder<CraftingRecipe> recipe : level.getRecipeManager().getAllRecipesFor(RecipeType.CRAFTING))
            if (recipe.value().matches(input, level)) {
                current = recipe.value().getResultItem(null).copy();
                entity.isDefaultCraftingRecipe = true;
                break;
            }
        for (RecipeHolder<ArcaneWorkBenchRecipe> recipe : level.getRecipeManager().getAllRecipesFor(TCRecipeRegister.ARCANE_WORK_BENCH))
            if (recipe.value().matches(input, level)) {
                current = recipe.value().getResultItem(null).copy();
                entity.needCost = recipe.value().getCost();
                entity.isDefaultCraftingRecipe = false;
                break;
            }
        entity.items.set(RESULT_SLOT, previous);
        if (previous.isEmpty() != current.isEmpty() || previous.getItem() != current.getItem() || previous.getCount() != current.getCount()) {
            entity.items.set(RESULT_SLOT, current);
            entity.setChanged();
        }
    }

    public boolean isDefaultCraftingRecipe() {
        return isDefaultCraftingRecipe;
    }

    public void addUsers() {
        this.users++;
    }

    public void removeUsers() {
        this.users--;
    }

    public boolean isNobodyUse() {
        return this.users <= 0;
    }

    public static float calculateVis(ItemStack stack, float origin, Player player) {
        return origin;
    }

    public static boolean hasEnoughBaseElementVis(ItemStack stack, StorageElements storageElements, float value, Player player) {
        float vis = calculateVis(stack, value, player);
        for (MagicElement magicElement : TCMagicElements.DEFAULT_ELEMENTS) {
            if (storageElements.getOrDefault(magicElement) < vis)
                return false;
        }
        return true;
    }

    public boolean hasEnoughVis(Player player) {
        ItemStack stack = this.items.get(WAND_ROD_SLOT);
        if (stack.getItem() instanceof WandRod)
            return hasEnoughBaseElementVis(stack, this.items.get(WAND_ROD_SLOT).get(TCDataComponentRegister.STORAGE_ELEMENTS), this.needCost, player);
        return false;
    }

    public float getNeedCost() {
        return needCost;
    }


}