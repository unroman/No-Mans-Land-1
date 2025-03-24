package com.farcr.nomansland.datagen.loot;

import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class FlowerPotBlockLootType extends BlockLootType {
    private final Supplier<Block> plant;

    public FlowerPotBlockLootType(Supplier<Block> plant) {
        this.plant = plant;
    }

    public ItemLike getPlant() {
        return plant.get();
    }
}
