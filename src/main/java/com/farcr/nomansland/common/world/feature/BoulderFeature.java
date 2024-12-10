package com.farcr.nomansland.common.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.ArrayList;
import java.util.Iterator;

public class BoulderFeature extends Feature<BoulderFeatureConfiguration> {
    public BoulderFeature(Codec<BoulderFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<BoulderFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();
        BoulderFeatureConfiguration config = context.config();

        int numBoulders = config.numCubes().sample(random);
        int cubeSpread = config.cubeSpread().sample(random);
        int blocksChanged = 0;

        ArrayList<BlockPos> stonePos = new ArrayList<>();

        BlockPos.MutableBlockPos pos = origin.mutable();

        for (int i = 0; i < numBoulders; i++) {
            int cubeSize = config.cubeSize().sample(random);
            int cubeSizeY = cubeSize - (i > 0 ? 1 : 0);
            //int cubeSizeZ = random.nextIntBetweenInclusive(2, 3);
            int minX = random.nextIntBetweenInclusive(-cubeSpread - cubeSize, cubeSpread);
            int minZ = random.nextIntBetweenInclusive(-cubeSpread - cubeSize, cubeSpread);
            for (int x = 0; x < cubeSize; x++) {
                for (int y = 0; y < cubeSizeY; y++) {
                    for (int z = 0; z < cubeSize; z++) {
                        if ((x > 0 && x < cubeSize - 1) || (z > 0 && z < cubeSize - 1) || y < cubeSizeY - 1 || random.nextFloat() < config.cubeRoundingPercentage()) {
                            pos.set(origin.offset(x + minX, y, z + minZ));
                            if (level.getBlockState(pos).is(BlockTags.REPLACEABLE) && !stonePos.contains(pos)) {
                                while (level.getBlockState(pos.below()).is(BlockTags.REPLACEABLE) && !stonePos.contains(pos.below())) {
                                    pos.move(Direction.DOWN);
                                }
                                //level.setBlock(pos, Blocks.COBBLESTONE.defaultBlockState(), 2);
                                stonePos.add(pos.immutable());
                                blocksChanged++;
                            }
                        }
                    }
                }
            }
        }

        for (int i = 0; i < config.numErosionRounds(); i++) {
            for (Iterator<BlockPos> it = stonePos.iterator(); it.hasNext();) {
                BlockPos pos1 = it.next();
                if (!level.getBlockState(pos1.below()).isAir()) {
                    int solidCount = 0;
                    for (Direction direction : Direction.values()) {
                        if (stonePos.contains(pos1.relative(direction))) {
                            solidCount += 1;
                        }
                    }
                    if (solidCount < 3 && stonePos.contains(pos1) && random.nextFloat() < config.erosionPercentage()) {
                        it.remove();
                        blocksChanged--;
                    }
                }
            }
        }

        if (blocksChanged < 10) {
            return false;
        }

        for (BlockPos pos1 : stonePos) {
            if (level.getBlockState(pos1).is(BlockTags.REPLACEABLE)) {
                level.setBlock(pos1, config.blockProvider().getState(random, pos1), 2);
            }
        }

        return true;
    }
}
