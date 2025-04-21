package com.farcr.nomansland.common.definitions;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

public class ItemDefinition<T extends Item> extends ItemLikeDefinition<Item, T> {

    protected ItemDefinition(ResourceKey<Item> key, boolean customLang) {
        super(key, customLang);
    }

    protected ItemDefinition(ResourceKey<Item> key) {
        this(key, false);
    }

    public static <T extends Item> ItemDefinition<T> fromHolder(DeferredItem<T> holder, boolean customLang) {
        return new ItemDefinition<>(holder.getKey(), customLang);
    }

    public static <T extends Item> ItemDefinition<T> fromHolder(DeferredItem<T> holder) {
        return fromHolder(holder, false);
    }

    public boolean is(Item item) {
        return get().equals(item);
    }

    public boolean isBlockItem() {
        return get() instanceof BlockItem;
    }

    @Override
    public String langKey() {
        return "item." + super.langKey();
    }
}
