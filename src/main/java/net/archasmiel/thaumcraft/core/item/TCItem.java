package net.archasmiel.thaumcraft.core.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TCItem extends Item {
    public final TCProperties tcProperties;

    public TCItem(TCProperties tcProperties) {
        super(tcProperties.getProperties());
        this.tcProperties = tcProperties;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, Item.@NotNull TooltipContext tooltipContext, @NotNull List<Component> components, @NotNull TooltipFlag flag) {
        if(flag.isAdvanced()){
            components.addAll(tcProperties.getTooltips());
            components.addAll(tcProperties.getShifted_tooltips());
        }else{
            components.addAll(tcProperties.getTooltips());
            components.add(Component.translatable("item.tooltips.shift_message"));
        }
    }

    public static class TCProperties {
        public static final TCProperties EMPTY = new TCProperties.Builder(new Item.Properties()).build();
        private final List<Component> tooltips;
        public final List<Component> shifted_tooltips;
        private final String name;
        private final Item.Properties properties;

        private TCProperties(TCProperties.Builder builder) {
            this.tooltips = builder.tooltips;
            this.name = builder.name;
            this.properties = builder.properties;
            this.shifted_tooltips = builder.shifted_tooltips;
        }

        public List<Component> getTooltips() {
            return this.tooltips;
        }

        public List<Component> getShifted_tooltips() {
            return shifted_tooltips;
        }

        public String getName() {
            return this.name;
        }

        public Item.Properties getProperties() {
            return properties;
        }

        public static class Builder {
            public final List<Component> tooltips = new ArrayList<>();
            public final List<Component> shifted_tooltips = new ArrayList<>();
            public final Item.Properties properties;

            public Builder(Item.Properties properties){
                this.properties = properties;
            }

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
                    shifted_tooltips.add(Component.translatable("item.thaumcraft." + name + ".shifted_tooltip" + (i + 1)));
                }
                return this;
            }

            public TCProperties build() {
                return new TCProperties(this);
            }
        }
    }

}
