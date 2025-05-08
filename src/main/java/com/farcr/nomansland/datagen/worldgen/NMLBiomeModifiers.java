package com.farcr.nomansland.datagen.worldgen;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.registry.NMLParticleTypes;
import com.farcr.nomansland.common.registry.NMLTags;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.*;

import static net.minecraft.data.worldgen.placement.VegetationPlacements.*;
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
                                flowerBed("jungle"),
                                patch("hearty_succulent")
                        )
                )
                .removeFeatures(PATCH_PUMPKIN)
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
                        PATCH_GRASS_BADLANDS,
                        FLOWER_DEFAULT
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
                .addFeatures(FeatureWithStep.vegetationFeatures(flowerBed("white"), flowers("birch_forest"), patch("peony_and_lilac")))
                .removeFeatures(FOREST_FLOWERS)
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
                        flowerBed("white_and_violet"),
                        flowers("cherry"),
                        PATCH_SUGAR_CANE,
                        BROWN_MUSHROOM_NORMAL,
                        PATCH_WATERLILY
                ))
                .removeFeatures(TREES_CHERRY)
                .changeSpawns(
                        new SpawnerData(EntityType.PIG, 4, 1, 8),
                        new SpawnerData(EntityType.RABBIT, 8, 2, 6),
                        new SpawnerData(EntityType.SHEEP, 8, 2, 4)
                )
                .addSpawns(new SpawnerData(EntityType.CHICKEN, 4, 2, 6))
                .build(featureToBiomes);

        modifyBiome("cold_ocean", Biomes.COLD_OCEAN)
                .changeColors(
                        12639487,
                        3899064,
                        2386591,
                        8431871,
                        9680231,
                        8960833
                )
                .addFeatures(FeatureWithStep.vegetationFeatures(
                        flowers("white"),
                        flowerBed("white")
                ))
                .removeFeatures(FLOWER_DEFAULT, PATCH_PUMPKIN, PATCH_SUGAR_CANE)
                .build(featureToBiomes);

        modifyBiome("dark_forest", Biomes.DARK_FOREST)
                .changeColors(
                        12906239,
                        3902136,
                        2388383,
                        7715315,
                        6387766,
                        6523436
                )
                .addFeatures(FeatureWithStep.vegetationFeatures(
                        flowers("maple"),
                        flowerBed("red"),
                        patch("rose_bush"),
                        feature("shelf_mushroom_dark_forest"),
                        BROWN_MUSHROOM_OLD_GROWTH
                ))
                .removeFeatures(FLOWER_DEFAULT, FOREST_FLOWERS, BROWN_MUSHROOM_NORMAL)
                .changeSpawns(new SpawnerData(EntityType.CHICKEN, 14, 4, 6))
                .addSpawns(
                        new SpawnerData(EntityType.RABBIT, 8, 3, 4),
                        new SpawnerData(EntityType.WOLF, 7, 2, 6)
                )
                .removeSpawns(EntityType.SHEEP, EntityType.COW)
                .build(featureToBiomes);
        
        modifyBiome("deep_cold_ocean", Biomes.DEEP_COLD_OCEAN)
                .changeColors(
                        12639487,
                        3899064,
                        2386591,
                        8431871,
                        9680231,
                        8960833
                )
                .addFeatures(FeatureWithStep.vegetationFeatures(
                        flowers("white"),
                        flowerBed("white")
                ))
                .removeFeatures(FLOWER_DEFAULT, PATCH_PUMPKIN, PATCH_SUGAR_CANE)
                .build(featureToBiomes);

        modifyBiome("deep_dark", Biomes.DEEP_DARK)
                .changeColors(
                        745045,
                        1327441,
                        662565,
                        745045,
                        8622426,
                        7631435
                )
                .changeParticle(NMLParticleTypes.SCULK_AMBIENCE.get(), 0.005F)
                .build(featureToBiomes);

        modifyBiome("deep_frozen_ocean", Biomes.DEEP_FROZEN_OCEAN)
                .changeColors(
                        15200511,
                        4223408,
                        2383519,
                        8431871,
                        10205860,
                        9680231
                )
                .addFeatures(FeatureWithStep.vegetationFeatures(
                        flowers("white"),
                        flowerBed("white")
                ))
                .removeFeatures(FLOWER_DEFAULT, PATCH_PUMPKIN, PATCH_SUGAR_CANE)
                .build(featureToBiomes);

        modifyBiome("deep_lukewarm_ocean", Biomes.DEEP_LUKEWARM_OCEAN)
                .changeColors(
                        12711423,
                        3906239,
                        2979747,
                        6800872,
                        9419352,
                        8960833
                )
                .addFeatures(FeatureWithStep.vegetationFeatures(
                        flowers("white"),
                        flowerBed("white")
                ))
                .removeFeatures(FLOWER_DEFAULT, PATCH_PUMPKIN)
                .build(featureToBiomes);

        modifyBiome("deep_ocean", Biomes.DEEP_OCEAN)
                .changeColors(
                        12906239,
                        3902136,
                        2388383,
                        7715315,
                        9680231,
                        8960833
                )
                .addFeatures(FeatureWithStep.vegetationFeatures(
                        flowers("white"),
                        flowerBed("white")
                ))
                .removeFeatures(FLOWER_DEFAULT, PATCH_PUMPKIN)
                .build(featureToBiomes);

        modifyBiome("desert", Biomes.DESERT)
                .changeColors(
                        15200511,
                        4223408,
                        2383519,
                        8431871,
                        10205860,
                        9680231
                )
                .removeFeatures(
                        PATCH_SUGAR_CANE_DESERT
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
                .changeColors(
                        4210492,
                        6913929,
                        3820113,
                        7907327,
                        8622426,
                        7631435
                )
                .changeParticle(NMLParticleTypes.CAVE_DUST.get(), 0.01F)
                .addFeatures(new FeatureWithStep(ORE_SILT, GenerationStep.Decoration.UNDERGROUND_ORES))
                .build(featureToBiomes);

        modifyBiome("flower_forest", Biomes.FLOWER_FOREST)
                .changeColors(
                        12906239,
                        3902136,
                        2388383,
                        7715315,
                        11393889,
                        10407783
                )
                .addFeatures(FeatureWithStep.vegetationFeatures(
                        flowers("flower_forest"),
                        flowerBed("flower_forest"),
                        feature("patch_peony")
                ))
                .removeFeatures(FLOWER_FOREST_FLOWERS, FLOWER_FLOWER_FOREST, PATCH_PUMPKIN)
                .changeSpawns(
                        new SpawnerData(EntityType.SHEEP, 8, 3, 4),
                        new SpawnerData(EntityType.PIG, 14, 4, 6),
                        new SpawnerData(EntityType.CHICKEN, 12, 4, 8),
                        new SpawnerData(EntityType.COW, 6, 3, 4),
                        new SpawnerData(EntityType.RABBIT, 10, 3, 6)
                )
                .addSpawns(new SpawnerData(EntityType.FOX, 5, 4, 4))
                .build(featureToBiomes);

        modifyBiome("forest", Biomes.FOREST)
                .changeColors(
                        12906239,
                        3902136,
                        2388383,
                        7715315,
                        7974991,
                        7252796
                )
                .addFeatures(FeatureWithStep.vegetationFeatures(
                        flowers("forest"),
                        flowerBed("white_and_yellow"),
                        patch("peony_and_lilac")
                ))
                .changeSpawns(
                        new SpawnerData(EntityType.SHEEP, 5, 3, 4),
                        new SpawnerData(EntityType.PIG, 14, 4, 6),
                        new SpawnerData(EntityType.CHICKEN, 16, 4, 8),
                        new SpawnerData(EntityType.COW, 4, 3, 4)
                )
                .addSpawns(new SpawnerData(EntityType.RABBIT, 8, 3, 6))
                .removeFeatures(FLOWER_DEFAULT, FOREST_FLOWERS)
                .build(featureToBiomes);

        modifyBiome("frozen_ocean", Biomes.FROZEN_OCEAN)
                .changeColors(
                        15200511,
                        4220592,
                        2771621,
                        8431871,
                        10205860,
                        5541228
                )
                .addFeatures(FeatureWithStep.vegetationFeatures(
                        flowers("white"),
                        flowerBed("frosted")
                ))
                .removeFeatures(PATCH_PUMPKIN, PATCH_SUGAR_CANE)
                .build(featureToBiomes);

        modifyBiome("frozen_peaks", Biomes.FROZEN_PEAKS)
                .changeColors(
                        15200511,
                        4220592,
                        2771621,
                        8431871,
                        10205860,
                        5541228
                )
                .addSpawns(new SpawnerData(EntityType.POLAR_BEAR, 1, 1, 2))
                .build(featureToBiomes);

        modifyBiome("frozen_river", Biomes.FROZEN_RIVER)
                .changeColors(
                        15200511,
                        4220592,
                        2771621,
                        10797823,
                        10205860,
                        5541228
                )
                .addFeatures(FeatureWithStep.vegetationFeatures(
                        flowers("white"),
                        flowerBed("frosted"),
                        patch("frosted_grass_plains")
                ))
                .addSpawns(new SpawnerData(EntityType.POLAR_BEAR, 1, 1, 2))
                .removeFeatures(FLOWER_DEFAULT, PATCH_GRASS_BADLANDS)
                .build(featureToBiomes);

        modifyBiome("grove", Biomes.GROVE)
                .changeColors(
                        15200511,
                        4220592,
                        2771621,
                        7715315,
                        10205860,
                        10205860
                )
                .addFeatures(FeatureWithStep.vegetationFeatures(
                        flowers("white"),
                        flowerBed("frosted"),
                        patch("frosted_grass_plains")
                ))
                .removeFeatures(PATCH_PUMPKIN, PATCH_SUGAR_CANE)
                .changeSpawns(
                        new SpawnerData(EntityType.WOLF, 3, 1, 1),
                        new SpawnerData(EntityType.RABBIT, 12, 2, 3),
                        new SpawnerData(EntityType.FOX, 7, 2 ,4)
                )
                .addSpawns(new SpawnerData(EntityType.POLAR_BEAR, 1, 1, 2))
                .build(featureToBiomes);

        modifyBiome("ice_spikes", Biomes.ICE_SPIKES)
                .changeColors(
                        15200511,
                        4220592,
                        2771621,
                        8431871,
                        10205860,
                        5541228
                )
                .addFeatures(FeatureWithStep.vegetationFeatures(
                        flowers("white"),
                        flowerBed("frosted")
                ))
                .changeSpawns(new SpawnerData(EntityType.POLAR_BEAR, 1, 1, 2))
                .removeFeatures(FLOWER_DEFAULT)
                .build(featureToBiomes);

        modifyBiome("jagged_peaks", Biomes.JAGGED_PEAKS)
                .changeColors(
                        15200511,
                        3899064,
                        2386591,
                        7715315,
                        10205860,
                        5541228
                )
                .build(featureToBiomes);

        modifyBiome("jungle", Biomes.JUNGLE)
                .changeColors(
                        11071699,
                        2403207,
                        1479552,
                        7194319,
                        6798649,
                        6203171
                )
                .addFeatures(FeatureWithStep.vegetationFeatures(
                        flowerBed("jungle"),
                        patch("duckweed_sparse"),
                        patch("hearty_succulent")
                ))
                .removeFeatures(PATCH_PUMPKIN)
                .changeSpawns(
                        new SpawnerData(EntityType.PIG, 16, 4, 4),
                        new SpawnerData(EntityType.CHICKEN, 14, 4, 4),
                        new SpawnerData(EntityType.CHICKEN, 14, 4, 4),
                        new SpawnerData(EntityType.PANDA, 4, 1, 2),
                        new SpawnerData(EntityType.OCELOT, 8, 1, 3)
                )
                .addSpawns(
                        new SpawnerData(EntityType.FROG, 8, 3, 5)
                )
                .removeSpawns(EntityType.SHEEP)
                .build(featureToBiomes);

        modifyBiome("lukewarm_ocean", Biomes.LUKEWARM_OCEAN)
                .changeColors(
                        12711423,
                        3906239,
                        2979747,
                        6800872,
                        9419352,
                        8960833
                )
                .addFeatures(FeatureWithStep.vegetationFeatures(
                        flowers("white"),
                        flowerBed("white")
                ))
                .removeFeatures(FLOWER_DEFAULT, PATCH_PUMPKIN)
                .build(featureToBiomes);

        modifyBiome("lush_caves", Biomes.LUSH_CAVES)
                .changeColors(
                        11071699,
                        5536348,
                        2897190,
                        8103167,
                        11851346,
                        11851346
                )
                .addFeatures(FeatureWithStep.vegetationFeatures(feature("underground/patch_duckweed_lush_caves")))
                .build(featureToBiomes);

        modifyBiome("mangrove_swamp", Biomes.MANGROVE_SWAMP)
                .changeColors(
                        11333800,
                        3832426,
                        5077600,
                        7194319,
                        0,
                        9285927
                )
                .addFeatures(FeatureWithStep.vegetationFeatures(patch("water_mosaic")))
                .build(featureToBiomes);

        modifyBiome("meadow", Biomes.MEADOW)
                .changeColors(
                        12639487,
                        3895992,
                        2386591,
                        8431871,
                        8501355,
                        6465351
                )
                .addFeatures(FeatureWithStep.vegetationFeatures(
                        flowerBed("white_and_violet"),
                        patch("field_mushroom_extra"),
                        feature("clover_patch_meadow"),
                        feature("field_mushroom_circle_meadow"),
                        BROWN_MUSHROOM_NORMAL,
                        RED_MUSHROOM_NORMAL
                ))
                .changeSpawns(
                        new SpawnerData(EntityType.DONKEY, 1, 1, 2)
                )
                .build(featureToBiomes);

        addFeaturesToTag(Tags.Biomes.IS_BEACH, vegetalDecoration, patch("beachgrass"));
        addFeaturesToTag(NMLTags.HAS_CACTUS, vegetalDecoration, patch("barrel_cactus_desert"), patch("succulent_desert"), flowers("all_tulips"));
        
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
