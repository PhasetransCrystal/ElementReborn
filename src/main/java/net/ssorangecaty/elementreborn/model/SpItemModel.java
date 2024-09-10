package net.ssorangecaty.elementreborn.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.ssorangecaty.elementreborn.item.SpItem;

import java.util.*;


public class SpItemModel extends Model {
    private static SpItemModel instance;
    final ModelPart[][] pixel;
    private CompoundTag pixelData = SpItem.generateNullPixelData();
    public SpItemModel() {
        super(RenderType::entitySolid);
        ModelPart inner = createPixelModelPart(0);
        ModelPart[][] p = new ModelPart[16][16];
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                String pixelName = "pixel_" + x + "_" + y;
                p[x][y] = inner.getChild(pixelName);
            }
        }
        this.pixel = p;
    }

    public static SpItemModel getInstance(){
        if (instance == null){
            instance = new SpItemModel();
        }
        return instance;
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

    public static ModelPart createPixelModelPart(float p_170683_) {
        List<ModelPart.Cube> cubes = new ArrayList<>();
        Map<String, ModelPart> children = new HashMap<>();
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                String pixelName = "pixel_" + x + "_" + y;
                cubes.add(createPixelCube(x - 8.0F, y - 8.0F + p_170683_));
                ModelPart child = new ModelPart(List.of(createPixelCube(x - 8.0F, y - 8.0F + p_170683_)), new HashMap<>());
                children.put(pixelName, child);
            }
        }
        return new ModelPart(cubes, children);
    }


    private static ModelPart.Cube createPixelCube(float xOffset, float yOffset) {
        float size = 1.0F;
        float minX = 0.0F;
        float minY = 0.0F;
        float minZ = 0.0F;
        float maxX = size;
        float maxY = size;

        minX += xOffset;
        minY += yOffset;
        maxX += xOffset;
        maxY += yOffset;

        return new ModelPart.Cube(
                0, // Texture offset X
                0, // Texture offset Y
                minX, minY, minZ, // Min corner
                maxX, maxY, size, // Max corner
                size, size, size, // Dimensions
                false, // Mirror
                1.0F, 1.0F, // Texture scale
                Collections.singleton(Direction.UP) // Face direction
        );
    }


}
