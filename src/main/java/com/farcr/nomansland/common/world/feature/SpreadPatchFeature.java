package com.farcr.nomansland.common.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

//Thank you greatest cappin for helping with this
public class SpreadPatchFeature extends Feature<SpreadPatchConfiguration> {
    public SpreadPatchFeature(Codec<SpreadPatchConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<SpreadPatchConfiguration> context) {
        BlockPos origin = context.origin();
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        SpreadPatchConfiguration config = context.config();

        int tries = config.tries();
        int xzSpread = config.xzSpread();
        int ySpread = config.ySpread();
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

            if (distanceFromOriginXZ < 1.5*16) {
                config.feature().value().place(level, context.chunkGenerator(), random, pos);
            }
        }
        return true;
    }
}
