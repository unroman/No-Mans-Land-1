package com.farcr.nomansland.common.mixin.mob_variants;

import com.farcr.nomansland.common.entity.mob_variant.SheepVariant;
import com.farcr.nomansland.common.mixin.RenderLayerMixin;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.SheepModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.SheepFurLayer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SheepFurLayer.class)
public abstract class SheepFurLayerMixin extends RenderLayerMixin<Sheep, SheepModel<Sheep>> {
    @Unique
    private SheepModel<Sheep> model;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(RenderLayerParent<Sheep, SheepModel<Sheep>> renderer, EntityModelSet modelSet, CallbackInfo ci) {
        this.model = new SheepModel<>(modelSet.bakeLayer(ModelLayers.SHEEP));
    }

    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/animal/Sheep;FFFFFF)V", at = @At("HEAD"), cancellable = true)
    private void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Sheep sheep, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {

        ResourceLocation furTexture = (sheep.isBaby() ? ((VariantHolder<Holder<SheepVariant>>) sheep).getVariant().value().babyFurTexture() : sheep.isSheared() ? ((VariantHolder<Holder<SheepVariant>>) sheep).getVariant().value().shearedFurTexture() : ((VariantHolder<Holder<SheepVariant>>) sheep).getVariant().value().furTexture()).withPath((path) -> "textures/" + path + ".png");

        if (sheep.isInvisible()) {
                Minecraft minecraft = Minecraft.getInstance();
                if (minecraft.shouldEntityAppearGlowing(sheep)) {
                    this.getParentModel().copyPropertiesTo(this.model);
                    this.model.prepareMobModel(sheep, limbSwing, limbSwingAmount, partialTicks);
                    this.model.setupAnim(sheep, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                    VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.outline(furTexture));
                    this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, LivingEntityRenderer.getOverlayCoords(sheep, 0.0F), -16777216);
                }
            } else {
                int i;
                if (sheep.hasCustomName() && sheep.getName().getString().equals("jeb_")) {
                    int k = sheep.tickCount / 25 + sheep.getId();
                    int l = DyeColor.values().length;
                    int i1 = k % l;
                    int j1 = (k + 1) % l;
                    float f = ((float) (sheep.tickCount % 25) + partialTicks) / 25.0F;
                    int k1 = Sheep.getColor(DyeColor.byId(i1));
                    int l1 = Sheep.getColor(DyeColor.byId(j1));
                    i = FastColor.ARGB32.lerp(f, k1, l1);
                } else {
                    i = Sheep.getColor(sheep.getColor());
                }

                coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, furTexture, poseStack, buffer, packedLight, sheep, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTicks, i);
        }

        ci.cancel();
    }

    @Unique
    private static <T extends LivingEntity> void coloredCutoutModelCopyLayerRender(EntityModel<T> modelParent, EntityModel<T> model, ResourceLocation textureLocation, PoseStack poseStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float partialTick, int color) {
        if (!entity.isInvisible()) {
            modelParent.copyPropertiesTo(model);
            model.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTick);
            model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            renderColoredCutoutModel(model, textureLocation, poseStack, buffer, packedLight, entity, color);
        }

    }

    @Unique
    private static <T extends LivingEntity> void renderColoredCutoutModel(EntityModel<T> model, ResourceLocation textureLocation, PoseStack poseStack, MultiBufferSource buffer, int packedLight, T entity, int color) {
        VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(textureLocation));
        model.renderToBuffer(poseStack, vertexconsumer, packedLight, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), color);
    }
}
