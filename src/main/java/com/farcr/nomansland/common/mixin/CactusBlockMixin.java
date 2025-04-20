package com.farcr.nomansland.common.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CactusBlock.class)
public class CactusBlockMixin implements BonemealableBlock {
    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
        for (int i = 0; i < 20; i++) {
            BlockPos pos = blockPos.above(i+1);
            if (levelReader.isEmptyBlock(pos)) {
                return true;
            } else if (levelReader.getBlockState(pos) != blockState) {
                return false;
            }
        }

        return false;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        BlockPos pos = null;
        for (int i = 0; i < 20; i++) {
            BlockPos posAbove = blockPos.above(i+1);
            if (serverLevel.isEmptyBlock(posAbove)) {
                pos = posAbove;
                break;
            } else if (serverLevel.getBlockState(posAbove) != blockState) {
                break;
            }
        }

        if (pos != null) serverLevel.setBlockAndUpdate(pos, blockState);
    }
}
