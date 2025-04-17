package com.farcr.nomansland.datagen.tags;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.registry.NMLTags;
import com.farcr.nomansland.common.registry.blocks.NMLBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import static net.minecraft.tags.BlockTags.*;

public class NMLBlockTagsProvider extends BlockTagsProvider {
    public NMLBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, NoMansLand.MODID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        for (NMLBlocks.Woodset woodset : NMLBlocks.WOODSETS) {
            tag(STANDING_SIGNS).add(woodset.sign().block());
            tag(WALL_SIGNS).add(woodset.wallSign().block());
            tag(CEILING_HANGING_SIGNS).add(woodset.hangingSign().block());
            tag(WALL_HANGING_SIGNS).add(woodset.hangingWallSign().block());
            tag(PLANKS).add(woodset.planks().block());
            tag(Tags.Blocks.BOOKSHELVES).add(woodset.bookshelf().block());
            tag(Tags.Blocks.FENCE_GATES_WOODEN).add(woodset.fenceGate().block());
            tag(WOODEN_FENCES).add(woodset.fence().block());
            tag(WOODEN_STAIRS).add(woodset.stairs().block());
            tag(WOODEN_BUTTONS).add(woodset.button().block());
            tag(WOODEN_DOORS).add(woodset.door().block());
            tag(WOODEN_SLABS).add(woodset.slab().block());
            tag(WOODEN_PRESSURE_PLATES).add(woodset.pressurePlate().block());
            tag(WOODEN_TRAPDOORS).add(woodset.trapdoor().block());
            tag(LOGS).add(woodset.log().block());
            tag(Tags.Blocks.STRIPPED_LOGS).add(woodset.strippedLog().block());
            tag(Tags.Blocks.STRIPPED_WOODS).add(woodset.strippedWood().block());
        }

        tag(LOGS_THAT_BURN).addTags(NMLTags.MAPLE_LOGS.blockTag(), NMLTags.PINE_LOGS.blockTag(), NMLTags.WALNUT_LOGS.blockTag(), NMLTags.WILLOW_LOGS.blockTag());
        tag(OVERWORLD_NATURAL_LOGS).add(
                NMLBlocks.MAPLE.log().block(),
                NMLBlocks.PINE.log().block(),
                NMLBlocks.WALNUT.log().block(),
                NMLBlocks.WILLOW.log().block()
        );

        addToTags(NMLBlocks.SILT.block(),
                OVERWORLD_CARVER_REPLACEABLES,
                LUSH_GROUND_REPLACEABLE,
                MOSS_REPLACEABLE,
                SCULK_REPLACEABLE,
                CONVERTABLE_TO_MUD,
                DEAD_BUSH_MAY_PLACE_ON,
                BIG_DRIPLEAF_PLACEABLE,
                BAMBOO_PLANTABLE_ON
        );

        addToTags(NMLBlocks.CUT_VINE.get(), MANGROVE_LOGS_CAN_GROW_THROUGH, MANGROVE_ROOTS_CAN_GROW_THROUGH);
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
