package com.farcr.nomansland.datagen.loot;

import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class OtherShearsBlockLootType extends BlockLootType {
    private final Supplier<Block> block;

    public OtherShearsBlockLootType(Supplier<Block> block) {
        this.block = block;
    }

    ItemLike getBlock() {
        return block.get();
    }
}
