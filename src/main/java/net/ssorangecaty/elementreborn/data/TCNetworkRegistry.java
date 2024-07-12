package net.ssorangecaty.elementreborn.data;

import net.ssorangecaty.elementreborn.ElementReborn;
import net.ssorangecaty.elementreborn.block.entity.NodeBlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = ElementReborn.MODID, bus = EventBusSubscriber.Bus.MOD)
public class TCNetworkRegistry {

    @SubscribeEvent
    public static void registry(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1.0.0");
        registrar.commonToClient(NodeBlockEntity.DataSyn.TYPE, NodeBlockEntity.DataSyn.CODEC, new DirectionalPayloadHandler<>((payload, context) -> {
            context.enqueueWork(() -> ((NodeBlockEntity) context.player().level().getBlockEntity(payload.pos())).loadNetwork(payload.nbt()));
        }, (payload, context) -> {
        }));
    }
}
