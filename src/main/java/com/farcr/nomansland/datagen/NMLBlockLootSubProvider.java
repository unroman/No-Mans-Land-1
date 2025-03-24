package com.farcr.nomansland.datagen;

import com.farcr.nomansland.common.registry.blocks.BlockDefinition;
import com.farcr.nomansland.common.registry.blocks.BlockProperties;
import com.farcr.nomansland.common.registry.blocks.NMLBlocks;
import com.farcr.nomansland.datagen.loot.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.HashSet;

public class NMLBlockLootSubProvider extends BlockLootSubProvider {
    private final HolderLookup.Provider provider;

    public NMLBlockLootSubProvider(HolderLookup.Provider provider) {
        super(new HashSet<>(), FeatureFlags.REGISTRY.allFlags(), provider);
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

        for (BlockDefinition<?> def : NMLBlocks.BLOCK_DEFINITIONS) {
            BlockProperties properties = def.properties();
            DeferredBlock<?> block = def.block();
            BlockLootType lootType = properties.loot();
            if (lootType instanceof SelfBlockLootType)
                dropSelf(block.get());
            else if (lootType instanceof OtherBlockLootType otherBlockLootType)
                add(block.get(), createSingleItemTable(otherBlockLootType.getBlock()));
            else if (lootType instanceof ShearsBlockLootType)
                add(block.get(), createShearsOnlyDrop(block.get()));
            else if (lootType instanceof OtherShearsBlockLootType otherShearsBlockLootType)
                add(block.get(), createShearsOnlyDrop(otherShearsBlockLootType.getBlock()));
            else if (lootType instanceof SlabBlockLootType)
                add(block.get(), createSlabItemTable(block.get()));
            else if (lootType instanceof DoorBlockLootType)
                add(block.get(), createDoorTable(block.get()));
            else if (lootType instanceof FlowerPotBlockLootType flowerPotBlockLootType)
                add(block.get(), createPotFlowerItemTable(flowerPotBlockLootType.getPlant()));
            else if (lootType instanceof BookshelfBlockLootType)
                add(block.get(), createSelfDropDispatchTable(block.get(), hasSilkTouch(), this.applyExplosionDecay(block, LootItem.lootTableItem(Items.BOOK).apply(SetItemCountFunction.setCount(ConstantValue.exactly(3.0f))))));
        }
    }
}
