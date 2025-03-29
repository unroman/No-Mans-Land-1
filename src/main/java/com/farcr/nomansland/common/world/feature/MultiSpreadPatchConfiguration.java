package com.farcr.nomansland.common.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public record MultiSpreadPatchConfiguration(int tries, IntProvider xzSpread, IntProvider ySpread, float sparseness, HolderSet<PlacedFeature> features) implements FeatureConfiguration {
    public static final Codec<MultiSpreadPatchConfiguration> CODEC = RecordCodecBuilder.create(
            record -> record.group(
                    ExtraCodecs.POSITIVE_INT.fieldOf("tries").orElse(128).forGetter(MultiSpreadPatchConfiguration::tries),
                    IntProvider.codec(0, 16).fieldOf("xz_spread").forGetter(MultiSpreadPatchConfiguration::xzSpread),
                    IntProvider.codec(0, 16).fieldOf("y_spread").forGetter(MultiSpreadPatchConfiguration::ySpread),
                    ExtraCodecs.POSITIVE_FLOAT.fieldOf("sparseness").orElse(1.0f).forGetter(MultiSpreadPatchConfiguration::sparseness),
                    PlacedFeature.LIST_CODEC.fieldOf("features").forGetter(MultiSpreadPatchConfiguration::features)
            ).apply(record, MultiSpreadPatchConfiguration::new)
    );
}
