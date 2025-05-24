package com.farcr.nomansland.client.renderer;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.client.NMLModelLayers;
import com.farcr.nomansland.client.model.MooseModel;
import com.farcr.nomansland.common.entity.Moose;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class MooseRenderer extends MobRenderer<Moose, MooseModel> {
    public MooseRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new MooseModel(pContext.bakeLayer(NMLModelLayers.MOOSE_LAYER)), 1.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(Moose pEntity) {
        return NoMansLand.location("textures/entity/moose/moose_brown.png");
    }

    @Override
    public void render(Moose pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        if (pEntity.isBaby()) {
            pMatrixStack.scale(0.5f, 0.5f, 0.5f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}