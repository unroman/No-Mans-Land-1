package com.farcr.nomansland.common.world.feature.decorator;

import com.farcr.nomansland.common.registry.NMLPondDecoratorType;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderSet;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.ArrayList;
import java.util.List;

public class FeatureClusterPondDecorator extends PondDecorator {
    public static final MapCodec<FeatureClusterPondDecorator> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    IntProvider.CODEC.fieldOf("place_count").forGetter(f -> f.placeCount),
                    ExtraCodecs.POSITIVE_INT.fieldOf("tries").orElse(128).forGetter(f -> f.tries),
                    ExtraCodecs.NON_NEGATIVE_INT.fieldOf("xz_spread").orElse(7).forGetter(f -> f.xzSpread),
                    ExtraCodecs.NON_NEGATIVE_INT.fieldOf("y_spread").orElse(3).forGetter(f -> f.ySpread),
                    PlacedFeature.LIST_CODEC.fieldOf("features").forGetter(f -> f.features)
            ).apply(instance, FeatureClusterPondDecorator::new)
    );
    protected final IntProvider placeCount;
    protected final int tries;
    protected final int xzSpread;
    protected final int ySpread;
    protected final HolderSet<PlacedFeature> features;

    public FeatureClusterPondDecorator(IntProvider placeCount, int tries, int xzSpread, int ySpread, HolderSet<PlacedFeature> features) {
        this.placeCount = placeCount;
        this.tries = tries;
        this.xzSpread = xzSpread;
        this.ySpread = ySpread;
        this.features = features;
    }

    @Override
    protected PondDecoratorType<?> type() { return NMLPondDecoratorType.FEATURE_CLUSTER.get(); }

    @Override
    public void place(Context context) {
        RandomSource random = context.random();
        WorldGenLevel level = context.level();

        int count = 0;
        int limit = placeCount.sample(random);

        for (BlockPos pos : Util.shuffledCopy(context.water(), random)) {
            if (count >= limit) break;

            BlockPos adj = getAdjacentGround(level, random, pos);
            if (adj != null) {
                count++;

                BlockPos.MutableBlockPos pos1 = adj.mutable();
                for (int i = 0; i < tries; i++) {
                    int x = (int) (adj.getX() + random.nextGaussian() * xzSpread);
                    int z = (int) (adj.getZ() + random.nextGaussian() * xzSpread);
                    int y = adj.getY() + random.nextInt(-ySpread, ySpread);
                    pos1.set(x, y, z);

                    float distanceFromOriginXZ = Mth.sqrt((x - adj.getX())*(x - adj.getX()) + (z - adj.getZ())*(z - adj.getZ()));
                    distanceFromOriginXZ+=((random.nextFloat() * 2) - 1) * 0.8;
                    distanceFromOriginXZ = Mth.clamp(distanceFromOriginXZ / (xzSpread*3), 0.0f, 0.99f);
                    int index = Mth.floor(distanceFromOriginXZ * features.size());

                    if (distanceFromOriginXZ < 1.5*16) features.get(index).value().place(level, context.chunkGenerator(), random, pos1);
                }
            }
        }
    }

    private BlockPos getAdjacentGround(LevelReader level, RandomSource random, BlockPos pos) {
        List<BlockPos> validPositions = new ArrayList<>();
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            BlockPos rel = pos.relative(dir).above();
            if (level.getBlockState(rel.below()).isSolid() && level.getBlockState(rel).is(BlockTags.REPLACEABLE)) {
                validPositions.add(rel);
            }
        }
        if (validPositions.isEmpty()) return null;
        return validPositions.get(random.nextInt(validPositions.size()));
    }
}
