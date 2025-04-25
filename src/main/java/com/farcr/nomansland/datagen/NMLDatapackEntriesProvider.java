package com.farcr.nomansland.datagen;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.world.biomemodifiers.ChangeColorsBiomeModifier;
import com.farcr.nomansland.common.world.biomemodifiers.ChangeMusicBiomeModifier;
import com.farcr.nomansland.common.world.biomemodifiers.ChangeSpawnsBiomeModifier;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static com.farcr.nomansland.common.registry.worldgen.NMLPlacedFeatures.*;
import static net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import static net.neoforged.neoforge.common.world.BiomeModifiers.*;

public class NMLDatapackEntriesProvider extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, bootstrap -> {
                HolderGetter<Biome> biomes = bootstrap.lookup(Registries.BIOME);
                HolderGetter<PlacedFeature> placedFeatures = bootstrap.lookup(Registries.PLACED_FEATURE);
                HolderGetter<EntityType<?>> entityTypes = bootstrap.registryLookup(Registries.ENTITY_TYPE).orElseThrow();

                GenerationStep.Decoration vegetalDecoration = GenerationStep.Decoration.VEGETAL_DECORATION;

//                addFeatures(
//                        "white_flowers",
//                        bootstrap,
//                        HolderSet.direct(
//                                placedFeatures.getOrThrow(FLOWERBED_WHITE),
//                                placedFeatures.getOrThrow(FLOWERS_BIRCH_FOREST),
//                                placedFeatures.getOrThrow(PATCH_PEONY_AND_LILAC)
//                        )
//                );
//
//                addFeatures(
//                        "cherry_flowers",
//                        bootstrap,
//                        HolderSet.direct(
//                                placedFeatures.getOrThrow(FLOWERBED_WHITE_AND_VIOLET),
//                                placedFeatures.getOrThrow(FLOWERS_CHERRY),
//                                placedFeatures.getOrThrow(VegetationPlacements.PATCH_SUGAR_CANE),
//                                placedFeatures.getOrThrow(VegetationPlacements.BROWN_MUSHROOM_NORMAL),
//                                placedFeatures.getOrThrow(VegetationPlacements.PATCH_WATERLILY)
//                        )
//                );

                addFeatures(
                        "cactus_features",
                        bootstrap,
                        HolderSet.direct(
                                placedFeatures.getOrThrow(PATCH_BARREL_CACTUS_DESERT),
                                placedFeatures.getOrThrow(PATCH_SUCCULENT_DESERT),
                                placedFeatures.getOrThrow(ALL_TULIPS)
                        )
                );

                addFeatures(
                        "beachgrass",
                        bootstrap,
                        HolderSet.direct(
                                placedFeatures.getOrThrow(PATCH_BEACHGRASS)
                        )
                );

                addFeatures(
                        "silt",
                        bootstrap,
                        HolderSet.direct(
                                placedFeatures.getOrThrow(ORE_SILT)
                        ),
                        GenerationStep.Decoration.UNDERGROUND_ORES
                );

                removeFeatures(
                        "dirt_ore",
                        bootstrap,
                        HolderSet.direct(
                                placedFeatures.getOrThrow(OrePlacements.ORE_DIRT)
                        )
                );

                HolderSet<Biome> badlands = biomes.getOrThrow(Tags.Biomes.IS_BADLANDS);
                changeBiome(
                        "badlands",
                        bootstrap,
                        new ChangeColorsBiomeModifier(
                                badlands,
                                15322281,
                                4106959,
                                3048361,
                                11128544
                        ),
                        null,
                        new AddSpawnsBiomeModifier(
                                badlands,
                                List.of(
                                        new SpawnerData(EntityType.RABBIT, 15, 2, 8),
                                        new SpawnerData(EntityType.HUSK, 100, 4, 4)
                                )
                        ),
                        new RemoveSpawnsBiomeModifier(
                                badlands,
                                HolderSet.direct(BuiltInRegistries.ENTITY_TYPE.wrapAsHolder(EntityType.ZOMBIE))
                        ),
                        null
                );

