package net.ssorangecaty.elementreborn.inventory.slot;

import net.ssorangecaty.elementreborn.block.entity.ArcaneWorkBenchBlockEntity;
import net.ssorangecaty.elementreborn.core.element.StorageElements;
import net.ssorangecaty.elementreborn.data.ERDataComponentRegister;
import net.ssorangecaty.elementreborn.inventory.menu.ArcaneWorkBenchMenu;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class OutputWithWandSlot extends Slot {
    private final ArcaneWorkBenchMenu menu;

    public OutputWithWandSlot(Container p_40223_, int p_40224_, int p_40225_, int p_40226_, ArcaneWorkBenchMenu menu) {
        super(p_40223_, p_40224_, p_40225_, p_40226_);
        this.menu = menu;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return false;
    }

    @Override
    public void onTake(Player player, ItemStack stack) {
        for (int c = 0; c < 9; c++)
            this.container.removeItem(c, 1);
        float vis = ArcaneWorkBenchBlockEntity.calculateVis(this.getItem(), this.menu.getDefaultVis(), player);
        StorageElements storage = this.container.getItem(ArcaneWorkBenchBlockEntity.WAND_ROD_SLOT).get(ERDataComponentRegister.STORAGE_ELEMENTS);
        if (storage != null) storage.reduceRootElements(vis);
        super.onTake(player, stack);
    }

    @Override
    public boolean mayPickup(Player player) {
        return menu.isDefaultCraftingRecipe() || menu.hasEnoughVis(player);
    }
}
