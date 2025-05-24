package com.farcr.nomansland.common.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public record MultiSpreadPatchConfiguration(float tryDensity, IntProvider xzSpread, IntProvider ySpread, float sparseness, float randomness, HolderSet<PlacedFeature> features) implements FeatureConfiguration {
    public static final Codec<MultiSpreadPatchConfiguration> CODEC = RecordCodecBuilder.create(
            record -> record.group(
                    ExtraCodecs.POSITIVE_FLOAT.fieldOf("try_density").orElse(16.0f).forGetter(MultiSpreadPatchConfiguration::tryDensity),
                    IntProvider.codec(0, 16).fieldOf("xz_spread").forGetter(MultiSpreadPatchConfiguration::xzSpread),
                    IntProvider.codec(0, 16).fieldOf("y_spread").forGetter(MultiSpreadPatchConfiguration::ySpread),
                    ExtraCodecs.POSITIVE_FLOAT.fieldOf("sparseness").orElse(1.0f).forGetter(MultiSpreadPatchConfiguration::sparseness),
                    ExtraCodecs.POSITIVE_FLOAT.fieldOf("randomness").orElse(0.5f).forGetter(MultiSpreadPatchConfiguration::randomness),
                    PlacedFeature.LIST_CODEC.fieldOf("features").forGetter(MultiSpreadPatchConfiguration::features)
            ).apply(record, MultiSpreadPatchConfiguration::new)
    );
}
