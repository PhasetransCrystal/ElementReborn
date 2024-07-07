package net.archasmiel.thaumcraft.core.element;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.archasmiel.thaumcraft.Thaumcraft;
import net.archasmiel.thaumcraft.util.MemorizeSupplier;

import java.util.*;
import java.util.function.Supplier;

public class ElementsManager {
    public static final Map<String, MagicElement> ELEMENTS = new HashMap<>();

    public static void reload(List<JsonObject> objects) {
        ELEMENTS.clear();
        List<ElementHolder> holderMap = new ArrayList<>();
        for (JsonObject obj : objects)
            try {
                String id = obj.get("id").getAsString();
                int color = obj.get("color").getAsInt();
                List<String> components = obj.get("components").getAsJsonArray().asList().stream().map(JsonElement::getAsString).toList();
                if (obj.has("blend")) {
                    int blend = obj.get("blend").getAsInt();
                    holderMap.add(new ElementHolder(id, color, blend, components, false));
                } else holderMap.add(new ElementHolder(id, color, components, false));
            } catch (Exception e) {
                Thaumcraft.LOGGER.error("Failed to load {}", obj, e);
            }
        load(holderMap);
    }

    private static void load(List<ElementHolder> holderMap) {
        Map<String, MemorizeSupplier<MagicElement>> supplierMap = new HashMap<>();
        for (ElementHolder holder : holderMap) supplierMap.put(holder.id, new MemorizeSupplier<>(null));
        for (ElementHolder holder : holderMap) {
            List<MemorizeSupplier<MagicElement>> suppliers = holder.components.stream().map(supplierMap::get).filter(Objects::nonNull).toList();
            supplierMap.get(holder.id).setOrigin(() -> new MagicElement(holder.id, holder.color, holder.blend, suppliers.stream().map(Supplier::get).toArray(MagicElement[]::new)));
        }
        for (Map.Entry<String, MemorizeSupplier<MagicElement>> entry : supplierMap.entrySet())
            ELEMENTS.put(entry.getKey(), entry.getValue().get());
    }


    record ElementHolder(String id, int color, int blend, List<String> components, boolean loaded) {
        public ElementHolder(String id, int color, List<String> components, boolean loaded) {
            this(id, color, 1, components, loaded);
        }
    }
}
