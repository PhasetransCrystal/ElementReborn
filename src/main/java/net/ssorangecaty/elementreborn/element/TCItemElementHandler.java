package net.ssorangecaty.elementreborn.element;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.ssorangecaty.elementreborn.ElementReborn;
import net.ssorangecaty.elementreborn.core.element.ElementsRegistry;
import net.ssorangecaty.elementreborn.core.element.MagicElement;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import org.lwjgl.system.NonnullDefault;

import java.util.HashMap;
import java.util.Map;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = ElementReborn.MODID)
public class TCItemElementHandler {
    public static final Map<Holder<Item>, Object2IntMap<ResourceLocation>> unRegistered = new HashMap<>();

    private static ImmutableMap<Item, ImmutableMap<MagicElement, Integer>> itemElement;

    public static ImmutableMap<Item, ImmutableMap<MagicElement, Integer>> getItemElement() {
        return itemElement;
    }

    @NonnullDefault
    public static ImmutableMap<MagicElement, Integer> getElement(Item item) {
        return itemElement == null ? null : itemElement.getOrDefault(item, ImmutableMap.of());
    }


    @SubscribeEvent
    public static void init(FMLLoadCompleteEvent event) {
        ImmutableMap.Builder<Item, ImmutableMap<MagicElement, Integer>> builder = ImmutableMap.builder();
        unRegistered.forEach((item, countMap) -> {
            builder.put(item.value(),
                    countMap.object2IntEntrySet().stream()
                            .map(s -> Map.entry(ElementsRegistry.REGISTRY_ELEMENTS.get(s.getKey()), s.getIntValue()))
                            .collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, Map.Entry::getValue))
            );
        });
        unRegistered.clear();
        itemElement = builder.build();
    }

}
