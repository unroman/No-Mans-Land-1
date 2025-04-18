package com.farcr.nomansland.common.mixin;

import com.farcr.nomansland.common.registry.NMLTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(FlowerBlock.class)
public class FlowerBlockMixin implements BonemealableBlock {
    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
        return blockState.is(NMLTags.BONEMEAL_SPREADS);
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        int x = blockPos.getX();
        int y = blockPos.getY();
        int z = blockPos.getZ();
        for (BlockPos pos : BlockPos.betweenClosed(x - 3, y - 1, z - 3, x + 3, y + 2, z + 3)) {
            if (randomSource.nextFloat() < 0.3F && blockState.canSurvive(serverLevel, pos) && serverLevel.isEmptyBlock(pos)) {
                serverLevel.setBlockAndUpdate(pos, blockState);
            }
        }
    }
}
