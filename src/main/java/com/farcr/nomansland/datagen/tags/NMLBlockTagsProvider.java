package com.farcr.nomansland.datagen.tags;

import com.farcr.nomansland.common.registry.blocks.BlockDefinition;
import com.farcr.nomansland.common.registry.blocks.NMLBlocks;
import com.google.common.collect.Maps;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class NMLBlockTagsProvider extends BlockTagsProvider {
    public NMLBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // This sucks idk how to do this
        /*HashMap<TagKey<Block>, List<Block>> tagMap = Maps.newHashMap();
        for (BlockDefinition<?> definition : NMLBlocks.BLOCK_DEFINITIONS) {
            for (TagKey<Block> key : definition.properties().blockTags()) {
                if (tagMap.get(key) == null) {
                    tagMap.put(key, List.of(definition.block().get()));
                } else {
                    tagMap.get(key).add(definition.block().get());
                }
            }
        }

        Iterator<Map.Entry<TagKey<Block>, List<Block>>> tagMapIterator = tagMap.entrySet().iterator();
        while (tagMapIterator.hasNext()) {
            Map.Entry<TagKey<Block>, List<Block>> element = tagMapIterator.next();
            IntrinsicTagAppender<Block> tagAppender = tag(element.getKey());
            for (Block b : element.getValue()) {
                tagAppender.add(b);
            }
        }*/
    }
}
