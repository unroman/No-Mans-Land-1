package com.farcr.nomansland.common.registry.items;

import com.farcr.nomansland.NMLEnumParams;
import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.definitions.BlockDefinition;
import com.farcr.nomansland.common.definitions.ItemDefinition;
import com.farcr.nomansland.common.integration.FDIntegration;
import com.farcr.nomansland.common.integration.Mods;
import com.farcr.nomansland.common.item.*;
import com.farcr.nomansland.common.registry.blocks.NMLBlocks;
import com.farcr.nomansland.common.registry.entities.NMLEffects;
import com.farcr.nomansland.common.registry.entities.NMLEntities;
import com.google.common.collect.Sets;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.*;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class NMLItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(NoMansLand.MODID);
    public static List<ItemDefinition<?>> ITEM_DEFINITIONS = new ArrayList<>();

    public static final DeferredItem<Item> NO_MANS_GLOBE = ITEMS.register("no_mans_globe",
            () -> new Item(new Properties()));
    public static final DeferredItem<Item> TRINKET = ITEMS.register("trinket",
            () -> new Item(new Properties()));
    public static LinkedHashSet<DeferredItem<Item>> CREATIVE_TAB_ITEMS = Sets.newLinkedHashSet();
    //Foods
    public static final DeferredItem<Item> MASHED_POTATOES_WITH_MUSHROOMS = registerItem("mashed_potatoes_with_mushrooms",
            () -> new Item(new Properties().food(NMLFoods.MASHED_POTATOES_WITH_MUSHROOMS).stacksTo(1)));
    public static final DeferredItem<Item> GRILLED_MUSHROOMS = registerItem("grilled_mushrooms",
            () -> new Item(new Properties().food(NMLFoods.GRILLED_MUSHROOMS)));

    public static final DeferredItem<Item> FROG_LEG = registerItem("frog_leg",
            () -> new Item(new Properties().food(NMLFoods.FROG_LEG)));
    public static final DeferredItem<Item> COOKED_FROG_LEG = registerItem("cooked_frog_leg",
            () -> new Item(new Properties().food(NMLFoods.COOKED_FROG_LEG)));
    public static final DeferredItem<Item> RAW_HORSE = registerItem("raw_horse",
            () -> new Item(new Properties().food(NMLFoods.RAW_HORSE)));
    public static final DeferredItem<Item> HORSE_STEAK = registerItem("horse_steak",
            () -> new Item(new Properties().food(NMLFoods.HORSE_STEAK)));
    public static final DeferredItem<Item> RAW_VENISON = registerItem("raw_venison",
            () -> new Item(new Properties().food(NMLFoods.RAW_VENISON)));
    public static final DeferredItem<Item> COOKED_VENISON = registerItem("cooked_venison",
            () -> new Item(new Properties().food(NMLFoods.COOKED_VENISON)));

    public static final DeferredItem<Item> BILLHOOK_BASS = registerItem("billhook_bass",
            () -> new Item(new Properties().food(NMLFoods.BILLHOOK_BASS)));
    public static final DeferredItem<Item> COOKED_BILLHOOK_BASS = registerItem("cooked_billhook_bass",
            () -> new Item(new Properties().food(NMLFoods.COOKED_BILLHOOK_BASS)));
//    public static final DeferredItem<Item> CAVE_CARP = registerItem("cave_carp",
//            () -> new Item(new Properties().food(NMLFoods.CAVE_CARP)));

    public static final DeferredItem<Item> PEAR = registerItem("pear",
            () -> new Item(new Properties().food(NMLFoods.PEAR)));
    public static final DeferredItem<Item> SYRUPED_PEAR = registerItem("syruped_pear",
            () -> new MapleFoodItem(new Properties().food(NMLFoods.SYRUPED_PEAR)));
    public static final DeferredItem<Item> PANCAKE = registerItem("pancake",
            () -> new MapleFoodItem(new Properties().food(NMLFoods.PANCAKE)));
    public static final DeferredItem<Item> HONEYED_APPLE = registerItem("honeyed_apple",
            () -> new HoneyFoodItem(new Properties().food(NMLFoods.HONEYED_APPLE)));
    public static final DeferredItem<Item> WALNUTS = registerItem("walnuts",
            () -> new Item(new Properties().food(NMLFoods.WALNUTS)));

    //Materials
    public static final DeferredItem<Item> MAPLE_SYRUP_BOTTLE = registerItem("maple_syrup_bottle",
            () -> new MapleSyrupBottleItem(new Properties().food(NMLFoods.MAPLE_SYRUP_BOTTLE).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16)));

    public static final DeferredItem<Item> RESIN = registerItem("resin",
            () -> new FuelItem(new Properties(), 1000));

    public static final DeferredItem<Item> RESIN_OIL_BOTTLE = registerItem("resin_oil_bottle",
            () -> new ResinOilBottleItem(new Properties()
                    .stacksTo(16)
                    .craftRemainder(Items.GLASS_BOTTLE)
                    .component(DataComponents.POTION_CONTENTS, new PotionContents(Optional.empty(), Optional.empty(), List.of(new MobEffectInstance(NMLEffects.FLAMMABLE, 2400))))));

    public static final DeferredItem<Item> SCONCE_TORCH = registerItem("sconce_torch",
            () -> new StandingAndWallBlockItem(NMLBlocks.SCONCE_TORCH.get(), NMLBlocks.SCONCE_WALL_TORCH.get(), new Properties(), Direction.DOWN));
    public static final DeferredItem<Item> SCONCE_SOUL_TORCH = registerItem("sconce_soul_torch",
            () -> new StandingAndWallBlockItem(NMLBlocks.SCONCE_SOUL_TORCH.get(), NMLBlocks.SCONCE_SOUL_WALL_TORCH.get(), new Properties(), Direction.DOWN));

    public static final DeferredItem<Item> FIREBOMB = registerItem("firebomb",
            () -> new FirebombItem(new Properties().stacksTo(8)));
    public static final DeferredItem<Item> EXPLOSIVE = registerItem("explosive",
            () -> new ExplosiveItem(new Properties().stacksTo(8)));

    public static final DeferredItem<Item> WOODEN_SCAFFOLDING = registerItem("wooden_scaffolding",
            () -> new ScaffoldingBlockItem(NMLBlocks.WOODEN_SCAFFOLDING.get(), new Properties()));

    public static final DeferredItem<Item> BILLHOOK_BASS_BUCKET = registerItem("billhook_bass_bucket",
            () -> new MobBucketItem(NMLEntities.BILLHOOK_BASS.get(), Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH,
                    (new Properties()).stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));

    public static final DeferredItem<Item> BILLHOOK_BASS_SPAWN_EGG = registerItem("billhook_bass_spawn_egg",
            () -> new DeferredSpawnEggItem(NMLEntities.BILLHOOK_BASS, 6443553, 11236417, new Properties()));

    public static final DeferredItem<Item> DEER_SPAWN_EGG = registerItem("deer_spawn_egg",
            () -> new DeferredSpawnEggItem(NMLEntities.DEER, 8412743, 12828347, new Properties()));

    public static final DeferredItem<Item> GOOSE_SPAWN_EGG = registerItem("goose_spawn_egg",
            () -> new DeferredSpawnEggItem(NMLEntities.GOOSE, 11773851, 11888408, new Properties()));

    public static final DeferredItem<Item> MOOSE_SPAWN_EGG = registerItem("moose_spawn_egg",
            () -> new DeferredSpawnEggItem(NMLEntities.MOOSE, 4335898, 2497045, new Properties()));
    // egg color probably temporary - inverted deer lmao

