package net.ssorangecaty.elementreborn.util;

import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class MemorizeSupplier<T> implements Supplier<T> {
    private Supplier<T> origin;
    private boolean cached = false;
    private T cache = null;

    public MemorizeSupplier(@Nullable Supplier<T> origin) {
        this.origin = origin;
    }

    public void setOrigin(Supplier<T> origin) {
        this.origin = origin;
    }

    @Override
    public T get() {
        if (!this.cached && this.origin != null) {
            this.cached = true;
            this.cache = this.origin.get();
        }
        return this.cache;
    }
}
