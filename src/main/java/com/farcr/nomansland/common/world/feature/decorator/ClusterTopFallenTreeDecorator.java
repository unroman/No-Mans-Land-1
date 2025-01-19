package com.farcr.nomansland.common.world.feature.decorator;

import com.farcr.nomansland.common.registry.NMLFallenTreeDecoratorTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.HashSet;
import java.util.Set;

public class ClusterTopFallenTreeDecorator extends FallenTreeDecorator {
    public static final MapCodec<ClusterTopFallenTreeDecorator> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    Codec.floatRange(0.0F, 1.0f).fieldOf("probability").forGetter(f -> f.probability),
                    Codec.floatRange(0.0F, 1.0f).fieldOf("block_probability").forGetter(f -> f.blockProbability),
                    Codec.intRange(0, 16).fieldOf("limit").forGetter(f -> f.limit),
                    BlockStateProvider.CODEC.fieldOf("block_provider").forGetter(f -> f.blockProvider)
            ).apply(instance, ClusterTopFallenTreeDecorator::new)
    );
    protected final float probability;
    protected final float blockProbability;
    protected final int limit;
    protected final BlockStateProvider blockProvider;

    public ClusterTopFallenTreeDecorator(float probability, float blockProbability, int limit, BlockStateProvider blockProvider) {
        this.probability = probability;
        this.blockProbability = blockProbability;
        this.limit = limit;
        this.blockProvider = blockProvider;
    }

    @Override
    protected FallenTreeDecoratorType<?> type() { return NMLFallenTreeDecoratorTypes.CLUSTER_TOP.get(); }

    @Override
    public void place(Context context) {
        Set<BlockPos> excluded = new HashSet<>();
        int blocks = 0;
        RandomSource random = context.random();
        if (random.nextFloat() > probability) return;
        for (BlockPos pos : Util.shuffledCopy(context.logs(), random)) {
            if (!excluded.contains(pos) && context.isAir(pos.above()) && random.nextFloat() < blockProbability) {
                excluded.add(pos.above());
                context.setBlock(pos.above(), blockProvider.getState(random, pos.above()));
                blocks++;
            }
            if (blocks > limit) break;
        }
    }
}
