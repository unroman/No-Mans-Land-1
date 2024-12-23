package com.farcr.nomansland.integration;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.ModList;

import java.util.Optional;
import java.util.function.Supplier;

public enum Mods {
    FARMERSDELIGHT;

    private final String id;

    Mods() {
        id = name().toLowerCase();
    }

    public String id() {
        return id;
    }

    public ResourceLocation location(String path) {
        return ResourceLocation.fromNamespaceAndPath(id, path);
    }

    public Block getBlock(String id) {
        return BuiltInRegistries.BLOCK.get(location(id));
    }

    public Item getItem(String id) {
        return BuiltInRegistries.ITEM.get(location(id));
    }

    public boolean isLoaded() {
        return ModList.get().isLoaded(id);
    }

    public <T> Optional<T> runIfInstalled(Supplier<T> toRun) {
        if (isLoaded())
            return Optional.of(toRun.get());
        return Optional.empty();
    }
}
