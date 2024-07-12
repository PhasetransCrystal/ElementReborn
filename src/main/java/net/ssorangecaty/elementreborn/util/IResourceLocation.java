package net.ssorangecaty.elementreborn.util;

import net.ssorangecaty.elementreborn.ElementReborn;
import net.minecraft.resources.ResourceLocation;

public class IResourceLocation {
    public static ResourceLocation create(String namespace, String path) {
        return ResourceLocation.tryBuild(namespace, path);
    }

    public static ResourceLocation create(String path) {
        return ResourceLocation.tryBuild(ElementReborn.MODID, path);
    }

    public static ResourceLocation createByVanilla(String path) {
        return ResourceLocation.tryBuild("minecraft", path);
    }

}
