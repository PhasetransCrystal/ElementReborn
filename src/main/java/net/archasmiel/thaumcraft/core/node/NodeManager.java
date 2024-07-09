package net.archasmiel.thaumcraft.core.node;

import net.archasmiel.thaumcraft.block.entity.NodeBlockEntity;
import net.archasmiel.thaumcraft.core.element.ElementsRegistry;
import net.archasmiel.thaumcraft.core.element.MagicElement;
import net.archasmiel.thaumcraft.core.element.StorageElements;
import net.archasmiel.thaumcraft.element.TCMagicElements;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;

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
        registerBiomesTag(BiomeTags.IS_JUNGLE, TCMagicElements.EARTH);
        registerBiomesTag(BiomeTags.IS_FOREST, TCMagicElements.EARTH);
        registerBiomesTag(BiomeTags.IS_MOUNTAIN, TCMagicElements.EARTH);
        registerBiomesTag(BiomeTags.IS_TAIGA, TCMagicElements.EARTH);
        registerBiomesTag(BiomeTags.IS_HILL, TCMagicElements.EARTH);

        registerBiomesTag(BiomeTags.IS_OCEAN, TCMagicElements.WATER);
        registerBiomesTag(BiomeTags.IS_DEEP_OCEAN, TCMagicElements.WATER);
        registerBiomesTag(BiomeTags.IS_RIVER, TCMagicElements.WATER);
        registerBiomesTag(BiomeTags.HAS_OCEAN_RUIN_COLD, TCMagicElements.WATER);

        registerBiomesTag(BiomeTags.IS_NETHER, TCMagicElements.FIRE);
        registerBiomesTag(BiomeTags.IS_BADLANDS, TCMagicElements.FIRE);
        registerBiomesTag(BiomeTags.SNOW_GOLEM_MELTS, TCMagicElements.FIRE);

        registerBiomesTag(BiomeTags.HAS_VILLAGE_PLAINS, TCMagicElements.AIR);

        registerBiomesTag(BiomeTags.HAS_VILLAGE_SNOWY, TCMagicElements.ORDER);
        registerBiomesTag(BiomeTags.SPAWNS_WHITE_RABBITS, TCMagicElements.ORDER);

        registerBiomesTag(BiomeTags.HAS_CLOSER_WATER_FOG,TCMagicElements.ENTROPY);
    }


    public Map<TagKey<Biome>, MagicElement> getTagKeyElements() {
        return tagKeyElements;
    }
}
