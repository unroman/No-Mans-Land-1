package com.farcr.nomansland.common.mixin;

import com.farcr.nomansland.common.registry.NMLBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.levelgen.feature.treedecorators.LeaveVineDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LeaveVineDecorator.class)
public class LeaveVineDecoratorMixin {
    @Inject(method = "place", at = @At("TAIL"))
    private void place(TreeDecorator.Context context, CallbackInfo ci) {
        LevelSimulatedReader level = context.level();
        for (BlockPos pos : context.leaves()) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                BlockPos pos1 = pos.relative(direction);
                if (level.isStateAtPosition(pos1, state -> state.is(Blocks.VINE))) {
                    for (int i = 0; i < 10; i++) {
                        BlockPos pos2 = pos1.below(i);
                        if (level.isStateAtPosition(pos2, state -> state.is(Blocks.VINE))) continue;
                        if (level.isStateAtPosition(pos2, state -> state.is(NMLBlocks.CUT_VINE))) break;
                        context.setBlock(pos2.above(), NMLBlocks.CUT_VINE.get().defaultBlockState().setValue(VineBlock.getPropertyForFace(direction.getOpposite()), true));
                        break;
                    }
                }
            }
        }
    }
}
