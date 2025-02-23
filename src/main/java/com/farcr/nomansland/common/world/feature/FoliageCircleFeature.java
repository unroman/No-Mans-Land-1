package com.farcr.nomansland.common.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class FoliageCircleFeature extends Feature<FoliageCircleFeatureConfiguration> {
    public FoliageCircleFeature(Codec<FoliageCircleFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<FoliageCircleFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin().below();
        RandomSource random = context.random();
        FoliageCircleFeatureConfiguration config = context.config();

        int radius = config.radius().sample(random);
        int count = 0;

        // Evil and distressing implementation of Bresenham's circle drawing algorithm for Minecraft plants

        int x = 0;
        int y = radius;
        int d = 3 - 2*radius;
        count += drawCircle(origin.getX(), origin.getZ(), x, y, level, random, config);
        while (y >= x) {
            if (d > 0) {
                y--;
                d = d + 4 * (x-y) + 10;
            } else {
                d = d + 4 * x + 6;
            }
            x++;

            count += drawCircle(origin.getX(), origin.getZ(), x, y, level, random, config);
        }
        return count > 0;
    }

    int drawCircle(int xc, int yc, int x, int y, WorldGenLevel level, RandomSource random, FoliageCircleFeatureConfiguration config) {
        int count = 0;
        count += placeBlock(xc+x, yc+y, level, random, config);
        count += placeBlock(xc-x, yc+y, level, random, config);
        count += placeBlock(xc+x, yc-y, level, random, config);
        count += placeBlock(xc-x, yc-y, level, random, config);
        count += placeBlock(xc+y, yc+x, level, random, config);
        count += placeBlock(xc-y, yc+x, level, random, config);
        count += placeBlock(xc+y, yc-x, level, random, config);
        count += placeBlock(xc-y, yc-x, level, random, config);
        return count;
    }

    int placeBlock(int x, int z, WorldGenLevel level, RandomSource random, FoliageCircleFeatureConfiguration config) {
        BlockPos pos = new BlockPos(x, 0, z);
        int y = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, pos).getY();
        BlockPos pos2 = new BlockPos(x, y, z);
        BlockState state = config.state().getState(random, pos2);
        if (state.canSurvive(level, pos2)) {
            level.setBlock(pos2, state, 3);
            return 1;
        }
        return 0;
    }
}
