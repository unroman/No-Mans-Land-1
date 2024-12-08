package com.farcr.nomansland.common.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record PondFeatureConfiguration(IntProvider numPools, IntProvider poolSize, IntProvider poolEccentricity, int poolSpread, IntProvider poolDepth, float poolNoise, float poolStepth, BlockStateProvider waterState, BlockStateProvider floorState) implements FeatureConfiguration {
    public static final Codec<PondFeatureConfiguration> CODEC = RecordCodecBuilder.create(
            record -> record.group(
                    IntProvider.codec(1, 8).fieldOf("num_pools").forGetter(PondFeatureConfiguration::numPools),
                    IntProvider.codec(1, 8).fieldOf("pool_size").forGetter(PondFeatureConfiguration::poolSize),
                    IntProvider.codec(0, 8).fieldOf("pool_eccentricity").forGetter(PondFeatureConfiguration::poolEccentricity),
                    ExtraCodecs.NON_NEGATIVE_INT.fieldOf("pool_spread").forGetter(PondFeatureConfiguration::poolSpread),
                    IntProvider.codec(1,8).fieldOf("pool_depth").forGetter(PondFeatureConfiguration::poolDepth),
                    Codec.FLOAT.fieldOf("pool_noise").forGetter(PondFeatureConfiguration::poolNoise),
                    ExtraCodecs.POSITIVE_FLOAT.fieldOf("pool_steepness").forGetter(PondFeatureConfiguration::poolStepth),
                    BlockStateProvider.CODEC.fieldOf("water_provider").forGetter(PondFeatureConfiguration::waterState),
                    BlockStateProvider.CODEC.fieldOf("floor_provider").forGetter(PondFeatureConfiguration::floorState)
            ).apply(record, PondFeatureConfiguration::new)
    );
}
