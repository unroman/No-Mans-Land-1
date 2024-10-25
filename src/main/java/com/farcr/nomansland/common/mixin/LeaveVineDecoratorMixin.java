package com.farcr.nomansland.common.mixin;

import com.farcr.nomansland.common.registry.NMLBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.levelgen.feature.treedecorators.LeaveVineDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LeaveVineDecorator.class)
public class LeaveVineDecoratorMixin {
    @Inject(method = "addHangingVine", at = @At("HEAD"), cancellable = true)
    private static void addHangingVine(BlockPos pos, BooleanProperty sideProperty, TreeDecorator.Context context, CallbackInfo ci) {
        for (int i = 0; i < 4; i++) {
            pos = pos.below(i);
            if (context.isAir(pos)) {
                if (i == 3) context.setBlock(pos, NMLBlocks.CUT_VINE.get().defaultBlockState().setValue(sideProperty, true));
                else context.placeVine(pos, sideProperty);
            } else if (context.level().isStateAtPosition(pos.above(), state -> state.is(Blocks.VINE) || state.isAir())) {
                context.setBlock(pos.above(), NMLBlocks.CUT_VINE.get().defaultBlockState().setValue(sideProperty, true));
                return;
            }
        }

        ci.cancel();
    }
}
