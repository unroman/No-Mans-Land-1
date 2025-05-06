package com.farcr.nomansland.datagen.worldgen;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.world.biomemodifiers.ChangeColorsBiomeModifier;
import com.farcr.nomansland.common.world.biomemodifiers.ChangeMusicBiomeModifier;
import com.farcr.nomansland.common.world.biomemodifiers.ChangeSpawnsBiomeModifier;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import javax.annotation.Nullable;
import java.util.*;

import static com.farcr.nomansland.common.registry.worldgen.NMLPlacedFeatures.*;
import static net.neoforged.neoforge.common.world.BiomeModifiers.*;

public class NMLBiomeModifiers {
    private final BootstrapContext<BiomeModifier> bootstrap;
    private final Map<FeatureWithStep, List<Holder<Biome>>> featureToBiomes = new HashMap<>();

    public NMLBiomeModifiers(BootstrapContext<BiomeModifier> bootstrap) {
        this.bootstrap = bootstrap;
        setup(bootstrap);
    }
    
    private void setup(BootstrapContext<BiomeModifier> bootstrap) {
        HolderGetter<Biome> biomes = bootstrap.lookup(Registries.BIOME);
        HolderGetter<PlacedFeature> placedFeatures = bootstrap.lookup(Registries.PLACED_FEATURE);
        HolderGetter<EntityType<?>> entityTypes = bootstrap.registryLookup(Registries.ENTITY_TYPE).orElseThrow();
        GenerationStep.Decoration vegetalDecoration = GenerationStep.Decoration.VEGETAL_DECORATION;

        HolderSet<Biome> beach = HolderSet.direct(biomes.getOrThrow(Biomes.BEACH));
        biome(
                "beach",
                Biomes.BEACH,
                FeatureWithStep.features(PATCH_BEACHGRASS),
                new ChangeColorsBiomeModifier(
                        beach,
                        12906239,
                        3902136,
                        2388383,
                        7715315,
                        9680231,
                        8960833
                ),
                null,
                null,
                null,
                null,
                RemoveFeaturesBiomeModifier.allSteps(
                        beach,
                        HolderSet.direct(
                                placedFeatures.getOrThrow(VegetationPlacements.PATCH_GRASS_BADLANDS),
                                placedFeatures.getOrThrow(VegetationPlacements.FLOWER_DEFAULT)
                        )
                )
        );

        processFeatures();
    }

    private void biome(String name, ResourceKey<Biome> biomeKey, List<FeatureWithStep> features, @Nullable ChangeColorsBiomeModifier changeColors, @Nullable ChangeMusicBiomeModifier changeMusic, @Nullable ChangeSpawnsBiomeModifier changeSpawns, @Nullable AddSpawnsBiomeModifier addSpawns, @Nullable RemoveSpawnsBiomeModifier removeSpawns, @Nullable RemoveFeaturesBiomeModifier removeFeatures) {
        addBiomeFeatures(biomeKey, features);

        if (changeColors != null) {
            bootstrap.register(
                    ResourceKey.create(
                            NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                            NoMansLand.location(name + "/change_color")
                    ),
                    changeColors
            );
        }

        if (changeMusic != null) {
            bootstrap.register(
                    ResourceKey.create(
                            NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                            NoMansLand.location(name + "/change_music" )
                    ),
                    changeMusic
            );
        }

        if (changeSpawns != null) {
            bootstrap.register(
                    ResourceKey.create(
                            NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                            NoMansLand.location(name + "/change_spawns" )
                    ),
                    changeSpawns
            );
        }

        if (addSpawns != null) {
            bootstrap.register(
                    ResourceKey.create(
                            NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                            NoMansLand.location(name + "/add_spawns" )
                    ),
                    addSpawns
            );
        }

        if (removeSpawns != null) {
            bootstrap.register(
                    ResourceKey.create(
                            NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                            NoMansLand.location(name + "/remove_spawns" )
                    ),
                    removeSpawns
            );
        }

        if (removeFeatures != null) {
            bootstrap.register(
                    ResourceKey.create(
                            NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                            NoMansLand.location(name + "/remove_features" )
                    ),
                    removeFeatures
            );
        }
    }

    private void addBiomeFeatures(ResourceKey<Biome> biomeKey, List<FeatureWithStep> features) {
        features.forEach(feature -> {
            Holder<Biome> biomeHolder = bootstrap.lookup(Registries.BIOME).getOrThrow(biomeKey);

            featureToBiomes.computeIfAbsent(feature, k -> new ArrayList<>()).add(biomeHolder);
        });
    }

    private void biomeTagFeatures(TagKey<Biome> biomeTag, List<ResourceKey<PlacedFeature>> features, GenerationStep.Decoration step) {
        List<Holder<PlacedFeature>> placedFeatureHolders = new ArrayList<>();
        features.forEach(featureKey -> placedFeatureHolders.add(bootstrap.lookup(Registries.PLACED_FEATURE).getOrThrow(featureKey)));
        addFeaturesToTag(HolderSet.direct(placedFeatureHolders), biomeTag, step);
    }

    private void processFeatures() {
        featureToBiomes.forEach((feature, biomeSet) -> {
            HolderSet<Biome> mergedBiomes = HolderSet.direct(biomeSet);
            addFeatureToSet(feature, mergedBiomes);
        });
    }

    private void addFeatureToSet(FeatureWithStep feature, HolderSet<Biome> biomes) {
        Holder<PlacedFeature> placedFeature = bootstrap.lookup(Registries.PLACED_FEATURE).getOrThrow(feature.key());
        bootstrap.register(
                ResourceKey.create(
                        NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                        NoMansLand.location("add_" + placedFeature.unwrapKey().orElseThrow().location().getPath())
                ),
                new AddFeaturesBiomeModifier(
                        biomes,
                        HolderSet.direct(placedFeature),
                        feature.step()
                )
        );
    }

    private void addFeaturesToTag(HolderSet<PlacedFeature> features, TagKey<Biome> biomeTag, GenerationStep.Decoration step) {
        String name = biomeTag.location().getPath() + "_tag_features";

        bootstrap.register(
                ResourceKey.create(
                        NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                        NoMansLand.location("add_" + name)
                ),
                new AddFeaturesBiomeModifier(
                        bootstrap.lookup(Registries.BIOME).getOrThrow(biomeTag),
                        features,
                        step
                )
        );
    }

    private void removeFeatures(String name, List<ResourceKey<PlacedFeature>> features) {
        List<Holder<PlacedFeature>> placedFeatureHolders = new ArrayList<>();
        features.forEach(feature -> placedFeatureHolders.add(bootstrap.lookup(Registries.PLACED_FEATURE).getOrThrow(feature)));

        bootstrap.register(
                ResourceKey.create(
                        NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                        NoMansLand.location("remove_" + name)
                ),
                RemoveFeaturesBiomeModifier.allSteps(
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
