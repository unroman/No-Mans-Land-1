package com.farcr.nomansland.common.mixin;

import com.farcr.nomansland.common.block.FrostedGrassBlock;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SnowyDirtBlock.class)
public class SnowyDirtBlockMixin {

    @Inject(method = "isSnowySetting", at = @At("RETURN"), cancellable = true)
    private static void isSnowLogged(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (state.hasProperty(FrostedGrassBlock.SNOWLOGGED) && state.getValue(FrostedGrassBlock.SNOWLOGGED)) cir.setReturnValue(true);
    }
}
