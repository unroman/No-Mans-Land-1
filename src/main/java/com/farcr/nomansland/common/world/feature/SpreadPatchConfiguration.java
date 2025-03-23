package com.farcr.nomansland.common.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public record SpreadPatchConfiguration(int tries, int xzSpread, int ySpread, float sparseness, Holder<PlacedFeature> feature) implements FeatureConfiguration {
    public static final Codec<SpreadPatchConfiguration> CODEC = RecordCodecBuilder.create(
            record -> record.group(
                    ExtraCodecs.POSITIVE_INT.fieldOf("tries").orElse(128).forGetter(SpreadPatchConfiguration::tries),
                    ExtraCodecs.NON_NEGATIVE_INT.fieldOf("xz_spread").orElse(7).forGetter(SpreadPatchConfiguration::xzSpread),
                    ExtraCodecs.NON_NEGATIVE_INT.fieldOf("y_spread").orElse(3).forGetter(SpreadPatchConfiguration::ySpread),
                    ExtraCodecs.POSITIVE_FLOAT.fieldOf("sparseness").orElse(1.0f).forGetter(SpreadPatchConfiguration::sparseness),
                    PlacedFeature.CODEC.fieldOf("feature").forGetter(SpreadPatchConfiguration::feature)
            ).apply(record, SpreadPatchConfiguration::new)
    );
}
