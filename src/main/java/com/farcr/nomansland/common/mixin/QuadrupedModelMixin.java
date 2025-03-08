package com.farcr.nomansland.common.mixin;

import com.farcr.nomansland.client.model.vanilla.NMLCowModel;
import com.farcr.nomansland.client.model.vanilla.NMLPigModel;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Pig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(QuadrupedModel.class)
public class QuadrupedModelMixin<T extends Entity> {

    @Shadow @Final protected ModelPart head;

    @Unique
    protected ModelPart root;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void nml$init(ModelPart root, boolean scaleHead, float babyYHeadOffset, float babyZHeadOffset, float babyHeadScale, float babyBodyScale, int bodyYOffset, CallbackInfo ci) {
        this.root = root;
    }

    @Inject(method = "setupAnim", at = @At("TAIL"))
    private void nml$setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        if (entity.getType() == EntityType.PIG)
            NMLPigModel.setupAnim((Pig) entity, root, limbSwing, limbSwingAmount, netHeadYaw, headPitch);
        if (entity.getType() == EntityType.COW)
            NMLCowModel.setupAnim((Cow) entity, root);
    }
}
