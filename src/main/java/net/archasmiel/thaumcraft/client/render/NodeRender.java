package net.archasmiel.thaumcraft.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.archasmiel.thaumcraft.block.entity.NodeBlockEntity;
import net.archasmiel.thaumcraft.util.RefGui;
import net.archasmiel.thaumcraft.core.element.MagicElement;
import net.archasmiel.thaumcraft.core.element.StorageElements;
import net.archasmiel.thaumcraft.core.node.NodeModifier;
import net.archasmiel.thaumcraft.core.node.NodeType;
import net.archasmiel.thaumcraft.util.IResourceLocation;
import net.archasmiel.thaumcraft.util.QuadHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

public class NodeRender<T extends NodeBlockEntity> implements BlockEntityRendererProvider<T> {
    @Override
    public @NotNull BlockEntityRenderer<T> create(@NotNull Context p_173571_) {
        return new BlockEntityRenderer<T>() {
            @Override
            public void render(@NotNull T node, float tickDelta, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int light, int overlay) {
                Player player = Minecraft.getInstance().player;
                float size =  0.7f;
                double viewDistance = 64.0d;
                boolean condition = true;
                boolean depthIgnore = true;
                if (player != null ){
                    renderNode(player,tickDelta,viewDistance, condition, depthIgnore, size, node);
                }
            }

            public static final ResourceLocation nodetex = IResourceLocation.create("thaumcraft", "textures/misc/nodes.png");

            public static void renderNode(Player viewer, float tickDelta, double viewDistance, boolean visible, boolean depthIgnore, float size,NodeBlockEntity node) {
                long nt = System.nanoTime();
                RenderSystem.setShaderTexture(0,nodetex);
                StorageElements elements = node.getStorage();
                NodeModifier mod = node.getNodeModifier();
                NodeType type = node.getNodeType();
                BlockPos pos = node.getBlockPos();
                int x = pos.getX();
                int y = pos.getY();
                int z = pos.getZ();
                if (!elements.isEmpty() && visible) {
                    double distance = viewer.distanceToSqr(x + 0.5d, y + 0.5d, z + 0.5d);
                    if (distance > viewDistance) {
                        return;
                    }
                    float alpha = (float) ((viewDistance - distance) / viewDistance);
                    if (mod != null) {
                        switch (mod.ordinal()) {
                            case 1:
                                alpha *= 1.5f;
                                break;
                            case 2:
                                alpha *= 0.66f;
                                break;
                            case RefGui.GUI_THAUMATORIUM /* 3 */:
                                alpha *= (Mth.sin(tickDelta / 3.0f) * 0.25f) + 0.33f;
                                break;
                        }
                    }
                    GL11.glPushMatrix();
                    GL11.glAlphaFunc(516, 0.003921569f);
                    GL11.glDepthMask(false);
                    if (depthIgnore) {
                        GL11.glDisable(2929);
                    }
                    GL11.glDisable(2884);
                    long time = nt / 5000000;
                    GL11.glPushMatrix();
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);
                    int i = (int) (((nt / 40000000) + x) % 32);
                    int count = 0;
                    float angle = 0.0f;
                    float average = 0.0f;
                    Iterable<MagicElement> arr$ = elements.getElements();
                    for (MagicElement aspect : arr$) {
                        if (aspect.getBlend() == 771) {
                            alpha = (float) (alpha * 1.5d);
                        }
                        average += elements.getElementValue(aspect);
                        GL11.glPushMatrix();
                        GL11.glEnable(3042);
                        GL11.glBlendFunc(770, aspect.getBlend());
                        angle = (((float) (time % (5000 + (500L * count)))) / (5000.0f + (500 * count))) * 6.2831855f;
                        renderFacingStrip(x + 0.5d, y + 0.5d, z + 0.5d, angle, (0.2f + (((Mth.sin(tickDelta / (14.0f - count)) * 0.25f) + (0.25f * 2.0f)) * (elements.getElementValue(aspect) / 50.0f))) * size, alpha / Math.max(1.0f, elements.size() / 2.0f), 32, 0, i, tickDelta, aspect.getColor());
                        GL11.glDisable(3042);
                        GL11.glPopMatrix();
                        count++;
                        if (aspect.getBlend() == 771) {
                            alpha = (float) (alpha / 1.5d);
                        }
                    }
                    GL11.glPushMatrix();
                    GL11.glEnable(3042);
                    int i2 = (int) (((nt / 40000000) + x) % 32);
                    float scale = (0.1f + ((average / elements.size()) / 150.0f)) * size;
                    int strip = 1;
                    switch (type.ordinal()) {
                        case 1:
                            GL11.glBlendFunc(770, 1);
                            break;
                        case 2:
                            GL11.glBlendFunc(770, 1);
                            strip = 6;
                            angle = 0.0f;
                            break;
                        case RefGui.GUI_THAUMATORIUM /* 3 */:
                            GL11.glBlendFunc(770, 771);
                            strip = 2;
                            break;
                        case 4 /* 4 */:
                            GL11.glBlendFunc(770, 771);
                            strip = 5;
                            break;
                        case RefGui.GUI_FOCUS_POUCH /* 5 */:
                            GL11.glBlendFunc(770, 1);
                            strip = 4;
                            break;
                        case 6:
                            scale *= 0.75f;
                            GL11.glBlendFunc(770, 1);
                            strip = 3;
                            break;
                    }
                    GL11.glColor4f(1.0f, 0.0f, 1.0f, alpha);
                    renderFacingStrip(x + 0.5d, y + 0.5d, z + 0.5d, angle, scale, alpha, 32, strip, i2, tickDelta, 16777215);
                    GL11.glDisable(3042);
                    GL11.glPopMatrix();
                    GL11.glPopMatrix();
                    GL11.glEnable(2884);
                    if (depthIgnore) {
                        GL11.glEnable(2929);
                    }
                    GL11.glDepthMask(true);
                    GL11.glAlphaFunc(516, 0.1f);
                    GL11.glPopMatrix();
                    return;
                }
                GL11.glPushMatrix();
                GL11.glAlphaFunc(516, 0.003921569f);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 1);
                GL11.glDepthMask(false);
                int i3 = (int) (((nt / 40000000) + x) % 32);
                GL11.glColor4f(1.0f, 0.0f, 1.0f, 0.1f);
                renderFacingStrip(x + 0.5d, y + 0.5d, z + 0.5d, 0.0f, 0.5f, 0.1f, 32, 1, i3, tickDelta, 16777215);
                GL11.glDepthMask(true);
                GL11.glDisable(3042);
                GL11.glAlphaFunc(516, 0.1f);
                GL11.glPopMatrix();
            }

