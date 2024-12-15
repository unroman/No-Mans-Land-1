package com.farcr.nomansland.common.world.feature;

import com.farcr.nomansland.common.world.feature.decorator.PondDecorator;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.List;

public record PondFeatureConfiguration(IntProvider numPools, IntProvider poolSize, IntProvider poolEccentricity, int poolSpread, IntProvider poolDepth, float poolNoise, float poolStepth, BlockStateProvider waterState, BlockStateProvider floorState, List<PondDecorator> decorators) implements FeatureConfiguration {
    public static final Codec<PondFeatureConfiguration> CODEC = RecordCodecBuilder.create(
            record -> record.group(
                    IntProvider.codec(1, 8).fieldOf("num_pools").forGetter(PondFeatureConfiguration::numPools),
                    IntProvider.codec(1, 8).fieldOf("pool_size").forGetter(PondFeatureConfiguration::poolSize),
                    IntProvider.codec(0, 8).fieldOf("pool_eccentricity").forGetter(PondFeatureConfiguration::poolEccentricity),
                    ExtraCodecs.NON_NEGATIVE_INT.fieldOf("pool_spread").orElse(2).forGetter(PondFeatureConfiguration::poolSpread),
                    IntProvider.codec(1,8).fieldOf("pool_depth").forGetter(PondFeatureConfiguration::poolDepth),
                    Codec.FLOAT.fieldOf("pool_noise").orElse(0.0F).forGetter(PondFeatureConfiguration::poolNoise),
                    ExtraCodecs.POSITIVE_FLOAT.fieldOf("pool_steepness").orElse(1.0F).forGetter(PondFeatureConfiguration::poolStepth),
                    BlockStateProvider.CODEC.fieldOf("water_provider").orElse(BlockStateProvider.simple(Blocks.WATER)).forGetter(PondFeatureConfiguration::waterState),
                    BlockStateProvider.CODEC.fieldOf("floor_provider").orElse(BlockStateProvider.simple(Blocks.AIR)).forGetter(PondFeatureConfiguration::floorState),
                    PondDecorator.CODEC.listOf().fieldOf("decorators").orElse(List.of()).forGetter(PondFeatureConfiguration::decorators)
            ).apply(record, PondFeatureConfiguration::new)
    );
}
