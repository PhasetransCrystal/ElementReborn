package net.archasmiel.thaumcraft.inventory.slot;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class OutputWithWandSlot extends Slot {
    private final int wandSlot;
    public OutputWithWandSlot(Container p_40223_, int p_40224_, int p_40225_, int p_40226_ , int wandSlot) {
        super(p_40223_, p_40224_, p_40225_, p_40226_);
        this.wandSlot = wandSlot;
    }

    @Override
    public boolean mayPlace(ItemStack p_40231_) {
        return false;
    }

    @Override
    public void onTake(Player player, ItemStack stack) {
        for (int c = 0; c < this.container.getContainerSize(); c++){
            if (c == this.wandSlot) continue;
            this.container.removeItem(c, 1);
        }

        //TODO: Reduce elements
        super.onTake(player, stack);
    }
}
