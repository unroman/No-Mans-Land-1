package com.farcr.nomansland.common.world.feature.decorator;

import com.farcr.nomansland.common.registry.NMLFallenTreeDecoratorTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.BaseCoralWallFanBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class ClusterSideFallenTreeDecorator extends FallenTreeDecorator {
    public static final MapCodec<ClusterSideFallenTreeDecorator> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    Codec.floatRange(0.0F, 1.0f).fieldOf("probability").forGetter(f -> f.probability),
                    Codec.floatRange(0.0F, 1.0f).fieldOf("block_probability").forGetter(f -> f.blockProbability),
                    Codec.intRange(0, 16).fieldOf("limit").forGetter(f -> f.limit),
                    BlockStateProvider.CODEC.fieldOf("block_provider").forGetter(f -> f.blockProvider)
            ).apply(instance, ClusterSideFallenTreeDecorator::new)
    );
    protected final float probability;
    protected final float blockProbability;
    protected final int limit;
    protected final BlockStateProvider blockProvider;

    public ClusterSideFallenTreeDecorator(float probability, float blockProbability, int limit, BlockStateProvider blockProvider) {
        this.probability = probability;
        this.blockProbability = blockProbability;
        this.limit = limit;
        this.blockProvider = blockProvider;
    }

    @Override
    protected FallenTreeDecoratorType<?> type() { return NMLFallenTreeDecoratorTypes.CLUSTER_SIDE.get(); }

    @Override
    public void place(Context context) {
        int blocks = 0;
        RandomSource random = context.random();
        if (random.nextFloat() > probability) return;
        for (BlockPos pos : Util.shuffledCopy(context.logs(), random)) {
            for (Direction dir : Direction.Plane.HORIZONTAL.shuffledCopy(random)) {
                if (context.level().getBlockState(pos.relative(dir)).is(BlockTags.REPLACEABLE) && random.nextFloat() < blockProbability) {
                    BlockState state = blockProvider.getState(random, pos.relative(dir));
                    if (state.hasProperty(BaseCoralWallFanBlock.FACING))
                        state = state.setValue(BaseCoralWallFanBlock.FACING, dir);
                    context.setBlock(pos.relative(dir), state);
                    blocks++;
                    break;
                }
            }
            if (blocks > limit) break;
        }
    }
}
