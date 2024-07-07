package net.archasmiel.thaumcraft.core.element;

import net.minecraft.world.item.Items;

import java.util.HashMap;

public interface IStorageElementsAble {
    StorageElements elements = new StorageElements(new HashMap<>());

    default StorageElements getStorage() {
        return elements;
    }
}
