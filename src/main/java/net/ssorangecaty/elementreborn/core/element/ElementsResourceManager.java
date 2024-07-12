package net.ssorangecaty.elementreborn.core.element;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.ssorangecaty.elementreborn.ElementReborn;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ElementsResourceManager implements ResourceManagerReloadListener {
    @Override
    public void onResourceManagerReload(ResourceManager manager) {
        List<JsonObject> objects = new ArrayList<>();
        for (Map.Entry<ResourceLocation, Resource> entry : manager.listResources(ElementReborn.MODID + "/elements", p -> p.getPath().endsWith(".json")).entrySet())
            try (InputStream stream = entry.getValue().open()) {
                objects.add(JsonParser.parseReader(new InputStreamReader(stream)).getAsJsonObject());
            } catch (Exception e) {
                ElementReborn.LOGGER.error("Error occurred while loading resource json {}", entry.getKey().toString(), e);
            }
        ElementsManager.reload(objects);
    }
}
