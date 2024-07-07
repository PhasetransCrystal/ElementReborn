package net.archasmiel.thaumcraft;

import net.archasmiel.thaumcraft.block.TCBlockRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ThaumcraftTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Thaumcraft.MODID);
    public static final List<DeferredItem<? extends Item>> ITEMS = new ArrayList<>();
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> THAUMCRAFT_TAB = CREATIVE_MODE_TABS.register("thaumcraft_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.thaumcraft")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(TCBlockRegister.ARCANE_WORKBENCH.asItem()::getDefaultInstance)
            .displayItems((parameters, output) -> ITEMS.stream().map(Supplier::get).map(Item::getDefaultInstance).forEach(output::accept))
            .build());
}
