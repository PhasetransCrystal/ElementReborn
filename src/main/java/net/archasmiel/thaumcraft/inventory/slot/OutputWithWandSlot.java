package net.archasmiel.thaumcraft.inventory.slot;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class OutputWithWandSlot extends Slot {
    public OutputWithWandSlot(Container p_40223_, int p_40224_, int p_40225_, int p_40226_) {
        super(p_40223_, p_40224_, p_40225_, p_40226_);
    }

    @Override
    public boolean mayPlace(ItemStack p_40231_) {
        return false;
    }

    @Override
    public void onTake(Player player, ItemStack stack) {
        int c;
        for (c = 0; c < this.container.getContainerSize(); c++)
            this.container.removeItem(c, 1);
        //TODO: Reduce elements
        super.onTake(player, stack);
    }
}
