package com.farcr.nomansland.common.registry.worldgen;

import com.farcr.nomansland.NoMansLand;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class NMLPlacedFeatures {
    public static final ResourceKey<PlacedFeature> ORE_SILT = register("ore_silt");
    public static final ResourceKey<PlacedFeature> PATCH_BARREL_CACTUS_DESERT = register("patch_barrel_cactus_desert");
    public static final ResourceKey<PlacedFeature> PATCH_BEACHGRASS = register("patch_beachgrass");
    public static final ResourceKey<PlacedFeature> PATCH_SUCCULENT_DESERT = register("patch_succulent_desert");
    public static final ResourceKey<PlacedFeature> ALL_TULIPS = register("flower_patches/flowers_all_tulips");
    public static final ResourceKey<PlacedFeature> FLOWERBED_JUNGLE = register("flower_patches/flowerbed_jungle");
    public static final ResourceKey<PlacedFeature> FLOWERBED_WHITE = register("flower_patches/flowerbed_white");
    public static final ResourceKey<PlacedFeature> FLOWERS_WHITE = register("flower_patches/flowers_white");
    public static final ResourceKey<PlacedFeature> PATCH_HEARTY_SUCCULENT = register("patch_hearty_succulent");
    public static final ResourceKey<PlacedFeature> FLOWERS_BIRCH_FOREST = register("flower_patches/flowers_birch_forest");
    public static final ResourceKey<PlacedFeature> FLOWERS_CHERRY = register("flower_patches/flowers_cherry");
    public static final ResourceKey<PlacedFeature> FLOWERBED_WHITE_AND_VIOLET = register("flower_patches/flowerbed_white_and_violet");
    public static final ResourceKey<PlacedFeature> PATCH_PEONY_AND_LILAC = register("patch_peony_and_lilac");


    private static ResourceKey<PlacedFeature> register(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, name));
    }
}
