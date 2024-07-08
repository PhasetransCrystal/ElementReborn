package net.archasmiel.thaumcraft.core.element;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.archasmiel.thaumcraft.element.TCMagicElements;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class StorageElements {
    public static final Codec<StorageElements> CODEC = ExtraCodecs.JSON.comapFlatMap(x -> {
        try {
            Map<MagicElement, Float> map = new HashMap<>();
            for (Map.Entry<String, JsonElement> entry : x.getAsJsonObject().asMap().entrySet())
                map.put(ElementsRegistry.getElement(entry.getKey()), entry.getValue().getAsFloat());
            return DataResult.success(new StorageElements(map));
        } catch (Exception e) {
            return DataResult.error(() -> "Cannot parse Json: " + e);
        }
    }, elements -> {
        JsonObject obj = new JsonObject();
        for (Map.Entry<MagicElement, Float> entry : elements.elements.entrySet())
            obj.add(ElementsRegistry.getId(entry.getKey()).toString(), new JsonPrimitive(entry.getValue()));
        return obj;
    });
    public static final StreamCodec<FriendlyByteBuf, StorageElements> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public @NotNull StorageElements decode(FriendlyByteBuf p_320376_) {
            return new StorageElements(p_320376_.readNbt());
        }

        @Override
        public void encode(FriendlyByteBuf p_320158_, StorageElements p_320396_) {
            CompoundTag tag = new CompoundTag();
            p_320396_.writeToNBT(tag);
            p_320158_.writeNbt(tag);
        }
    };

    private final Map<MagicElement, Float> elements;

    public StorageElements(Map<MagicElement, Float> elements) {
        this.elements = elements;
    }

    public StorageElements(CompoundTag tag) {
        this.elements = new HashMap<>();
        this.readFromNBT(tag);
    }

    public float getElementValue(MagicElement element) {
        return elements.get(element);
    }

    public float getOrDefault(MagicElement element) {
        return elements.getOrDefault(element, 0f);
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

    public StorageElements addElement(MagicElement element, float value) {
        if (this.containsElement(element)) {
            elements.put(element, elements.get(element) + value);
        } else {
            elements.put(element, value);
        }
        return this;
    }

    public StorageElements setElement(MagicElement element, float value) {
        elements.put(element, value);
        return this;
    }

    public void copyFrom(StorageElements other) {
        for (Map.Entry<MagicElement, Float> entry : other.elements.entrySet()) {
            this.addElement(entry.getKey(), entry.getValue());
        }
    }

    public StorageElements removeElement(MagicElement elementName, float value, boolean deleteIfZero) {
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
        return this;
    }

    public void deleteElement(MagicElement elementName) {
        if (this.containsElement(elementName)) {
            elements.remove(elementName);
        }
    }

    public void readFromNBT(CompoundTag nbt) {
        this.elements.clear();
        ListTag list = nbt.getList("MagicElements", 10);

        for (int j = 0; j < list.size(); ++j) {
            CompoundTag rs = list.getCompound(j);
            if (rs.contains("key")) {
                this.addElement(ElementsRegistry.getElementByName(rs.getString("key")), rs.getInt("amount"));
            }
        }
    };

    public void readFromNBT(CompoundTag nbt, String label) {
        this.elements.clear();
        ListTag list = nbt.getList(label, 10);

        for (int j = 0; j < list.size(); ++j) {
            CompoundTag rs = list.getCompound(j);
            if (rs.contains("key")) {
                this.addElement(ElementsRegistry.getElementByName(rs.getString("key")), rs.getInt("amount"));
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

    public void reduceRootElements(float vis) {
        for (MagicElement element : TCMagicElements.DEFAULT_ELEMENTS)
            if (this.elements.containsKey(element))
                this.elements.put(element, this.elements.get(element) - vis);
    }

    public static StorageElements createByElement(MagicElement element, float value) {
        return new StorageElements(Map.of(element, value));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof StorageElements another)) return false;
        if (this.size() != another.size()) return false;
        for (MagicElement element : this.getElements())
            if (this.getElementValue(element) != another.getOrDefault(element))
                return false;
        return true;
    }

    @Override
    public int hashCode() {
        return this.elements.hashCode();
    }
}
