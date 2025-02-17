package com.farcr.nomansland.common.mixin.mob_variants;

import com.farcr.nomansland.client.model.vanilla.NMLSheepModel;
import com.farcr.nomansland.common.mixin.QuadrupedModelMixin;
import net.minecraft.client.model.SheepModel;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.world.entity.animal.Sheep;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SheepModel.class)
public class SheepModelMixin<T extends Sheep> extends QuadrupedModelMixin<T> {
    @Unique
    private float noMansLand$headXRot;
    @Inject(method = "createBodyLayer", at = @At("RETURN"), cancellable = true)
    private static void createBodyLayer(CallbackInfoReturnable<LayerDefinition> cir) {
        cir.setReturnValue(NMLSheepModel.createBodyLayer());
    }

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/animal/Sheep;FFFFF)V", at = @At("TAIL"))
    private void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        NMLSheepModel.setupAnim(entity, root, ageInTicks, noMansLand$headXRot);
    }

    @Inject(method = "prepareMobModel(Lnet/minecraft/world/entity/animal/Sheep;FFF)V", at = @At("HEAD"), cancellable = true)
    private void prepareMobModel(T entity, float limbSwing, float limbSwingAmount, float partialTick, CallbackInfo ci) {
        head.y = 11 + entity.getHeadEatPositionScale(partialTick) * 7.0F;
        noMansLand$headXRot = entity.getHeadEatAngleScale(partialTick);
        ci.cancel();
    }
}
