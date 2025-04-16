package com.farcr.nomansland.common.integration;

import com.farcr.nomansland.common.definitions.BlockDefinition;
import com.farcr.nomansland.common.definitions.ItemDefinition;
import com.farcr.nomansland.common.registry.blocks.NMLBlocks;
import com.farcr.nomansland.common.registry.items.NMLFoods;
import com.farcr.nomansland.common.registry.items.NMLItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import org.checkerframework.checker.units.qual.C;
import vectorwing.farmersdelight.common.FoodValues;
import vectorwing.farmersdelight.common.block.CabinetBlock;
import vectorwing.farmersdelight.common.block.MushroomColonyBlock;
import vectorwing.farmersdelight.common.block.PieBlock;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.item.DrinkableItem;
import vectorwing.farmersdelight.common.item.MushroomColonyItem;
import vectorwing.farmersdelight.common.registry.ModBlockEntityTypes;

import static com.farcr.nomansland.common.registry.blocks.NMLBlocks.registerNoItem;
import static net.minecraft.world.level.block.state.BlockBehaviour.Properties.ofFullCopy;

public class FDIntegration {

    public static final BlockDefinition<CabinetBlock> PINE_CABINET = NMLBlocks.register("pine_cabinet",
            () -> new CabinetBlock(ofFullCopy(Blocks.BARREL)));

    public static final BlockDefinition<CabinetBlock> MAPLE_CABINET = NMLBlocks.register("maple_cabinet",
            () -> new CabinetBlock(ofFullCopy(Blocks.BARREL)));

    public static final BlockDefinition<CabinetBlock> WALNUT_CABINET = NMLBlocks.register("walnut_cabinet",
            () -> new CabinetBlock(ofFullCopy(Blocks.BARREL)));

    public static final BlockDefinition<CabinetBlock> WILLOW_CABINET = NMLBlocks.register("willow_cabinet",
            () -> new CabinetBlock(ofFullCopy(Blocks.BARREL)));

    public static final BlockDefinition<MushroomColonyBlock> FIELD_MUSHROOM_COLONY = registerNoItem("field_mushroom_colony",
            () -> new MushroomColonyBlock(NMLItems.FIELD_MUSHROOM, Block.Properties.ofFullCopy(NMLBlocks.FIELD_MUSHROOM.get())), false);

    public static final ItemDefinition<MushroomColonyItem> FIELD_MUSHROOM_COLONY_ITEM = NMLItems.register("field_mushroom_colony",
            () -> new MushroomColonyItem(FIELD_MUSHROOM_COLONY.get(), new Item.Properties()));

    public static final ItemDefinition<ConsumableItem> SEARED_VENISON = NMLItems.register("seared_venison",
            () -> new ConsumableItem(new Item.Properties().food(NMLFoods.SEARED_VENISON).craftRemainder(Items.BOWL).stacksTo(16), true));

    public static final ItemDefinition<Item> PEAR_COBBLER_SLICE = NMLItems.register("pear_cobbler_slice",
            () -> new Item(new Item.Properties().food(FoodValues.PIE_SLICE)));

    public static final BlockDefinition<PieBlock> PEAR_COBBLER = registerNoItem("pear_cobbler",
            () -> new PieBlock(ofFullCopy(Blocks.CAKE), PEAR_COBBLER_SLICE), false);

    public static final ItemDefinition<BlockItem> PEAR_COBBLER_ITEM = NMLItems.register("pear_cobbler",
            () -> new BlockItem(PEAR_COBBLER.get(), new Item.Properties()));

    public static final ItemDefinition<DrinkableItem> PEAR_JUICE = NMLItems.register("pear_juice",
            () -> new DrinkableItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).food(NMLFoods.PEAR_JUICE)));

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
