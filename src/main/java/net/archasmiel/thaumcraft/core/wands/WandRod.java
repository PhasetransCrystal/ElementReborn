package net.archasmiel.thaumcraft.core.wands;

import net.archasmiel.thaumcraft.core.element.MagicElement;
import net.archasmiel.thaumcraft.core.element.StorageElements;
import net.archasmiel.thaumcraft.core.item.TCItem;
import net.archasmiel.thaumcraft.data.TCDataComponentRegister;
import net.archasmiel.thaumcraft.element.TCMagicElements;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class WandRod extends TCItem implements IItemStorageElementsAble {
    private final int maxCapacity;
    private float receiveSpeed = 1.0f;

    public WandRod(TCProperties tcProperties, int maxCapacity) {
        super(tcProperties);
        this.maxCapacity = maxCapacity;
    }

    public static StorageElements getElements(ItemStack stack) {
        StorageElements elements = stack.get(TCDataComponentRegister.STORAGE_ELEMENTS);
        if (elements == null)
            elements = stack.set(TCDataComponentRegister.STORAGE_ELEMENTS, new StorageElements(Map.of()));
        return elements;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @NotNull TooltipContext tooltipContext, @NotNull List<Component> components, @NotNull TooltipFlag flag) {
        super.appendHoverText(itemStack, tooltipContext, components, flag);
        StorageElements elements = getElements(itemStack);
        MutableComponent component = Component.empty();
        boolean first = true;
        for (MagicElement element : TCMagicElements.DEFAULT_ELEMENTS) {
            if (!first) component.append(Component.literal(" | "));
            component.append(Component.literal(String.format("%.2f", elements.getOrDefault(element))).withColor(element.getColor()));
            first = false;
        }
        components.add(component);
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
