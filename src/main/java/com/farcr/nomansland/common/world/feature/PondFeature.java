package com.farcr.nomansland.common.world.feature;

import com.farcr.nomansland.common.world.feature.decorator.PondDecorator;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.Set;
import java.util.function.BiConsumer;

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
        Set<BlockPos> waterPos = Sets.newHashSet();
        Set<BlockPos> decoPos = Sets.newHashSet();
        for (int i = 0; i < numPools; i++) {
            int basePoolSize = config.poolSize().sample(random);
            int poolSizeX = max(basePoolSize - config.poolEccentricity().sample(random), 1);
            int poolSizeZ = max(basePoolSize - config.poolEccentricity().sample(random), 1);
            int poolDepth = config.poolDepth().sample(random);
            float poolStepth = 1 / config.poolStepth();
            if (i == 0) {
                poolSizeX += 1;
                poolSizeZ += 1;
            }
            int originX = origin.getX();
            int originZ = origin.getZ();
            if (i != 0) {
                int offsetX = poolSpread > 0 ? random.nextIntBetweenInclusive(1, poolSpread) : 0;
                int offsetZ = poolSpread > 0 ? random.nextIntBetweenInclusive(1, poolSpread) : 0;
                offsetX *= random.nextBoolean() ? 1 : -1;
                offsetZ *= random.nextBoolean() ? 1 : -1;
                originX += offsetX;
                originZ += offsetZ;
            }
            for (int x = -poolSizeX; x <= poolSizeX; x++) {
                for (int y = 0; y < poolDepth; y++) {
                    for (int z = -poolSizeZ; z <= poolSizeZ; z++) {
                        float xr = max(poolSizeX - ((float)y*poolStepth), 0) + 1;
                        float zr = max(poolSizeZ - ((float)y*poolStepth), 0) + 1;
                        float xrm1 = max(xr - 1, 0);
                        float zrm1 = max(zr - 1, 0);
                        float smoothing = (getSmoothingValue((int)xr) + getSmoothingValue((int)zr)) / 2;
                        float smoothingM1 = (getSmoothingValue((int)xrm1) + getSmoothingValue((int)zrm1)) / 2;
                        if (
                                (((((float)x * (float)x)/(xr * xr) + ((float)z * (float)z)/(zr * zr) <= 1 + smoothing)) && random.nextFloat() < config.poolNoise())
                                        || ((((float)x * (float)x)/(xrm1 * xrm1) + ((float)z * (float)z)/(zrm1 * zrm1) <= 1 + smoothingM1))
                        ) {
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
                if (!level.getBlockState(bpos.above(y)).isAir() && level.getBlockState(bpos.above(y)) != config.waterState().getState(random, bpos.above(y))) {
                    if (y < origin.getY() - bpos.getY() + 1)
                    {
                        level.setBlock(bpos.above(y), config.waterState().getState(random, bpos.above(y)), 2);
                    }
                    else
                    {
                        level.setBlock(bpos.above(y), Blocks.AIR.defaultBlockState(), 2);
                    }
                }
            }
        }
        for (BlockPos bpos : waterPos) {
            level.setBlock(bpos, config.waterState().getState(random, bpos), 2);
            BlockState floorState = config.floorState().getState(random, bpos.below());
            if (!floorState.isAir() && level.getBlockState(bpos.below()) != config.waterState().getState(random, bpos.below())) {
                level.setBlock(bpos.below(), floorState, 2);
            }
            blocksChanged++;
        }

        BiConsumer<BlockPos, BlockState> decoratorConsumer = (pos1, state1) -> {
            decoPos.add(pos1.immutable());
            level.setBlock(pos1, state1, 19);
        };
        if (!config.decorators().isEmpty()) {
            PondDecorator.Context ponddecorator$context = new PondDecorator.Context(level, decoratorConsumer, random, waterPos);
            config.decorators().forEach((deco) -> {
                deco.place(ponddecorator$context);
            });
        }

        return blocksChanged > 0;
    }

    float getSmoothingValue(int radius) {
        // This was figured out by trial and error, do not mess with unless you know what you're doing lol
        if (radius <= 2) return 0.3f;
        if (radius == 3 || radius == 4 || radius == 6 || radius >= 8) return 0.2f;
        return 0.1f;
    }
}
