package net.archasmiel.thaumcraft.core.element;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

import java.util.Iterator;
import java.util.Map;

public class StorageElements {
    private final Map<MagicElement,Float> elements;

    private float receiveSpeed = 1.0f;

    public StorageElements(Map<MagicElement,Float> elements) {
        this.elements = elements;
    }

    public StorageElements(Map<MagicElement,Float> elements,float receiveSpeed) {
        this.receiveSpeed = receiveSpeed;
        this.elements = elements;
    }

    public float getElementValue(MagicElement element) {
        return elements.get(element);
    }

    public Iterable<MagicElement> getElements() {
        return elements.keySet();
    }


    public boolean containsElement(MagicElement element) {
        return elements.containsKey(element);
    }


    public int size() {
        return elements.size();
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }


    public void clear() {
        elements.clear();
    }

    public void addElement(MagicElement element, float value) {
        if(this.containsElement(element)) {
            elements.put(element, elements.get(element) + value);
        } else {
            elements.put(element, value);
        }
    }

    public void removeElement(MagicElement elementName, float value, boolean deleteIfZero) {
        if (this.containsElement(elementName)) {
            float currentValue = elements.get(elementName);
            if (currentValue - value >= 0) {
                elements.put(elementName, currentValue - value);
            } else {
                if (deleteIfZero) {
                    this.deleteElement(elementName);
                } else {
                    elements.put(elementName, 0f);
                }
            }
        }
    }

    public void deleteElement(MagicElement elementName) {
        if (this.containsElement(elementName)) {
            elements.remove(elementName);
        }
    }

    public void setReceiveSpeed(float receiveSpeed) {
        this.receiveSpeed = receiveSpeed;
    }


    // 需要一直调用的方法 一次会扣除一次目标元素
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

        if (this.containsElement(element)) {
            float remainingValue = other.getElementValue(element);
            float amountToReceive = Math.min(remainingValue, this.receiveSpeed);

            this.addElement(element, amountToReceive);
            other.removeElement(element, amountToReceive, false);
        }
    }

    public void readFromNBT(CompoundTag nbt) {
        this.elements.clear();
        ListTag list = nbt.getList("MagicElements", 10);

        for(int j = 0; j < list.size(); ++j) {
            CompoundTag rs = list.getCompound(j);
            if (rs.contains("key")) {
                this.addElement(ElementsRegistry.getElement(rs.getString("key")), rs.getInt("amount"));
            }
        }
    }

    public void readFromNBT(CompoundTag nbt, String label) {
        this.elements.clear();
        ListTag list = nbt.getList(label, 10);

        for(int j = 0; j < list.size(); ++j) {
            CompoundTag rs = list.getCompound(j);
            if (rs.contains("key")) {
                this.addElement(ElementsRegistry.getElement(rs.getString("key")), rs.getInt("amount"));
            }
        }

    }

    public void writeToNBT(CompoundTag nbt) {
        ListTag list = new ListTag();
        elements.forEach((element, value) -> {
            CompoundTag n = new CompoundTag();
            n.putString("key", element.getName());
            n.putFloat("amount", value);
            list.add(n);
        });
        nbt.put("MagicElements", list);
    }

    public void writeToNBT(CompoundTag CompoundTag, String label) {
        ListTag list = new ListTag();
        elements.forEach((element, value) -> {
            CompoundTag n = new CompoundTag();
            n.putString("key", element.getName());
            n.putFloat("amount", value);
            list.add(n);
        });
        CompoundTag.put(label, list);
    }

    public String toString() {
        StringBuilder text = new StringBuilder("Storage Elements:\n");
        for (MagicElement element : this.getElements()) {
            text.append(element.getName()).append(" : ").append(this.getElementValue(element)).append("\n");
        }
        return text.toString();
    }

}
