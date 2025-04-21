package com.farcr.nomansland.datagen.tags;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.registry.NMLTags;
import com.farcr.nomansland.common.registry.blocks.NMLBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class NMLBlockTagsProvider extends BlockTagsProvider {
    public NMLBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, NoMansLand.MODID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        for (NMLBlocks.Woodset woodset : NMLBlocks.WOODSETS) {
            tag(BlockTags.STANDING_SIGNS).add(woodset.sign().block());
            tag(BlockTags.WALL_SIGNS).add(woodset.wallSign().block());
            tag(BlockTags.CEILING_HANGING_SIGNS).add(woodset.hangingSign().block());
            tag(BlockTags.WALL_HANGING_SIGNS).add(woodset.hangingWallSign().block());
            tag(BlockTags.PLANKS).add(woodset.planks().block());
            tag(Tags.Blocks.BOOKSHELVES).add(woodset.bookshelf().block());
            tag(Tags.Blocks.FENCE_GATES_WOODEN).add(woodset.fenceGate().block());
            tag(Tags.Blocks.FENCES_WOODEN).add(woodset.fence().block());
            tag(BlockTags.WOODEN_STAIRS).add(woodset.stairs().block());
            tag(BlockTags.WOODEN_BUTTONS).add(woodset.button().block());
            tag(BlockTags.WOODEN_DOORS).add(woodset.door().block());
            tag(BlockTags.WOODEN_SLABS).add(woodset.slab().block());
            tag(BlockTags.WOODEN_PRESSURE_PLATES).add(woodset.pressurePlate().block());
            tag(BlockTags.WOODEN_TRAPDOORS).add(woodset.trapdoor().block());
            tag(BlockTags.LOGS).add(woodset.log().block());
            tag(Tags.Blocks.STRIPPED_LOGS).add(woodset.strippedLog().block());
            tag(Tags.Blocks.STRIPPED_WOODS).add(woodset.strippedWood().block());
        }

        tag(BlockTags.LOGS_THAT_BURN).addTags(NMLTags.MAPLE_LOGS.blockTag(), NMLTags.PINE_LOGS.blockTag(), NMLTags.WALNUT_LOGS.blockTag(), NMLTags.WILLOW_LOGS.blockTag());
        tag(BlockTags.OVERWORLD_NATURAL_LOGS).add(
                NMLBlocks.MAPLE.log().block(),
                NMLBlocks.PINE.log().block(),
                NMLBlocks.WALNUT.log().block(),
                NMLBlocks.WILLOW.log().block()
        );

        addToTags(NMLBlocks.SILT.block(),
                BlockTags.OVERWORLD_CARVER_REPLACEABLES,
                BlockTags.LUSH_GROUND_REPLACEABLE,
                BlockTags.MOSS_REPLACEABLE,
                BlockTags.SCULK_REPLACEABLE,
                BlockTags.CONVERTABLE_TO_MUD,
                BlockTags.DEAD_BUSH_MAY_PLACE_ON,
                BlockTags.BIG_DRIPLEAF_PLACEABLE,
                BlockTags.BAMBOO_PLANTABLE_ON
        );

        addToTags(NMLBlocks.CUT_VINE.get(), BlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH, BlockTags.MANGROVE_ROOTS_CAN_GROW_THROUGH);
    }

    @SafeVarargs
    protected final void addToTags(Block block, TagKey<Block>... blockTags) {
        Arrays.stream(blockTags).toList().forEach(blockTag -> tag(blockTag).add(block));
    }

    @SafeVarargs
    protected final void addToTags(TagKey<Block> block, TagKey<Block>... blockTags) {
        Arrays.stream(blockTags).toList().forEach(blockTag -> tag(blockTag).addTag(block));
    }
}
