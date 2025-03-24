package com.farcr.nomansland.datagen.loot;

import com.farcr.nomansland.common.registry.blocks.NMLBlocks;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class OtherBlockLootType extends BlockLootType {
    private final Supplier<Block> block;

    public OtherBlockLootType(Supplier<Block> block) {
        this.block = block;
    }

    ItemLike getBlock() {
        return block.get();
    }
}
