package com.farcr.nomansland.common.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class MultiSpreadPatchFeature extends Feature<MultiSpreadPatchConfiguration> {
    public MultiSpreadPatchFeature(Codec<MultiSpreadPatchConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<MultiSpreadPatchConfiguration> context) {
        BlockPos origin = context.origin();
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        MultiSpreadPatchConfiguration config = context.config();

        int tries = config.tries();
        int xzSpread = config.xzSpread().sample(random);
        int ySpread = config.ySpread().sample(random);
        double sparseness = config.sparseness();

        BlockPos.MutableBlockPos pos = origin.mutable();
        for (int i = 0; i < tries; i++) {
            int x = (int) (origin.getX() + (Math.clamp(random.nextGaussian(), -sparseness, sparseness) / sparseness) * xzSpread);
            int z = (int) (origin.getZ() + (Math.clamp(random.nextGaussian(), -sparseness, sparseness) / sparseness) * xzSpread);
            int y = origin.getY() + random.nextInt(-ySpread, ySpread);
            pos.set(x, y, z);

            if (Math.abs(x - origin.getX()) > 16 || Math.abs(z - origin.getZ()) > 16)
                break;

            float distanceFromOriginXZ = Mth.sqrt((x - origin.getX())*(x - origin.getX()) + (z - origin.getZ())*(z - origin.getZ()));
            float modifiedDistanceFromOriginXZ = distanceFromOriginXZ + ((random.nextFloat() * 2) - 1) * 0.8f;
            modifiedDistanceFromOriginXZ = Mth.clamp(modifiedDistanceFromOriginXZ / (xzSpread*3), 0.0f, 0.99f);
            int index = Mth.floor(modifiedDistanceFromOriginXZ * config.features().size());

            if (distanceFromOriginXZ < 1.5*16 && modifiedDistanceFromOriginXZ < 1.5*16) config.features().get(index).value().place(level, context.chunkGenerator(), random, pos);
        }

        return true;
    }
}