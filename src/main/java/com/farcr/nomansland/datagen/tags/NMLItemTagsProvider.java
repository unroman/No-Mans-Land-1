package com.farcr.nomansland.datagen.tags;

import com.farcr.nomansland.NoMansLand;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class NMLItemTagsProvider extends ItemTagsProvider {


    public NMLItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, NoMansLand.MODID, existingFileHelper);
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

    @SafeVarargs
    protected final void addTagsTo(ResourceKey<Item> item, TagKey<Item>... itemTags) {
        Arrays.stream(itemTags).toList().forEach(itemTag -> {
            this.tag(itemTag).add(item);
        });
    }
}
