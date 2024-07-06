package net.archasmiel.thaumcraft.inventory;

import net.archasmiel.thaumcraft.Thaumcraft;
import net.archasmiel.thaumcraft.inventory.menu.ArcaneWorkBenchMenu;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class TCInventoryRegister {
    private static final DeferredRegister<MenuType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.MENU, Thaumcraft.MODID);

    public static final MenuType<ArcaneWorkBenchMenu> ARCANE_WORKBENCH = registerMenu("arcane_workbench", ArcaneWorkBenchMenu::new);

    public static <T extends AbstractContainerMenu> MenuType<T> registerMenu(String name, MenuType.MenuSupplier<T> menu) {
        MenuType<T> menuType = new MenuType<>(menu, FeatureFlags.VANILLA_SET);
        RECIPE_TYPES.register(name,() -> menuType);
        return menuType;
    }

}
