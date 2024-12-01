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
                for (int z = -poolSize; z <= poolSize; z++) {
                    if ((x*x + z*z < poolSize*poolSize - 1 && random.nextInt(3) == 0) || x*x + z*z < (poolSize-1)*(poolSize-1)) {
                        pos.set(originX + x, origin.getY(), originZ + z);
                        boolean placeable = level.getBlockState(pos).isSolid();
                        for (Direction direction : Direction.values())
                        {
                            if (direction != Direction.UP && !level.getBlockState(pos.relative(direction)).isSolid()) {
                                placeable = false;
                            }
                            if (direction == Direction.UP && level.getBlockState(pos.relative(direction)).isSolid()) {
                                return false;
                            }
                        }
                        if (placeable)
                        {
                            waterPos.add(pos.immutable());
                        }
                    }
                }
            }
        }
        for (BlockPos bpos : waterPos)
        {
            level.setBlock(bpos, Blocks.WATER.defaultBlockState(), 2);
            if (!level.getBlockState(bpos.above()).isAir()) {
                level.setBlock(bpos.above(), Blocks.AIR.defaultBlockState(), 2);
            }
            blocksChanged++;
        }
        return blocksChanged > 0;
    }
}
