package com.farcr.nomansland.common.mixin;

import com.farcr.nomansland.common.registry.NMLBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.extensions.IBlockExtension;
import net.neoforged.neoforge.common.extensions.IBlockStateExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IBlockExtension.class)
public class IBlockExtensionMixin {
    @Inject(method = "isScaffolding", at = @At("RETURN"), cancellable = true)
    private void isScaffolding(BlockState state, LevelReader level, BlockPos pos, LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        if (state.is(NMLBlocks.WOODEN_SCAFFOLDING)) cir.setReturnValue(true);
    }
}
