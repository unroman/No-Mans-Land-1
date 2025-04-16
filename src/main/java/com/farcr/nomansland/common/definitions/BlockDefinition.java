package com.farcr.nomansland.common.definitions;

import com.farcr.nomansland.datagen.loot.BlockLootType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;

public class BlockDefinition<T extends Block> extends ItemLikeDefinition<Block, T> {

    private final BlockProperties properties;

    protected BlockDefinition(ResourceKey<Block> key, boolean customLang, BlockProperties properties) {
        super(key, customLang);
        this.properties = properties;
    }

    protected BlockDefinition(ResourceKey<Block> key, boolean customLang) {
        this(key, customLang, BlockProperties.basic());
    }

    protected BlockDefinition(ResourceKey<Block> key) {
        this(key, false);
    }

    public static <T extends Block> BlockDefinition<T> fromHolder(DeferredBlock<T> holder) {
        return fromHolder(holder, BlockProperties.basic());
    }

    public static <T extends Block> BlockDefinition<T> fromHolder(DeferredBlock<T> holder, BlockProperties properties) {
        return fromHolder(holder, false, properties);
    }

    public static <T extends Block> BlockDefinition<T> fromHolder(DeferredBlock<T> holder, boolean customLang, BlockProperties properties) {
        return new BlockDefinition<>(holder.getKey(), customLang, properties);
    }

    public boolean is(Block block) {
        return get().equals(block);
    }

    @Override
    public String langKey() {
        return "block." + super.langKey();
    }

    public BlockLootType lootType() {
        return properties.lootType();
    }

    public Block block() {
        return get();
    }

    public BlockProperties properties() {
        return properties;
    }
}
