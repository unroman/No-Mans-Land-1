package com.farcr.nomansland.datagen;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.registry.NMLTags;
import com.farcr.nomansland.datagen.worldgen.NMLBiomeModifiers;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.farcr.nomansland.common.registry.worldgen.NMLPlacedFeatures.*;
import static net.neoforged.neoforge.common.world.BiomeModifiers.*;

public class NMLDatapackEntriesProvider extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, NMLBiomeModifiers::new);

    public NMLDatapackEntriesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(NoMansLand.MODID));
    }
}
