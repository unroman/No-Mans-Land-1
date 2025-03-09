package com.farcr.nomansland.integration;

import com.farcr.nomansland.common.registry.blocks.NMLBlocks;
import com.farcr.nomansland.common.registry.items.NMLFoods;
import com.farcr.nomansland.common.registry.items.NMLItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import vectorwing.farmersdelight.common.FoodValues;
import vectorwing.farmersdelight.common.block.CabinetBlock;
import vectorwing.farmersdelight.common.block.MushroomColonyBlock;
import vectorwing.farmersdelight.common.block.PieBlock;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.item.DrinkableItem;
import vectorwing.farmersdelight.common.item.MushroomColonyItem;
import vectorwing.farmersdelight.common.registry.ModBlockEntityTypes;

import static com.farcr.nomansland.common.registry.blocks.NMLBlocks.BLOCKS;
import static com.farcr.nomansland.common.registry.blocks.NMLBlocks.registerBlock;
import static com.farcr.nomansland.common.registry.items.NMLItems.registerItem;
import static net.minecraft.world.level.block.state.BlockBehaviour.Properties.ofFullCopy;

public class FDIntegration {

    public static final DeferredBlock<Block> PINE_CABINET = registerBlock("pine_cabinet",
            () -> new CabinetBlock(ofFullCopy(Blocks.BARREL)));

    public static final DeferredBlock<Block> MAPLE_CABINET = registerBlock("maple_cabinet",
            () -> new CabinetBlock(ofFullCopy(Blocks.BARREL)));

    public static final DeferredBlock<Block> WALNUT_CABINET = registerBlock("walnut_cabinet",
            () -> new CabinetBlock(ofFullCopy(Blocks.BARREL)));

    public static final DeferredBlock<Block> WILLOW_CABINET = registerBlock("willow_cabinet",
            () -> new CabinetBlock(ofFullCopy(Blocks.BARREL)));

    public static final DeferredBlock<Block> FIELD_MUSHROOM_COLONY = BLOCKS.register("field_mushroom_colony",
            () -> new MushroomColonyBlock(NMLItems.FIELD_MUSHROOM, Block.Properties.ofFullCopy(NMLBlocks.FIELD_MUSHROOM.get())));

    public static final DeferredItem<Item> FIELD_MUSHROOM_COLONY_ITEM = registerItem("field_mushroom_colony",
            () -> new MushroomColonyItem(FIELD_MUSHROOM_COLONY.get(), new Item.Properties()));

    public static final DeferredItem<Item> SEARED_VENISON = registerItem("seared_venison",
            () -> new ConsumableItem(new Item.Properties().food(NMLFoods.SEARED_VENISON).craftRemainder(Items.BOWL).stacksTo(16), true));

    public static final DeferredItem<Item> PEAR_COBBLER_SLICE = registerItem("pear_cobbler_slice",
            () -> new Item(new Item.Properties().food(FoodValues.PIE_SLICE)));

    public static final DeferredBlock<Block> PEAR_COBBLER = BLOCKS.register("pear_cobbler",
            () -> new PieBlock(ofFullCopy(Blocks.CAKE), PEAR_COBBLER_SLICE));

    public static final DeferredItem<Item> PEAR_COBBLER_ITEM = registerItem("pear_cobbler",
            () -> new BlockItem(PEAR_COBBLER.get(), new Item.Properties()));

    public static final DeferredItem<Item> PEAR_JUICE = registerItem("pear_juice",
            () -> new DrinkableItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).food(NMLFoods.PEAR)));

    public static void addBlockEntities(final BlockEntityTypeAddBlocksEvent event) {
        event.modify(
                ModBlockEntityTypes.CABINET.get(),
                MAPLE_CABINET.get(),
                PINE_CABINET.get(),
                WALNUT_CABINET.get(),
                WILLOW_CABINET.get()
        );
    }

    public static void register() {
    }
}
