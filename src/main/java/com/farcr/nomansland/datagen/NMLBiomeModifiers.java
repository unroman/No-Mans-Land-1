package com.farcr.nomansland.datagen;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.registry.NMLTags;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.farcr.nomansland.common.registry.worldgen.NMLPlacedFeatures.*;
import static net.neoforged.neoforge.common.world.BiomeModifiers.*;

public class NMLBiomeModifiers extends DatapackBuiltinEntriesProvider {

    private static final Map<Holder<PlacedFeature>, List<HolderSet<Biome>>> featureToBiomeSets = new HashMap<>();
    private static final Map<Holder<PlacedFeature>, List<TagKey<Biome>>> featureToBiomeTags = new HashMap<>();

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, bootstrap -> {
                setupBiomeFeatures(
                        bootstrap,
                        List.of(Biomes.BEACH),
                        List.of(
                                PATCH_BEACHGRASS,
                                PATCH_BARREL_CACTUS_DESERT
                        )
                );

                setupBiomeFeatures(
                        bootstrap,
                        List.of(Biomes.DESERT),
                        List.of(PATCH_BARREL_CACTUS_DESERT)
                );

//                setupBiomeTagFeatures(
//                        bootstrap,
//                        NMLTags.CAVES,
//                        List.of(ORE_SILT)
//                );

                processFeatures(bootstrap);
            });

    public static void setupBiomeFeatures(BootstrapContext<BiomeModifier> bootstrap, List<ResourceKey<Biome>> biomeKeys, List<ResourceKey<PlacedFeature>> features) {
        features.forEach(featureKey -> {
            Holder<PlacedFeature> featureHolder = bootstrap.lookup(Registries.PLACED_FEATURE).getOrThrow(featureKey);
            List<Holder.Reference<Biome>> biomeHolders = biomeKeys.stream()
                    .map(key -> bootstrap.lookup(Registries.BIOME).getOrThrow(key))
                    .toList();

            featureToBiomeSets
                    .computeIfAbsent(featureHolder, k -> new ArrayList<>())
                    .add(HolderSet.direct(biomeHolders));
        });
    }

    public static void setupBiomeTagFeatures(BootstrapContext<BiomeModifier> bootstrap, TagKey<Biome> biomeTag, List<ResourceKey<PlacedFeature>> features) {
        features.forEach(featureKey -> {
            Holder<PlacedFeature> featureHolder = bootstrap.lookup(Registries.PLACED_FEATURE).getOrThrow(featureKey);
            featureToBiomeTags.computeIfAbsent(featureHolder, k -> new ArrayList<>()).add(biomeTag);
        });
    }

    public static void processFeatures(BootstrapContext<BiomeModifier> bootstrap) {
        featureToBiomeSets.forEach((featureHolder, biomeSets) -> {
            List<Holder<Biome>> allBiomes = biomeSets.stream()
                    .flatMap(HolderSet::stream)
                    .collect(Collectors.toList());

            if (!allBiomes.isEmpty()) {
                HolderSet<Biome> mergedBiomes = HolderSet.direct(allBiomes);
                addFeatureTo(bootstrap, featureHolder, mergedBiomes);
            }
        });

        featureToBiomeTags.forEach((featureHolder, tagList) ->
                tagList.forEach(tag ->
                        addFeatureToTag(bootstrap, featureHolder, tag)));
    }

    public static void addFeatureTo(BootstrapContext<BiomeModifier> bootstrap, Holder<PlacedFeature> feature, HolderSet<Biome> biomes) {
        bootstrap.register(
                ResourceKey.create(
                        NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                        NoMansLand.location("add_" + feature.unwrapKey().orElseThrow().location().getPath())
                ),
                new AddFeaturesBiomeModifier(
                        biomes,
                        HolderSet.direct(feature),
                        GenerationStep.Decoration.VEGETAL_DECORATION
                )
        );
    }

    public static void addFeatureToTag(BootstrapContext<BiomeModifier> bootstrap, Holder<PlacedFeature> feature, TagKey<Biome> biomeTag) {
        String name = feature.unwrapKey().orElseThrow().location().getPath();

        bootstrap.register(
                ResourceKey.create(
                        NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                        NoMansLand.location("add_" + name + "_tag_" + biomeTag.location().getPath())
                ),
                new AddFeaturesBiomeModifier(
                        bootstrap.lookup(Registries.BIOME).getOrThrow(biomeTag),
                        HolderSet.direct(feature),
                        GenerationStep.Decoration.VEGETAL_DECORATION
                )
        );
    }

    public NMLBiomeModifiers(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(NoMansLand.MODID));
    }
}
