package net.archasmiel.thaumcraft.core.element;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;

public class ElementRender {
    public static void render(GuiGraphics gui, MagicElement element, int x, int y, int width, int height) {
        gui.blit(element.getTexture(), x, y, width, height, 0, 0);
    }

    public static void render(GuiGraphics gui, int x, int y, MagicElement element, RenderPlace place) {
        render(gui, x, y, element, place, false);
    }

    public static void render(GuiGraphics gui, int x, int y, MagicElement element, RenderPlace place, boolean stable) {
        int color = element.getColor();
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;
        gui.pose().pushPose();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(r / 255.0f, g / 255.0f, b / 255.0f, stable ? 1 : place.getAlpha());
        gui.blit(element.getTexture(), x + place.getX(), y + place.getY(), 0, 0, 16, 16, 16, 16);
        RenderSystem.disableBlend();
        gui.pose().popPose();
        RenderSystem.setShaderColor(1, 1, 1, 1);
    }

    public enum RenderPlace {
        First(16, 35),
        Second(64, 13),
        Third(112, 35),
        Forth(112, 94),
        Fifth(64, 116),
        Sixth(16, 94);

        private static final long INTERVAL = 120;
        private final int x, y;

        RenderPlace(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public float getAlpha() {
            long current = System.currentTimeMillis() - INTERVAL * this.ordinal();
            long time = current % (INTERVAL * 6);
            float transparency = (float) time / (INTERVAL * 6) + 0.5f;
            return (transparency <= 1 ? transparency : 2 - transparency) - 0.25f;
        }
    }
}
