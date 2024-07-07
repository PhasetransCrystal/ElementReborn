package net.archasmiel.thaumcraft.core.element;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;

public class ElementRender {
    public static void render(GuiGraphics gui, MagicElement element, int x, int y, int width, int height) {
        gui.blit(element.getTexture(), x, y, width, height, 0, 0);
    }


    public static void render(GuiGraphics gui, MagicElement element, int x, int y, int width, int height, int color) {
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;
        int a = (color >> 24) & 0xFF;
        gui.pose().pushPose();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(r / 255.0f, g / 255.0f, b / 255.0f, getAlpha());
        gui.blit(element.getTexture(), x, y, 0, 0, width, height, width, height);
        RenderSystem.disableBlend();
        gui.pose().popPose();
        RenderSystem.setShaderColor(1, 1, 1, 1);
    }

    public static float getAlpha() {
        final long TIME = 350;
        long current = System.currentTimeMillis();
        long time = current % (TIME * 2);
        float transparency= (float) time /TIME;
        return transparency <= 1 ? transparency : 2 - transparency;
    }
}
