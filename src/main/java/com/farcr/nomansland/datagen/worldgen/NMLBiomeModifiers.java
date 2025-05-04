package com.farcr.nomansland.datagen.worldgen;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.registry.NMLTags;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.farcr.nomansland.common.registry.worldgen.NMLPlacedFeatures.*;

public class NMLBiomeModifiers {
    private final BootstrapContext<BiomeModifier> bootstrap;
    private final Map<Holder<PlacedFeature>, List<Holder<Biome>>> featureToBiomes = new HashMap<>();

    public NMLBiomeModifiers(BootstrapContext<BiomeModifier> bootstrap) {
        this.bootstrap = bootstrap;
        setup(bootstrap);
    }
    
    private void setup(BootstrapContext<BiomeModifier> bootstrap) {
        biome(
                Biomes.BEACH,
                List.of(
                        PATCH_BEACHGRASS,
                        PATCH_BARREL_CACTUS_DESERT
                )
        );

        biome(
                Biomes.DESERT,
                List.of(PATCH_BARREL_CACTUS_DESERT)
        );

        processFeatures();
    }

    private void biome(ResourceKey<Biome> biomeKey, List<ResourceKey<PlacedFeature>> features) {
        features.forEach(featureKey -> {
            Holder<PlacedFeature> featureHolder = bootstrap.lookup(Registries.PLACED_FEATURE).getOrThrow(featureKey);
            Holder<Biome> biomeHolder = bootstrap.lookup(Registries.BIOME).getOrThrow(biomeKey);

            featureToBiomes.computeIfAbsent(featureHolder, k -> new ArrayList<>()).add(biomeHolder);
        });
    }

//    private void biomeTagFeatures(TagKey<Biome> biomeTag, List<ResourceKey<PlacedFeature>> features, GenerationStep.Decoration step) {
//        List<Holder<PlacedFeature>> placedFeatureHolders = new ArrayList<>();
//        features.forEach(featureKey -> placedFeatureHolders.add(bootstrap.lookup(Registries.PLACED_FEATURE).getOrThrow(featureKey)));
//        addFeaturesToTag(HolderSet.direct(placedFeatureHolders), biomeTag, step);
//    }

    private void processFeatures() {
        featureToBiomes.forEach((featureHolder, biomeSet) -> {
            HolderSet<Biome> mergedBiomes = HolderSet.direct(biomeSet);
            addFeatureToSet(featureHolder, mergedBiomes);
        });
    }

    private void addFeatureToSet(Holder<PlacedFeature> feature, HolderSet<Biome> biomes) {
        bootstrap.register(
                ResourceKey.create(
                        NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                        NoMansLand.location("add_" + feature.unwrapKey().orElseThrow().location().getPath())
                ),
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        biomes,
                        HolderSet.direct(feature),
                        GenerationStep.Decoration.VEGETAL_DECORATION
                )
        );
    }

//    private void addFeaturesToTag(HolderSet<PlacedFeature> features, TagKey<Biome> biomeTag, GenerationStep.Decoration step) {
//        String name = biomeTag.location().getPath() + "_tag_features";
//
//        bootstrap.register(
//                ResourceKey.create(
//                        NeoForgeRegistries.Keys.BIOME_MODIFIERS,
//                        NoMansLand.location("add_" + name)
//                ),
//                new BiomeModifiers.AddFeaturesBiomeModifier(
//                        bootstrap.lookup(Registries.BIOME).getOrThrow(biomeTag),
//                        features,
//                        step
//                )
//        );
//    }

    private void removeFeatures(String name, List<ResourceKey<PlacedFeature>> features) {
        List<Holder<PlacedFeature>> placedFeatureHolders = new ArrayList<>();
        features.forEach(feature -> placedFeatureHolders.add(bootstrap.lookup(Registries.PLACED_FEATURE).getOrThrow(feature)));

        bootstrap.register(
                ResourceKey.create(
                        NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                        NoMansLand.location("remove_" + name)
                ),
                BiomeModifiers.RemoveFeaturesBiomeModifier.allSteps(
                        bootstrap.lookup(Registries.BIOME).getOrThrow(
                                TagKey.create(
                                        Registries.BIOME,
                                        NoMansLand.location("feature_removal/no_" + name
                                        )
                                )
                        ),
                        HolderSet.direct(placedFeatureHolders)
                )
        );
    }
}
