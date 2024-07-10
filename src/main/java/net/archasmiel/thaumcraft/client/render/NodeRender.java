package net.archasmiel.thaumcraft.client.render;

import net.archasmiel.thaumcraft.block.entity.NodeBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import org.jetbrains.annotations.NotNull;

public class NodeRender<T extends NodeBlockEntity> implements BlockEntityRendererProvider<T> {
    @Override
    public @NotNull BlockEntityRenderer<T> create(@NotNull Context p_173571_) {
        return new NodeBlockEntityRenderer<>();
    }

}
