package com.farcr.nomansland.integration;

import com.farcr.nomansland.common.block.NMLCabinetBlock;
import com.farcr.nomansland.common.blockentity.NMLCabinetBlockEntity;
import com.farcr.nomansland.common.registry.NMLBlocks;
import com.farcr.nomansland.common.registry.NMLFoods;
import com.farcr.nomansland.common.registry.NMLItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import vectorwing.farmersdelight.common.block.MushroomColonyBlock;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.item.MushroomColonyItem;

import java.util.function.Supplier;

import static com.farcr.nomansland.common.registry.NMLBlockEntities.BLOCK_ENTITIES;
import static com.farcr.nomansland.common.registry.NMLBlocks.BLOCKS;
import static com.farcr.nomansland.common.registry.NMLBlocks.registerBlock;
import static com.farcr.nomansland.common.registry.NMLItems.registerItem;

public class FDIntegration {

    public static final DeferredBlock<Block> PINE_CABINET = registerBlock("pine_cabinet",
            () -> new NMLCabinetBlock(Block.Properties.ofFullCopy(Blocks.BARREL)));

    public static final DeferredBlock<Block> MAPLE_CABINET = registerBlock("maple_cabinet",
            () -> new NMLCabinetBlock(Block.Properties.ofFullCopy(Blocks.BARREL)));

    public static final DeferredBlock<Block> WALNUT_CABINET = registerBlock("walnut_cabinet",
            () -> new NMLCabinetBlock(Block.Properties.ofFullCopy(Blocks.BARREL)));

    public static final DeferredBlock<Block> WILLOW_CABINET = registerBlock("willow_cabinet",
            () -> new NMLCabinetBlock(Block.Properties.ofFullCopy(Blocks.BARREL)));

    public static final Supplier<BlockEntityType<?>> CABINET = BLOCK_ENTITIES.register("nml_cabinets",
            () -> BlockEntityType.Builder.of(NMLCabinetBlockEntity::new,
                    PINE_CABINET.get(),
                    MAPLE_CABINET.get(),
                    WALNUT_CABINET.get(),
                    WILLOW_CABINET.get()
            ).build(null));


    public static final DeferredBlock<Block> FIELD_MUSHROOM_COLONY = BLOCKS.register("field_mushroom_colony",
            () -> new MushroomColonyBlock(NMLItems.FIELD_MUSHROOM, Block.Properties.ofFullCopy(NMLBlocks.FIELD_MUSHROOM.get())));

    public static final DeferredItem<Item> FIELD_MUSHROOM_COLONY_ITEM = registerItem("field_mushroom_colony",
            () -> new MushroomColonyItem(FIELD_MUSHROOM_COLONY.get(), new Item.Properties()));

    public static final DeferredItem<Item> SEARED_VENISON = registerItem("seared_venison",
            () -> new ConsumableItem(new Item.Properties().food(NMLFoods.SEARED_VENISON).craftRemainder(Items.BOWL).stacksTo(16), true));

    public static void register() {
    }
}

