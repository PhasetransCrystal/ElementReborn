package net.ssorangecaty.elementreborn.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.ssorangecaty.elementreborn.item.SpItem;


public class SpItemModel extends Model {
    final ModelPart[][] pixel;
    private CompoundTag pixelData = SpItem.generateNullPixelData();
    public SpItemModel(ModelPart modelPart) {
        super(RenderType::entitySolid);
        ModelPart[][] p = new ModelPart[15][15];
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                String pixelName = "pixel_" + x + "_" + y;
                p[x][y] = modelPart.getChild(pixelName);
            }
        }
        this.pixel = p;

    }

    public void setPixelData(ItemStack itemStack) {
        if(itemStack.getItem() instanceof SpItem){
            this.pixelData = itemStack.get(DataComponents.CUSTOM_DATA).copyTag();
        }
    }


    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int light, int overlay, int p_350308_) {
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                ModelPart part = pixel[x][y];
                String key = "pixel_" + x + "_" + y;
                if (pixelData.contains(key, Tag.TAG_COMPOUND)) {
                    CompoundTag pixelTag = pixelData.getCompound(key);
                    int color = 0xFF000000; // 黑色
                    int alpha = 255; // 不透明
                    if (pixelTag.contains("color", Tag.TAG_INT)) {
                        color = pixelTag.getInt("color");
                    }
                    if (pixelTag.contains("alpha", Tag.TAG_INT)) {
                        alpha = pixelTag.getInt("alpha");
                    }
                    int r = (color >> 16) & 0xFF;
                    int g = (color >> 8) & 0xFF;
                    int b = color & 0xFF;
                    int packedColor = (alpha << 24) | (r << 16) | (g << 8) | b;
                    part.render(poseStack, vertexConsumer, light, overlay, packedColor);
                }
            }
        }
    }

    public static MeshDefinition createPixelMesh(CubeDeformation p_170682_, float p_170683_) {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        // 遍历 16x16 的网格
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                String pixelName = "pixel_" + x + "_" + y;

                // 创建每个像素的立方体
                partdefinition.addOrReplaceChild(
                        pixelName,
                        CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, p_170682_),
                        PartPose.offset(x - 8.0F, y - 8.0F + p_170683_, 0.0F)
                );
            }
        }

        return meshdefinition;
    }

}
