package com.farcr.nomansland.common.world.generation;

import com.farcr.nomansland.common.registry.NMLBiomes;
import com.terraformersmc.biolith.api.biome.BiomePlacement;
import com.terraformersmc.biolith.api.biome.sub.BiomeParameterTargets;
import com.terraformersmc.biolith.api.biome.sub.CriterionBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;

import static com.terraformersmc.biolith.api.biome.sub.CriterionBuilder.*;

public class NMLBiomePlacements {
    public static void register() {

        // Caves
        BiomePlacement.addOverworld(NMLBiomes.CAVES,
                Climate.parameters(
                        Climate.Parameter.span(-1.5F, 1.5F),
                        Climate.Parameter.span(-1.5F, 1.5F),
                        Climate.Parameter.span(-1.5F, 1.5F),
                        Climate.Parameter.span(-1.5F, 1.5F),
                        Climate.Parameter.span(0.15F, 1.5F),
                        Climate.Parameter.span(-1.5F, 1.5F),
                        0.125F));

        // Autumnal Forest
        BiomePlacement.replaceOverworld(
                Biomes.FOREST,
                NMLBiomes.AUTUMNAL_FOREST,
                0.2
        );

        // Maple Forest & Grove
        BiomePlacement.replaceOverworld(
                Biomes.FOREST,
                NMLBiomes.MAPLE_FOREST,
                0.2
        );

        BiomePlacement.replaceOverworld(
                Biomes.GROVE,
                NMLBiomes.MAPLE_GROVE,
                0.2
        );

        BiomePlacement.addSubOverworld(
                Biomes.GROVE,
                NMLBiomes.MAPLE_GROVE,
                allOf(alternate(NMLBiomes.MAPLE_FOREST, Biomes.FOREST))
        );

        BiomePlacement.addSubOverworld(
                Biomes.FOREST,
                NMLBiomes.MAPLE_FOREST,
                allOf(alternate(NMLBiomes.MAPLE_GROVE, Biomes.GROVE))
        );

        // Bog
        BiomePlacement.replaceOverworld(
                Biomes.SWAMP,
                NMLBiomes.BOG,
                0.2
        );

        BiomePlacement.replaceOverworld(
                Biomes.MANGROVE_SWAMP,
                NMLBiomes.BOG,
                0.05
        );

        BiomePlacement.addSubOverworld(
                Biomes.SWAMP,
                NMLBiomes.BOG,
                allOf(neighbor(Biomes.PLAINS))
        );

        // Bayou
        BiomePlacement.replaceOverworld(
                Biomes.MANGROVE_SWAMP,
                NMLBiomes.BAYOU,
                0.4
        );

        BiomePlacement.replaceOverworld(
                Biomes.JUNGLE,
                NMLBiomes.BAYOU,
                0.1
        );

        transitionalBiome(
                Biomes.SWAMP,
                Biomes.JUNGLE,
                NMLBiomes.BAYOU
        );

        // Dark Swamp
        transitionalBiome(
                Biomes.SWAMP,
                Biomes.DARK_FOREST,
                NMLBiomes.DARK_SWAMP
        );

        BiomePlacement.replaceOverworld(
                Biomes.DARK_FOREST,
                NMLBiomes.DARK_SWAMP,
                0.1
        );

        // Old Growth Forest
        BiomePlacement.replaceOverworld(
                Biomes.DARK_FOREST,
                NMLBiomes.OLD_GROWTH_FOREST,
                0.3
        );

        BiomePlacement.replaceOverworld(
                Biomes.OLD_GROWTH_BIRCH_FOREST,
                NMLBiomes.OLD_GROWTH_FOREST,
                0.2
        );

        edgeBiome(NMLBiomes.OLD_GROWTH_FOREST, NMLBiomes.OLD_GROWTH_FOREST_EDGE, BiomeParameterTargets.HUMIDITY);
        clearingBiome(NMLBiomes.OLD_GROWTH_FOREST, NMLBiomes.OLD_GROWTH_FOREST_CLEARING);
    }

    public static void transitionalBiome(ResourceKey<Biome> mainBiome, ResourceKey<Biome> secondaryBiome, ResourceKey<Biome> transitionalBiome) {
        BiomePlacement.addSubOverworld(
                mainBiome,
                transitionalBiome,
                allOf(neighbor(secondaryBiome), not(NEAR_INTERIOR))
        );

        BiomePlacement.addSubOverworld(
                secondaryBiome,
                transitionalBiome,
                allOf(alternate(transitionalBiome, mainBiome), not(NEAR_INTERIOR))
        );
    }

    public static void edgeBiome(ResourceKey<Biome> interiorBiome, ResourceKey<Biome> edgeBiome, BiomeParameterTargets biomeParameterTarget) {
        BiomePlacement.addSubOverworld(
                interiorBiome,
                edgeBiome,
                allOf(
                        CriterionBuilder.deviationMin(biomeParameterTarget, .02F),
                        anyOf(
                                allOf(
                                        NEAR_BORDER,
                                        not(NEAR_INTERIOR)
                                ),
                                CriterionBuilder.BEACHSIDE,
                                CriterionBuilder.OCEANSIDE,
                                allOf(NEAR_BORDER, neighbor(BiomeTags.IS_RIVER))
                        )
                )
        );
    }

    public static void clearingBiome(ResourceKey<Biome> interiorBiome, ResourceKey<Biome> clearingBiome) {
        BiomePlacement.addSubOverworld(
                interiorBiome,
                clearingBiome,
                allOf(CriterionBuilder.deviationMin(BiomeParameterTargets.PEAKS_VALLEYS, .05F), NEAR_INTERIOR)
        );
    }
}
