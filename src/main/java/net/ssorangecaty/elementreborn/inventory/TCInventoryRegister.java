package net.ssorangecaty.elementreborn.inventory;

import net.ssorangecaty.elementreborn.ElementReborn;
import net.ssorangecaty.elementreborn.inventory.container.ArcaneWorkBenchScreen;
import net.ssorangecaty.elementreborn.inventory.menu.ArcaneWorkBenchMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class TCInventoryRegister {
    public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(Registries.MENU, ElementReborn.MODID);

    public static final MenuType<ArcaneWorkBenchMenu> ARCANE_WORKBENCH = registerMenu("arcane_workbench", ArcaneWorkBenchMenu::new);

    public static <T extends AbstractContainerMenu> MenuType<T> registerMenu(String name, MenuType.MenuSupplier<T> menu) {
        MenuType<T> menuType = new MenuType<>(menu, FeatureFlags.VANILLA_SET);
        REGISTRY.register(name, () -> menuType);
        return menuType;
    }

    @SubscribeEvent
    public static void registerScreen(RegisterMenuScreensEvent event) {
        event.register(TCInventoryRegister.ARCANE_WORKBENCH, ArcaneWorkBenchScreen::new);
    }
}
