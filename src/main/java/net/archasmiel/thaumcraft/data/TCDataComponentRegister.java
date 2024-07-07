package net.archasmiel.thaumcraft.data;

import net.archasmiel.thaumcraft.Thaumcraft;
import net.archasmiel.thaumcraft.core.element.StorageElements;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class TCDataComponentRegister {
    public static final DeferredRegister<DataComponentType<?>> REGISTRY = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, Thaumcraft.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<StorageElements>> STORAGE_ELEMENTS = register("storage_elements", () -> p_341856_ -> p_341856_.persistent(StorageElements.CODEC).networkSynchronized(StorageElements.STREAM_CODEC).cacheEncoding());

    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String p_332092_, Supplier<UnaryOperator<DataComponentType.Builder<T>>> p_331261_) {
        return REGISTRY.register(p_332092_, () -> p_331261_.get().apply(DataComponentType.builder()).build());
    }
}
