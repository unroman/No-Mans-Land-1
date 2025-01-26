package com.farcr.nomansland.common.mixin;

import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.SurfaceRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(SurfaceRules.Context.SteepMaterialCondition.class)
public abstract class SteepMaterialConditionMixin {
    @Inject(method = "compute", at = @At(value = "TAIL"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void fixMountainBug(CallbackInfoReturnable<Boolean> cir, int i, int j, int k, int l, ChunkAccess chunkaccess, int i1, int j1, int k1, int l1, int i2, int j2)  {
        if (i1 >= j1 + 4) {
            cir.setReturnValue(true);
        }
        if (j2 >= i2 + 4) {
            cir.setReturnValue(true);
        }
    }
}
