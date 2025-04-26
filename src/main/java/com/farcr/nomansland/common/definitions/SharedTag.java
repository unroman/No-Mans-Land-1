package com.farcr.nomansland.common.definitions;

import com.farcr.nomansland.NoMansLand;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class SharedTag {
    private final TagKey<Block> blockTag;
    private final TagKey<Item> itemTag;

    public SharedTag(String name) {
        this(NoMansLand.MODID, name);
    }

    public SharedTag(String modId, String name) {
        blockTag = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(modId, name));
        itemTag = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(modId, name));
    }

    public SharedTag(TagKey<Block> blockTag, TagKey<Item> itemTag) {
        this.blockTag = blockTag;
        this.itemTag = itemTag;
    }

    public TagKey<Item> itemTag() {
        return itemTag;
    }

    public TagKey<Block> blockTag() {
        return blockTag;
    }
}
