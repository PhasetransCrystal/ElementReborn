package net.archasmiel.thaumcraft.block.entity;

import io.netty.buffer.ByteBuf;
import net.archasmiel.thaumcraft.block.TCBlockEntityRegister;
import net.archasmiel.thaumcraft.core.element.ElementsRegistry;
import net.archasmiel.thaumcraft.core.element.MagicElement;
import net.archasmiel.thaumcraft.core.element.StorageElements;
import net.archasmiel.thaumcraft.core.node.*;
import net.archasmiel.thaumcraft.core.wands.WandRod;
import net.archasmiel.thaumcraft.element.TCMagicElements;
import net.archasmiel.thaumcraft.util.IResourceLocation;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.renderer.entity.ExperienceOrbRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.extensions.IBlockEntityExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class NodeBlockEntity extends BlockEntity implements INode {
    private NodeType type;
    private NodeModifier modifier;
    private Player drainPlayer;
    private Map<MagicElement, Integer> baseStorage = new HashMap<>();
    private final StorageElements storage = new StorageElements(new HashMap<>());

    public NodeBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(TCBlockEntityRegister.NODE.get(), p_155229_, p_155230_);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, NodeBlockEntity node) {
        if (level.isClientSide) return;
        //init when null
        if (node.type == null || node.modifier == null) {
            setChanged(level, pos, state);
//            PacketDistributor.sendToAllPlayers(new DataSyn(node.saveNetwork(new CompoundTag()),pos));
        }
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider provider) {
        if (this.type == null || this.modifier == null){
            this.randomNodeType();
            this.randomNodeModifier();
            this.initNode();
            this.loadBaseStorage();
            assert level != null;
            level.players().forEach((player) -> {
                player.sendSystemMessage(Component.literal("Node initialized : " + this.getBlockPos()));
                player.sendSystemMessage(Component.literal("Type : " + this.type.name()));
                player.sendSystemMessage(Component.literal("Modifier : " + this.modifier.name()));
                player.sendSystemMessage(this.getStorage().toComponent());
            });
        }
        return saveWithoutMetadata(provider);
    }

    /**自动发包的接收由方块实体直接代理，使用对应的加载方法
     * @see ClientPacketListener#handleBlockEntityData(ClientboundBlockEntityDataPacket)
     * @see IBlockEntityExtension#onDataPacket(Connection, ClientboundBlockEntityDataPacket, HolderLookup.Provider)
     * @see BlockEntity#loadWithComponents(CompoundTag, HolderLookup.Provider)
     * */

    public void loadBaseStorage() {
        this.storage.getElements().forEach((element) -> {
            this.baseStorage.put(element, (int) storage.getElementValue(element));
        });
    }


    public void onUsingWandTick(LivingEntity user) {
        Player player = user instanceof Player ? (Player) user : null;
        boolean modifiedNode = false;
        WandRod wandRod = user.getMainHandItem().getItem() instanceof WandRod ? (WandRod) user.getMainHandItem().getItem() : null;
        if (player == null || wandRod == null) return;
        StorageElements storage = WandRod.getElements(user.getMainHandItem());
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider provider) {
        super.loadAdditional(tag, provider);
        loadNetwork(tag);
    }

    public void loadNetwork(@NotNull CompoundTag tag) {
        this.getStorage().readFromNBT(tag);
        this.loadBaseStorage();
        this.type = NodeType.values()[tag.getInt("type")];
        this.modifier = NodeModifier.values()[tag.getInt("modifier")];
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider provider) {
        super.saveAdditional(tag, provider);
        saveNetwork(tag);
    }

    public CompoundTag saveNetwork(CompoundTag tag){
        this.getStorage().writeToNBT(tag);
        if (this.type != null && this.modifier != null) {
            tag.putInt("type", this.type.ordinal());
            tag.putInt("modifier", this.modifier.ordinal());
        }
        return tag;
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

    @Nullable
    public MagicElement takeRandomPrimalFromSource() {
        List<MagicElement> primals = this.getStorage().getPrimalElements();
        MagicElement asp = null;
        if (this.level != null && !this.level.isClientSide) {
            asp = primals.get(this.level.getRandom().nextInt(primals.size()));
        }
        return asp != null && this.getStorage().removeElement(asp, 1, true) ? asp : null;
    }

    @Nullable
    public MagicElement chooseRandomFilteredFromSource(List<MagicElement> elements, boolean preserve) {
        int min = preserve ? 1 : 0;
        List<MagicElement> filtered = this.getStorage().filterElements(elements, min);
        if (filtered.isEmpty()) return null;
        if (this.level != null && !this.level.isClientSide) {
            return filtered.get(this.level.getRandom().nextInt(filtered.size()));
        }
        return null;
    }


    public void initNode() {
        List<MagicElement> preInitElements = new ArrayList<>();
        StorageElements storage = new StorageElements(new HashMap<>());
        if (this.getLevel() == null || this.getLevel().isClientSide) return;
        RandomSource random = this.getLevel().random;
        Holder<Biome> biomes = this.getLevel().getBiome(this.getBlockPos());
        NodeManager.getInstance().getTagKeyElements().forEach((tag, element) -> {
            if (biomes.is(tag) && !preInitElements.contains(element)) {
                preInitElements.add(element);
            }
        });
        if (random.nextInt(100) < 25) {
            storage.addFrom(randomCompoundElements(random));
        }
        storage.merge(lavaCheck());
        storage.addFrom(randomPrimalElements(random, preInitElements));
        this.getStorage().addFrom(storage);
    }

    public void randomNodeType() {
        if (this.getLevel() == null || this.getLevel().isClientSide) return;
        RandomSource random = this.getLevel().random;
        int type = random.nextInt(0, 100);
        NodeType nodeType = NodeType.NORMAL;
        if (type < 50) {
            int t = random.nextInt(0, 100);
            if (t < 3) {
                nodeType = NodeType.HUNGRY;
            } else if (t < 15) {
                nodeType = NodeType.DARK;
            } else if (t < 30) {
                nodeType = NodeType.PURE;
            } else if (t < 45) {
                nodeType = NodeType.TAINTED;
            } else if (t < 60) {
                nodeType = NodeType.UNSTABLE;
            }
        }

        this.setNodeType(nodeType);
    }

    public void randomNodeModifier() {
        if (this.getLevel() == null || this.getLevel().isClientSide) return;
        NodeModifier modifier = NodeModifier.COMMON;
        int m = this.getLevel().random.nextInt(0, 120);
        if (m < 15) {
            modifier = NodeModifier.BRIGHT;
        } else if (m < 30) {
            modifier = NodeModifier.FADING;
        } else if (m < 50) {
            modifier = NodeModifier.PALE;
        }
        this.setNodeModifier(modifier);
    }

    public StorageElements lavaCheck() {
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
                        storage.addElement(TCMagicElements.FIRE, random.nextInt(4, 48));
                        return storage;
                    }
                }
            }
        }

        return storage;
    }


    public StorageElements randomCompoundElements(RandomSource random) {
        int time = random.nextInt(0, 6);
        StorageElements storage = new StorageElements(new HashMap<>());
        List<MagicElement> compoundElements = ElementsRegistry.getCompoundElements();
        for (int i = 0; i < time; i++) {
            int maxStorage = random.nextInt(10, 100);
            int index = random.nextInt(compoundElements.size());
            MagicElement element = compoundElements.get(index);
            storage.addElement(element, maxStorage);
        }

        return storage;
    }

    public StorageElements randomPrimalElements(RandomSource random, List<MagicElement> preInitElements) {
        List<MagicElement> primalElements = ElementsRegistry.getPrimalElements();
        int time = random.nextInt(1, 2);
        StorageElements storage = new StorageElements(new HashMap<>());

        preInitElements.forEach(i -> {
            if ((random.nextInt(100) >= 25)) {
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

    public int getColor() {
        StorageElements storage = this.getStorage();
        if(storage.getElements() != null && !storage.getElements().isEmpty()){
            return this.getStorage().getElements().getFirst().getColor();
        }
        return 16777086;
    }

    @Nullable public List<MagicElement> getElements() {
        if(storage.getElements() != null && !storage.getElements().isEmpty()) return this.getStorage().getElements();
        return null;
    }


    public record DataSyn(CompoundTag nbt, BlockPos pos) implements CustomPacketPayload {
        public static final Type<DataSyn> TYPE = new Type<>(IResourceLocation.create("node_init"));
        public static final StreamCodec<ByteBuf, DataSyn> CODEC = StreamCodec.composite(ByteBufCodecs.COMPOUND_TAG, DataSyn::nbt, BlockPos.STREAM_CODEC, DataSyn::pos, DataSyn::new);

        @Override
        public @NotNull Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }
}
