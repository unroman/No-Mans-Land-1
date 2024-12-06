package com.farcr.nomansland.client.model.deer;

import com.farcr.nomansland.common.entity.deer.Deer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DeerPatternLayer extends RenderLayer<Deer, DeerModel<Deer>> {

    public DeerPatternLayer(RenderLayerParent<Deer, DeerModel<Deer>> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, Deer deer, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ResourceLocation patternTexture = deer.isBaby() ? deer.getPatternVariant().value().babyTexture() : deer.getPatternVariant().value().texture().withPath((path) -> "textures/" + path + ".png");
        if (!deer.isInvisible()) {
            VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderType.entityTranslucent(patternTexture));
            getParentModel().renderToBuffer(poseStack, vertexconsumer, packedLight, LivingEntityRenderer.getOverlayCoords(deer, 0.0F));
        }
    }
}
