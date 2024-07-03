package net.archasmiel.thaumcraft.core.research;

import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class ResearchCategoryList {
    public int minDisplayColumn;
    public int minDisplayRow;
    public int maxDisplayColumn;
    public int maxDisplayRow;
    public ResourceLocation icon;
    public ResourceLocation background;
    public final Map<String, ResearchItem> research = new HashMap<>();

    public ResearchCategoryList(ResourceLocation icon, ResourceLocation background) {
        this.icon = icon;
        this.background = background;
    }

    public void updateDisplayBounds(int minColumn, int minRow) {
        this.minDisplayColumn = minColumn;
        this.minDisplayRow = minRow;
    }
}
