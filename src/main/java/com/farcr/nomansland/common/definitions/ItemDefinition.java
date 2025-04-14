package com.farcr.nomansland.common.definitions;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public class ItemDefinition<T extends Item> extends ItemLikeDefinition<T> {
    protected ItemDefinition(ResourceKey<T> key) {
        super(key);
    }

    public boolean is(Item item) {
        return get().equals(item);
    }

    @Override
    public String langKey() {
        return "item." + super.langKey();
    }
}
