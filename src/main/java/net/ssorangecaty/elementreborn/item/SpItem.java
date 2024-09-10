package net.ssorangecaty.elementreborn.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.ssorangecaty.elementreborn.core.item.ERItem;

public class SpItem extends ERItem {


    public SpItem(ERProperties tcProperties) {
        super(tcProperties);
    }

    public static void writePixelData(ItemStack itemStack, CompoundTag data) {
        if (itemStack.getItem() instanceof SpItem){
            itemStack.set(DataComponents.CUSTOM_DATA,CustomData.of(data));
        }
    }

    public static CompoundTag readPixelData(ItemStack itemStack) {
        if (itemStack.getItem() instanceof SpItem){
            return itemStack.get(DataComponents.CUSTOM_DATA).copyTag();
        }
        return new CompoundTag();
    }

    public static CompoundTag generateNullPixelData() {
        CompoundTag tag = new CompoundTag();
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                int color;
                if ((x < 4 && y >= 12) || (x >= 12 && y < 4)) {
                    color = 0xFF800080; // Purple
                } else {
                    color = 0xFF000000; // Black
                }
                int alpha = 255;
                setPixelDataByXY(tag,x, y, color, alpha);
            }
        }
        return tag;
    }


    public static void setPixelDataByXY(CompoundTag tag, int x, int y, int color, int alpha) {
        CompoundTag pixelTag = new CompoundTag();
        pixelTag.putInt("color", color);
        pixelTag.putInt("alpha", alpha);
        String key = "pixel_" + x + "_" + y;
        tag.put(key, pixelTag);
    }
}
