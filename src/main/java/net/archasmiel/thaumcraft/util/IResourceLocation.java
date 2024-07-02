package net.archasmiel.thaumcraft.util;

import net.archasmiel.thaumcraft.Thaumcraft;
import net.minecraft.resources.ResourceLocation;

public class IResourceLocation {
    public static ResourceLocation create(String namespace, String path) {
        ResourceLocation rl = ResourceLocation.tryBuild(namespace, path);

        if (rl == null) {
            throw new IllegalArgumentException("Invalid resource location: " + namespace + ":" + path);
        }

        return rl;
    }

    public static ResourceLocation create(String path) {
        ResourceLocation rl = ResourceLocation.tryBuild(Thaumcraft.MODID, path);

        if (rl == null) {
            throw new IllegalArgumentException("Invalid resource location: " + Thaumcraft.MODID + ":" + path);
        }

        return rl;
    }

    public static ResourceLocation createByVanilla(String path) {
        ResourceLocation rl = ResourceLocation.tryBuild("minecraft", path);

        if (rl == null) {
            throw new IllegalArgumentException("Invalid resource location: " + "minecraft" + ":" + path);
        }

        return rl;
    }
}
