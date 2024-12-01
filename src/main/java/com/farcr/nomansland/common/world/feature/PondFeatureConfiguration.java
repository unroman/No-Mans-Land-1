package com.farcr.nomansland.common.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record PondFeatureConfiguration(IntProvider numPools, IntProvider poolSize, int poolSpread) implements FeatureConfiguration {
    public static final Codec<PondFeatureConfiguration> CODEC = RecordCodecBuilder.create(
            record -> record.group(
                IntProvider.codec(1, 8).fieldOf("num_pools").forGetter(PondFeatureConfiguration::numPools),
                IntProvider.codec(1, 8).fieldOf("pool_size").forGetter(PondFeatureConfiguration::poolSize),
                ExtraCodecs.NON_NEGATIVE_INT.fieldOf("pool_spread").forGetter(PondFeatureConfiguration::poolSpread)
            ).apply(record, PondFeatureConfiguration::new)
    );
}
