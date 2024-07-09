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
    public static final Map<TagKey<Biome>, MagicElement> TagKeyElements = new HashMap<>();

    public static void registerBiomesTag(TagKey<Biome> tag, MagicElement element){
        TagKeyElements.put(tag, element);
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


    public void initNode(NodeBlockEntity node){
        List<MagicElement> preInitElements = new ArrayList<>();
        StorageElements storage = new StorageElements(new HashMap<>());
        if (node.getLevel() != null && (node.getLevel() == null || node.getLevel().isClientSide)) return;
        RandomSource random = node.getLevel().random;
        Holder<Biome> biomes = node.getLevel().getBiome(node.getBlockPos());
        TagKeyElements.forEach((tag, element) -> {
            if (biomes.is(tag) && !preInitElements.contains(element)){
                preInitElements.add(element);
            }
        });
        if(random.nextInt(100) < 25){
            storage.addFrom(randomCompoundElements(random));
        }
        storage.merge(lavaCheck(node));
        storage.addFrom(randomPrimalElements(random, preInitElements));
        if (node.type == null || node.modifier == null){
            randomNodeTypeAndModifier(node);
        }
        node.getStorage().addFrom(storage);
    }

    public void randomNodeTypeAndModifier(NodeBlockEntity node){
        if (node.getLevel() != null && (node.getLevel() == null || node.getLevel().isClientSide)) return;
        RandomSource random = node.getLevel().random;
        int type = random.nextInt(50, 100);
        NodeType nodeType = NodeType.NORMAL;
        NodeModifier modifier = NodeModifier.COMMON;
        if(type < 50){
            int t = random.nextInt(0,100);
            if(t < 3){
                nodeType = NodeType.HUNGRY;
            }else if(t < 15){
                nodeType = NodeType.DARK;
            }else if(t < 30){
                nodeType = NodeType.PURE;
            }else if(t < 45){
                nodeType = NodeType.TAINTED;
            } else if (t < 60){
                nodeType = NodeType.UNSTABLE;
            }
        }

        node.setNodeType(nodeType);
        int m = random.nextInt(0,120);
        if (m < 15){
            modifier = NodeModifier.BRIGHT;
        }else if(m < 30){
            modifier = NodeModifier.FADING;
        }  else if(m < 50){
            modifier = NodeModifier.PALE;
        }
        node.setNodeModifier(modifier);
    }


    public StorageElements lavaCheck(NodeBlockEntity node){
        StorageElements storage = new StorageElements(new HashMap<>());
        if (node.getLevel() != null && (node.getLevel() == null || node.getLevel().isClientSide)) return storage;
        Level level = node.getLevel();
        RandomSource random = level.random;
        BlockPos pos = node.getBlockPos();

        // 获取 xyz 正负3 是否有岩浆
        for (int x = -3; x <= 3; x++) {
            for (int y = -3; y <= 3; y++) {
                for (int z = -3; z <= 3; z++) {
                    if (x == 0 && y == 0 && z == 0) continue;
                    if (level.getBlockState(pos.offset(x, y, z)).getBlock() == Blocks.LAVA) {
                        storage.addElement(TCMagicElements.FIRE, random.nextInt(4,48));
                        return storage;
                    }
                }
            }
        }

        return storage;
    }


    public StorageElements randomCompoundElements(RandomSource random){
        int time = random.nextInt(0,6);
        StorageElements storage = new StorageElements(new HashMap<>());
        List<MagicElement> compoundElements = ElementsRegistry.getCompoundElements();
        for (int i = 0; i < time; i++){
            int maxStorage = random.nextInt(10,100);
            int index = random.nextInt(compoundElements.size());
            MagicElement element = compoundElements.get(index);
            storage.addElement(element, maxStorage);
        }

        return storage;
    }

    public StorageElements randomPrimalElements(RandomSource random, List<MagicElement> preInitElements){
        List<MagicElement> primalElements = ElementsRegistry.getPrimalElements();
        int time = random.nextInt(0, 2);
        StorageElements storage = new StorageElements(new HashMap<>());

        preInitElements.forEach(i -> {
            if ((random.nextInt(100) >= 25) ){
                int maxStorage = random.nextInt(4, 80);
                storage.addElement(i, maxStorage);
            }
        });

        for (int i = 0; i < time; i++) {
            int maxStorage = random.nextInt(4, 48);
            int index = random.nextInt(primalElements.size());
            MagicElement element = primalElements.get(index);
            storage.addElement(element, maxStorage);
        }

        return storage;
    }


}
