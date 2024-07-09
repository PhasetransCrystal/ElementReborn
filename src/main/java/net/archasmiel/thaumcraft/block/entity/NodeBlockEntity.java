package net.archasmiel.thaumcraft.block.entity;

import net.archasmiel.thaumcraft.block.TCBlockEntityRegister;
import net.archasmiel.thaumcraft.core.element.MagicElement;
import net.archasmiel.thaumcraft.core.element.StorageElements;
import net.archasmiel.thaumcraft.core.node.*;
import net.archasmiel.thaumcraft.core.wands.WandRod;
import net.archasmiel.thaumcraft.item.relics.ItemThaumometer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class NodeBlockEntity extends BlockEntity implements INode , IRevealer {
    public NodeType type;
    public NodeModifier modifier;
    public Player drainPlayer;
    public Map<MagicElement,Integer> baseStorage;

    public NodeBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(TCBlockEntityRegister.NODE.get(), p_155229_, p_155230_);
       // TODO NodeManager.getInstance().initNode(this);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, NodeBlockEntity entity) {
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
        this.type = NodeType.values()[tag.getInt("type")];
        this.modifier = NodeModifier.values()[tag.getInt("modifier")];
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider provider) {
        super.saveAdditional(tag, provider);
        this.getStorage().writeToNBT(tag);
        tag.putInt("type", this.type.ordinal());
        tag.putInt("modifier", this.modifier.ordinal());
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


}
