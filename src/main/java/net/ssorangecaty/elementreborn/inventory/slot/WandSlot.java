package net.ssorangecaty.elementreborn.inventory.slot;

import net.ssorangecaty.elementreborn.core.wands.WandRod;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class WandSlot extends Slot {
    public WandSlot(Container p_40223_, int p_40224_, int p_40225_, int p_40226_) {
        super(p_40223_, p_40224_, p_40225_, p_40226_);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.getItem() instanceof WandRod;
    }
}
