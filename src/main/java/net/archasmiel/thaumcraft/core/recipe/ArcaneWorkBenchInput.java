package net.archasmiel.thaumcraft.core.recipe;

import net.archasmiel.thaumcraft.core.wands.WandRod;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

import java.util.ArrayList;
import java.util.List;

public class ArcaneWorkBenchInput implements RecipeInput {
    public static final ArcaneWorkBenchInput EMPTY = new ArcaneWorkBenchInput(0, 0, List.of(), ItemStack.EMPTY);
    private final int width;
    private final int height;
    private final List<ItemStack> items;
    private final ItemStack wandRod;

    private final StackedContents stackedContents = new StackedContents();
    private final int ingredientCount;

    private ArcaneWorkBenchInput(int p_346099_, int p_344783_, List<ItemStack> p_345241_ , ItemStack wandRod) {
        this.width = p_346099_;
        this.height = p_344783_;
        this.items = p_345241_;
        if(wandRod.getItem() instanceof WandRod){
            this.wandRod = wandRod;
        }else{
            this.wandRod = ItemStack.EMPTY;
        }

        int i = 0;

        for (ItemStack itemstack : p_345241_) {
            if (!itemstack.isEmpty()) {
                i++;
                this.stackedContents.accountStack(itemstack, 1);
            }
        }

        this.ingredientCount = i;
    }

    public static ArcaneWorkBenchInput of(int p_346122_, int p_344877_, List<ItemStack> p_345183_,ItemStack wandRod) {
        return ofPositioned(p_346122_, p_344877_, p_345183_,wandRod).input();
    }

    public static ArcaneWorkBenchInput.Positioned ofPositioned(int p_347479_, int p_347466_, List<ItemStack> p_347585_, ItemStack wandRod) {
        if (p_347479_ != 0 && p_347466_ != 0) {
            int i = p_347479_ - 1;
            int j = 0;
            int k = p_347466_ - 1;
            int l = 0;

            for (int i1 = 0; i1 < p_347466_; i1++) {
                boolean flag = true;

                for (int j1 = 0; j1 < p_347479_; j1++) {
                    ItemStack itemstack = p_347585_.get(j1 + i1 * p_347479_);
                    if (!itemstack.isEmpty()) {
                        i = Math.min(i, j1);
                        j = Math.max(j, j1);
                        flag = false;
                    }
                }

                if (!flag) {
                    k = Math.min(k, i1);
                    l = Math.max(l, i1);
                }
            }

            int i2 = j - i + 1;
            int j2 = l - k + 1;
            if (i2 <= 0 || j2 <= 0) {
                return ArcaneWorkBenchInput.Positioned.EMPTY;
            } else if (i2 == p_347479_ && j2 == p_347466_) {
                return new ArcaneWorkBenchInput.Positioned(new ArcaneWorkBenchInput(p_347479_, p_347466_, p_347585_,wandRod), i, k);
            } else {
                List<ItemStack> list = new ArrayList<>(i2 * j2);

                for (int k2 = 0; k2 < j2; k2++) {
                    for (int k1 = 0; k1 < i2; k1++) {
                        int l1 = k1 + i + (k2 + k) * p_347479_;
                        list.add(p_347585_.get(l1));
                    }
                }

                return new ArcaneWorkBenchInput.Positioned(new ArcaneWorkBenchInput(i2, j2, list,wandRod), i, k);
            }
        } else {
            return ArcaneWorkBenchInput.Positioned.EMPTY;
        }
    }

    @Override
    public ItemStack getItem(int p_345667_) {
        return this.items.get(p_345667_);
    }

    public ItemStack getItem(int p_346237_, int p_345556_) {
        return this.items.get(p_346237_ + p_345556_ * this.width);
    }

    @Override
    public int size() {
        return this.items.size();
    }

    public ItemStack wandRod() {
        return this.wandRod;
    }
    @Override
    public boolean isEmpty() {
        return this.ingredientCount == 0;
    }

    public StackedContents stackedContents() {
        return this.stackedContents;
    }

    public List<ItemStack> items() {
        return this.items;
    }

    public int ingredientCount() {
        return this.ingredientCount;
    }

    public int width() {
        return this.width;
    }

    public int height() {
        return this.height;
    }

    @Override
    public boolean equals(Object p_345299_) {
        if (p_345299_ == this) {
            return true;
        } else {
            return p_345299_ instanceof ArcaneWorkBenchInput craftinginput && this.width == craftinginput.width
                    && this.height == craftinginput.height
                    && this.ingredientCount == craftinginput.ingredientCount
                    && ItemStack.listMatches(this.items, craftinginput.items);
        }
    }

    @Override
    public int hashCode() {
        int i = ItemStack.hashStackList(this.items);
        i = 31 * i + this.width;
        return 31 * i + this.height;
    }

    public static record Positioned(ArcaneWorkBenchInput input, int left, int top) {
        public static final ArcaneWorkBenchInput.Positioned EMPTY = new ArcaneWorkBenchInput.Positioned(ArcaneWorkBenchInput.EMPTY, 0, 0);
    }
}