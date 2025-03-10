package com.farcr.nomansland.client.model;

import com.farcr.nomansland.common.mixinduck.PigDuck;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Pig;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PigOverlayLayer extends RenderLayer<Pig, PigModel<Pig>> {

    public PigOverlayLayer(RenderLayerParent<Pig, PigModel<Pig>> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, Pig pig, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ResourceLocation overlayTexture = (pig.isBaby() ? ((PigDuck) pig).nml$getPigOverlayVariant().value().babyTexture() : ((PigDuck) pig).nml$getPigOverlayVariant().value().texture()).withPath((path) -> "textures/" + path + ".png");
        if (!pig.isInvisible()) {
            VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderType.entityTranslucent(overlayTexture));
            getParentModel().renderToBuffer(poseStack, vertexconsumer, packedLight, LivingEntityRenderer.getOverlayCoords(pig, 0.0F));
        }
    }
}
