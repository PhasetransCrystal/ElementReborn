package net.archasmiel.thaumcraft.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.archasmiel.thaumcraft.block.entity.NodeBlockEntity;
import net.archasmiel.thaumcraft.core.element.StorageElements;
import net.archasmiel.thaumcraft.core.node.NodeModifier;
import net.archasmiel.thaumcraft.core.node.NodeType;
import net.archasmiel.thaumcraft.util.IResourceLocation;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.blockentity.BedRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.BarrierBlock;
import net.minecraft.world.level.block.entity.BarrelBlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.neoforged.fml.earlydisplay.ColourScheme;
import net.neoforged.neoforge.client.loading.NeoForgeLoadingOverlay;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3d;
import org.joml.Vector3f;

public class NodeBlockEntityRenderer<T extends NodeBlockEntity> implements BlockEntityRenderer<T> {
    public static final ResourceLocation TEXTURE = IResourceLocation.create("thaumcraft", "textures/misc/nodes.png");
    public static final int SIZE = 64;
    public static final int FRAME = 32;
    public static final int TEXTURE_SIZE = 2048;
    public static final int DESTROY_TEXTURE_Y = 1984;

    @Override
    public void render(@NotNull T node, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int light, int overlay) {
        Player player = Minecraft.getInstance().player;
        float size = 0.7f;
        double viewDistance = 64.0d;
        boolean condition = true;
        boolean depthIgnore = true;
        if (player != null && node.getNodeType() != null && node.getNodeModifier() != null) {
            renderNode(player,poseStack, bufferSource, viewDistance, condition, depthIgnore, size, node);
        }
    }



    public static void renderNode(Player viewer, PoseStack poseStack,MultiBufferSource bufferSource , double viewDistance, boolean visible, boolean depthIgnore, float size, NodeBlockEntity node) {
        poseStack.pushPose();
        poseStack.translate(0.5d, 0.5d, 0.5d);
        Quaternionf rotation = Minecraft.getInstance().gameRenderer.getMainCamera().rotation();
        poseStack.mulPose(rotation);
        long time = System.currentTimeMillis() / 5;
        int frame = (int) ((time / 8) % 32);
        int color = node.getColor();
        renderQuad(poseStack, bufferSource, 32, 32, frame, 0.5f, color, 0.2f);
        poseStack.popPose();
    }


    public static void renderQuad(PoseStack stack, MultiBufferSource buffer, int gridX, int gridY, int frame, float scale, int color, float alpha) {
        int r = (color >> 16) & 255;
        int g = (color >> 8) & 255;
        int b = color & 255;
        int a = (int) (alpha * 256.0f);
        int xm = frame % gridX;
        int ym = frame / gridY;
        float f1 = xm / gridX;
        float f2 = f1 + (1.0f / gridX);
        float f3 = ym / gridY;
        float f4 = f3 + (1.0f / gridY);
        VertexConsumer builder = buffer.getBuffer(RenderType.entityTranslucent(TEXTURE));
        Matrix4f mat = stack.last().pose();
        builder.addVertex(mat, -scale, -scale, 0.0f).setColor(r, g, b, a).setUv(f1, f3).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(0,0).setNormal(0.0f, -1.0f, 0.0f);
        builder.addVertex(mat, -scale, scale, 0.0f).setColor(r, g, b, a).setUv(f1, f4).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(0,0).setNormal(0.0f, -1.0f, 0.0f);
        builder.addVertex(mat, scale, scale, 0.0f).setColor(r, g, b, a).setUv(f2, f4).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(0,0).setNormal(0.0f, -1.0f, 0.0f);
        builder.addVertex(mat, scale, -scale, 0.0f).setColor(r, g, b, a).setUv(f2, f3).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(0,0).setNormal(0.0f, -1.0f, 0.0f);
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
        return AABB.ofSize(blockEntity.getBlockPos().getCenter(), 0,0,0);
    }
}
