package net.archasmiel.thaumcraft.client.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.archasmiel.thaumcraft.block.entity.NodeBlockEntity;
import net.archasmiel.thaumcraft.core.element.MagicElement;
import net.archasmiel.thaumcraft.core.node.NodeType;
import net.archasmiel.thaumcraft.util.IResourceLocation;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import oshi.util.tuples.Pair;

import java.awt.*;
import java.util.List;
import java.util.function.BiFunction;


public class NodeBlockEntityRenderer<T extends NodeBlockEntity> implements BlockEntityRenderer<T> {
    public static final ResourceLocation TEXTURE = IResourceLocation.create("thaumcraft", "textures/misc/nodes.png");


    protected static final BiFunction<Integer, Boolean, RenderStateShard.TransparencyStateShard> LIGHTNING_TRANSPARENCY = Util.memoize(
            (blend, enableBlend) -> new RenderStateShard.TransparencyStateShard(enableBlend ? "magick_lightning_transparency_enable_blend" : "magick_lightning_transparency_disable_blend", () -> {
                RenderSystem.enableBlend();
                RenderSystem.depthMask(false);
                if (enableBlend){
                    RenderSystem.blendFunc(770, blend);
                }

            }, () -> {
                RenderSystem.depthMask(true);
                RenderSystem.disableBlend();
                RenderSystem.defaultBlendFunc();
            }));

    public static final BiFunction<Integer, Boolean, RenderType> NODE = Util.memoize(
            (blend, disableDepthTest) -> {
                RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.builder()
                        .setShaderState(RenderType.RENDERTYPE_ENTITY_TRANSLUCENT_EMISSIVE_SHADER)
                        .setTextureState(new RenderStateShard.TextureStateShard(TEXTURE, true, false))
                        .setTransparencyState(LIGHTNING_TRANSPARENCY.apply(blend, true))
                        .setCullState(RenderType.NO_CULL)
                        .setDepthTestState(disableDepthTest ? RenderType.NO_DEPTH_TEST : RenderType.LEQUAL_DEPTH_TEST)
                        .setWriteMaskState(RenderType.COLOR_WRITE)
                        .setOverlayState(RenderType.OVERLAY)
                        .createCompositeState(false);
                return RenderType.create("node", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 1536, true, true, rendertype$compositestate);
            }
    );

    public static final BiFunction<Boolean, Boolean, RenderType> NODE_CORE = Util.memoize(
            (v, disableDepthTest) -> {
                RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.builder()
                        .setShaderState(RenderType.RENDERTYPE_ENTITY_TRANSLUCENT_EMISSIVE_SHADER)
                        .setTextureState(new RenderStateShard.TextureStateShard(TEXTURE, true, false))
                        .setTransparencyState(LIGHTNING_TRANSPARENCY.apply(1, v))
                        .setCullState(RenderType.NO_CULL)
                        .setDepthTestState(disableDepthTest ? RenderType.NO_DEPTH_TEST : RenderType.LEQUAL_DEPTH_TEST)
                        .setWriteMaskState(RenderType.COLOR_WRITE)
                        .setOverlayState(RenderType.OVERLAY)
                        .createCompositeState(false);
                return RenderType.create("node", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 1536, true, true, rendertype$compositestate);
            }
    );


    @Override
    public void render(@NotNull T node, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int light, int overlay) {
        Player player = Minecraft.getInstance().player;
        float size = 1f;
        double viewDistance = 2560d;
        boolean condition = true;
        if (player != null && node.getNodeType() != null && node.getNodeModifier() != null) {
            double distance = player.distanceToSqr(node.getBlockPos().getCenter());
            if (distance > viewDistance) {
                return;
            }
            int alpha = (int) (((viewDistance - distance) / viewDistance) * 255);
            renderNode(poseStack, bufferSource, alpha, condition, size, node);
        }
    }



