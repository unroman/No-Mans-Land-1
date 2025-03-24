package com.farcr.nomansland.datagen.loot;

import com.farcr.nomansland.common.registry.NMLRegistries;
import com.farcr.nomansland.common.registry.blocks.NMLBlocks;
import com.google.common.collect.Sets;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.List;
import java.util.function.BiConsumer;

public class NMLBlockLootSubProvider extends BlockLootSubProvider {
    private final HolderLookup.Provider provider;

    public NMLBlockLootSubProvider(HolderLookup.Provider provider) {
        super(Sets.newHashSet(), FeatureFlags.REGISTRY.allFlags(), provider);
        this.provider = provider;
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
//        return NMLBlocks.BLOCKS.getEntries().stream().map(e -> (Block)e.value()).toList();
        return NMLBlocks.BLOCK_DEFINITIONS.stream().filter(e -> !(e.properties().loot() instanceof CustomBlockLootType)).map(e -> (Block)e.block().value()).toList();
    }

    @Override
    protected void generate() {
        //add(NMLBlocks.FIELD_MUSHROOM.get(), dropSelf(NMLBlocks.FIELD_MUSHROOM.get()));
        //dropSelf(NMLBlocks.FIELD_MUSHROOM.get());
        for (NMLBlocks.BlockDefinition<?> def : NMLBlocks.BLOCK_DEFINITIONS)
        {
            NMLBlocks.BlockProperties properties = def.properties();
            DeferredBlock<?> block = def.block();
            BlockLootType lootType = properties.loot();
            if (lootType instanceof SelfBlockLootType)
                dropSelf(block.get());
            else if (lootType instanceof OtherBlockLootType)
                add(block.get(), createSingleItemTable(((OtherBlockLootType)lootType).getBlock()));
            else if (lootType instanceof ShearsBlockLootType)
                add(block.get(), createShearsOnlyDrop(block.get()));
            else if (lootType instanceof OtherShearsBlockLootType)
                add(block.get(), createShearsOnlyDrop(((OtherShearsBlockLootType)lootType).getBlock()));
            else if (lootType instanceof SlabBlockLootType)
                add(block.get(), createSlabItemTable(block.get()));
            else if (lootType instanceof DoorBlockLootType)
                add(block.get(), createDoorTable(block.get()));
            else if (lootType instanceof FlowerPotBlockLootType)
                add(block.get(), createPotFlowerItemTable(((FlowerPotBlockLootType)lootType).getPlant()));
            else if (lootType instanceof BookshelfBlockLootType)
                add(block.get(), createSelfDropDispatchTable(block.get(), hasSilkTouch(), this.applyExplosionDecay(block, LootItem.lootTableItem(Items.BOOK).apply(SetItemCountFunction.setCount(ConstantValue.exactly(3.0f))))));
        }
    }
}
