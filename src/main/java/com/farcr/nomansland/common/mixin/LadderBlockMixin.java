package com.farcr.nomansland.common.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LadderBlock.class)
public class LadderBlockMixin {
    @Inject(method = "canAttachTo", at = @At("HEAD"), cancellable = true)
    protected void injected(BlockGetter blockReader, BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        BlockState blockstate = blockReader.getBlockState(pos);
        cir.setReturnValue(blockstate.isFaceSturdy(blockReader, pos, direction));
    }
}
