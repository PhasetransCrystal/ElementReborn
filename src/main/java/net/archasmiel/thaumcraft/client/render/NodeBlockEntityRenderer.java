package net.archasmiel.thaumcraft.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.archasmiel.thaumcraft.block.entity.NodeBlockEntity;
import net.archasmiel.thaumcraft.core.element.StorageElements;
import net.archasmiel.thaumcraft.core.node.NodeModifier;
import net.archasmiel.thaumcraft.core.node.NodeType;
import net.archasmiel.thaumcraft.util.IResourceLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BedRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.BarrierBlock;
import net.minecraft.world.level.block.entity.BarrelBlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3d;

public class NodeBlockEntityRenderer<T extends NodeBlockEntity> implements BlockEntityRenderer<T> {
    public static final ResourceLocation TEXTURE = IResourceLocation.create("thaumcraft", "textures/misc/nodes.png");
    public static final int SIZE = 64;
    public static final int FRAME = 32;
    public static final int TEXTURE_SIZE = 2048;
    public static final int DESTROY_TEXTURE_Y = 1984;

    @Override
    public void render(@NotNull T node, float tickDelta, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int light, int overlay) {
        Player player = Minecraft.getInstance().player;
        float size = 0.7f;
        double viewDistance = 64.0d;
        boolean condition = true;
        boolean depthIgnore = true;
        if (player != null && node.getNodeType() != null && node.getNodeModifier() != null) {
            renderNode(player,poseStack, bufferSource,tickDelta, viewDistance, condition, depthIgnore, size, node);
        }
    }



    public static void renderNode(Player viewer, PoseStack poseStack,MultiBufferSource bufferSource ,float tickDelta, double viewDistance, boolean visible, boolean depthIgnore, float size, NodeBlockEntity node) {
        StorageElements storage = node.getStorage();
        NodeModifier mod = node.getNodeModifier();
        NodeType type = node.getNodeType();
        BlockPos pos = node.getBlockPos();
        Matrix4f mat = poseStack.last().pose();
        int color = node.getColor();
        int r = (color >> 16) & 255;
        int g = (color >> 8) & 255;
        int b = color & 255;
        int a = (int) (1 * 256.0f);
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        int currentFrame = (int)(tickDelta * 24) % FRAME;
        int textureY = getNodeCoreTextureY(type);
        int textureX = currentFrame *  SIZE;
        VertexConsumer builder = bufferSource.getBuffer(RenderType.entityTranslucent(TEXTURE));
    }



    public static int getNodeCoreTextureY(NodeType type) {
        return switch (type) {
            case UNSTABLE -> 64 * 6;
            case DARK -> 64 * 2;
            case TAINTED -> 64 * 5;
            case HUNGRY -> 64 * 3;
            case PURE -> 64 * 4;
            default -> 0;
        };
    }





    @Override
    public int getViewDistance() {
        return BlockEntityRenderer.super.getViewDistance();
    }

    @Override
    public @NotNull AABB getRenderBoundingBox(T blockEntity) {
        return AABB.ofSize(blockEntity.getBlockPos().getCenter(), 0.5, 0.5, 0.5);
    }
}
