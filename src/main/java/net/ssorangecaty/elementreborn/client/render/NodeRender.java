package net.ssorangecaty.elementreborn.client.render;

import net.minecraft.client.model.ZombieModel;
import net.ssorangecaty.elementreborn.block.entity.NodeBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import org.jetbrains.annotations.NotNull;

public class NodeRender<T extends NodeBlockEntity> implements BlockEntityRendererProvider<T> {
    @Override
    public @NotNull BlockEntityRenderer<T> create(@NotNull Context p_173571_) {
        return new NodeBlockEntityRenderer<>();
    }

}