            public static void renderFacingStrip(double px, double py, double pz, float angle, float scale, float alpha, int frames, int strip, int frame, float partialTicks, int color) {
                Player player = Minecraft.getInstance().player;
                if (player != null) {
                    Tesselator tessellator = Tesselator.getInstance();
                    BufferBuilder bufferBuilder = tessellator.begin(VertexFormat.Mode.QUADS, VertexFormat.builder().build());
                    Vec3 lookVec = player.getLookAngle();
                    float arX = (float) lookVec.x;
                    float arZ = (float) lookVec.z;
                    float arYZ = 0;
                    float arXY = 1;
                    float arXZ = 0;
                    double interpPosX = player.xOld + ((player.getX() - player.xOld) * partialTicks);
                    double interpPosY = player.yOld + ((player.getY() - player.yOld) * partialTicks);
                    double interpPosZ = player.zOld + ((player.getZ() - player.zOld) * partialTicks);
                    GL11.glTranslated(-interpPosX, -interpPosY, -interpPosZ);
                    bufferBuilder.setLight(220);
                    bufferBuilder.setColor(color);
                    bufferBuilder.setWhiteAlpha((int) alpha*255);
                    Vec3 v1_ = new Vec3(((-arX) * scale) - (arYZ * scale), (-arXZ) * scale, ((-arZ) * scale) - (arXY * scale));
                    Vec3 v2_ = new Vec3(((-arX) * scale) + (arYZ * scale), arXZ * scale, ((-arZ) * scale) + (arXY * scale));
                    Vec3 v3_ = new Vec3((arX * scale) + (arYZ * scale), arXZ * scale, (arZ * scale) + (arXY * scale));
                    Vec3 v4_ = new Vec3((arX * scale) - (arYZ * scale), (-arXZ) * scale, (arZ * scale) - (arXY * scale));

                    Vec3 v1 = v1_;
                    Vec3 v2 = v2_;
                    Vec3 v3 = v3_;
                    Vec3 v4 = v4_;

                    if (angle != 0.0f) {
                       // Vec3 pvec = new Vec3(interpPosX, interpPosY, interpPosZ);
                        Vec3 tvec = new Vec3(px, py, pz);
                        Vec3 qvec = new Vec3(tvec.toVector3f()).normalize();
                        v1 = QuadHelper.setAxis(qvec, angle).rotate(v1);
                        v2 = QuadHelper.setAxis(qvec, angle).rotate(v2);
                        v3 = QuadHelper.setAxis(qvec, angle).rotate(v3);
                        v4 = QuadHelper.setAxis(qvec, angle).rotate(v4);
                    }

                    float f2 = (float) frame / frames;
                    float f3 = (float) (frame + 1) / frames;
                    float f4 = (float) strip / frames;
                    float f5 = (strip + 1.0f) / frames;

                    bufferBuilder.setNormal(0.0f, 0.0f, -1.0f);
                    bufferBuilder.addVertex((float) (px + v1.x), (float) (py + v1.y), (float) (pz + v1.z)).setUv(f3,f5);
                    bufferBuilder.addVertex((float) (px + v2.x), (float) (py + v2.y), (float) (pz + v2.z)).setUv(f3, f4);
                    bufferBuilder.addVertex((float) (px + v3.x), (float) (py + v3.y), (float) (pz + v3.z)).setUv( f2, f4);
                    bufferBuilder.addVertex((float) (px + v4.x), (float) (py + v4.y), (float) (pz + v4.z)).setUv( f2, f5);
                    BufferUploader.draw(bufferBuilder.buildOrThrow());
                }
            }
        };
    }
}
