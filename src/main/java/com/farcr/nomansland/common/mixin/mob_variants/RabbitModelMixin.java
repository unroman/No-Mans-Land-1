package com.farcr.nomansland.common.mixin.mob_variants;

import com.farcr.nomansland.client.model.vanilla.NMLRabbitModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.RabbitModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Rabbit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RabbitModel.class)
public class RabbitModelMixin<T extends Entity> {

    @Shadow private float jumpRotation;
    @Unique
    protected ModelPart root;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(ModelPart root, CallbackInfo ci) {
        this.root = root;
    }

    @Inject(method = "createBodyLayer", at = @At("RETURN"), cancellable = true)
    private static void createBodyLayer(CallbackInfoReturnable<LayerDefinition> cir) {
        cir.setReturnValue(NMLRabbitModel.createBodyLayer());
    }

    @Inject(method = "setupAnim*", at = @At("TAIL"), cancellable = true)
    private void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        NMLRabbitModel.setupAnim((Rabbit) entity, root, limbSwing, limbSwing, ageInTicks, netHeadYaw, headPitch, this.jumpRotation);
        ci.cancel();
    }

    @Inject(method = "renderToBuffer", at = @At("HEAD"), cancellable = true)
    private void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color, CallbackInfo ci) {
        NMLRabbitModel.renderToBuffer(root, poseStack, buffer, packedLight, packedOverlay, color);
        ci.cancel();
    }
}
