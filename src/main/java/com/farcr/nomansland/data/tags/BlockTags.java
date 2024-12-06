package com.farcr.nomansland.data.tags;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.registry.NMLBlocks;
import com.farcr.nomansland.common.registry.NMLTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class BlockTags extends BlockTagsProvider {
    public BlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, NoMansLand.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(NMLTags.TRIMMED_PLANKS).add(
                NMLBlocks.TRIMMED_ACACIA_PLANKS.get(),
                NMLBlocks.TRIMMED_BAMBOO_PLANKS.get(),
                NMLBlocks.TRIMMED_BIRCH_PLANKS.get(),
                NMLBlocks.TRIMMED_CHERRY_PLANKS.get(),
                NMLBlocks.TRIMMED_CRIMSON_PLANKS.get(),
                NMLBlocks.TRIMMED_DARK_OAK_PLANKS.get(),
                NMLBlocks.TRIMMED_JUNGLE_PLANKS.get(),
                NMLBlocks.TRIMMED_MANGROVE_PLANKS.get(),
                NMLBlocks.TRIMMED_MAPLE_PLANKS.get(),
                NMLBlocks.TRIMMED_OAK_PLANKS.get(),
                NMLBlocks.TRIMMED_PINE_PLANKS.get(),
                NMLBlocks.TRIMMED_SPRUCE_PLANKS.get(),
                NMLBlocks.TRIMMED_WALNUT_PLANKS.get(),
                NMLBlocks.TRIMMED_WARPED_PLANKS.get(),
                NMLBlocks.TRIMMED_WILLOW_PLANKS.get()
        );

        tag(NMLTags.BONEMEAL_SPREADS).addTag(
                net.minecraft.tags.BlockTags.SMALL_FLOWERS
        );
        tag(NMLTags.BONEMEAL_SPREADS).add(
                Blocks.LILY_PAD,
                NMLBlocks.CLOVER_PATCH.get(),
                NMLBlocks.FIDDLEHEAD.get(),
                NMLBlocks.DUCKWEED.get(),
                NMLBlocks.TALL_BEACHGRASS.get(),
                NMLBlocks.SHORT_BEACHGRASS.get(),
                NMLBlocks.MYCELIUM_GROWTHS.get(),
                NMLBlocks.MYCELIUM_SPROUTS.get()
        );

        // you can also do this with regular mc tags and basically any tag ever
        // remember to runData when you make changes to any datagen files!
    }
}
