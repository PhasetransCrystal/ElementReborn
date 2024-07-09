package net.archasmiel.thaumcraft.block.entity;

import net.archasmiel.thaumcraft.block.TCBlockEntityRegister;
import net.archasmiel.thaumcraft.core.element.ElementsRegistry;
import net.archasmiel.thaumcraft.core.element.MagicElement;
import net.archasmiel.thaumcraft.core.element.StorageElements;
import net.archasmiel.thaumcraft.core.node.*;
import net.archasmiel.thaumcraft.core.wands.WandRod;
import net.archasmiel.thaumcraft.element.TCMagicElements;
import net.archasmiel.thaumcraft.item.relics.ItemThaumometer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class NodeBlockEntity extends BlockEntity implements INode , IRevealer {
    private NodeType type;
    private NodeModifier modifier;
    private Player drainPlayer;
    private Map<MagicElement,Integer> baseStorage = new HashMap<>();
    private final StorageElements storage = new StorageElements(new HashMap<>());

    public NodeBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(TCBlockEntityRegister.NODE.get(), p_155229_, p_155230_);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, NodeBlockEntity node) {
        if (level.isClientSide) return;
        if (node.type == null || node.modifier == null) {
            node.initNode();
            node.loadBaseStorage(node.getStorage());
            level.players().forEach((player) -> {
                player.sendSystemMessage(Component.literal("Node initialized : " + node.getBlockPos()));
                player.sendSystemMessage(Component.literal("Type : " + node.type.name()));
                player.sendSystemMessage(Component.literal("Modifier : " + node.modifier.name()));
                player.sendSystemMessage(node.getStorage().toComponent());
            });
        }

    }

    public void loadBaseStorage(StorageElements storage) {
        storage.getElements().forEach((element) -> {
           this.baseStorage.put(element, (int) storage.getElementValue(element));
        });
    }


    public void onUsingWandTick(LivingEntity user) {
        Player player = user instanceof Player ? (Player) user : null;
        boolean modifiedNode = false;
        WandRod wandRod = user.getMainHandItem().getItem() instanceof WandRod? (WandRod) user.getMainHandItem().getItem() : null;
        if (player == null || wandRod == null) return;
        StorageElements storage = WandRod.getElements(user.getMainHandItem());
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider provider) {
        super.loadAdditional(tag, provider);
        this.getStorage().readFromNBT(tag);
        this.loadBaseStorage(this.getStorage());
        this.type = NodeType.valueOf(tag.getString("type"));
        this.modifier = NodeModifier.valueOf(tag.getString("modifier"));
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider provider) {
        super.saveAdditional(tag, provider);
        this.getStorage().writeToNBT(tag);
        tag.putString("type", this.type.name());
        tag.putString("modifier", this.modifier.name());
    }

    @Override
    public NodeType getNodeType() {
        return this.type;
    }

    @Override
    public void setNodeType(NodeType nodeType) {
        this.type = nodeType;
    }

    @Override
    public void setNodeModifier(NodeModifier modifier) {
        this.modifier = modifier;
    }

    @Override
    public NodeModifier getNodeModifier() {
        return this.modifier;
    }

    @Override
    public float getNodeVis(MagicElement element) {
        return this.getStorage().getElementValue(element);
    }

    @Override
    public void setNodeVis(MagicElement element, float v) {
        this.getStorage().setElement(element, v);
    }

    @Nullable public MagicElement takeRandomPrimalFromSource() {
        List<MagicElement> primals = this.getStorage().getPrimalElements();
        MagicElement asp = null;
        if (this.level != null && !this.level.isClientSide) {
            asp = primals.get(this.level.getRandom().nextInt(primals.size()));
        }
        return asp != null && this.getStorage().removeElement(asp, 1,true) ? asp : null;
    }

    @Nullable public MagicElement chooseRandomFilteredFromSource(List<MagicElement> elements, boolean preserve){
       int min = preserve ? 1 : 0;
       List<MagicElement> filtered = this.getStorage().filterElements(elements, min);
       if (filtered.isEmpty()) return null;
       if (this.level != null && !this.level.isClientSide) {
           return filtered.get(this.level.getRandom().nextInt(filtered.size()));
       }
       return null;
    }


    public void initNode(){
        List<MagicElement> preInitElements = new ArrayList<>();
        StorageElements storage = new StorageElements(new HashMap<>());
        if (this.getLevel() == null || this.getLevel().isClientSide) return;
        RandomSource random = this.getLevel().random;
        Holder<Biome> biomes = this.getLevel().getBiome(this.getBlockPos());
        NodeManager.getInstance().getTagKeyElements().forEach((tag, element) -> {
            if (biomes.is(tag) && !preInitElements.contains(element)){
                preInitElements.add(element);
            }
        });
        if(random.nextInt(100) < 25){
            storage.addFrom(randomCompoundElements(random));
        }
        storage.merge(lavaCheck());
        storage.addFrom(randomPrimalElements(random, preInitElements));
        if (this.type == null || this.modifier == null){
            randomNodeTypeAndModifier();
        }
        this.getStorage().addFrom(storage);
    }

    public void randomNodeTypeAndModifier(){
        if (this.getLevel() == null || this.getLevel().isClientSide) return;
        RandomSource random = this.getLevel().random;
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

        this.setNodeType(nodeType);
        int m = random.nextInt(0,120);
        if (m < 15){
            modifier = NodeModifier.BRIGHT;
        }else if(m < 30){
            modifier = NodeModifier.FADING;
        }  else if(m < 50){
            modifier = NodeModifier.PALE;
        }
        this.setNodeModifier(modifier);
    }

    public StorageElements lavaCheck(){
        StorageElements storage = new StorageElements(new HashMap<>());
        if (this.getLevel() == null || this.getLevel().isClientSide) return storage;
        Level level = this.getLevel();
        RandomSource random = level.random;
        BlockPos pos = this.getBlockPos();

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
        int time = random.nextInt(1, 2);
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

    @Override
    public StorageElements getStorage() {
        return this.storage;
    }
}
