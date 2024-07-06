package net.archasmiel.thaumcraft.block.entity;

import net.archasmiel.thaumcraft.inventory.menu.ArcaneWorkBenchMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ArcaneWorkBenchBlockEntity extends BaseContainerBlockEntity implements Container, StackedContentsCompatible {
    public static final int WAND_ROD_SLOT = 10;
    public static final int RESULT_SLOT = 11;
    protected NonNullList<ItemStack> items = NonNullList.withSize(12, ItemStack.EMPTY); // 11 slots for input + 1 for result

    protected ArcaneWorkBenchBlockEntity(BlockEntityType<?> entityType, BlockPos blockPos, BlockState blockState) {
        super(entityType, blockPos, blockState);
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
        return new ArcaneWorkBenchMenu(syncId, playerInventory,this,ContainerLevelAccess.create(playerInventory.player.level(), this.getBlockPos()));
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
}