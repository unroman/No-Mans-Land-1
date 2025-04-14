package com.farcr.nomansland.datagen.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

public class NMLItemTagsProvider extends ItemTagsProvider {

    public NMLItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags) {
        super(output, lookupProvider, blockTags);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
//        HashMap<TagKey<Item>, List<Item>> tagMap = Maps.newHashMap();
//        for (BlockDefinition<?> definition : NMLBlocks.BLOCK_DEFINITIONS) {
//            for (TagKey<Item> key : definition.properties().itemTags()) {
//                if (tagMap.containsKey(key)) {
//                    tagMap.put(key, List.of(definition.asItem()));
//                } else {
//                    tagMap.get(key).add(definition.asItem());
//                }
//            }
//        }
//
//        Iterator<Map.Entry<TagKey<Item>, List<Item>>> tagMapIterator = tagMap.entrySet().iterator();
//        while (tagMapIterator.hasNext()) {
//            Map.Entry<TagKey<Item>, List<Item>> element = tagMapIterator.next();
//            IntrinsicTagAppender<Item> tagAppender = tag(element.getKey());
//            for (Item b : element.getValue()) {
//                tagAppender.add(b);
//            }
//        }
    }
}
