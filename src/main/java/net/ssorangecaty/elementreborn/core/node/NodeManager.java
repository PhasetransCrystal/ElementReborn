package net.ssorangecaty.elementreborn.core.node;

import net.ssorangecaty.elementreborn.core.element.MagicElement;
import net.ssorangecaty.elementreborn.element.ERMagicElements;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

import java.util.*;

public class NodeManager {
    public static NodeManager instance;
    public final Map<TagKey<Biome>, MagicElement> tagKeyElements = new HashMap<>();

    public void registerBiomesTag(TagKey<Biome> tag, MagicElement element){
        tagKeyElements.put(tag, element);
    }

    public static NodeManager getInstance() {
        if (instance == null) {
            instance = new NodeManager();
        }
        return instance;
    }

    public NodeManager(){
        initBiomesTags();
    }

    public void initBiomesTags(){
        registerBiomesTag(BiomeTags.IS_JUNGLE, ERMagicElements.EARTH);
        registerBiomesTag(BiomeTags.IS_FOREST, ERMagicElements.EARTH);
        registerBiomesTag(BiomeTags.IS_MOUNTAIN, ERMagicElements.EARTH);
        registerBiomesTag(BiomeTags.IS_TAIGA, ERMagicElements.EARTH);
        registerBiomesTag(BiomeTags.IS_HILL, ERMagicElements.EARTH);

        registerBiomesTag(BiomeTags.IS_OCEAN, ERMagicElements.WATER);
        registerBiomesTag(BiomeTags.IS_DEEP_OCEAN, ERMagicElements.WATER);
        registerBiomesTag(BiomeTags.IS_RIVER, ERMagicElements.WATER);
        registerBiomesTag(BiomeTags.HAS_OCEAN_RUIN_COLD, ERMagicElements.WATER);

        registerBiomesTag(BiomeTags.IS_NETHER, ERMagicElements.FIRE);
        registerBiomesTag(BiomeTags.IS_BADLANDS, ERMagicElements.FIRE);
        registerBiomesTag(BiomeTags.SNOW_GOLEM_MELTS, ERMagicElements.FIRE);

        registerBiomesTag(BiomeTags.HAS_VILLAGE_PLAINS, ERMagicElements.WIND);

        registerBiomesTag(BiomeTags.HAS_VILLAGE_SNOWY, ERMagicElements.ORDER);
        registerBiomesTag(BiomeTags.SPAWNS_WHITE_RABBITS, ERMagicElements.ORDER);

        registerBiomesTag(BiomeTags.HAS_CLOSER_WATER_FOG, ERMagicElements.CHAOS);
    }


    public Map<TagKey<Biome>, MagicElement> getTagKeyElements() {
        return tagKeyElements;
    }
}
