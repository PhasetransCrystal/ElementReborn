package net.archasmiel.thaumcraft.core.item;

import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class TCProperties {
    public static final TCProperties EMPTY = new TCProperties.Builder().build();
    private final List<Component> tooltips;
    private final String name;

    private TCProperties(TCProperties.Builder builder) {
        this.tooltips = builder.tooltips;
        this.name = builder.name;
    }

    public List<Component> getTooltips() {
        return this.tooltips;
    }

    public String getName() {
        return this.name;
    }


    public static class Builder {
        public final List<Component> tooltips = new ArrayList<>();

        public String name = "unknown";

        public Builder setName(String name) {
            this.name = name;
            return this;
        }
        public Builder addTooltips(int count) {
            for (int i = 0; i < count; i++) {
                tooltips.add(Component.translatable("item.thaumcraft." + name + ".tooltip" + (i + 1)));
            }
            return this;
        }

        public Builder addShiftedTooltips(int count) {
            for (int i = 0; i < count; i++) {
                tooltips.add(Component.translatable("item.thaumcraft." + name + ".shifted_tooltip" + (i + 1)));
            }
            return this;
        }

        public TCProperties build() {
            return new TCProperties(this);
        }
    }
}
