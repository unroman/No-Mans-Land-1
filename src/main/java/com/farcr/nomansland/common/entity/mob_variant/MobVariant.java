package com.farcr.nomansland.common.entity.mob_variant;

import net.minecraft.core.HolderSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

import java.util.Optional;

public interface MobVariant {
    ResourceLocation texture();
    Optional<HolderSet<Biome>> biomes();
    int weight();
}
