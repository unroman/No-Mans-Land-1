package com.farcr.nomansland.common.world.feature.decorator;

import com.farcr.nomansland.common.registry.NMLPondDecoratorType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.HashSet;
import java.util.Set;

public class ClusterOnWaterPondDecorator extends PondDecorator {
    public static final MapCodec<ClusterOnWaterPondDecorator> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    Codec.floatRange(0.0F, 1.0f).fieldOf("probability").forGetter(f -> f.probability),
                    Codec.intRange(0, 16).fieldOf("limit").forGetter(f -> f.limit),
                    BlockStateProvider.CODEC.fieldOf("block_provider").forGetter(f -> f.blockProvider)
            ).apply(instance, ClusterOnWaterPondDecorator::new)
    );
    protected final float probability;
    protected final int limit;
    protected final BlockStateProvider blockProvider;

    public ClusterOnWaterPondDecorator(float probability, int limit, BlockStateProvider blockProvider) {
        this.probability = probability;
        this.limit = limit;
        this.blockProvider = blockProvider;
    }

    @Override
    protected PondDecoratorType<?> type() {
        return NMLPondDecoratorType.CLUSTER_ON_WATER.get();
    }

    @Override
    public void place(Context context) {
        Set<BlockPos> excluded = new HashSet<>();
        int lilies = 0;
        RandomSource random = context.random();
        for (BlockPos pos : Util.shuffledCopy(context.water(), random)) {
            if (!excluded.contains(pos) && context.isAir(pos.above()) && random.nextFloat() < probability) {
                excluded.add(pos.above());
                context.setBlock(pos.above(), blockProvider.getState(random, pos.above()));
                lilies++;
            }
            if (lilies > limit) break;
        }
    }
}
