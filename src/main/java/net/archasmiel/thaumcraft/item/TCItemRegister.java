package net.archasmiel.thaumcraft.item;

import net.archasmiel.thaumcraft.Thaumcraft;
import net.archasmiel.thaumcraft.ThaumcraftTabs;
import net.archasmiel.thaumcraft.core.item.TCItem;
import net.archasmiel.thaumcraft.core.wands.WandRod;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class TCItemRegister {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Thaumcraft.MODID);

    public static final DeferredItem<Item> staff_rod_blaze = register("staff_rod_blaze", () -> new WandRod(new TCItem.TCProperties.Builder(new Item.Properties()).build(), 50));

    public static <T extends Item> DeferredItem<T> register(String name, Supplier<T> item) {
        DeferredItem<T> i = ITEMS.register(name, item);
        ThaumcraftTabs.ITEMS.add(i);
        return i;
    }
}
