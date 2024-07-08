package net.archasmiel.thaumcraft.block.entity;

import net.archasmiel.thaumcraft.block.TCBlockEntityRegister;
import net.archasmiel.thaumcraft.core.element.MagicElement;
import net.archasmiel.thaumcraft.core.element.StorageElements;
import net.archasmiel.thaumcraft.core.node.INode;
import net.archasmiel.thaumcraft.core.node.IRevealer;
import net.archasmiel.thaumcraft.core.node.NodeModifier;
import net.archasmiel.thaumcraft.core.node.NodeType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class NodeBlockEntity extends BlockEntity implements INode , IRevealer {
    public NodeType type = NodeType.NORMAL;
    public NodeModifier modifier = NodeModifier.BRIGHT;

    public NodeBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(TCBlockEntityRegister.NODE.get(), p_155229_, p_155230_);
    }


    public NodeBlockEntity(BlockPos p_155229_, BlockState p_155230_, NodeType type, NodeModifier modifier, StorageElements elements) {
        super(TCBlockEntityRegister.NODE.get(), p_155229_, p_155230_);
        this.elements.copyFrom(elements);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, NodeBlockEntity entity) {

    }


    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider provider) {
        super.loadAdditional(tag, provider);
        this.getStorage().readFromNBT(tag);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider provider) {
        super.saveAdditional(tag, provider);
        this.getStorage().writeToNBT(tag);
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

    @Override
    public boolean showNodes(ItemStack var1, LivingEntity var2) {
        return true;
    }
}
