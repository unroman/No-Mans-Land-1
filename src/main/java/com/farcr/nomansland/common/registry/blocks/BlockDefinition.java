package com.farcr.nomansland.common.registry.blocks;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.jetbrains.annotations.NotNull;

public record BlockDefinition<T extends Block>(@NotNull DeferredBlock<T> block, @NotNull BlockProperties properties) {
    public ItemStack toStack() {
        return block.toStack();
    }

    public ItemStack toStack(int count) {
        return block.toStack(count);
    }

    public Item asItem() {
        return block.asItem();
    }

    public T get() {
        return block.get();
    }

    public T value() {
        return block.value();
    }

    public ResourceLocation getId() {
        return block.getId();
    }

    public String name() {
        return block.getRegisteredName();
    }
}
