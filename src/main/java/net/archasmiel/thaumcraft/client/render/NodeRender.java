package net.archasmiel.thaumcraft.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.archasmiel.thaumcraft.block.entity.NodeBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import org.jetbrains.annotations.NotNull;

public class NodeRender<T extends NodeBlockEntity> implements BlockEntityRendererProvider<T> {
    @Override
    public @NotNull BlockEntityRenderer<T> create(@NotNull Context p_173571_) {
        return new BlockEntityRenderer<T>() {
            @Override
            public void render(T p_112307_, float p_112308_, PoseStack p_112309_, MultiBufferSource p_112310_, int p_112311_, int p_112312_) {

            }

            private void renderNode(BlockEntityRendererProvider.Context ctx, T node, PoseStack poseStack, int light, float partialTicks) {

            }

            @Override
            public boolean shouldRenderOffScreen(@NotNull T p_112306_) {
                return true;
            }
        };
    }
}