//    public static final DeferredItem<Item> CAVE_CARP_BUCKET = registerItem("cave_carp_bucket",
//            () -> new MobBucketItem(EntityType.PIG, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH,
//                    (new Properties()).stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));

    public static final DeferredItem<Item> PINE_SIGN = registerItem("pine_sign",
            () -> new SignItem(new Properties().stacksTo(16), NMLBlocks.PINE.sign().get(), NMLBlocks.PINE.wallSign().get()));
    public static final DeferredItem<Item> PINE_HANGING_SIGN = registerItem("pine_hanging_sign",
            () -> new HangingSignItem(NMLBlocks.PINE.hangingSign().get(), NMLBlocks.PINE.hangingWallSign().get(), new Properties().stacksTo(16)));
    public static final DeferredItem<Item> PINE_BOAT = registerItem("pine_boat",
            () -> new BoatItem(false, NMLEnumParams.PINE_BOAT_TYPE.getValue(), new Properties().stacksTo(1)));
    public static final DeferredItem<Item> PINE_CHEST_BOAT = registerItem("pine_chest_boat",
            () -> new BoatItem(true, NMLEnumParams.PINE_BOAT_TYPE.getValue(), new Properties().stacksTo(1)));

    public static final DeferredItem<Item> MAPLE_SIGN = registerItem("maple_sign",
            () -> new SignItem(new Properties().stacksTo(16), NMLBlocks.MAPLE.sign().get(), NMLBlocks.MAPLE.wallSign().get()));
    public static final DeferredItem<Item> MAPLE_HANGING_SIGN = registerItem("maple_hanging_sign",
            () -> new HangingSignItem(NMLBlocks.MAPLE.hangingSign().get(), NMLBlocks.MAPLE.hangingWallSign().get(), new Properties().stacksTo(16)));
    public static final DeferredItem<Item> MAPLE_BOAT = registerItem("maple_boat",
            () -> new BoatItem(false, NMLEnumParams.MAPLE_BOAT_TYPE.getValue(), new Properties().stacksTo(1)));
    public static final DeferredItem<Item> MAPLE_CHEST_BOAT = registerItem("maple_chest_boat",
            () -> new BoatItem(true, NMLEnumParams.MAPLE_BOAT_TYPE.getValue(), new Properties().stacksTo(1)));
