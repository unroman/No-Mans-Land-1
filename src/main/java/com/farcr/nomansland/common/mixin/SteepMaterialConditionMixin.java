package com.farcr.nomansland.common.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.level.levelgen.SurfaceRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SurfaceRules.Context.SteepMaterialCondition.class)
public abstract class SteepMaterialConditionMixin {
    @Inject(method = "compute", at = @At(value = "TAIL"), cancellable = true)
    private void fixMountainBug(CallbackInfoReturnable<Boolean> cir, @Local(ordinal = 4) int i1, @Local(ordinal = 5) int j1, @Local(ordinal = 8) int i2, @Local(ordinal = 9) int j2)  {
        if (i1 >= j1 + 4) {
            cir.setReturnValue(true);
        }
        if (j2 >= i2 + 4) {
            cir.setReturnValue(true);
        }
    }
}
