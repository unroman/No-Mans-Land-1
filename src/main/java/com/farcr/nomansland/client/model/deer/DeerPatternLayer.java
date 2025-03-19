package com.farcr.nomansland.client.model.deer;

import com.farcr.nomansland.common.entity.deer.Deer;
import com.farcr.nomansland.common.entity.deer.DeerPatternVariant;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.tazer.mixed_litter.variants.MobVariant;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import static dev.tazer.mixed_litter.VariantUtil.getVariants;

@OnlyIn(Dist.CLIENT)
public class DeerPatternLayer extends RenderLayer<Deer, DeerModel<Deer>> {

    public DeerPatternLayer(RenderLayerParent<Deer, DeerModel<Deer>> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, Deer deer, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        DeerPatternVariant variant = null;

        for (Holder<MobVariant> mobVariantHolder : getVariants(deer)) {
            if (mobVariantHolder.value() instanceof DeerPatternVariant patternVariant) {
                variant = patternVariant;
                break;
            }
        }

        if (variant != null && !deer.isInvisible()) {
            ResourceLocation texture = deer.isBaby() ? variant.babyTexture : variant.texture;
            VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderType.entityTranslucent(texture.withPath(path -> "textures/" + path + ".png")));
            getParentModel().renderToBuffer(poseStack, vertexconsumer, packedLight, LivingEntityRenderer.getOverlayCoords(deer, 0.0F));
        }
    }
}
