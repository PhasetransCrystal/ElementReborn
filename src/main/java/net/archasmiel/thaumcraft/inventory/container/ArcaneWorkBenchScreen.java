package net.archasmiel.thaumcraft.inventory.container;

import com.mojang.blaze3d.systems.RenderSystem;
import net.archasmiel.thaumcraft.Thaumcraft;
import net.archasmiel.thaumcraft.core.element.ElementRender;
import net.archasmiel.thaumcraft.element.TCMagicElements;
import net.archasmiel.thaumcraft.inventory.menu.ArcaneWorkBenchMenu;
import net.archasmiel.thaumcraft.util.IResourceLocation;
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
    public static final ResourceLocation TEXTURE = IResourceLocation.create(Thaumcraft.MODID, "textures/gui/gui_arcaneworkbench.png");

    public ArcaneWorkBenchScreen(ArcaneWorkBenchMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics graphics, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (this.width - 256) / 2;
        int y = (this.height - 256) / 2;
        graphics.blit(TEXTURE, x, y, 0, 0, 256, 256);


        if (!this.getMenu().isDefaultCraftingRecipe() && !this.getMenu().hasEnoughVis()){
            ElementRender.render(graphics, x, y ,TCMagicElements.EARTH, ElementRender.RenderPlace.First);
            ElementRender.render(graphics, x, y ,TCMagicElements.AIR, ElementRender.RenderPlace.Second);
            ElementRender.render(graphics, x, y ,TCMagicElements.ENTROPY, ElementRender.RenderPlace.Third);
            ElementRender.render(graphics, x, y ,TCMagicElements.ORDER, ElementRender.RenderPlace.Forth);
            ElementRender.render(graphics, x, y ,TCMagicElements.WATER, ElementRender.RenderPlace.Fifth);
            ElementRender.render(graphics, x, y ,TCMagicElements.FIRE, ElementRender.RenderPlace.Sixth);
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
