package net.archasmiel.thaumcraft.core.element;

import net.archasmiel.thaumcraft.Thaumcraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;

import java.util.Map;

public class ElementsResourceManager implements ResourceManagerReloadListener {
    @Override
    public void onResourceManagerReload(ResourceManager manager) {
        for (Map.Entry<ResourceLocation, Resource> entry : manager.listResources(Thaumcraft.MODID + "/elements", p -> p.getPath().endsWith(".json")).entrySet()) {

        }
    }
}
