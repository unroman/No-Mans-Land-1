package com.farcr.nomansland.common.mixin;

import com.farcr.nomansland.common.registry.blocks.NMLBlocks;
import net.minecraft.world.damagesource.FallLocation;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FallLocation.class)
public class FallLocationMixin {

    @Inject(method = "blockToFallLocation", at = @At("HEAD"), cancellable = true)
    private static void addWoodenScaffolding(BlockState state, CallbackInfoReturnable<FallLocation> cir) {
        if (state.is(NMLBlocks.WOODEN_SCAFFOLDING)) cir.setReturnValue(FallLocation.SCAFFOLDING);
    }
}
