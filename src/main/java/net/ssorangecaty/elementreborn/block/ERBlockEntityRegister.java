package net.ssorangecaty.elementreborn.block;

import net.ssorangecaty.elementreborn.ElementReborn;
import net.ssorangecaty.elementreborn.block.entity.ArcaneWorkBenchBlockEntity;
import net.ssorangecaty.elementreborn.block.entity.NodeBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ERBlockEntityRegister {
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ElementReborn.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ArcaneWorkBenchBlockEntity>> ARCANE_WORKBENCH = register("arcane_workbench", () -> BlockEntityType.Builder.of(ArcaneWorkBenchBlockEntity::new, ERBlockRegister.ARCANE_WORKBENCH.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<NodeBlockEntity>> NODE = register("node", () -> BlockEntityType.Builder.of(NodeBlockEntity::new, ERBlockRegister.NODE.get()).build(null));


    private static <T extends BlockEntity> DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> register(String name, Supplier<BlockEntityType<T>> type) {
        return REGISTRY.register(name, type);
    }
}
