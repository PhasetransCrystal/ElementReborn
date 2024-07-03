package net.archasmiel.thaumcraft.core.research;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;


/**
 * 管理研究类别和研究项目的类。
 * <p>
 * 提供注册研究类别、获取研究列表、注册研究项目等功能。
 */
public class ResearchCategories {

    /**
     * 存储所有研究类别的映射，其中键是类别的标识符，值是对应的研究类别列表。
     */
    private static final Map<String, ResearchCategoryList> categories = new LinkedHashMap<>();

    public ResearchCategories() {
    }

    /**
     * 根据提供的键获取研究类别列表。
     *
     * @param key 类别的唯一标识符。
     * @return 一个包含研究类别列表的Optional对象，如果没有找到，则为Optional.empty()。
     */
    public static Optional<ResearchCategoryList> getResearchList(String key) {
        return Optional.ofNullable(categories.get(key));
    }

    /**
     * 获取研究类别的本地化名称。
     *
     * @param key 类别的唯一标识符。
     * @return 一个包含本地化名称的Component对象。
     */
    public static Component getCategoryName(String key) {
        return Component.translatable("tc.research_category." + key);
    }

    /**
     * 根据提供的键获取研究项目。
     *
     * @param key 研究项目的唯一标识符。
     * @return 一个包含研究项目的Optional对象，如果没有找到，则为Optional.empty()。
     */
    public static Optional<ResearchItem> getResearch(String key) {
        return categories.values().stream()
                .flatMap(category -> category.research.values().stream())
                .filter(researchItem -> researchItem.key.equals(key))
                .findFirst();
    }

    /**
     * 注册一个新的研究类别。
     *
     * @param key          类别的唯一标识符。
     * @param icon         类别的图标。
     * @param background   类别的背景图。
     */
    public static void registerCategory(String key, ResourceLocation icon, ResourceLocation background) {
        getResearchList(key).ifPresentOrElse(
                list -> {},
                () -> categories.put(key, new ResearchCategoryList(icon, background))
        );
    }

    /**
     * 向指定的研究类别添加研究项目。
     *
     * @param researchItem 要添加的研究项目。
     */
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

    /**
     * 更新研究类别的显示边界。
     *
     * @param category 研究类别列表。
     * @param researchItem 研究项目，包含显示列和行信息。
     */
    private static void updateDisplayBounds(ResearchCategoryList category, ResearchItem researchItem) {
        category.minDisplayColumn = Math.min(category.minDisplayColumn, researchItem.displayColumn);
        category.minDisplayRow = Math.min(category.minDisplayRow, researchItem.displayRow);
        category.maxDisplayColumn = Math.max(category.maxDisplayColumn, researchItem.displayColumn);
        category.maxDisplayRow = Math.max(category.maxDisplayRow, researchItem.displayRow);
    }
}