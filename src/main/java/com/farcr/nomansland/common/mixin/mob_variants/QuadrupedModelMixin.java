package com.farcr.nomansland.common.mixin.mob_variants;

import com.farcr.nomansland.client.model.NMLCowModel;
import com.farcr.nomansland.client.model.NMLPigModel;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Pig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(QuadrupedModel.class)
public class QuadrupedModelMixin<T extends Entity> {
    @Unique
    protected ModelPart root;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(ModelPart root, boolean scaleHead, float babyYHeadOffset, float babyZHeadOffset, float babyHeadScale, float babyBodyScale, int bodyYOffset, CallbackInfo ci) {
        this.root = root;
    }

    @Inject(method = "setupAnim", at = @At("TAIL"))
    private void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        if (entity instanceof Pig pig)
            NMLPigModel.setupAnim(pig, root, limbSwing, limbSwingAmount, netHeadYaw, headPitch);
        if (entity instanceof Cow cow)
            NMLCowModel.setupAnim(cow, root);
    }
}