    public static void renderNode(PoseStack poseStack, MultiBufferSource bufferSource , int alpha, boolean visible, float size, NodeBlockEntity node) {
        poseStack.pushPose();
        poseStack.translate(0.5d, 0.5d, 0.5d);
        Quaternionf rotation = Minecraft.getInstance().gameRenderer.getMainCamera().rotation();
        poseStack.mulPose(rotation);
        long time = System.currentTimeMillis() / 5;
        int frame = (int) ((time / 8) % 32);
        int color = node.getColor();
        List<MagicElement> elements = node.getElements();
        if(elements != null && !elements.isEmpty()){
            float scale = (0.1f + ((node.getStorage().getTotalValue() / elements.size()) / 150.0f)) * size;
            MagicElement m1 = elements.get(0);
            MagicElement m2 = elements.size() > 1 ? elements.get(1) : null;
            NodeType type = node.getNodeType();
            if (m1 != null && m2 == null) {
                renderNodeSide(poseStack, bufferSource,  frame, scale, color,m1.getBlend(),visible, alpha, 0);
                renderNodeCore(poseStack, bufferSource, frame, scale / 2, visible, alpha, getNodeCoreTextureY(type));
            }
            if (m2 != null && m1 != null) {
                renderNodeSide(poseStack, bufferSource,  frame, scale, color,m1.getBlend(),visible, alpha, 0);
                renderNodeCore(poseStack, bufferSource, frame, scale / 2 , visible, alpha, getNodeCoreTextureY(type));
            }
        }
        poseStack.popPose();
    }


    public static void renderNodeSide(PoseStack stack, MultiBufferSource buffer, int frame, float scale, int color, int blend, boolean disableDepthTest, int a, float offset) {
        int alpha = Math.min(a, 125);
        int r = color >> 16 & 0xFF;
        int g = color >> 8 & 0xFF;
        int b = color & 0xFF;
        int xm = frame % 32;
        int ym = (frame / 32);
        float f1 = (float) xm / 32;
        float f2 = f1 + (1f / 32);
        float f3 = (float) ym / 32;
        if (offset != 0){
            f3 = (float) ym / 32 + 1f / 32f * offset;
        }
        float f4 = f3 + (1f / 32)  ;

        VertexConsumer builder = buffer.getBuffer(NODE.apply(blend,disableDepthTest));
        Matrix4f mat = stack.last().pose();
        builder.addVertex(mat, -scale, -scale, 0.0f).setColor(r, g, b, alpha).setUv(f1, f3).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(0,0).setNormal(0, 1, 0);
        builder.addVertex(mat, -scale, scale, 0.0f).setColor(r, g, b, alpha).setUv(f1, f4).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(0,0).setNormal(0, 1, 0);
        builder.addVertex(mat, scale, scale, 0.0f).setColor(r, g, b, alpha).setUv(f2, f4).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(0,0).setNormal(0, 1, 0);
        builder.addVertex(mat, scale, -scale, 0.0f).setColor(r, g, b, alpha).setUv(f2, f3).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(0,0).setNormal(0, 1, 0);
    }

    public static void renderNodeCore(PoseStack stack, MultiBufferSource buffer, int frame, float scale, boolean disableDepthTest, int alpha_, float offset) {
        // int alpha = Math.min(alpha_, 125);
        boolean enableBlend = offset != 2 && offset != 5;
        int xm = frame % 32;
        int ym = (frame / 32);
        float f1 = (float) xm / 32;
        float f2 = f1 + (1f / 32);
        float f3 = (float) ym / 32;
        if (offset != 0){
            f3 = (float) ym / 32 + 1f / 32f * offset;
        }
        float f4 = f3 + (1f / 32) ;
        VertexConsumer builder = buffer.getBuffer(NODE_CORE.apply(enableBlend,disableDepthTest));
        Matrix4f mat = stack.last().pose();
        builder.addVertex(mat, -scale, -scale, 0.0f).setColor(255, 255, 255, 255).setUv(f1, f3).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(0,0).setNormal(0, 1, 0);
        builder.addVertex(mat, -scale, scale, 0.0f).setColor(255, 255, 255,255).setUv(f1, f4).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(0,0).setNormal(0, 1, 0);
        builder.addVertex(mat, scale, scale, 0.0f).setColor(255, 255, 255, 255).setUv(f2, f4).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(0,0).setNormal(0, 1, 0);
        builder.addVertex(mat, scale, -scale, 0.0f).setColor(255, 255, 255, 255).setUv(f2, f3).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(0,0).setNormal(0, 1, 0);
    }

    public static int getNodeCoreTextureY(NodeType type) {
        return switch (type) {
            case UNSTABLE -> 6;
            case DARK -> 5;
            case TAINTED -> 2;
            case HUNGRY -> 3;
            case PURE -> 4;
            default -> 1;
        };
    }
    @Override
    public boolean shouldRender(T p_173568_, Vec3 p_173569_) {
        return true; //Vec3.atCenterOf(p_173568_.getBlockPos()).closerThan(p_173569_, (double)this.getViewDistance());
    }

    public boolean shouldRenderOffScreen(T p_112306_) {
        return true;
    }


    @Override
    public int getViewDistance() {
        return BlockEntityRenderer.super.getViewDistance();
    }

}