                HolderSet<Biome> bambooJungle = HolderSet.direct(biomes.getOrThrow(Biomes.BAMBOO_JUNGLE));
                changeBiome(
                        "bamboo_jungle",
                        bootstrap,
                        new ChangeColorsBiomeModifier(
                                bambooJungle,
                                11071699,
                                2403207,
                                1479552,
                                7194319,
                                6798649,
                                6203171
                        ),
                        new ChangeMusicBiomeModifier(
                                bambooJungle,
                                new Music(SoundEvents.MUSIC_BIOME_JUNGLE, 12000, 24000, false)
                        ),
                        new ChangeSpawnsBiomeModifier(
                                bambooJungle,
                                List.of(
                                        new SpawnerData(EntityType.OCELOT, 8, 1, 3),
                                        new SpawnerData(EntityType.PANDA, 80, 1, 4),
                                        new SpawnerData(EntityType.PIG, 16, 4, 4),
                                        new SpawnerData(EntityType.CHICKEN, 14, 4, 4),
                                        new SpawnerData(EntityType.COW, 6, 4, 4)
                                )
                        ),
                        new AddSpawnsBiomeModifier(
                                bambooJungle,
                                List.of(
                                        new SpawnerData(EntityType.RABBIT, 15, 2, 8),
                                        new SpawnerData(EntityType.HUSK, 100, 4, 4)
                                )
                        ),
                        new RemoveSpawnsBiomeModifier(
                                bambooJungle,
                                HolderSet.direct(
                                        BuiltInRegistries.ENTITY_TYPE.wrapAsHolder(EntityType.SHEEP)
                                )
                        ),
//                        new AddFeaturesBiomeModifier(
//                                bambooJungle,
//                                HolderSet.direct(
//                                        placedFeatures.getOrThrow(FLOWERBED_JUNGLE),
//                                        placedFeatures.getOrThrow(PATCH_HEARTY_SUCCULENT)
//                                ),
//                                vegetalDecoration
//                        ),
                        new RemoveFeaturesBiomeModifier(
                                bambooJungle,
                                HolderSet.direct(
                                        placedFeatures.getOrThrow(VegetationPlacements.PATCH_PUMPKIN)
                                ),
                                Set.of(vegetalDecoration)
                        )
                );

                HolderSet<Biome> beach = biomes.getOrThrow(Tags.Biomes.IS_BEACH);
                changeBiome(
                        "beach",
                        bootstrap,
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
                        new RemoveFeaturesBiomeModifier(
                                beach,
                                HolderSet.direct(
                                        placedFeatures.getOrThrow(VegetationPlacements.PATCH_GRASS_BADLANDS),
                                        placedFeatures.getOrThrow(VegetationPlacements.FLOWER_DEFAULT)
                                ),
                                Set.of(vegetalDecoration)
                        )
                );

//                HolderSet<Biome> birchForest = HolderSet.direct(biomes.getOrThrow(Biomes.BIRCH_FOREST));
//                changeBiome(
//                        "birch_forest",
//                        bootstrap,
//                        new ChangeColorsBiomeModifier(
//                                birchForest,
//                                12906239,
//                                3902136,
//                                2388383,
//                                7715315,
//                                8367967,
//                                6594108
//                        ),
//                        new ChangeSpawnsBiomeModifier(
//                                birchForest,
//                                List.of(
//                                        new SpawnerData(EntityType.SHEEP, 4, 1, 4),
//                                        new SpawnerData(EntityType.CHICKEN, 14, 4, 6),
//                                        new SpawnerData(EntityType.COW, 2, 4, 4)
//                                )
//                        ),
//                        new AddSpawnsBiomeModifier(
//                                birchForest,
//                                List.of(
//                                        new SpawnerData(EntityType.RABBIT, 8, 3, 4)
//                                )
//                        ),
//                        null,
//                        new RemoveFeaturesBiomeModifier(
//                                birchForest,
//                                HolderSet.direct(
//                                        placedFeatures.getOrThrow(VegetationPlacements.FOREST_FLOWERS)
//                                ),
//                                Set.of(vegetalDecoration)
//                        )
//                );

//                HolderSet<Biome> cherryGrove = HolderSet.direct(biomes.getOrThrow(Biomes.CHERRY_GROVE));
//                changeBiome(
//                        "cherry_grove",
//                        bootstrap,
//                        new ChangeColorsBiomeModifier(
//                                cherryGrove,
//                                15724287,
//                                3895992,
//                                2388383,
//                                8307162,
//                                9620606,
//                                9098883
//                        ),
//                        new ChangeSpawnsBiomeModifier(
//                                cherryGrove,
//                                List.of(
//                                        new SpawnerData(EntityType.PIG, 4, 1, 8),
//                                        new SpawnerData(EntityType.RABBIT, 8, 2, 6),
//                                        new SpawnerData(EntityType.SHEEP, 8, 2, 4)
//                                )
//                        ),
//                        new AddSpawnsBiomeModifier(
//                                cherryGrove,
//                                List.of(
//                                        new SpawnerData(EntityType.CHICKEN, 4, 2, 6)
//                                )
//                        ),
//                        null,
//                        new RemoveFeaturesBiomeModifier(
//                                cherryGrove,
//                                HolderSet.direct(
//                                        placedFeatures.getOrThrow(VegetationPlacements.TREES_CHERRY)
//                                ),
//                                Set.of(vegetalDecoration)
//                        )
//                );

