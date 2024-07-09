package net.archasmiel.thaumcraft.item;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.archasmiel.thaumcraft.Thaumcraft;
import net.archasmiel.thaumcraft.ThaumcraftTabs;
import net.archasmiel.thaumcraft.core.element.ElementsRegistry;
import net.archasmiel.thaumcraft.core.element.MagicElement;
import net.archasmiel.thaumcraft.core.item.TCItem;
import net.archasmiel.thaumcraft.core.wands.WandRod;
import net.archasmiel.thaumcraft.element.TCItemElementHandler;
import net.archasmiel.thaumcraft.element.TCMagicElements;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class TCItemRegister {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Thaumcraft.MODID);

    public static final DeferredItem<Item> wand = register("wand", () -> new WandRod(new TCItem.TCProperties.Builder(new Item.Properties().component(DataComponents.MAX_STACK_SIZE, 1)).build(), 50, 1.1f));

    public static <T extends Item> DeferredItem<T> register(String name, Supplier<T> item) {
        DeferredItem<T> i = ITEMS.register(name, item);
        ThaumcraftTabs.ITEMS.add(i);
        return i;
    }

    public static class Builder {
        public final DeferredItem<Item> item;
        private Object2IntMap<ResourceLocation> elements = new Object2IntOpenHashMap<>();

        private Builder(DeferredItem<Item> item) {
            this.item = item;
        }

        public static Builder create(String name, Supplier<Item> item) {
            return new Builder(register(name, item));
        }

        public Builder addElement(ResourceLocation elementId, int count) {
            elements.put(elementId,count);
            return this;
        }

        public Builder addElement(Holder<MagicElement> holder, int count) {
            elements.put(holder.unwrapKey().get().location(),count);
            return this;
        }

        public DeferredItem<Item> build(){
            TCItemElementHandler.unRegistered.put(this.item,elements);
            return item;
        }
    }

}
