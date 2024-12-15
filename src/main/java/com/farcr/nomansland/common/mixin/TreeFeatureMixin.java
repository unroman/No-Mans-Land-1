package com.farcr.nomansland.common.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TreeFeature.class)
public class TreeFeatureMixin {
    @Inject(method = "isAirOrLeaves", at = @At("HEAD"), cancellable = true)
    private static void isAirOrLeaves(LevelSimulatedReader level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (level.isStateAtPosition(pos, state -> state.is(BlockTags.REPLACEABLE_BY_TREES))) cir.setReturnValue(true);
    }
}
