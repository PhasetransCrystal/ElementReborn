package net.ssorangecaty.elementreborn.inventory.container;

import com.mojang.blaze3d.systems.RenderSystem;
import net.ssorangecaty.elementreborn.ElementReborn;
import net.ssorangecaty.elementreborn.core.element.ElementRender;
import net.ssorangecaty.elementreborn.element.ERMagicElements;
import net.ssorangecaty.elementreborn.inventory.menu.ArcaneWorkBenchMenu;
import net.ssorangecaty.elementreborn.util.IResourceLocation;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ArcaneWorkBenchScreen extends AbstractContainerScreen<ArcaneWorkBenchMenu> {
    public static final ResourceLocation TEXTURE = IResourceLocation.create(ElementReborn.MODID, "textures/gui/gui_arcaneworkbench.png");

    public ArcaneWorkBenchScreen(ArcaneWorkBenchMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics graphics, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        int x = (this.width - 256) / 2;
        int y = (this.height - 256) / 2;
        graphics.blit(TEXTURE, x, y, 0, 0, 256, 256);
        RenderSystem.disableBlend();
        RenderSystem.disableDepthTest();

        assert this.minecraft != null;
        if (!this.getMenu().isDefaultCraftingRecipe() && !this.getMenu().hasEnoughVis(this.minecraft.player)) {
            ElementRender.render(graphics, x, y, ERMagicElements.EARTH, ElementRender.RenderPlace.First);
            ElementRender.render(graphics, x, y, ERMagicElements.WIND, ElementRender.RenderPlace.Second);
            ElementRender.render(graphics, x, y, ERMagicElements.CHAOS, ElementRender.RenderPlace.Third);
            ElementRender.render(graphics, x, y, ERMagicElements.ORDER, ElementRender.RenderPlace.Forth);
            ElementRender.render(graphics, x, y, ERMagicElements.WATER, ElementRender.RenderPlace.Fifth);
            ElementRender.render(graphics, x, y, ERMagicElements.FIRE, ElementRender.RenderPlace.Sixth);
        }
    }

    @Override
    public void render(@NotNull GuiGraphics context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        this.renderTooltip(context, mouseX, mouseY);
    }


    @Override
    protected void renderLabels(GuiGraphics p_281635_, int p_282681_, int p_283686_) {
    }
}
