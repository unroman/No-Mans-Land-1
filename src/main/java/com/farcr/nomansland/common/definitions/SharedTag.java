package com.farcr.nomansland.common.definitions;

import com.farcr.nomansland.NoMansLand;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class SharedTag {
    private final String name;
    public SharedTag(String name) {
        this.name = name;
    }

    public TagKey<Item> itemTag() {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, name));
    }

    public TagKey<Block> blockTag() {
        return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, name));
    }
}
