package net.archasmiel.thaumcraft.item.relics;

import net.archasmiel.thaumcraft.core.node.ShowMagicElementAble;
import net.minecraft.world.item.Item;

public class ItemThaumometer extends Item implements ShowMagicElementAble {

    public ItemThaumometer(Properties properties) {
        super(properties);
    }

    @Override
    public boolean showNode() {
        return true;
    }

    @Override
    public boolean showElements() {
        return true;
    }
}
