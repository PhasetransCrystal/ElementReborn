package net.ssorangecaty.elementreborn;

import com.mojang.logging.LogUtils;
import net.ssorangecaty.elementreborn.block.ERBlockEntityRegister;
import net.ssorangecaty.elementreborn.block.ERBlockRegister;
import net.ssorangecaty.elementreborn.client.render.NodeRender;
import net.ssorangecaty.elementreborn.data.ERDataComponentRegister;
import net.ssorangecaty.elementreborn.core.recipe.TCRecipeRegister;
import net.ssorangecaty.elementreborn.element.ERMagicElements;
import net.ssorangecaty.elementreborn.inventory.ERInventoryRegister;
import net.ssorangecaty.elementreborn.item.ERItemRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

@Mod(ElementReborn.MODID)
public class ElementReborn {
    public static final String MODID = "elementreborn";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ElementReborn(IEventBus bus, ModContainer modContainer) {
        bus.addListener(this::commonSetup);

        ERBlockRegister.BLOCKS.register(bus);
        ERItemRegister.ITEMS.register(bus);
        ERBlockEntityRegister.REGISTRY.register(bus);
        ElementRebornTabs.CREATIVE_MODE_TABS.register(bus);
        ERInventoryRegister.REGISTRY.register(bus);
        TCRecipeRegister.RECIPE_TYPES.register(bus);
        TCRecipeRegister.RECIPE_SERIALIZERS.register(bus);
        ERMagicElements.ELEMENTS.register(bus);
        ERDataComponentRegister.REGISTRY.register(bus);
        NeoForge.EVENT_BUS.register(this);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }

        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(ERBlockEntityRegister.NODE.get(), new NodeRender<>());
        }
    }
}