                HolderSet<Biome> desert = biomes.getOrThrow(Tags.Biomes.IS_DESERT);
                changeBiome(
                        "desert",
                        bootstrap,
                        new ChangeColorsBiomeModifier(
                                desert,
                                15527891,
                                4106959,
                                3048361,
                                12113081,
                                14663027,
                                12762457
                        ),
                        null,
                        new ChangeSpawnsBiomeModifier(
                                desert,
                                List.of(
                                        new SpawnerData(EntityType.HUSK, 100, 4, 4),
                                        new SpawnerData(EntityType.ZOMBIE_VILLAGER, 5, 1, 1),
                                        new SpawnerData(EntityType.RABBIT, 20, 2, 8)
                                )
                        ),
                        new AddSpawnsBiomeModifier(
                                desert,
                                List.of(
                                        new SpawnerData(EntityType.CAMEL, 20, 2, 8)
                                )
                        ),
                        new RemoveSpawnsBiomeModifier(
                                desert,
                                HolderSet.direct(
                                        BuiltInRegistries.ENTITY_TYPE.wrapAsHolder(EntityType.ZOMBIE)
                                )
                        ),
                        new RemoveFeaturesBiomeModifier(
                                desert,
                                HolderSet.direct(
                                        placedFeatures.getOrThrow(VegetationPlacements.PATCH_SUGAR_CANE_DESERT),
                                        placedFeatures.getOrThrow(VegetationPlacements.PATCH_PUMPKIN)
                                ),
                                Set.of(vegetalDecoration)
                        )
                );
            });

    public static void changeBiome(String name, BootstrapContext<BiomeModifier> bootstrap, @Nullable ChangeColorsBiomeModifier changeColors, @Nullable ChangeMusicBiomeModifier changeMusic, @Nullable ChangeSpawnsBiomeModifier changeSpawns, @Nullable AddSpawnsBiomeModifier addSpawns, @Nullable RemoveSpawnsBiomeModifier removeSpawns, @Nullable RemoveFeaturesBiomeModifier removeFeatures) {
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

    public static void changeBiome(String name, BootstrapContext<BiomeModifier> bootstrap, @Nullable ChangeColorsBiomeModifier changeColors, @Nullable ChangeSpawnsBiomeModifier changeSpawns, @Nullable AddSpawnsBiomeModifier addSpawns, @Nullable RemoveSpawnsBiomeModifier removeSpawns, @Nullable RemoveFeaturesBiomeModifier removeFeatures) {
        changeBiome(name, bootstrap, changeColors, null, changeSpawns, addSpawns, removeSpawns, removeFeatures);
    }

    public static void addFeatures(String name, BootstrapContext<BiomeModifier> bootstrap, HolderSet<PlacedFeature> features, GenerationStep.Decoration step) {
        bootstrap.register(
                ResourceKey.create(
                        NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                        NoMansLand.location("add_" + name)
                ),
                new AddFeaturesBiomeModifier(
                        bootstrap.lookup(Registries.BIOME).getOrThrow(
                                TagKey.create(
                                        Registries.BIOME,
                                        NoMansLand.location("feature_addition/has_" + name
                                        )
                                )
                        ),
                        features,
                        step
                ));
    }

    public static void addFeatures(String name, BootstrapContext<BiomeModifier> bootstrap, HolderSet<PlacedFeature> features) {
        addFeatures(name, bootstrap, features, GenerationStep.Decoration.VEGETAL_DECORATION);
    }

    public static void removeFeatures(String name, BootstrapContext<BiomeModifier> bootstrap, HolderSet<PlacedFeature> features) {
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
                        features
                )
        );
    }

    public NMLDatapackEntriesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(NoMansLand.MODID));
    }
}
