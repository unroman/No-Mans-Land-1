package com.farcr.nomansland.common.definitions;

import com.farcr.nomansland.common.registry.blocks.BlockProperties;
import com.farcr.nomansland.datagen.loot.BlockLootType;
import com.farcr.nomansland.datagen.loot.CustomBlockLootType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;

public class BlockDefinition<T extends Block> extends ItemLikeDefinition<T> {

    private final BlockProperties properties;

    protected BlockDefinition(ResourceKey<T> key) {
        this(key, false);
    }

    protected BlockDefinition(ResourceKey<T> key, boolean customLang) {
        this(key, new BlockProperties(new CustomBlockLootType(), customLang));
    }

    protected BlockDefinition(ResourceKey<T> key, BlockProperties properties) {
        super(key);
        this.properties = properties;
    }

    public boolean is(Block block) {
        return get().equals(block);
    }

    @Override
    public String langKey() {
        return "block." + super.langKey();
    }

    public static BlockDefinition<?> fromHolder(DeferredHolder<Block, ? extends Block> holder) {
        return new BlockDefinition<>(holder.getKey());
    }

    public static BlockDefinition<?> fromHolder(DeferredHolder<Block, ? extends Block> holder, BlockProperties properties) {
        return new BlockDefinition<>(holder.getKey(), properties);
    }

    public static BlockDefinition<?> fromHolder(DeferredHolder<Block, ? extends Block> holder, boolean customLang) {
        return new BlockDefinition<>(holder.getKey(), customLang);
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
