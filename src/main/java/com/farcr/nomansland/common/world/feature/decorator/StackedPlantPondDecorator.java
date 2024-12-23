package com.farcr.nomansland.common.world.feature.decorator;

import com.farcr.nomansland.common.registry.NMLPondDecoratorType;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StackedPlantPondDecorator extends PondDecorator {
    public static final MapCodec<StackedPlantPondDecorator> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    BlockStateProvider.CODEC.fieldOf("block_provider").forGetter(f -> f.blockProvider),
                    IntProvider.CODEC.fieldOf("cluster_count").forGetter(f -> f.clusterCount),
                    IntProvider.CODEC.fieldOf("cluster_size").forGetter(f -> f.clusterSize),
                    FloatProvider.CODEC.fieldOf("cluster_density").forGetter(f -> f.clusterDensity),
                    IntProvider.CODEC.fieldOf("cluster_spread").forGetter(f -> f.clusterSpread),
                    IntProvider.CODEC.fieldOf("plant_height").forGetter(f -> f.plantHeight)
            ).apply(instance, StackedPlantPondDecorator::new)
    );
    protected final BlockStateProvider blockProvider;
    protected final IntProvider clusterCount;
    protected final IntProvider clusterSize;
    protected final FloatProvider clusterDensity;
    protected final IntProvider clusterSpread;
    protected final IntProvider plantHeight;

    public StackedPlantPondDecorator(BlockStateProvider blockProvider, IntProvider clusterCount, IntProvider clusterSize, FloatProvider clusterDensity, IntProvider clusterSpread, IntProvider plantHeight) {
        this.blockProvider = blockProvider;
        this.clusterCount = clusterCount;
        this.clusterSize = clusterSize;
        this.clusterDensity = clusterDensity;
        this.clusterSpread = clusterSpread;
        this.plantHeight = plantHeight;
    }

    @Override
    protected PondDecoratorType<?> type() {
        return NMLPondDecoratorType.STACKED_PLANT.get();
    }

    @Override
    public void place(Context context) {
        Set<BlockPos> excluded = new HashSet<>();
        RandomSource random = context.random();
        LevelReader level = context.level();
        int count = 0;
        int limit = clusterCount.sample(random);
        for (BlockPos pos : Util.shuffledCopy(context.water(), random)) {
            if (!excluded.contains(pos)) {
                BlockPos adj = getAdjacentGround(level, random, pos);
                if (adj != null) {
                    placePlant(random, level, context, adj);
                    //context.setBlock(adj, Blocks.SUGAR_CANE.defaultBlockState());
                    excluded.add(pos);
                    int size = clusterSize.sample(random);
                    int spread = clusterSpread.sample(random);
                    float density = clusterDensity.sample(random);
                    int inClusterCount = 1;
                    for (int x = -spread; x <= spread; x++) {
                        for (int z = -spread; z <= spread; z++) {
                            if (inClusterCount < size && random.nextFloat() < density) {
                                BlockPos adj2 = adj.offset(x, 0, z);
                                if (blockProvider.getState(random, adj2).canSurvive(level, adj2) && level.getBlockState(adj2).is(BlockTags.REPLACEABLE)) {
                                    //context.setBlock(adj2, Blocks.SUGAR_CANE.defaultBlockState());
                                    placePlant(random, level, context, adj2);
                                    inClusterCount++;
                                }
                            }
                        }
                    }
                    count++;
                }
            }
            if (count >= limit) break;
        }
    }

    private BlockPos getAdjacentGround(LevelReader level, RandomSource random, BlockPos pos) {
        List<BlockPos> validPositions = new ArrayList<>();
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            BlockPos rel = pos.relative(dir).above();
            if (blockProvider.getState(random, rel).canSurvive(level, rel) && level.getBlockState(rel).is(BlockTags.REPLACEABLE)) {
                validPositions.add(rel);
            }
        }
        if (validPositions.isEmpty()) return null;
        return validPositions.get(random.nextInt(validPositions.size()));
    }

    private void placePlant(RandomSource random, LevelReader level, Context context, BlockPos pos) {
        int i = 0;
        int height = plantHeight.sample(random);
        BlockState plantState = blockProvider.getState(random, pos);
        while (i < height && level.getBlockState(pos.above(i)).is(BlockTags.REPLACEABLE)) {
            context.setBlock(pos.above(i), plantState);
            i++;
        }
    }
}
