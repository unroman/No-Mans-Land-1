package com.farcr.nomansland.common.world.feature;

import com.farcr.nomansland.common.world.feature.decorator.FallenTreeDecorator;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.List;

public record FallenTreeFeatureConfiguration(IntProvider gap, IntProvider size, float stumpProbability, List<FallenTreeDecorator> decorators) implements FeatureConfiguration {
    public static final Codec<FallenTreeFeatureConfiguration> CODEC = RecordCodecBuilder.create(
            record -> record.group(
                    IntProvider.codec(1, 8).fieldOf("gap").forGetter(FallenTreeFeatureConfiguration::gap),
                    IntProvider.codec(1, 8).fieldOf("size").forGetter(FallenTreeFeatureConfiguration::size),
                    Codec.FLOAT.fieldOf("stump_probability").orElse(1f).forGetter(FallenTreeFeatureConfiguration::stumpProbability),
                    FallenTreeDecorator.CODEC.listOf().fieldOf("decorators").orElse(List.of()).forGetter(FallenTreeFeatureConfiguration::decorators)
            ).apply(record, FallenTreeFeatureConfiguration::new)
    );
}
