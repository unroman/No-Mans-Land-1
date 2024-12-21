package com.farcr.nomansland.common.world.feature.decorator;

import com.farcr.nomansland.common.registry.NMLBoulderDecoratorType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.HashSet;
import java.util.Set;

public class ClusterOnStoneBoulderDecorator extends BoulderDecorator {
    public static final MapCodec<ClusterOnStoneBoulderDecorator> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    Codec.floatRange(0.0F, 1.0f).fieldOf("probability").forGetter(f -> f.probability),
                    Codec.intRange(0, 16).fieldOf("limit").forGetter(f -> f.limit),
                    BlockStateProvider.CODEC.fieldOf("block_provider").forGetter(f -> f.blockProvider)
            ).apply(instance, ClusterOnStoneBoulderDecorator::new)
    );
    protected final float probability;
    protected final int limit;
    protected final BlockStateProvider blockProvider;

    public ClusterOnStoneBoulderDecorator(float probability, int limit, BlockStateProvider blockProvider) {
        this.probability = probability;
        this.limit = limit;
        this.blockProvider = blockProvider;
    }

    @Override
    protected BoulderDecoratorType<?> type() {
        return NMLBoulderDecoratorType.CLUSTER_ON_STONE.get();
    }

    @Override
    public void place(Context context) {
        Set<BlockPos> excluded = new HashSet<>();
        int blocks = 0;
        RandomSource random = context.random();
        for (BlockPos pos : Util.shuffledCopy(context.stone(), random)) {
            if (!excluded.contains(pos) && context.isAir(pos.above()) && random.nextFloat() < probability) {
                excluded.add(pos.above());
                context.setBlock(pos.above(), blockProvider.getState(random, pos.above()));
                blocks++;
            }
            if (blocks > limit) break;
        }
    }
}
