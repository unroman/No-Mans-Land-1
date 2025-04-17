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
import net.neoforged.neoforge.common.Tags;
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
        tag(BiomeTags.IS_OVERWORLD).add(
                NMLBiomes.AUTUMNAL_FOREST,
                NMLBiomes.BAYOU,
                NMLBiomes.BOG,
                NMLBiomes.DARK_SWAMP,
                NMLBiomes.DARK_TAIGA,
                NMLBiomes.MAPLE_FOREST,
                NMLBiomes.MAPLE_GROVE,
                NMLBiomes.OLD_GROWTH_FOREST,
                NMLBiomes.OLD_GROWTH_FOREST_CLEARING,
                NMLBiomes.OLD_GROWTH_FOREST_EDGE,

                NMLBiomes.CAVES,
                NMLBiomes.CAVE_DEPTHS
        );

        addTagsTo(NMLBiomes.AUTUMNAL_FOREST, BiomeTags.IS_FOREST, Tags.Biomes.IS_DECIDUOUS_TREE, Tags.Biomes.IS_TEMPERATE);
        addTagsTo(NMLBiomes.BAYOU, BiomeTags.HAS_SWAMP_HUT, BiomeTags.IS_JUNGLE, Tags.Biomes.IS_SWAMP, Tags.Biomes.IS_DENSE_VEGETATION, Tags.Biomes.IS_LUSH, Tags.Biomes.IS_HOT, Tags.Biomes.IS_JUNGLE_TREE, Tags.Biomes.IS_WET);
        addTagsTo(NMLBiomes.BOG, BiomeTags.HAS_SWAMP_HUT, Tags.Biomes.IS_DECIDUOUS_TREE, Tags.Biomes.IS_CONIFEROUS_TREE, Tags.Biomes.IS_COLD, Tags.Biomes.IS_WET, Tags.Biomes.IS_WET_OVERWORLD);
        addTagsTo(NMLBiomes.DARK_SWAMP, BiomeTags.HAS_SWAMP_HUT, BiomeTags.IS_FOREST, Tags.Biomes.IS_SWAMP, Tags.Biomes.IS_DENSE_VEGETATION, Tags.Biomes.IS_SPOOKY, Tags.Biomes.IS_TEMPERATE, Tags.Biomes.IS_DECIDUOUS_TREE, Tags.Biomes.IS_WET, BiomeTags.HAS_WOODLAND_MANSION);
        addTagsTo(NMLBiomes.DARK_TAIGA, BiomeTags.IS_TAIGA, Tags.Biomes.IS_CONIFEROUS_TREE, Tags.Biomes.IS_COLD, Tags.Biomes.IS_SPOOKY);
        addTagsTo(NMLBiomes.MAPLE_FOREST, BiomeTags.IS_FOREST, Tags.Biomes.IS_DECIDUOUS_TREE, Tags.Biomes.IS_COLD);
        addTagsTo(NMLBiomes.MAPLE_GROVE, BiomeTags.IS_FOREST, Tags.Biomes.IS_DECIDUOUS_TREE, Tags.Biomes.IS_COLD, Tags.Biomes.IS_SNOWY, Tags.Biomes.IS_PLATEAU);
        addTagsTo(NMLBiomes.OLD_GROWTH_FOREST, BiomeTags.IS_FOREST, Tags.Biomes.IS_DECIDUOUS_TREE, Tags.Biomes.IS_DENSE_VEGETATION, Tags.Biomes.IS_TEMPERATE, Tags.Biomes.IS_OLD_GROWTH, Tags.Biomes.IS_RARE);
        addTagsTo(NMLBiomes.OLD_GROWTH_FOREST_EDGE, BiomeTags.IS_FOREST, Tags.Biomes.IS_DECIDUOUS_TREE, Tags.Biomes.IS_DENSE_VEGETATION, Tags.Biomes.IS_TEMPERATE, Tags.Biomes.IS_OLD_GROWTH, Tags.Biomes.IS_RARE);
        addTagsTo(NMLBiomes.OLD_GROWTH_FOREST_CLEARING, BiomeTags.IS_FOREST, Tags.Biomes.IS_DECIDUOUS_TREE, Tags.Biomes.IS_DENSE_VEGETATION, Tags.Biomes.IS_TEMPERATE, Tags.Biomes.IS_OLD_GROWTH, Tags.Biomes.IS_RARE);

    }

    @SafeVarargs
    protected final void addTagsTo(ResourceKey<Biome> biome, TagKey<Biome>... biomeTags) {
        Arrays.stream(biomeTags).toList().forEach(biomeTag -> tag(biomeTag).add(biome));
    }
}
