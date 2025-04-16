package com.farcr.nomansland.common.definitions;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

public class ItemLikeDefinition<T extends ItemLike> extends Definition<T, T> implements ItemLike {

    protected ItemLikeDefinition(ResourceKey<T> key, boolean customLang) {
        super(key, customLang);
    }

    protected ItemLikeDefinition(ResourceKey<T> key) {
        this(key, false);
    }

    @Override
    public @NotNull Item asItem() {
        Item item = get().asItem();
        if (item == Items.AIR) throw new IllegalArgumentException("No registered item for " + getRegisteredName());
        return item;
    }

    public Item item() {
        return asItem();
    }

    public Ingredient ingredient() {
        return ingredient(1);
    }

    public Ingredient ingredient(int count) {
        return Ingredient.of(new ItemStack(item(), count));
    }

    public ItemStack stack() {
        return stack(1);
    }

    public ItemStack stack(int count) {
        return new ItemStack(item(), count);
    }

    public boolean is(ItemLike itemLike) {
        return get().equals(itemLike);
    }
}
