package com.farcr.nomansland.datagen.worldgen;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.registry.NMLTags;
import com.farcr.nomansland.common.registry.worldgen.NMLBiomes;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.*;

import static com.farcr.nomansland.common.registry.worldgen.NMLPlacedFeatures.*;
import static net.neoforged.neoforge.common.world.BiomeModifiers.*;

public class NMLBiomeModifiers {
    private final BootstrapContext<BiomeModifier> bootstrap;
    private final Map<FeatureWithStep, List<Holder<Biome>>> featureToBiomes = new HashMap<>();

    public NMLBiomeModifiers(BootstrapContext<BiomeModifier> bootstrap) {
        this.bootstrap = bootstrap;
        setup();
    }
    
    private void setup() {
        GenerationStep.Decoration vegetalDecoration = GenerationStep.Decoration.VEGETAL_DECORATION;

        modifyBiome("badlands", Biomes.BADLANDS)
                .changeColors(15322281,
                        4106959,
                        3048361,
                        11128544
                )
                .addSpawns(
                        new SpawnerData(EntityType.RABBIT, 15, 2, 8),
                        new SpawnerData(EntityType.HUSK, 100, 4, 4)
                )
                .removeSpawns(EntityType.ZOMBIE)
                .build(featureToBiomes);

        modifyBiome("bamboo_jungle", Biomes.BAMBOO_JUNGLE)
                .changeColors(
                        11071699,
                        2403207,
                        1479552,
                        7194319,
                        6798649,
                        6203171
                )
                .changeMusic(
                        new Music(SoundEvents.MUSIC_BIOME_JUNGLE, 12000, 24000, false)
                )
                .addFeatures(
                        FeatureWithStep.vegetationFeatures(
                                FLOWERBED_JUNGLE,
                                PATCH_HEARTY_SUCCULENT
                        )
                )
                .removeFeatures(VegetationPlacements.PATCH_PUMPKIN)
                .changeSpawns(
                        new SpawnerData(EntityType.OCELOT, 8, 1, 3),
                        new SpawnerData(EntityType.PANDA, 80, 1, 4),
                        new SpawnerData(EntityType.PIG, 16, 4, 4),
                        new SpawnerData(EntityType.CHICKEN, 14, 4, 4),
                        new SpawnerData(EntityType.COW, 6, 4, 4)
                )
                .addSpawns(
                        new SpawnerData(EntityType.RABBIT, 15, 2, 8),
                        new SpawnerData(EntityType.HUSK, 100, 4, 4)
                )
                .removeSpawns(EntityType.SHEEP)
                .build(featureToBiomes);



        modifyBiome("beach", Biomes.BEACH)
                .changeColors(
                        12906239,
                        3902136,
                        2388383,
                        7715315,
                        9680231,
                        8960833
                )
                .removeFeatures(
                        VegetationPlacements.PATCH_GRASS_BADLANDS,
                        VegetationPlacements.FLOWER_DEFAULT
                )
                .build(featureToBiomes);

        modifyBiome("birch_forest", Biomes.BIRCH_FOREST)
                .changeColors(
                        12906239,
                        3902136,
                        2388383,
                        7715315,
                        8367967,
                        6594108
                )
                .addFeatures(FeatureWithStep.vegetationFeatures(FLOWERBED_WHITE, FLOWERS_BIRCH_FOREST, PATCH_PEONY_AND_LILAC))
                .removeFeatures(VegetationPlacements.FOREST_FLOWERS)
                .changeSpawns(
                        new SpawnerData(EntityType.SHEEP, 4, 1, 4),
                        new SpawnerData(EntityType.CHICKEN, 14, 4, 6),
                        new SpawnerData(EntityType.COW, 2, 4, 4)

                )
                .addSpawns(new SpawnerData(EntityType.RABBIT, 8, 3, 4))
                .build(featureToBiomes);

        modifyBiome("cherry_grove", Biomes.CHERRY_GROVE)
                .changeColors(
                        15724287,
                        3895992,
                        2388383,
                        8307162,
                        9620606,
                        9098883
                )
                .addFeatures(FeatureWithStep.vegetationFeatures(
                        FLOWERBED_WHITE_AND_VIOLET,
                        FLOWERS_CHERRY,
                        VegetationPlacements.PATCH_SUGAR_CANE,
                        VegetationPlacements.BROWN_MUSHROOM_NORMAL,
                        VegetationPlacements.PATCH_WATERLILY
                ))
                .removeFeatures(VegetationPlacements.TREES_CHERRY)
                .changeSpawns(
                        new SpawnerData(EntityType.PIG, 4, 1, 8),
                        new SpawnerData(EntityType.RABBIT, 8, 2, 6),
                        new SpawnerData(EntityType.SHEEP, 8, 2, 4)
                )
                .addSpawns(new SpawnerData(EntityType.CHICKEN, 4, 2, 6))
                .build(featureToBiomes);

        modifyBiome("desert", Biomes.DESERT)
                .changeColors(
                        15527891,
                        4106959,
                        3048361,
                        12113081,
                        14663027,
                        12762457
                )
                .removeFeatures(
                        VegetationPlacements.PATCH_SUGAR_CANE_DESERT,
                        VegetationPlacements.PATCH_PUMPKIN
                )
                .changeSpawns(
                        new SpawnerData(EntityType.HUSK, 100, 4, 4),
                        new SpawnerData(EntityType.ZOMBIE_VILLAGER, 5, 1, 1),
                        new SpawnerData(EntityType.RABBIT, 20, 2, 8)
                )
                .addSpawns(new SpawnerData(EntityType.CAMEL, 20, 2, 8))
                .removeSpawns(EntityType.ZOMBIE)
                .build(featureToBiomes);

        modifyBiome("dripstone_caves", Biomes.DRIPSTONE_CAVES)
                .addFeatures(new FeatureWithStep(ORE_SILT, GenerationStep.Decoration.UNDERGROUND_ORES))
                .build(featureToBiomes);

        addFeaturesToTag(Tags.Biomes.IS_BEACH, vegetalDecoration, PATCH_BEACHGRASS);
        addFeaturesToTag(NMLTags.HAS_CACTUS, vegetalDecoration, PATCH_BARREL_CACTUS_DESERT, PATCH_SUCCULENT_DESERT, ALL_TULIPS);
        
        processFeatures();
    }
    
    private BiomeModifierBuilder modifyBiome(String name, ResourceKey<Biome> biome) {
        return new BiomeModifierBuilder(bootstrap, name, biome);
    }

    @SafeVarargs
    private void addFeaturesToTag(TagKey<Biome> biomeTag, GenerationStep.Decoration step, ResourceKey<PlacedFeature>... features) {
        List<Holder<PlacedFeature>> featureHolders = new ArrayList<>();
        List.of(features).forEach(featureKey ->
                featureHolders.add(bootstrap.lookup(Registries.PLACED_FEATURE).getOrThrow(featureKey)));
        String name = biomeTag.location().getPath() + "_tag_features";

        bootstrap.register(
                ResourceKey.create(
                        NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                        NoMansLand.location("add_" + name)
                ),
                new AddFeaturesBiomeModifier(
                        bootstrap.lookup(Registries.BIOME).getOrThrow(biomeTag),
                        HolderSet.direct(featureHolders),
                        step
                )
        );
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
}
