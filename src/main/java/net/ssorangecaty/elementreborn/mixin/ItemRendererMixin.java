package net.ssorangecaty.elementreborn.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.ssorangecaty.elementreborn.item.SpItem;
import net.ssorangecaty.elementreborn.model.SpItemModel;
import net.ssorangecaty.elementreborn.util.IResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

    @Shadow
    private BakedModel getModel(ItemStack stack, Level level, LivingEntity entity, int overlay) {
        throw new IllegalStateException("Mixin shadow");
    }

    @Shadow
    private void render(ItemStack stack, ItemDisplayContext context, boolean leftHanded, PoseStack matrixStack, MultiBufferSource bufferSource, int light, int overlay, BakedModel model) {
        throw new IllegalStateException("Mixin shadow");
    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    public void renderStatic(
            @Nullable LivingEntity entity,
            ItemStack stack,
            ItemDisplayContext displayContext,
            boolean leftHanded,
            PoseStack matrixStack,
            MultiBufferSource bufferSource,
            @Nullable Level level,
            int light,
            int overlay,
            int p_270845_
    ) {
        if (!stack.isEmpty()) {
            if (stack.getItem() instanceof SpItem) {
                SpItemModel model = SpItemModel.getInstance();
                model.setPixelData(stack);
                model.renderToBuffer(matrixStack, bufferSource.getBuffer(RenderType.entitySolid(IResourceLocation.create("textures/item/sp_item.png"))), light, overlay, 0);
            }else{
                BakedModel bakedModel = this.getModel(stack, level, entity, p_270845_);
                this.render(stack, displayContext, leftHanded, matrixStack, bufferSource, light, overlay, bakedModel);
            }
        }
    }
}

