package com.farcr.nomansland.common.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.ArrayList;

import static java.lang.Math.max;

public class PondFeature extends Feature<PondFeatureConfiguration> {

    public PondFeature(Codec<PondFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<PondFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin().below();
        RandomSource random = context.random();
        PondFeatureConfiguration config = context.config();

        int numPools = config.numPools().sample(random);
        int poolSpread = config.poolSpread();
        int blocksChanged = 0;
        BlockPos.MutableBlockPos pos = origin.mutable();
        ArrayList<BlockPos> waterPos = new ArrayList<>();
        for (int i = 0; i < numPools; i++) {
            int poolSize = config.numPools().sample(random);
            int poolDepth = config.poolDepth().sample(random);
            float poolStepth = 1 / config.poolStepth();
            if (i == 0) {
                poolSize += 1;
            }
            int originX = origin.getX();
            int originZ = origin.getZ();
            if (i != 0) {
                originX += random.nextIntBetweenInclusive(-1, 1) * poolSpread;
                originZ += random.nextIntBetweenInclusive(-1, 1) * poolSpread;
            }
            for (int x = -poolSize; x <= poolSize; x++) {
                for (int y = 0; y < poolDepth; y++) {
                    for (int z = -poolSize; z <= poolSize; z++) {
                        float rf = max(poolSize - ((float)y*poolStepth), 0);
                        float rfm1 = max(rf - 1, 0);
                        if (((float) x * (float) x + (float) z * (float) z < rf * rf && random.nextInt(3) == 0) || (float) x * (float) x + (float) z * (float) z < (rfm1) * (rfm1)) {
                            pos.set(originX + x, origin.getY() - y, originZ + z);
                            boolean placeable = level.getBlockState(pos).isSolid();
                            for (Direction direction : Direction.values()) {
                                if (direction != Direction.UP && direction != Direction.DOWN && !level.getBlockState(pos.relative(direction)).isSolid()) {
                                    placeable = false;
                                }
                                if (y == poolDepth - 1 && direction == Direction.DOWN && !level.getBlockState(pos.relative(direction)).isSolid()) {
                                    placeable = false;
                                }
                                if (y == 0 && direction == Direction.UP && level.getBlockState(pos.relative(direction)).isSolid()) {
                                    return false;
                                }
                            }
                            if (y > 0 && !waterPos.contains(pos.above(y))) {
                                placeable = false;
                            }
                            if (placeable) {
                                waterPos.add(pos.immutable());
                            }
                        }
                    }
                }
            }
        }
        for (BlockPos bpos : waterPos)
        {
            for (int y = 1; y <= origin.getY() - bpos.getY() + 1; y++) {
                if (!level.getBlockState(bpos.above(y)).isAir() && !level.getBlockState(bpos.above(y)).is(Blocks.WATER)) {
                    if (y < origin.getY() - bpos.getY() + 1)
                    {
                        level.setBlock(bpos.above(y), Blocks.WATER.defaultBlockState(), 2);
                    }
                    else
                    {
                        level.setBlock(bpos.above(y), Blocks.AIR.defaultBlockState(), 2);
                    }
                }
            }
        }
        for (BlockPos bpos : waterPos) {
            level.setBlock(bpos, Blocks.WATER.defaultBlockState(), 2);
            blocksChanged++;
        }
        return blocksChanged > 0;
    }
}
