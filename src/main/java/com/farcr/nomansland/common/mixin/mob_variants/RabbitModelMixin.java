package com.farcr.nomansland.common.mixin.mob_variants;

import com.farcr.nomansland.client.model.vanilla.NMLRabbitModel;
import com.farcr.nomansland.client.model.vanilla.NMLSheepModel;
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

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/animal/Rabbit;FFFFF)V", at = @At("TAIL"))
    private void setupAnim(Rabbit entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        NMLRabbitModel.setupAnim(entity, root, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    }
}
