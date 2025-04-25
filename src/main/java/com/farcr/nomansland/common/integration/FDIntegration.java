package com.farcr.nomansland.common.integration;

import com.farcr.nomansland.common.definitions.BlockDefinition;
import com.farcr.nomansland.common.definitions.ItemDefinition;
import com.farcr.nomansland.common.registry.blocks.NMLBlocks;
import com.farcr.nomansland.common.registry.items.NMLFoods;
import com.farcr.nomansland.common.registry.items.NMLItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import vectorwing.farmersdelight.common.FoodValues;
import vectorwing.farmersdelight.common.block.CabinetBlock;
import vectorwing.farmersdelight.common.block.MushroomColonyBlock;
import vectorwing.farmersdelight.common.block.PieBlock;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.item.DrinkableItem;
import vectorwing.farmersdelight.common.item.MushroomColonyItem;
import vectorwing.farmersdelight.common.registry.ModBlockEntityTypes;
import vectorwing.farmersdelight.common.registry.ModEffects;

import static net.minecraft.world.level.block.state.BlockBehaviour.Properties.ofFullCopy;

@SuppressWarnings("unused")
public class FDIntegration {

    public static final BlockDefinition<CabinetBlock> PINE_CABINET = NMLBlocks.register("pine_cabinet",
            () -> new CabinetBlock(ofFullCopy(Blocks.BARREL)));

    public static final BlockDefinition<CabinetBlock> MAPLE_CABINET = NMLBlocks.register("maple_cabinet",
            () -> new CabinetBlock(ofFullCopy(Blocks.BARREL)));

    public static final BlockDefinition<CabinetBlock> WALNUT_CABINET = NMLBlocks.register("walnut_cabinet",
            () -> new CabinetBlock(ofFullCopy(Blocks.BARREL)));

    public static final BlockDefinition<CabinetBlock> WILLOW_CABINET = NMLBlocks.register("willow_cabinet",
            () -> new CabinetBlock(ofFullCopy(Blocks.BARREL)));

    public static final BlockDefinition<MushroomColonyBlock> FIELD_MUSHROOM_COLONY = NMLBlocks.registerNoItem("field_mushroom_colony",
            () -> new MushroomColonyBlock(NMLItems.FIELD_MUSHROOM, Block.Properties.ofFullCopy(NMLBlocks.FIELD_MUSHROOM.get())));

    public static final ItemDefinition<MushroomColonyItem> FIELD_MUSHROOM_COLONY_ITEM = NMLItems.register("field_mushroom_colony",
            () -> new MushroomColonyItem(FIELD_MUSHROOM_COLONY.get(), new Item.Properties()));

    public static final FoodProperties SEARED_VENISON_FOOD = new FoodProperties.Builder().nutrition(12).saturationModifier(0.9F).usingConvertsTo(Items.BOWL)
            .effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 3600, 0, true, false), 1.0F).build();

    public static final FoodProperties WITCH_STEW_FOOD = new FoodProperties.Builder().nutrition(8).saturationModifier(1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 200, 0), 0.1F)
            .effect(() -> new MobEffectInstance(ModEffects.COMFORT, 360, 0), 1).build();


    public static final FoodProperties PASTA_WITH_PESTO_FOOD = new FoodProperties.Builder().nutrition(14).saturationModifier(0.75F)
            .effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 6000, 0), 1).build();

    public static final ItemDefinition<ConsumableItem> SEARED_VENISON = NMLItems.register("seared_venison",
            () -> new ConsumableItem(new Item.Properties().food(SEARED_VENISON_FOOD).craftRemainder(Items.BOWL).stacksTo(16), true));

    public static final ItemDefinition<Item> PEAR_COBBLER_SLICE = NMLItems.register("pear_cobbler_slice",
            () -> new Item(new Item.Properties().food(FoodValues.PIE_SLICE)));

    public static final BlockDefinition<PieBlock> PEAR_COBBLER = NMLBlocks.registerNoItem("pear_cobbler",
            () -> new PieBlock(ofFullCopy(Blocks.CAKE), PEAR_COBBLER_SLICE));

    public static final ItemDefinition<BlockItem> PEAR_COBBLER_ITEM = NMLItems.register("pear_cobbler",
            () -> new BlockItem(PEAR_COBBLER.get(), new Item.Properties()));

    public static final ItemDefinition<Item> FRUIT_CAKE_SLICE = NMLItems.register("fruit_cake_slice",
            () -> new Item(new Item.Properties().food(NMLFoods.FRUIT_CAKE_SLICE)));

    public static final BlockDefinition<PieBlock> FRUIT_CAKE = NMLBlocks.registerNoItem("fruit_cake",
            () -> new PieBlock(ofFullCopy(Blocks.CAKE), FRUIT_CAKE_SLICE));

    public static final ItemDefinition<BlockItem> FRUIT_CAKE_ITEM = NMLItems.register("fruit_cake",
            () -> new BlockItem(FRUIT_CAKE.get(), new Item.Properties()));

    public static final ItemDefinition<DrinkableItem> PEAR_JUICE = NMLItems.register("pear_juice",
            () -> new DrinkableItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).food(NMLFoods.PEAR_JUICE)));

    public static final ItemDefinition<DrinkableItem> PESTO_BOTTLE = NMLItems.register("pesto_bottle",
            () -> new DrinkableItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).food(NMLFoods.PESTO_BOTTLE)));

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
