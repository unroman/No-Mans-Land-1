package com.farcr.nomansland.common.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record BoulderFeatureConfiguration(IntProvider numCubes, IntProvider cubeSize, float percentLargerCube, IntProvider cubeErosion, float cubeErosionSizeMultiplier, IntProvider extraErosion, int heightMin, int heightMax, int minimumSize, BlockStateProvider blockProvider) implements FeatureConfiguration {
    public static final Codec<BoulderFeatureConfiguration> CODEC = RecordCodecBuilder.create(
            record -> record.group(
                    IntProvider.codec(1, 8).fieldOf("num_cubes").forGetter(BoulderFeatureConfiguration::numCubes),
                    IntProvider.codec(1, 8).fieldOf("cube_size").forGetter(BoulderFeatureConfiguration::cubeSize),
                    Codec.FLOAT.fieldOf("percent_larger_cube").forGetter(BoulderFeatureConfiguration::percentLargerCube),
                    IntProvider.codec(0, 8).fieldOf("cube_erosion").forGetter(BoulderFeatureConfiguration::cubeErosion),
                    Codec.FLOAT.fieldOf("cube_erosion_size_multiplier").forGetter(BoulderFeatureConfiguration::cubeErosionSizeMultiplier),
                    IntProvider.codec(0, 8).fieldOf("extra_erosion").forGetter(BoulderFeatureConfiguration::extraErosion),
                    ExtraCodecs.NON_NEGATIVE_INT.fieldOf("height_min").forGetter(BoulderFeatureConfiguration::heightMin),
                    ExtraCodecs.NON_NEGATIVE_INT.fieldOf("height_max").forGetter(BoulderFeatureConfiguration::heightMax),
                    ExtraCodecs.NON_NEGATIVE_INT.fieldOf("minimum_size").forGetter(BoulderFeatureConfiguration::minimumSize),
                    BlockStateProvider.CODEC.fieldOf("block_provider").forGetter(BoulderFeatureConfiguration::blockProvider)
            ).apply(record, BoulderFeatureConfiguration::new)
    );
}
