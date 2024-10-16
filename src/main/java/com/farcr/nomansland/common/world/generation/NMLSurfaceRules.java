package com.farcr.nomansland.common.world.generation;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.registry.NMLBiomes;
import com.terraformersmc.biolith.api.surface.SurfaceGeneration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public class NMLSurfaceRules {
    private static final SurfaceRules.RuleSource COARSE_DIRT = makeStateRule(Blocks.COARSE_DIRT);
    private static final SurfaceRules.RuleSource PODZOL = makeStateRule(Blocks.PODZOL);
    private static final SurfaceRules.RuleSource MUD = makeStateRule(Blocks.MUD);
    private static final SurfaceRules.RuleSource WATER = makeStateRule(Blocks.WATER);

    public static void registerSurfaceRules() {

        SurfaceRules.RuleSource jungle = SurfaceRules.ifTrue(
                SurfaceRules.isBiome(Biomes.JUNGLE),
                SurfaceRules.ifTrue(surfaceNoiseAbove(1.25), COARSE_DIRT)
        );

        SurfaceRules.RuleSource darkForest = SurfaceRules.ifTrue(
                SurfaceRules.isBiome(Biomes.DARK_FOREST),
                SurfaceRules.ifTrue(surfaceNoiseAbove(1.25), PODZOL)
        );

        SurfaceRules.RuleSource autumnalForest = SurfaceRules.ifTrue(
                SurfaceRules.isBiome(NMLBiomes.AUTUMNAL_FOREST),
                SurfaceRules.sequence(
                        SurfaceRules.ifTrue(surfaceNoiseAbove(1.75), COARSE_DIRT),
                        SurfaceRules.ifTrue(surfaceNoiseAbove(-0.95), PODZOL))
        );

        SurfaceRules.RuleSource mapleForest = SurfaceRules.ifTrue(
                SurfaceRules.isBiome(NMLBiomes.MAPLE_FOREST),
                SurfaceRules.ifTrue(surfaceNoiseAbove(1.25), PODZOL)
        );

        SurfaceRules.RuleSource oldGrowthForest = SurfaceRules.ifTrue(
                SurfaceRules.isBiome(NMLBiomes.OLD_GROWTH_FOREST),
                SurfaceRules.ifTrue(surfaceNoiseAbove(1.25), COARSE_DIRT)
        );

        SurfaceRules.RuleSource bog = SurfaceRules.ifTrue(
                SurfaceRules.isBiome(NMLBiomes.BOG),
                SurfaceRules.sequence(SurfaceRules.ifTrue(surfaceNoiseAbove(2.0), MUD),
                        SurfaceRules.ifTrue(SurfaceRules.yBlockCheck(VerticalAnchor.absolute(62), 0),
                                SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.yBlockCheck(VerticalAnchor.absolute(63), 0)),
                                        SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.SWAMP, 0.0), WATER))))
        );

        SurfaceRules.RuleSource bayou = SurfaceRules.ifTrue(
                SurfaceRules.isBiome(NMLBiomes.BAYOU),
                SurfaceRules.sequence(
                        SurfaceRules.ifTrue(surfaceNoiseAbove(2.0), MUD), SurfaceRules.ifTrue(surfaceNoiseAbove(1.5), PODZOL),
                        SurfaceRules.ifTrue(SurfaceRules.yBlockCheck(VerticalAnchor.absolute(62), 0),
                                SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.yBlockCheck(VerticalAnchor.absolute(63), 0)),
                                        SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.SWAMP, 0.0), WATER))))
        );

        SurfaceRules.RuleSource darkSwamp = SurfaceRules.ifTrue(
                SurfaceRules.isBiome(NMLBiomes.DARK_SWAMP),
                SurfaceRules.sequence(
                        SurfaceRules.ifTrue(surfaceNoiseAbove(1.25), PODZOL),
                        SurfaceRules.ifTrue(SurfaceRules.yBlockCheck(VerticalAnchor.absolute(62), 0),
                                SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.yBlockCheck(VerticalAnchor.absolute(63), 0)),
                                        SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.SWAMP, 0.0), WATER))))
        );

        SurfaceGeneration.addOverworldSurfaceRules(
                ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "rules/overworld"),
                SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(),
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.sequence(
                                jungle, darkForest, autumnalForest, mapleForest, oldGrowthForest, bog, bayou, darkSwamp)))
        );
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }

    private static SurfaceRules.ConditionSource surfaceNoiseAbove(double value) {
        return SurfaceRules.noiseCondition(Noises.SURFACE, value / 8.25, Double.MAX_VALUE);
    }
}