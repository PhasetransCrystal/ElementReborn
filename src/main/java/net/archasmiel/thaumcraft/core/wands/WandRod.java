package net.archasmiel.thaumcraft.core.wands;

import net.archasmiel.thaumcraft.core.item.TCItem;

public class WandRod extends TCItem implements IItemStorageElementsAble {
    private final int maxCapacity;

    public WandRod(TCProperties tcProperties, int maxCapacity) {
        super(tcProperties);
        this.maxCapacity = maxCapacity;
    }

    @Override
    public int getMaxCapacity() {
        return this.maxCapacity;
    }
}
