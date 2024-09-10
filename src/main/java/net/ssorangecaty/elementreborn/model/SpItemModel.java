package net.ssorangecaty.elementreborn.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
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
        ModelPart inner = createPixelModelPart();
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
                    int color = pixelTag.getInt("color");
                    part.render(poseStack, vertexConsumer, light, overlay, color);
                }
            }
        }
    }

    public static ModelPart createPixelModelPart() {
        List<ModelPart.Cube> cubes = new ArrayList<>();
        Map<String, ModelPart> children = new HashMap<>();
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                ModelPart.Cube cube = createPixelCube(x, y ,0);
                String pixelName = "pixel_" + x + "_" + y;
                cubes.add(cube);
                ModelPart child = new ModelPart(List.of(cube), new HashMap<>());
                children.put(pixelName, child);
            }
        }
        return new ModelPart(cubes, children);
    }


    private static ModelPart.Cube createPixelCube(float x, float y, float z) {
        float size = 1.0F;
        return new ModelPart.Cube(
                0, // 纹理坐标的起始X
                0, // 纹理坐标的起始Y
                x, // 立方体的最小X坐标
                y, // 立方体的最小Y坐标
                z, // 立方体的最小Z坐标
                size, // X方向上的大小
                size, // Y方向上的大小
                size, // Z方向上的大小
                0, // UV翻转标志
                0.0F, // UV偏移X
                0.0F, // UV偏移Y
                false,0,0,
                EnumSet.allOf(Direction.class)
        );
    }

}
