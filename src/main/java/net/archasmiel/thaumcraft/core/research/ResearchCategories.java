package net.archasmiel.thaumcraft.core.research;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class ResearchCategories {

    private static final Map<String, ResearchCategoryList> categories = new LinkedHashMap<>();

    public ResearchCategories() {
    }

    public static Optional<ResearchCategoryList> getResearchList(String key) {
        return Optional.ofNullable(categories.get(key));
    }

    public static Component getCategoryName(String key) {
        return Component.translatable("tc.research_category." + key);
    }

    public static Optional<ResearchItem> getResearch(String key) {
        return categories.values().stream()
                .flatMap(category -> category.research.values().stream())
                .filter(researchItem -> researchItem.key.equals(key))
                .findFirst();
    }

    public static void registerCategory(String key, ResourceLocation icon, ResourceLocation background) {
        getResearchList(key).ifPresentOrElse(
                list -> {},
                () -> categories.put(key, new ResearchCategoryList(icon, background))
        );
    }

    public static void addResearch(ResearchItem researchItem) {
        Optional<ResearchCategoryList> categoryList = getResearchList(researchItem.category);
        categoryList.ifPresentOrElse(
                category -> {
                    if (!category.research.containsKey(researchItem.key)) {
                        if (!researchItem.isVirtual()) {
                            category.research.values().stream()
                                    .filter(ri -> ri.displayColumn == researchItem.displayColumn
                                            && ri.displayRow == researchItem.displayRow)
                                    .findFirst()
                                    .ifPresentOrElse(
                                            ri -> System.err.println("[Thaumcraft] Research [" + researchItem.getName() + "] not added as it overlaps with existing research [" + ri.getName() + "]"),
                                            () -> {
                                                category.research.put(researchItem.key, researchItem);
                                                updateDisplayBounds(category, researchItem);
                                            }
                                    );
                        }
                    }
                },
                () -> System.err.println("[Thaumcraft] Cannot add research [" + researchItem.getName() + "] as category is not registered.")
        );
    }

    private static void updateDisplayBounds(ResearchCategoryList category, ResearchItem researchItem) {
        category.minDisplayColumn = Math.min(category.minDisplayColumn, researchItem.displayColumn);
        category.minDisplayRow = Math.min(category.minDisplayRow, researchItem.displayRow);
        category.maxDisplayColumn = Math.max(category.maxDisplayColumn, researchItem.displayColumn);
        category.maxDisplayRow = Math.max(category.maxDisplayRow, researchItem.displayRow);
    }
}