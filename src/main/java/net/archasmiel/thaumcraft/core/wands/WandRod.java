package net.archasmiel.thaumcraft.core.wands;

import net.archasmiel.thaumcraft.core.element.MagicElement;
import net.archasmiel.thaumcraft.core.element.StorageElements;
import net.archasmiel.thaumcraft.core.item.TCItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.CustomData;

import java.util.Iterator;

public class WandRod extends TCItem implements IItemStorageElementsAble {
    private final int maxCapacity;
    private float receiveSpeed = 1.0f;

    public WandRod(TCProperties tcProperties, int maxCapacity) {
        super(tcProperties);
        this.maxCapacity = maxCapacity;
    }

    @Override
    public int getMaxCapacity() {
        return this.maxCapacity;
    }

    public float getReceiveSpeed() {
        return receiveSpeed;
    }

    public void setReceiveSpeed(float receiveSpeed) {
        this.receiveSpeed = receiveSpeed;
    }

    public void receiveElement(StorageElements other) {
        if (other == null) {
            return;
        }

        Iterator<MagicElement> iterator = other.getElements().iterator();

        if (!iterator.hasNext()) {
            return;
        }

        MagicElement element;
        do {
            element = iterator.next();
        } while (iterator.hasNext() && other.getElementValue(element) == 0);

        if (this.getStorage().containsElement(element)) {
            float remainingValue = other.getElementValue(element);
            float amountToReceive = Math.min(remainingValue, this.receiveSpeed);

            this.getStorage().addElement(element, amountToReceive);
            other.removeElement(element, amountToReceive, false);
        }
    }

}
