package com.farcr.nomansland.common.registry.worldgen;

import com.farcr.nomansland.NoMansLand;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class NMLPlacedFeatures {
    public static final ResourceKey<PlacedFeature> ORE_SILT = feature("underground/ore_silt");

    public static ResourceKey<PlacedFeature> patch(String patch) {
        return ResourceKey.create(Registries.PLACED_FEATURE, NoMansLand.location("patch_" + patch));
    }

    public static ResourceKey<PlacedFeature> flowers(String flowerPatch) {
        return ResourceKey.create(Registries.PLACED_FEATURE, NoMansLand.location("flower_patches/flowers_" + flowerPatch));
    }

    public static ResourceKey<PlacedFeature> flowerBed(String flowerBed) {
        return ResourceKey.create(Registries.PLACED_FEATURE, NoMansLand.location("flower_patches/flowerbed_" + flowerBed));
    }

    public static ResourceKey<PlacedFeature> feature(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, NoMansLand.location(name));
    }
}
