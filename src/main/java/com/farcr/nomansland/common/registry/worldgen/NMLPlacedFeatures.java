package com.farcr.nomansland.common.registry.worldgen;

import com.farcr.nomansland.NoMansLand;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class NMLPlacedFeatures {
    public static final ResourceKey<PlacedFeature> ORE_SILT = feature("underground/ore_silt");

    public static ResourceKey<PlacedFeature> patch(String patch) {
        return feature("patch_" + patch);
    }

    public static ResourceKey<PlacedFeature> flowers(String flowerPatch) {
        return feature("flower_patches/flowers_" + flowerPatch);
    }

    public static ResourceKey<PlacedFeature> flowerBed(String flowerBed) {
        return feature("flower_patches/flowerbed_" + flowerBed);
    }

    public static ResourceKey<PlacedFeature> trees(String tree) {
        return feature("biome_trees/trees_" + tree);
    }

    public static ResourceKey<PlacedFeature> feature(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, NoMansLand.location(name));
    }

    public static ResourceKey<PlacedFeature> featureVanilla(String name) {
        return feature("vanilla/" + name);
    }

    public static ResourceKey<PlacedFeature> patchVanilla(String name) {
        return feature("vanilla/patch_" + name);
    }
}
