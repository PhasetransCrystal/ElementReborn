package net.archasmiel.thaumcraft.util;

import net.archasmiel.thaumcraft.Thaumcraft;
import net.minecraft.resources.ResourceLocation;

public class IResourceLocation {
    public static ResourceLocation create(String namespace, String path) {
        return ResourceLocation.tryBuild(namespace, path);
    }

    public static ResourceLocation create(String path) {
        return ResourceLocation.tryBuild(Thaumcraft.MODID, path);
    }

    public static ResourceLocation createByVanilla(String path) {
        return ResourceLocation.tryBuild("minecraft", path);
    }

}
