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

import java.awt.*;
import java.util.List;
import java.util.function.BiFunction;


public class NodeBlockEntityRenderer<T extends NodeBlockEntity> implements BlockEntityRenderer<T> {
    public static final ResourceLocation TEXTURE = IResourceLocation.create("thaumcraft", "textures/misc/nodes.png");


    protected static final BiFunction<Integer, Void, RenderStateShard.TransparencyStateShard> LIGHTNING_TRANSPARENCY = Util.memoize(
            (blend, a) -> new RenderStateShard.TransparencyStateShard("magick_lightning_transparency", () -> {
                RenderSystem.enableBlend();
                RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
                RenderSystem.depthMask(false);
                RenderSystem.blendFunc(770, blend);
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
                        .setTransparencyState(LIGHTNING_TRANSPARENCY.apply(blend, null))
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
            if (m1 != null && m2 == null) {
                renderQuad(poseStack, bufferSource, 32, 32, frame, scale, color,m1.getBlend(),visible, alpha);
                renderQuad(poseStack, bufferSource, 32, 32, frame, scale / 4, color, 1,visible, alpha);
            }
            if (m2 != null && m1 != null) {
                renderQuad(poseStack, bufferSource, 32, 32, frame, scale, color,m1.getBlend(),visible, alpha);
                renderQuad(poseStack, bufferSource, 32, 32, frame, scale / 4, color, 1,visible, alpha);
            }
        }
        poseStack.popPose();
    }


    public static void renderQuad(PoseStack stack, MultiBufferSource buffer, int gridX, int gridY, int frame, float scale, int color, int blend, boolean disableDepthTest, int a) {
        Color color1 = new Color(color);
        int alpha = Math.min(a, 125);
        int r = color1.getRed();
        int g = color1.getGreen();
        int b = color1.getBlue();
        int xm = frame % gridX;
        int ym = (frame / gridY);
        float f1 = (float) xm / gridX;
        float f2 = f1 + ((float) 1 / gridX);
        float f3 = (float) ym / gridY;
        float f4 = f3 + ((float) 1 / gridY);

        VertexConsumer builder = buffer.getBuffer(NODE.apply(blend,disableDepthTest));
        Matrix4f mat = stack.last().pose();
        builder.addVertex(mat, -scale, -scale, 0.0f).setColor(r, g, b, alpha).setUv(f1, f3).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(0,0).setNormal(0, 1, 0);
        builder.addVertex(mat, -scale, scale, 0.0f).setColor(r, g, b, alpha).setUv(f1, f4).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(0,0).setNormal(0, 1, 0);
        builder.addVertex(mat, scale, scale, 0.0f).setColor(r, g, b, alpha).setUv(f2, f4).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(0,0).setNormal(0, 1, 0);
        builder.addVertex(mat, scale, -scale, 0.0f).setColor(r, g, b, alpha).setUv(f2, f3).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(0,0).setNormal(0, 1, 0);
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
