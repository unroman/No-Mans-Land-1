package com.farcr.nomansland.datagen;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.registry.NMLBiomeModifiers;
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
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
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

import static net.neoforged.neoforge.common.world.BiomeModifiers.*;
import static net.minecraft.world.level.biome.MobSpawnSettings.*;
import static com.farcr.nomansland.common.registry.worldgen.NMLPlacedFeatures.*;

public class NMLDatapackEntriesProvider extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, bootstrap -> {
                HolderGetter<Biome> biomes = bootstrap.lookup(Registries.BIOME);
                HolderGetter<PlacedFeature> placedFeatures = bootstrap.lookup(Registries.PLACED_FEATURE);
                HolderGetter<EntityType<?>> entityTypes = bootstrap.registryLookup(Registries.ENTITY_TYPE).orElseThrow();

                GenerationStep.Decoration vegetalDecoration = GenerationStep.Decoration.VEGETAL_DECORATION;

                HolderSet<Biome> badlands = biomes.getOrThrow(BiomeTags.IS_BADLANDS);
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
                        new AddFeaturesBiomeModifier(
                                badlands,
                                HolderSet.direct(
                                        placedFeatures.getOrThrow(PATCH_BARREL_CACTUS_DESERT),
                                        placedFeatures.getOrThrow(PATCH_SUCCULENT_DESERT),
                                        placedFeatures.getOrThrow(ALL_TULIPS)
                                ),
                                vegetalDecoration
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
                        new AddFeaturesBiomeModifier(
                                bambooJungle,
                                HolderSet.direct(
                                        placedFeatures.getOrThrow(FLOWERBED_JUNGLE),
                                        placedFeatures.getOrThrow(PATCH_HEARTY_SUCCULENT)
                                ),
                                vegetalDecoration
                        ),
                        new RemoveFeaturesBiomeModifier(
                                bambooJungle,
                                HolderSet.direct(
                                        placedFeatures.getOrThrow(VegetationPlacements.PATCH_PUMPKIN)
                                ),
                                Set.of(vegetalDecoration)
                        )
                );

                HolderSet<Biome> beach = biomes.getOrThrow(BiomeTags.IS_BEACH);
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
                        null,
                        new AddFeaturesBiomeModifier(
                                beach,
                                HolderSet.direct(
                                        placedFeatures.getOrThrow(VegetationPlacements.PATCH_GRASS_NORMAL),
                                        placedFeatures.getOrThrow(FLOWERBED_WHITE),
                                        placedFeatures.getOrThrow(FLOWERS_WHITE)
                                ),
                                vegetalDecoration
                        ),
                        new RemoveFeaturesBiomeModifier(
                                beach,
                                HolderSet.direct(
                                        placedFeatures.getOrThrow(VegetationPlacements.PATCH_GRASS_BADLANDS),
                                        placedFeatures.getOrThrow(VegetationPlacements.FLOWER_DEFAULT)
                                ),
                                Set.of(vegetalDecoration)
                        )
                );
            });

    public static void changeBiome(String name, BootstrapContext<BiomeModifier> bootstrap, @Nullable ChangeColorsBiomeModifier changeColors, @Nullable ChangeMusicBiomeModifier changeMusic, @Nullable ChangeSpawnsBiomeModifier changeSpawns, @Nullable AddSpawnsBiomeModifier addSpawns, @Nullable RemoveSpawnsBiomeModifier removeSpawns, @Nullable AddFeaturesBiomeModifier addFeatures, @Nullable RemoveFeaturesBiomeModifier removeFeatures) {
        if (changeColors != null) {
            bootstrap.register(
                    ResourceKey.create(
                            NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                            ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, name + "_change_color")
                    ),
                    changeColors
            );
        }

        if (changeMusic != null) {
            bootstrap.register(
                    ResourceKey.create(
                            NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                            ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, name + "_change_music" )
                    ),
                    changeMusic
            );
        }

        if (changeSpawns != null) {
            bootstrap.register(
                    ResourceKey.create(
                            NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                            ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, name + "_change_spawns" )
                    ),
                    changeSpawns
            );
        }

        if (addSpawns != null) {
            bootstrap.register(
                    ResourceKey.create(
                            NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                            ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, name + "_add_spawns" )
                    ),
                    addSpawns
            );
        }

        if (removeSpawns != null) {
            bootstrap.register(
                    ResourceKey.create(
                            NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                            ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, name + "_remove_spawns" )
                    ),
                    removeSpawns
            );
        }

        if (addFeatures != null) {
            bootstrap.register(
                    ResourceKey.create(
                            NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                            ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, name + "_add_features" )
                    ),
                    addFeatures
            );
        }

        if (removeFeatures != null) {
            bootstrap.register(
                    ResourceKey.create(
                            NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                            ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, name + "_remove_features" )
                    ),
                    removeFeatures
            );
        }
    }

    public NMLDatapackEntriesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(NoMansLand.MODID));
    }
}
