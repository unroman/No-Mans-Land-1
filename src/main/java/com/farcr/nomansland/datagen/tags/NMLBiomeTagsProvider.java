package com.farcr.nomansland.datagen.tags;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.registry.worldgen.NMLBiomes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class NMLBiomeTagsProvider extends BiomeTagsProvider {
    public NMLBiomeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, NoMansLand.MODID, existingFileHelper);
    }



    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // Add biomes to tags
        tag(BiomeTags.HAS_ANCIENT_CITY).add(NMLBiomes.CAVES, NMLBiomes.AUTUMNAL_FOREST);

        // Add tags to biomes
        addTagsTo(NMLBiomes.AUTUMNAL_FOREST, BiomeTags.IS_BEACH, BiomeTags.ALLOWS_SURFACE_SLIME_SPAWNS);
        addTagsTo(NMLBiomes.BAYOU, BiomeTags.IS_BEACH);
    }

    @SafeVarargs
    protected final void addTagsTo(ResourceKey<Biome> biome, TagKey<Biome>... biomeTags) {
        Arrays.stream(biomeTags).toList().forEach(biomeTag -> {
            this.tag(biomeTag).add(biome);
        });
    }
}
