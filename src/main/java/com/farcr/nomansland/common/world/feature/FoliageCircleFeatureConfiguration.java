package com.farcr.nomansland.common.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record FoliageCircleFeatureConfiguration(IntProvider radius, BlockStateProvider state) implements FeatureConfiguration {
    public static final Codec<FoliageCircleFeatureConfiguration> CODEC = RecordCodecBuilder.create(
            record -> record.group(
                    IntProvider.codec(1, 8).fieldOf("radius").forGetter(FoliageCircleFeatureConfiguration::radius),
                    BlockStateProvider.CODEC.fieldOf("block_provider").forGetter(FoliageCircleFeatureConfiguration::state)
            ).apply(record, FoliageCircleFeatureConfiguration::new)
    );
}
