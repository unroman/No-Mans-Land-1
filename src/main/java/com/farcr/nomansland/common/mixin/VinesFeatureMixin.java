package com.farcr.nomansland.common.mixin;

import com.farcr.nomansland.common.registry.NMLBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.VinesFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VinesFeature.class)
public class VinesFeatureMixin {
    @Inject(method = "place", at = @At("HEAD"), cancellable = true)
    private void place(FeaturePlaceContext<NoneFeatureConfiguration> context, CallbackInfoReturnable<Boolean> cir) {
//        WorldGenLevel worldgenlevel = context.level();
//        BlockPos pos = context.origin();
//        context.config();
//        if (!worldgenlevel.isEmptyBlock(pos)) {
//            if (worldgenlevel.getBlockState(pos).is(Blocks.VINE)) {
//                for (int i = 0; i < 10; i++) {
//                    BlockPos pos1 = pos.below(i);
//                    if (worldgenlevel.getBlockState(pos1).is(Blocks.VINE)) continue;
//                    if (worldgenlevel.getBlockState(pos1).is(NMLBlocks.CUT_VINE)) break;
//                    worldgenlevel.setBlock(pos1.above(), NMLBlocks.CUT_VINE.get().withPropertiesOf(worldgenlevel.getBlockState(pos)), 2);
//                    cir.setReturnValue(true);
//                }
//            } else cir.setReturnValue(false);
//        } else {
//            for (Direction direction : Direction.values()) {
//                if (direction != Direction.DOWN && VineBlock.isAcceptableNeighbour(worldgenlevel, pos.relative(direction), direction)) {
//                    worldgenlevel.setBlock(pos, Blocks.VINE.defaultBlockState().setValue(VineBlock.getPropertyForFace(direction), true), 2);
//                    cir.setReturnValue(true);
//                }
//            }
//
//            cir.setReturnValue(false);
//        }
//        cir.cancel();
    }
}
