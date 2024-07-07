package net.archasmiel.thaumcraft.block;

import net.archasmiel.thaumcraft.Thaumcraft;
import net.archasmiel.thaumcraft.block.entity.ArcaneWorkBenchBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class TCBlockEntityRegister {
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Thaumcraft.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ArcaneWorkBenchBlockEntity>> ARCANE_WORKBENCH = register("arcane_workbench", () -> BlockEntityType.Builder.of(ArcaneWorkBenchBlockEntity::new, TCBlockRegister.ARCANE_WORKBENCH.get()).build(null));

    private static <T extends BlockEntity> DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> register(String name, Supplier<BlockEntityType<T>> type) {
        return REGISTRY.register(name, type);
    }
}
