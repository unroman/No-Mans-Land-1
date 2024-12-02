package com.farcr.nomansland.common.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record BoulderFeatureConfiguration(IntProvider numCubes, IntProvider cubeSpread, IntProvider cubeSize, float cubeRoundingPercentage, int numErosionRounds, float erosionPercentage) implements FeatureConfiguration {
    public static final Codec<BoulderFeatureConfiguration> CODEC = RecordCodecBuilder.create(
            record -> record.group(
                    IntProvider.codec(1, 8).fieldOf("num_cubes").forGetter(BoulderFeatureConfiguration::numCubes),
                    IntProvider.codec(0, 8).fieldOf("cube_spread").forGetter(BoulderFeatureConfiguration::cubeSpread),
                    IntProvider.codec(1, 8).fieldOf("cube_size").forGetter(BoulderFeatureConfiguration::cubeSize),
                    ExtraCodecs.POSITIVE_FLOAT.fieldOf("cube_rounding_percentage").forGetter(BoulderFeatureConfiguration::cubeRoundingPercentage),
                    ExtraCodecs.NON_NEGATIVE_INT.fieldOf("num_erosion_rounds").forGetter(BoulderFeatureConfiguration::numErosionRounds),
                    ExtraCodecs.POSITIVE_FLOAT.fieldOf("erosion_percentage").forGetter(BoulderFeatureConfiguration::erosionPercentage)
            ).apply(record, BoulderFeatureConfiguration::new)
    );
}
