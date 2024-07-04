package net.archasmiel.thaumcraft.core.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TCItem extends Item {
    public final TCProperties tcProperties;

    public TCItem(Properties properties,TCProperties tcProperties) {
        super(properties);
        this.tcProperties = tcProperties;
    }


    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, Item.@NotNull TooltipContext tooltipContext, @NotNull List<Component> components, @NotNull TooltipFlag flag) {
        components.addAll(tcProperties.getTooltips());
    }



}
