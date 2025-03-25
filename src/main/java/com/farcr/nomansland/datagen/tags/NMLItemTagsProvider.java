package com.farcr.nomansland.datagen.tags;

import com.farcr.nomansland.common.registry.blocks.BlockDefinition;
import com.farcr.nomansland.common.registry.blocks.NMLBlocks;
import com.google.common.collect.Maps;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class NMLItemTagsProvider extends ItemTagsProvider {

    public NMLItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags) {
        super(output, lookupProvider, blockTags);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        HashMap<TagKey<Item>, List<Item>> tagMap = Maps.newHashMap();
        for (BlockDefinition<?> definition : NMLBlocks.BLOCK_DEFINITIONS) {
            for (TagKey<Item> key : definition.properties().itemTags()) {
                if (tagMap.containsKey(key)) {
                    tagMap.put(key, List.of(definition.block().asItem()));
                } else {
                    tagMap.get(key).add(definition.block().asItem());
                }
            }
        }

        Iterator<Map.Entry<TagKey<Item>, List<Item>>> tagMapIterator = tagMap.entrySet().iterator();
        while (tagMapIterator.hasNext()) {
            Map.Entry<TagKey<Item>, List<Item>> element = tagMapIterator.next();
            IntrinsicTagAppender<Item> tagAppender = tag(element.getKey());
            for (Item b : element.getValue()) {
                tagAppender.add(b);
            }
        }
    }
}