//    public static final DeferredItem<Item> MOOSE_SPAWN_EGG = registerItem("moose_spawn_egg",
//            () -> new SpawnEggItem(NMLEntities.MOOSE.get(), 0x8b4513, 0xa52a2a, new Properties()));

    public static final DeferredItem<Item> WALNUT_SIGN = registerItem("walnut_sign",
            () -> new SignItem(new Properties().stacksTo(16), NMLBlocks.WALNUT.sign().get(), NMLBlocks.WALNUT.wallSign().get()));
    public static final DeferredItem<Item> WALNUT_HANGING_SIGN = registerItem("walnut_hanging_sign",
            () -> new HangingSignItem(NMLBlocks.WALNUT.hangingSign().get(), NMLBlocks.WALNUT.hangingWallSign().get(), new Properties().stacksTo(16)));
    public static final DeferredItem<Item> WALNUT_BOAT = registerItem("walnut_boat",
            () -> new BoatItem(false, NMLEnumParams.WALNUT_BOAT_TYPE.getValue(), new Properties().stacksTo(1)));
    public static final DeferredItem<Item> WALNUT_CHEST_BOAT = registerItem("walnut_chest_boat",
            () -> new BoatItem(true, NMLEnumParams.WALNUT_BOAT_TYPE.getValue(), new Properties().stacksTo(1)));

    public static final DeferredItem<Item> WILLOW_SIGN = registerItem("willow_sign",
            () -> new SignItem(new Properties().stacksTo(16), NMLBlocks.WILLOW.sign().get(), NMLBlocks.WILLOW.wallSign().get()));
    public static final DeferredItem<Item> WILLOW_HANGING_SIGN = registerItem("willow_hanging_sign",
            () -> new HangingSignItem(NMLBlocks.WILLOW.hangingSign().get(), NMLBlocks.WILLOW.hangingWallSign().get(), new Properties().stacksTo(16)));
    public static final DeferredItem<Item> WILLOW_BOAT = registerItem("willow_boat",
            () -> new BoatItem(false, NMLEnumParams.WILLOW_BOAT_TYPE.getValue(), new Properties().stacksTo(1)));
    public static final DeferredItem<Item> WILLOW_CHEST_BOAT = registerItem("willow_chest_boat",
            () -> new BoatItem(true, NMLEnumParams.WILLOW_BOAT_TYPE.getValue(), new Properties().stacksTo(1)));

    public static final DeferredItem<Item> FIELD_MUSHROOM = registerItem("field_mushroom", () -> new BlockItem(NMLBlocks.FIELD_MUSHROOM.get(), new Properties()));
    public static final DeferredItem<Item> DUCKWEED = registerItem("duckweed",
            () -> new PlaceOnWaterBlockItem(NMLBlocks.DUCKWEED.get(), new Properties()));

    public static final DeferredItem<Item> WATER_MOSAIC = registerItem("water_mosaic",
            () -> new PlaceOnWaterBlockItem(NMLBlocks.WATER_MOSAIC.get(), new Properties()));

    public static void addCreative(final BuildCreativeModeTabContentsEvent event) {
        ResourceKey<CreativeModeTab> tab = event.getTabKey();

        //Note about the methods "addBefore and addAfter"
        //"addAfter" reads from the bottom up, while addBefore reads from up to bottom.
        // Might look messy, but trust me it makes sense I swear. -Farcr
        if (tab == CreativeModeTabs.BUILDING_BLOCKS) {
            insertAfter(event, Items.STONE_BRICKS, NMLBlocks.FADED_STONE_BRICKS);
            insertBefore(event, Items.STONE_BRICKS, NMLBlocks.POLISHED_STONE);
            insertBefore(event, Items.STONE_BRICKS, NMLBlocks.POLISHED_STONE_STAIRS);
            insertBefore(event, Items.STONE_BRICKS, NMLBlocks.POLISHED_STONE_SLAB);

            insertAfter(event, Items.MOSSY_COBBLESTONE_WALL, NMLBlocks.MOSSY_COBBLESTONE_BRICK_WALL);
            insertAfter(event, Items.MOSSY_COBBLESTONE_WALL, NMLBlocks.MOSSY_COBBLESTONE_BRICK_SLAB);
            insertAfter(event, Items.MOSSY_COBBLESTONE_WALL, NMLBlocks.MOSSY_COBBLESTONE_BRICK_STAIRS);
            insertAfter(event, Items.MOSSY_COBBLESTONE_WALL, NMLBlocks.MOSSY_COBBLESTONE_BRICKS);
            insertAfter(event, Items.MOSSY_COBBLESTONE_WALL, NMLBlocks.CRACKED_COBBLESTONE_BRICKS);
            insertAfter(event, Items.MOSSY_COBBLESTONE_WALL, NMLBlocks.COBBLESTONE_BRICK_WALL);
            insertAfter(event, Items.MOSSY_COBBLESTONE_WALL, NMLBlocks.COBBLESTONE_BRICK_SLAB);
            insertAfter(event, Items.MOSSY_COBBLESTONE_WALL, NMLBlocks.COBBLESTONE_BRICK_STAIRS);
            insertAfter(event, Items.MOSSY_COBBLESTONE_WALL, NMLBlocks.COBBLESTONE_BRICKS);

            insertAfter(event, Items.SMOOTH_STONE_SLAB, NMLBlocks.MUNDANE_TILE_SLAB);
            insertAfter(event, Items.SMOOTH_STONE_SLAB, NMLBlocks.MUNDANE_TILE_STAIRS);
            insertAfter(event, Items.SMOOTH_STONE_SLAB, NMLBlocks.MUNDANE_TILES);
            insertBefore(event, Items.PACKED_MUD, NMLBlocks.EARTHEN_TILES);
            insertBefore(event, Items.PACKED_MUD, NMLBlocks.EARTHEN_TILE_STAIRS);
            insertBefore(event, Items.PACKED_MUD, NMLBlocks.EARTHEN_TILE_SLAB);
            insertBefore(event, Items.PACKED_MUD, NMLBlocks.DROSS_TILES);
            insertBefore(event, Items.PACKED_MUD, NMLBlocks.DROSS_TILE_STAIRS);
            insertBefore(event, Items.PACKED_MUD, NMLBlocks.DROSS_TILE_SLAB);
            insertBefore(event, Items.PACKED_MUD, NMLBlocks.SILT_BRICKS);
            insertBefore(event, Items.PACKED_MUD, NMLBlocks.SILT_BRICK_STAIRS);
            insertBefore(event, Items.PACKED_MUD, NMLBlocks.SILT_BRICK_SLAB);

            insertAfter(event, Items.BRICK_WALL, NMLBlocks.MOSSY_COARSE_BRICK_WALL);
            insertAfter(event, Items.BRICK_WALL, NMLBlocks.MOSSY_COARSE_BRICK_SLAB);
            insertAfter(event, Items.BRICK_WALL, NMLBlocks.MOSSY_COARSE_BRICK_STAIRS);
            insertAfter(event, Items.BRICK_WALL, NMLBlocks.MOSSY_COARSE_BRICKS);
            insertAfter(event, Items.BRICK_WALL, NMLBlocks.COARSE_BRICK_WALL);
            insertAfter(event, Items.BRICK_WALL, NMLBlocks.COARSE_BRICK_SLAB);
            insertAfter(event, Items.BRICK_WALL, NMLBlocks.COARSE_BRICK_STAIRS);
            insertAfter(event, Items.BRICK_WALL, NMLBlocks.COARSE_BRICKS);

            insertAfter(event, Items.SPRUCE_BUTTON, NMLBlocks.PINE.button());
            insertAfter(event, Items.SPRUCE_BUTTON, NMLBlocks.PINE.pressurePlate());
            insertAfter(event, Items.SPRUCE_BUTTON, NMLBlocks.PINE.trapdoor());
            insertAfter(event, Items.SPRUCE_BUTTON, NMLBlocks.PINE.door());
            insertAfter(event, Items.SPRUCE_BUTTON, NMLBlocks.PINE.fenceGate());
            insertAfter(event, Items.SPRUCE_BUTTON, NMLBlocks.PINE.fence());
            if (Mods.FARMERSDELIGHT.isLoaded()) insertAfter(event, Items.SPRUCE_BUTTON, FDIntegration.PINE_CABINET);
            insertAfter(event, Items.SPRUCE_BUTTON, NMLBlocks.PINE.trimmedPlanks());
            insertAfter(event, Items.SPRUCE_BUTTON, NMLBlocks.PINE.bookshelf());
            insertAfter(event, Items.SPRUCE_BUTTON, NMLBlocks.PINE.slab());
            insertAfter(event, Items.SPRUCE_BUTTON, NMLBlocks.PINE.stairs());
            insertAfter(event, Items.SPRUCE_BUTTON, NMLBlocks.PINE.planks());
            insertAfter(event, Items.SPRUCE_BUTTON, NMLBlocks.PINE.strippedWood());
            insertAfter(event, Items.SPRUCE_BUTTON, NMLBlocks.PINE.strippedLog());
            insertAfter(event, Items.SPRUCE_BUTTON, NMLBlocks.PINE.wood());
            insertAfter(event, Items.SPRUCE_BUTTON, NMLBlocks.PINE.log());

            insertAfter(event, Items.DARK_OAK_BUTTON, NMLBlocks.WALNUT.button());
            insertAfter(event, Items.DARK_OAK_BUTTON, NMLBlocks.WALNUT.pressurePlate());
            insertAfter(event, Items.DARK_OAK_BUTTON, NMLBlocks.WALNUT.trapdoor());
            insertAfter(event, Items.DARK_OAK_BUTTON, NMLBlocks.WALNUT.door());
            insertAfter(event, Items.DARK_OAK_BUTTON, NMLBlocks.WALNUT.fenceGate());
            insertAfter(event, Items.DARK_OAK_BUTTON, NMLBlocks.WALNUT.fence());
            if (Mods.FARMERSDELIGHT.isLoaded()) insertAfter(event, Items.DARK_OAK_BUTTON, FDIntegration.WALNUT_CABINET);
            insertAfter(event, Items.DARK_OAK_BUTTON, NMLBlocks.WALNUT.trimmedPlanks());
            insertAfter(event, Items.DARK_OAK_BUTTON, NMLBlocks.WALNUT.bookshelf());
            insertAfter(event, Items.DARK_OAK_BUTTON, NMLBlocks.WALNUT.slab());
            insertAfter(event, Items.DARK_OAK_BUTTON, NMLBlocks.WALNUT.stairs());
            insertAfter(event, Items.DARK_OAK_BUTTON, NMLBlocks.WALNUT.planks());
            insertAfter(event, Items.DARK_OAK_BUTTON, NMLBlocks.WALNUT.strippedWood());
            insertAfter(event, Items.DARK_OAK_BUTTON, NMLBlocks.WALNUT.strippedLog());
            insertAfter(event, Items.DARK_OAK_BUTTON, NMLBlocks.WALNUT.wood());
            insertAfter(event, Items.DARK_OAK_BUTTON, NMLBlocks.WALNUT.log());

            insertBefore(event, Items.DARK_OAK_LOG, NMLBlocks.MAPLE.log());
            insertBefore(event, Items.DARK_OAK_LOG, NMLBlocks.MAPLE.wood());
            insertBefore(event, Items.DARK_OAK_LOG, NMLBlocks.MAPLE.strippedLog());
            insertBefore(event, Items.DARK_OAK_LOG, NMLBlocks.MAPLE.strippedWood());
            insertBefore(event, Items.DARK_OAK_LOG, NMLBlocks.MAPLE.planks());
            insertBefore(event, Items.DARK_OAK_LOG, NMLBlocks.MAPLE.stairs());
            insertBefore(event, Items.DARK_OAK_LOG, NMLBlocks.MAPLE.slab());
            insertBefore(event, Items.DARK_OAK_LOG, NMLBlocks.MAPLE.bookshelf());
            insertBefore(event, Items.DARK_OAK_LOG, NMLBlocks.MAPLE.trimmedPlanks());
            if (Mods.FARMERSDELIGHT.isLoaded()) insertBefore(event, Items.DARK_OAK_LOG, FDIntegration.MAPLE_CABINET);
            insertBefore(event, Items.DARK_OAK_LOG, NMLBlocks.MAPLE.fence());
            insertBefore(event, Items.DARK_OAK_LOG, NMLBlocks.MAPLE.fenceGate());
            insertBefore(event, Items.DARK_OAK_LOG, NMLBlocks.MAPLE.door());
            insertBefore(event, Items.DARK_OAK_LOG, NMLBlocks.MAPLE.trapdoor());
            insertBefore(event, Items.DARK_OAK_LOG, NMLBlocks.MAPLE.pressurePlate());
            insertBefore(event, Items.DARK_OAK_LOG, NMLBlocks.MAPLE.button());

            insertBefore(event, Items.MANGROVE_LOG, NMLBlocks.WILLOW.log());
            insertBefore(event, Items.MANGROVE_LOG, NMLBlocks.WILLOW.wood());
            insertBefore(event, Items.MANGROVE_LOG, NMLBlocks.WILLOW.strippedLog());
            insertBefore(event, Items.MANGROVE_LOG, NMLBlocks.WILLOW.strippedWood());
            insertBefore(event, Items.MANGROVE_LOG, NMLBlocks.WILLOW.planks());
            insertBefore(event, Items.MANGROVE_LOG, NMLBlocks.WILLOW.stairs());
            insertBefore(event, Items.MANGROVE_LOG, NMLBlocks.WILLOW.slab());
            insertBefore(event, Items.MANGROVE_LOG, NMLBlocks.WILLOW.bookshelf());
            insertBefore(event, Items.MANGROVE_LOG, NMLBlocks.WILLOW.trimmedPlanks());
            if (Mods.FARMERSDELIGHT.isLoaded()) insertBefore(event, Items.MANGROVE_LOG, FDIntegration.WILLOW_CABINET);
            insertBefore(event, Items.MANGROVE_LOG, NMLBlocks.WILLOW.fence());
            insertBefore(event, Items.MANGROVE_LOG, NMLBlocks.WILLOW.fenceGate());
            insertBefore(event, Items.MANGROVE_LOG, NMLBlocks.WILLOW.door());
            insertBefore(event, Items.MANGROVE_LOG, NMLBlocks.WILLOW.trapdoor());
            insertBefore(event, Items.MANGROVE_LOG, NMLBlocks.WILLOW.pressurePlate());
            insertBefore(event, Items.MANGROVE_LOG, NMLBlocks.WILLOW.button());

            insertAfter(event, Items.OAK_SLAB, NMLBlocks.TRIMMED_OAK_PLANKS);
            insertAfter(event, Items.OAK_SLAB, Items.BOOKSHELF.getDefaultInstance().getItemHolder());
            insertAfter(event, Items.SPRUCE_SLAB, NMLBlocks.TRIMMED_SPRUCE_PLANKS);
            insertAfter(event, Items.SPRUCE_SLAB, NMLBlocks.SPRUCE_BOOKSHELF);
            insertAfter(event, Items.BIRCH_SLAB, NMLBlocks.TRIMMED_BIRCH_PLANKS);
            insertAfter(event, Items.BIRCH_SLAB, NMLBlocks.BIRCH_BOOKSHELF);
            insertAfter(event, Items.JUNGLE_SLAB, NMLBlocks.TRIMMED_JUNGLE_PLANKS);
            insertAfter(event, Items.JUNGLE_SLAB, NMLBlocks.JUNGLE_BOOKSHELF);
            insertAfter(event, Items.ACACIA_SLAB, NMLBlocks.TRIMMED_ACACIA_PLANKS);
            insertAfter(event, Items.ACACIA_SLAB, NMLBlocks.ACACIA_BOOKSHELF);
            insertAfter(event, Items.DARK_OAK_SLAB, NMLBlocks.TRIMMED_DARK_OAK_PLANKS);
            insertAfter(event, Items.DARK_OAK_SLAB, NMLBlocks.DARK_OAK_BOOKSHELF);
            insertAfter(event, Items.CHERRY_SLAB, NMLBlocks.TRIMMED_CHERRY_PLANKS);
            insertAfter(event, Items.CHERRY_SLAB, NMLBlocks.CHERRY_BOOKSHELF);
            insertAfter(event, Items.MANGROVE_SLAB, NMLBlocks.TRIMMED_MANGROVE_PLANKS);
            insertAfter(event, Items.MANGROVE_SLAB, NMLBlocks.MANGROVE_BOOKSHELF);
            insertAfter(event, Items.CRIMSON_SLAB, NMLBlocks.TRIMMED_CRIMSON_PLANKS);
            insertAfter(event, Items.CRIMSON_SLAB, NMLBlocks.CRIMSON_BOOKSHELF);
            insertAfter(event, Items.WARPED_SLAB, NMLBlocks.TRIMMED_WARPED_PLANKS);
            insertAfter(event, Items.WARPED_SLAB, NMLBlocks.WARPED_BOOKSHELF);
            insertAfter(event, Items.BAMBOO_MOSAIC_SLAB, NMLBlocks.TRIMMED_BAMBOO_PLANKS);
            insertAfter(event, Items.BAMBOO_MOSAIC_SLAB, NMLBlocks.BAMBOO_BOOKSHELF);

            insertBefore(event, Items.AMETHYST_BLOCK, NMLBlocks.QUARTZITE);
        }

        if (tab == CreativeModeTabs.COLORED_BLOCKS) {
        }

        if (tab == CreativeModeTabs.NATURAL_BLOCKS) {
            insertAfter(event, Items.SHORT_GRASS, NMLBlocks.FROSTED_GRASS);
            insertAfter(event, Items.SHORT_GRASS, NMLBlocks.OAT_GRASS);
            insertAfter(event, Items.SHORT_GRASS, NMLBlocks.GRASS_SPROUTS);
            insertAfter(event, Items.FERN, NMLBlocks.FIDDLEHEAD);
            insertBefore(event, Items.DEAD_BUSH, NMLBlocks.SHORT_BEACHGRASS);
            insertBefore(event, Items.DEAD_BUSH, NMLBlocks.TALL_BEACHGRASS);
            insertBefore(event, Items.DEAD_BUSH, NMLBlocks.CAVE_WEEDS);
            insertBefore(event, Items.DEAD_BUSH, NMLBlocks.DRIED_GRASS);
            insertBefore(event, Items.CRIMSON_ROOTS, NMLBlocks.MYCELIUM_GROWTHS);
            insertBefore(event, Items.CRIMSON_ROOTS, NMLBlocks.MYCELIUM_SPROUTS);
            insertAfter(event, Items.LARGE_FERN, NMLBlocks.CATTAIL);
            insertAfter(event, Items.LARGE_FERN, NMLBlocks.REEDS);
            insertAfter(event, Items.LILY_PAD, NMLBlocks.DUCKWEED);
            insertAfter(event, Items.LILY_PAD, NMLBlocks.WATER_MOSAIC);
            insertAfter(event, Items.HANGING_ROOTS, NMLBlocks.BEARD_MOSS);
            insertAfter(event, Items.PINK_PETALS, NMLBlocks.CLOVER_PATCH);
            insertAfter(event, Items.PINK_PETALS, NMLBlocks.RED_FLOWERBED);
            insertAfter(event, Items.PINK_PETALS, NMLBlocks.YELLOW_FLOWERBED);
            insertAfter(event, Items.PINK_PETALS, NMLBlocks.BLUE_FLOWERBED);
            insertAfter(event, Items.PINK_PETALS, NMLBlocks.VIOLET_FLOWERBED);
            insertAfter(event, Items.PINK_PETALS, NMLBlocks.WHITE_FLOWERBED);
            insertAfter(event, Items.LILY_OF_THE_VALLEY, NMLBlocks.RED_LUPINE);
            insertAfter(event, Items.LILY_OF_THE_VALLEY, NMLBlocks.BLUE_LUPINE);
            insertAfter(event, Items.LILY_OF_THE_VALLEY, NMLBlocks.PINK_LUPINE);
            insertAfter(event, Items.LILY_OF_THE_VALLEY, NMLBlocks.YELLOW_LUPINE);
            insertAfter(event, Items.LILY_OF_THE_VALLEY, NMLBlocks.ACONITE);
            insertAfter(event, Items.LILY_OF_THE_VALLEY, NMLBlocks.WILD_MINT);
            insertAfter(event, Items.LILY_OF_THE_VALLEY, NMLBlocks.AUTUMN_CROCUS);
            insertAfter(event, Items.LILY_OF_THE_VALLEY, NMLBlocks.THISTLE);
            insertAfter(event, Items.SPORE_BLOSSOM, NMLBlocks.RAFFLESIA);
            insertAfter(event, Items.DEAD_BUSH, NMLBlocks.BARREL_CACTUS);
            insertAfter(event, Items.DEAD_BUSH, NMLBlocks.SUCCULENT);
            insertAfter(event, Items.DEAD_BUSH, NMLBlocks.PICKLEWEED);
            insertAfter(event, Items.WARPED_FUNGUS, NMLBlocks.PEBBLES);
            insertAfter(event, Items.WARPED_FUNGUS, NMLBlocks.SEASHELLS);
            insertAfter(event, Items.BIRCH_LEAVES, NMLBlocks.YELLOW_BIRCH_LEAVES);
            insertAfter(event, Items.BIRCH_SAPLING, NMLBlocks.YELLOW_BIRCH_SAPLING);
            insertAfter(event, Items.OAK_LEAVES, NMLBlocks.AUTUMNAL_OAK_LEAVES);
            insertAfter(event, Items.OAK_SAPLING, NMLBlocks.AUTUMNAL_OAK_SAPLING);
            insertAfter(event, Items.CHERRY_LEAVES, NMLBlocks.PALE_CHERRY_LEAVES);
            insertAfter(event, Items.CHERRY_SAPLING, NMLBlocks.PALE_CHERRY_SAPLING);
            insertBefore(event, Items.GRAVEL, NMLBlocks.SILT);
            insertBefore(event, Items.GRAVEL, NMLBlocks.COARSE_SILT);
            insertBefore(event, Items.GRAVEL, NMLBlocks.SILT_PATH);
            insertAfter(event, Items.RED_MUSHROOM, NMLBlocks.SHELF_MUSHROOM);
            insertAfter(event, Items.RED_MUSHROOM, NMLBlocks.FIELD_MUSHROOM);
            if (Mods.FARMERSDELIGHT.isLoaded()) insertAfter(event, Items.RED_MUSHROOM, FDIntegration.FIELD_MUSHROOM_COLONY);
            insertAfter(event, Items.RED_MUSHROOM_BLOCK, NMLBlocks.SHELF_MUSHROOM_BLOCK);
            insertAfter(event, Items.RED_MUSHROOM_BLOCK, NMLBlocks.FIELD_MUSHROOM_BLOCK);
            insertAfter(event, Items.SPRUCE_LEAVES, NMLBlocks.FROSTED_LEAVES);
            insertAfter(event, Items.SAND, NMLBlocks.SAND_PATH);
            insertAfter(event, Items.RED_SAND, NMLBlocks.RED_SAND_PATH);
            insertAfter(event, Items.SNOW_BLOCK, NMLBlocks.SNOW_PATH);
            insertAfter(event, Items.GRAVEL, NMLBlocks.GRAVEL_PATH);
            insertAfter(event, Items.SNOW, NMLBlocks.SNOWY_GRASS_PATH);
            insertAfter(event, Items.MYCELIUM, NMLBlocks.MYCELIUM_PATH);
            insertAfter(event, Items.PODZOL, NMLBlocks.PODZOL_PATH);
            insertBefore(event, Items.DIRT, NMLBlocks.DIRT_PATH);
            insertAfter(event, Items.AMETHYST_CLUSTER, NMLBlocks.QUARTZITE_CLUSTER);
            insertAfter(event, Items.AMETHYST_CLUSTER, NMLBlocks.LARGE_QUARTZITE_BUD);
            insertAfter(event, Items.AMETHYST_CLUSTER, NMLBlocks.MEDIUM_QUARTZITE_BUD);
            insertAfter(event, Items.AMETHYST_CLUSTER, NMLBlocks.SMALL_QUARTZITE_BUD);
            insertAfter(event, Items.AMETHYST_CLUSTER, NMLBlocks.BUDDING_QUARTZITE);
            insertAfter(event, Items.AMETHYST_CLUSTER, NMLBlocks.QUARTZITE);

            insertAfter(event, Items.HAY_BLOCK, NMLBlocks.TROPICAL_FISH_BARREL);
            insertAfter(event, Items.HAY_BLOCK, NMLBlocks.PUFFERFISH_BARREL);
            insertAfter(event, Items.HAY_BLOCK, NMLBlocks.BILLHOOK_BASS_BARREL);
            insertAfter(event, Items.HAY_BLOCK, NMLBlocks.SALMON_BARREL);
            insertAfter(event, Items.HAY_BLOCK, NMLBlocks.COD_BARREL);
            insertAfter(event, Items.HAY_BLOCK, NMLBlocks.PEAR_CRATE);
            insertAfter(event, Items.HAY_BLOCK, NMLBlocks.APPLE_CRATE);

//            event.accept(NMLBlocks.REMAINS);

            insertAfter(event, Items.SPRUCE_LOG, NMLBlocks.PINE.log());
            insertAfter(event, Items.SPRUCE_LEAVES, NMLBlocks.PINE_LEAVES);
            insertAfter(event, Items.SPRUCE_SAPLING, NMLBlocks.PINE_SAPLING);

            insertBefore(event, Items.DARK_OAK_LOG, NMLBlocks.MAPLE.log());
            insertBefore(event, Items.DARK_OAK_LEAVES, NMLBlocks.MAPLE_LEAVES);
            insertBefore(event, Items.DARK_OAK_SAPLING, NMLBlocks.MAPLE_SAPLING);
            insertBefore(event, Items.DARK_OAK_LEAVES, NMLBlocks.RED_MAPLE_LEAVES);
            insertBefore(event, Items.DARK_OAK_SAPLING, NMLBlocks.RED_MAPLE_SAPLING);

            insertAfter(event, Items.DARK_OAK_LOG, NMLBlocks.WALNUT.log());
            insertAfter(event, Items.DARK_OAK_LEAVES, NMLBlocks.WALNUT_LEAVES);
            insertAfter(event, Items.DARK_OAK_SAPLING, NMLBlocks.WALNUT_SAPLING);

            insertBefore(event, Items.MANGROVE_LOG, NMLBlocks.WILLOW.log());
            insertBefore(event, Items.MANGROVE_LEAVES, NMLBlocks.WILLOW_LEAVES);
            insertBefore(event, Items.MANGROVE_PROPAGULE, NMLBlocks.WILLOW_SAPLING);

        }

        if (tab == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            insertAfter(event, Items.BOOKSHELF, NMLBlocks.WARPED_BOOKSHELF);
            insertAfter(event, Items.BOOKSHELF, NMLBlocks.CRIMSON_BOOKSHELF);
            insertAfter(event, Items.BOOKSHELF, NMLBlocks.CHERRY_BOOKSHELF);
            insertAfter(event, Items.BOOKSHELF, NMLBlocks.BAMBOO_BOOKSHELF);
            insertAfter(event, Items.BOOKSHELF, NMLBlocks.MANGROVE_BOOKSHELF);
            insertAfter(event, Items.BOOKSHELF, NMLBlocks.WILLOW.bookshelf());
            insertAfter(event, Items.BOOKSHELF, NMLBlocks.WALNUT.bookshelf());
            insertAfter(event, Items.BOOKSHELF, NMLBlocks.DARK_OAK_BOOKSHELF);
            insertAfter(event, Items.BOOKSHELF, NMLBlocks.MAPLE.bookshelf());
            insertAfter(event, Items.BOOKSHELF, NMLBlocks.ACACIA_BOOKSHELF);
            insertAfter(event, Items.BOOKSHELF, NMLBlocks.JUNGLE_BOOKSHELF);
            insertAfter(event, Items.BOOKSHELF, NMLBlocks.BIRCH_BOOKSHELF);
            insertAfter(event, Items.BOOKSHELF, NMLBlocks.PINE.bookshelf());
            insertAfter(event, Items.BOOKSHELF, NMLBlocks.SPRUCE_BOOKSHELF);
            insertAfter(event, Items.SPRUCE_HANGING_SIGN, PINE_HANGING_SIGN);
            insertAfter(event, Items.SPRUCE_HANGING_SIGN, PINE_SIGN);
            insertAfter(event, Items.DARK_OAK_HANGING_SIGN, WALNUT_HANGING_SIGN);
            insertAfter(event, Items.DARK_OAK_HANGING_SIGN, WALNUT_SIGN);
            insertBefore(event, Items.DARK_OAK_SIGN, MAPLE_SIGN);
            insertBefore(event, Items.DARK_OAK_SIGN, MAPLE_HANGING_SIGN);
            insertBefore(event, Items.MANGROVE_SIGN, WILLOW_SIGN);
            insertBefore(event, Items.MANGROVE_SIGN, WILLOW_HANGING_SIGN);
            insertAfter(event, Items.REDSTONE_TORCH, SCONCE_SOUL_TORCH);
            insertAfter(event, Items.REDSTONE_TORCH, SCONCE_TORCH);
            insertAfter(event, Items.CAULDRON, NMLBlocks.TAP);
            insertBefore(event, Items.SCAFFOLDING, WOODEN_SCAFFOLDING);
        }

        if (tab == CreativeModeTabs.FOOD_AND_DRINKS) {
            insertAfter(event, Items.COOKED_BEEF, HORSE_STEAK);
            insertAfter(event, Items.COOKED_BEEF, RAW_HORSE);
            insertAfter(event, Items.COOKED_MUTTON, COOKED_VENISON);
            insertAfter(event, Items.COOKED_MUTTON, RAW_VENISON);
            insertAfter(event, Items.COOKED_RABBIT, COOKED_FROG_LEG);
            insertAfter(event, Items.COOKED_RABBIT, FROG_LEG);
            insertAfter(event, Items.HONEY_BOTTLE, MAPLE_SYRUP_BOTTLE);
            if (Mods.FARMERSDELIGHT.isLoaded()) {
                insertAfter(event, Items.HONEY_BOTTLE, FDIntegration.PEAR_JUICE);
                insertAfter(event, Items.PUMPKIN_PIE, FDIntegration.PEAR_COBBLER_ITEM);
                insertAfter(event, Items.PUMPKIN_PIE, FDIntegration.PEAR_COBBLER_SLICE);
            }
            insertAfter(event, Items.PUMPKIN_PIE, PANCAKE);
            insertAfter(event, Items.ENCHANTED_GOLDEN_APPLE, SYRUPED_PEAR);
            insertAfter(event, Items.ENCHANTED_GOLDEN_APPLE, PEAR);
            insertAfter(event, Items.APPLE, HONEYED_APPLE);
            insertAfter(event, Items.MELON_SLICE, WALNUTS);
            insertAfter(event, Items.MUSHROOM_STEW, MASHED_POTATOES_WITH_MUSHROOMS);
            insertAfter(event, Items.POISONOUS_POTATO, GRILLED_MUSHROOMS);
            insertAfter(event, Items.COOKED_SALMON, COOKED_BILLHOOK_BASS);
            insertAfter(event, Items.COOKED_SALMON, BILLHOOK_BASS);
//            insertAfter(event, Items.TROPICAL_FISH, CAVE_CARP);
        }

        if (tab == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            insertAfter(event, Items.SPRUCE_CHEST_BOAT, PINE_CHEST_BOAT);
            insertAfter(event, Items.SPRUCE_CHEST_BOAT, PINE_BOAT);
            insertBefore(event, Items.DARK_OAK_BOAT, MAPLE_BOAT);
            insertBefore(event, Items.DARK_OAK_BOAT, MAPLE_CHEST_BOAT);
            insertBefore(event, Items.MANGROVE_BOAT, WILLOW_BOAT);
            insertBefore(event, Items.MANGROVE_BOAT, WILLOW_CHEST_BOAT);
            insertAfter(event, Items.DARK_OAK_CHEST_BOAT, WALNUT_CHEST_BOAT);
            insertAfter(event, Items.DARK_OAK_CHEST_BOAT, WALNUT_BOAT);
            insertAfter(event, Items.SALMON_BUCKET, BILLHOOK_BASS_BUCKET);
            insertAfter(event, Items.SALMON_BUCKET, NMLBlocks.WARDING_EFFIGY);
//            insertAfter(event, Items.TROPICAL_FISH_BUCKET, CAVE_CARP_BUCKET);
            if (!event.getFlags().contains(FeatureFlags.BUNDLE)) event.insertBefore(Items.FLINT_AND_STEEL.getDefaultInstance(), Items.BUNDLE.getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }

        if (tab == CreativeModeTabs.COMBAT) {
            insertAfter(event, Items.WIND_CHARGE, FIREBOMB);
            insertBefore(event, Items.TNT, EXPLOSIVE);
            insertAfter(event, Items.EGG, RESIN_OIL_BOTTLE);
        }

        if (tab == CreativeModeTabs.INGREDIENTS) {
            insertAfter(event, Items.HONEYCOMB, RESIN);
            insertAfter(event, RESIN, RESIN_OIL_BOTTLE);
        }

        if (tab == CreativeModeTabs.REDSTONE_BLOCKS) {
            insertAfter(event, Blocks.LIGHTNING_ROD, NMLBlocks.SPIKE_TRAP);
        }

        if (tab == CreativeModeTabs.SPAWN_EGGS) {
            insertAfter(event, Blocks.SPAWNER, NMLBlocks.MONSTER_ANCHOR);
            insertAfter(event, Items.CREEPER_SPAWN_EGG, MOOSE_SPAWN_EGG);
            insertAfter(event, Items.CREEPER_SPAWN_EGG, DEER_SPAWN_EGG);
            insertAfter(event, Items.BEE_SPAWN_EGG, BILLHOOK_BASS_SPAWN_EGG);
            insertAfter(event, Items.CHICKEN_SPAWN_EGG, GOOSE_SPAWN_EGG);
        }

        if (Mods.CREATE.isLoaded())
            event.remove(Mods.CREATE.getItem("honeyed_apple").getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
    }

    private static void insertBefore(BuildCreativeModeTabContentsEvent event, Object existingEntry, Holder<?> newEntry) {
        ItemStack existingStack = null;
        ItemStack newStack = null;
        if (existingEntry instanceof Item item) existingStack = item.getDefaultInstance();
        if (existingEntry instanceof Block block) existingStack = block.asItem().getDefaultInstance();
        if (newEntry.value() instanceof Item item) newStack = item.getDefaultInstance();
        if (newEntry.value() instanceof Block block) newStack = block.asItem().getDefaultInstance();
        if (existingStack != null && newStack != null) event.insertBefore(existingStack, newStack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
    }

    private static void insertAfter(BuildCreativeModeTabContentsEvent event, Object existingEntry, Holder<?> newEntry) {
        ItemStack existingStack = null;
        ItemStack newStack = null;
        if (existingEntry instanceof Item item) existingStack = item.getDefaultInstance();
        if (existingEntry instanceof Block block) existingStack = block.asItem().getDefaultInstance();
        if (newEntry.value() instanceof Item item) newStack = item.getDefaultInstance();
        if (newEntry.value() instanceof Block block) newStack = block.asItem().getDefaultInstance();
        if (existingStack != null && newStack != null) event.insertAfter(existingStack, newStack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Item> DeferredItem<T> registerItem(String name, Supplier<? extends Item> item) {
        DeferredItem<Item> toReturn = ITEMS.register(name, item);
        CREATIVE_TAB_ITEMS.add(toReturn);
        return (DeferredItem<T>) toReturn;
    }
}