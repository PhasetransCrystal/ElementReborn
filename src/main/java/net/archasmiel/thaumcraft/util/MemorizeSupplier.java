package net.archasmiel.thaumcraft.util;

import java.util.function.Supplier;

public class MemorizeSupplier<T> implements Supplier<T> {
    private final Supplier<T> origin;
    private boolean cached = false;
    private T cache = null;

    public MemorizeSupplier(Supplier<T> origin) {
        this.origin = origin;
    }

    @Override
    public T get() {
        if (!this.cached) {
            this.cached = true;
            this.cache = this.origin.get();
        }
        return this.cache;
    }
}
