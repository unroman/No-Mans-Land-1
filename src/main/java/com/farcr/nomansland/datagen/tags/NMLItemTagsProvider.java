package com.farcr.nomansland.datagen.tags;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.registry.NMLTags;
import com.farcr.nomansland.common.registry.blocks.NMLBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class NMLItemTagsProvider extends ItemTagsProvider {


    public NMLItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, NoMansLand.MODID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        for (NMLBlocks.Woodset woodset : NMLBlocks.WOODSETS) {
            tag(ItemTags.SIGNS).add(woodset.sign().item());
            tag(ItemTags.HANGING_SIGNS).add(woodset.hangingSign().item());
            tag(ItemTags.PLANKS).add(woodset.planks().item());
            tag(Tags.Items.BOOKSHELVES).add(woodset.bookshelf().item());
            tag(Tags.Items.FENCE_GATES_WOODEN).add(woodset.fenceGate().item());
            tag(ItemTags.WOODEN_FENCES).add(woodset.fence().item());
            tag(ItemTags.WOODEN_STAIRS).add(woodset.stairs().item());
            tag(ItemTags.WOODEN_BUTTONS).add(woodset.button().item());
            tag(ItemTags.WOODEN_DOORS).add(woodset.door().item());
            tag(ItemTags.WOODEN_SLABS).add(woodset.slab().item());
            tag(ItemTags.WOODEN_PRESSURE_PLATES).add(woodset.pressurePlate().item());
            tag(ItemTags.WOODEN_TRAPDOORS).add(woodset.trapdoor().item());
            tag(ItemTags.LOGS).add(woodset.log().item());
            tag(Tags.Items.STRIPPED_LOGS).add(woodset.strippedLog().item());
            tag(Tags.Items.STRIPPED_WOODS).add(woodset.strippedWood().item());
        }

        tag(ItemTags.LOGS_THAT_BURN).addTags(NMLTags.MAPLE_LOGS.itemTag(), NMLTags.PINE_LOGS.itemTag(), NMLTags.WALNUT_LOGS.itemTag(), NMLTags.WILLOW_LOGS.itemTag());
    }

    @SafeVarargs
    protected final void addToTags(Item item, TagKey<Item>... itemTags) {
        Arrays.stream(itemTags).toList().forEach(itemTag -> tag(itemTag).add(item));
    }

    @SafeVarargs
    protected final void addToTags(TagKey<Item> item, TagKey<Item>... itemTags) {
        Arrays.stream(itemTags).toList().forEach(itemTag -> tag(itemTag).addTag(item));
    }
}
