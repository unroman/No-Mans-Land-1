package com.farcr.nomansland.common.registry;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.entity.BoatEntity;
import com.farcr.nomansland.common.item.BoatItem;
import com.farcr.nomansland.common.item.*;
import com.google.common.collect.Sets;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.LinkedHashSet;
import java.util.function.Supplier;

public class NMLItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(NoMansLand.MODID);
    public static final DeferredItem<Item> NO_MANS_GLOBE = ITEMS.register("no_mans_globe",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> TRINKET = ITEMS.register("trinket",
            () -> new Item(new Item.Properties()));
    public static LinkedHashSet<DeferredItem<Item>> CREATIVE_TAB_ITEMS = Sets.newLinkedHashSet();
    //Foods
    public static final DeferredItem<Item> MASHED_POTATOES_WITH_MUSHROOMS = registerItem("mashed_potatoes_with_mushrooms",
            () -> new Item(new Item.Properties().food(NMLFoods.MASHED_POTATOES_WITH_MUSHROOMS).stacksTo(1)));
    public static final DeferredItem<Item> GRILLED_MUSHROOMS = registerItem("grilled_mushrooms",
            () -> new Item(new Item.Properties().food(NMLFoods.GRILLED_MUSHROOMS)));

    public static final DeferredItem<Item> FROG_LEG = registerItem("frog_leg",
            () -> new Item(new Item.Properties().food(NMLFoods.FROG_LEG)));
    public static final DeferredItem<Item> COOKED_FROG_LEG = registerItem("cooked_frog_leg",
            () -> new Item(new Item.Properties().food(NMLFoods.COOKED_FROG_LEG)));
    public static final DeferredItem<Item> RAW_HORSE = registerItem("raw_horse",
            () -> new Item(new Item.Properties().food(NMLFoods.RAW_HORSE)));
    public static final DeferredItem<Item> HORSE_STEAK = registerItem("horse_steak",
            () -> new Item(new Item.Properties().food(NMLFoods.HORSE_STEAK)));
    public static final DeferredItem<Item> RAW_VENISON = registerItem("raw_venison",
            () -> new Item(new Item.Properties().food(NMLFoods.RAW_VENISON)));
    public static final DeferredItem<Item> COOKED_VENISON = registerItem("cooked_venison",
            () -> new Item(new Item.Properties().food(NMLFoods.COOKED_VENISON)));

    public static final DeferredItem<Item> BILLHOOK_BASS = registerItem("billhook_bass",
            () -> new Item(new Item.Properties().food(NMLFoods.BILLHOOK_BASS)));
    public static final DeferredItem<Item> COOKED_BILLHOOK_BASS = registerItem("cooked_billhook_bass",
            () -> new Item(new Item.Properties().food(NMLFoods.COOKED_BILLHOOK_BASS)));
//    public static final DeferredItem<Item> CAVE_CARP = registerItem("cave_carp",
//            () -> new Item(new Item.Properties().food(NMLFoods.CAVE_CARP)));

    public static final DeferredItem<Item> PEAR = registerItem("pear",
            () -> new Item(new Item.Properties().food(NMLFoods.PEAR)));
    public static final DeferredItem<Item> SYRUPED_PEAR = registerItem("syruped_pear",
            () -> new MapleFoodItem(new Item.Properties().food(NMLFoods.SYRUPED_PEAR)));
    public static final DeferredItem<Item> PANCAKE = registerItem("pancake",
            () -> new MapleFoodItem(new Item.Properties().food(NMLFoods.PANCAKE)));
    public static final DeferredItem<Item> PEAR_COBBLER = registerItem("pear_cobbler",
            () -> new Item(new Item.Properties().food(NMLFoods.PEAR_COBBLER)));
    //TODO: FD compat pear juice and cobbler slice
    public static final DeferredItem<Item> HONEYED_APPLE = registerItem("honeyed_apple",
            () -> new HoneyFoodItem(new Item.Properties().food(NMLFoods.HONEYED_APPLE)));
    public static final DeferredItem<Item> WALNUTS = registerItem("walnuts",
            () -> new Item(new Item.Properties().food(NMLFoods.WALNUTS)));

    //Materials
    public static final DeferredItem<Item> MAPLE_SYRUP_BOTTLE = registerItem("maple_syrup_bottle",
            () -> new MapleSyrupBottleItem(new Item.Properties().food(NMLFoods.MAPLE_SYRUP_BOTTLE).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16)));

    public static final DeferredItem<Item> RESIN = registerItem("resin",
            () -> new FuelItem(new Item.Properties(), 1000));
    public static final DeferredItem<Item> RESIN_OIL_BOTTLE = registerItem("resin_oil_bottle",
            () -> new Item(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE)));

    public static final DeferredItem<Item> SCONCE_TORCH = registerItem("sconce_torch",
            () -> new StandingAndWallBlockItem(NMLBlocks.SCONCE_TORCH.get(), NMLBlocks.SCONCE_WALL_TORCH.get(), new Item.Properties(), Direction.DOWN));
    public static final DeferredItem<Item> SCONCE_SOUL_TORCH = registerItem("sconce_soul_torch",
            () -> new StandingAndWallBlockItem(NMLBlocks.SCONCE_SOUL_TORCH.get(), NMLBlocks.SCONCE_SOUL_WALL_TORCH.get(), new Item.Properties(), Direction.DOWN));

    public static final DeferredItem<Item> FIREBOMB = registerItem("firebomb",
            () -> new FirebombItem(new Item.Properties().stacksTo(8)));
    public static final DeferredItem<Item> EXPLOSIVE = registerItem("explosive",
            () -> new ExplosiveItem(new Item.Properties().stacksTo(8)));

    public static final DeferredItem<Item> WOODEN_SCAFFOLDING = registerItem("wooden_scaffolding",
            () -> new ScaffoldingBlockItem(NMLBlocks.WOODEN_SCAFFOLDING.get(), new Item.Properties()));

    public static final DeferredItem<Item> BILLHOOK_BASS_BUCKET = registerItem("billhook_bass_bucket",
            () -> new MobBucketItem(EntityType.PIG, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH,
                    (new Item.Properties()).stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
//    public static final DeferredItem<Item> CAVE_CARP_BUCKET = registerItem("cave_carp_bucket",
//            () -> new MobBucketItem(EntityType.PIG, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH,
//                    (new Item.Properties()).stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));

    public static final DeferredItem<Item> PINE_SIGN = registerItem("pine_sign",
            () -> new SignItem(new Item.Properties().stacksTo(16), NMLBlocks.PINE_SIGN.get(), NMLBlocks.PINE_WALL_SIGN.get()));
    public static final DeferredItem<Item> PINE_HANGING_SIGN = registerItem("pine_hanging_sign",
            () -> new HangingSignItem(NMLBlocks.PINE_HANGING_SIGN.get(), NMLBlocks.PINE_HANGING_WALL_SIGN.get(), new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> PINE_BOAT = registerItem("pine_boat",
            () -> new BoatItem(false, BoatEntity.Type.PINE, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> PINE_CHEST_BOAT = registerItem("pine_chest_boat",
            () -> new BoatItem(true, BoatEntity.Type.PINE, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> MAPLE_SIGN = registerItem("maple_sign",
            () -> new SignItem(new Item.Properties().stacksTo(16), NMLBlocks.MAPLE_SIGN.get(), NMLBlocks.MAPLE_WALL_SIGN.get()));
    public static final DeferredItem<Item> MAPLE_HANGING_SIGN = registerItem("maple_hanging_sign",
            () -> new HangingSignItem(NMLBlocks.MAPLE_HANGING_SIGN.get(), NMLBlocks.MAPLE_HANGING_WALL_SIGN.get(), new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> MAPLE_BOAT = registerItem("maple_boat",
            () -> new BoatItem(false, BoatEntity.Type.MAPLE, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> MAPLE_CHEST_BOAT = registerItem("maple_chest_boat",
            () -> new BoatItem(true, BoatEntity.Type.MAPLE, new Item.Properties().stacksTo(1)));
//    public static final DeferredItem<Item> MOOSE_SPAWN_EGG = registerItem("moose_spawn_egg",
//            () -> new SpawnEggItem(NMLEntities.MOOSE.get(), 0x8b4513, 0xa52a2a, new Item.Properties()));

    public static final DeferredItem<Item> WALNUT_SIGN = registerItem("walnut_sign",
            () -> new SignItem(new Item.Properties().stacksTo(16), NMLBlocks.WALNUT_SIGN.get(), NMLBlocks.WALNUT_WALL_SIGN.get()));
    public static final DeferredItem<Item> WALNUT_HANGING_SIGN = registerItem("walnut_hanging_sign",
            () -> new HangingSignItem(NMLBlocks.WALNUT_HANGING_SIGN.get(), NMLBlocks.WALNUT_HANGING_WALL_SIGN.get(), new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> WALNUT_BOAT = registerItem("walnut_boat",
            () -> new BoatItem(false, BoatEntity.Type.WALNUT, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> WALNUT_CHEST_BOAT = registerItem("walnut_chest_boat",
            () -> new BoatItem(true, BoatEntity.Type.WALNUT, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> WILLOW_SIGN = registerItem("willow_sign",
            () -> new SignItem(new Item.Properties().stacksTo(16), NMLBlocks.WILLOW_SIGN.get(), NMLBlocks.WILLOW_WALL_SIGN.get()));
    public static final DeferredItem<Item> WILLOW_HANGING_SIGN = registerItem("willow_hanging_sign",
            () -> new HangingSignItem(NMLBlocks.WILLOW_HANGING_SIGN.get(), NMLBlocks.WILLOW_HANGING_WALL_SIGN.get(), new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> WILLOW_BOAT = registerItem("willow_boat",
            () -> new BoatItem(false, BoatEntity.Type.WILLOW, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> WILLOW_CHEST_BOAT = registerItem("willow_chest_boat",
            () -> new BoatItem(true, BoatEntity.Type.WILLOW, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> FIELD_MUSHROOM = registerItem("field_mushroom", () -> new BlockItem(NMLBlocks.FIELD_MUSHROOM.get(), new Item.Properties()));
    public static final DeferredItem<Item> DUCKWEED = registerItem("duckweed",
            () -> new PlaceOnWaterBlockItem(NMLBlocks.DUCKWEED.get(), new Item.Properties()));

    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
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
            insertAfter(event, Items.MOSSY_COBBLESTONE_WALL, NMLBlocks.COBBLESTONE_BRICK_WALL);
            insertAfter(event, Items.MOSSY_COBBLESTONE_WALL, NMLBlocks.COBBLESTONE_BRICK_SLAB);
            insertAfter(event, Items.MOSSY_COBBLESTONE_WALL, NMLBlocks.COBBLESTONE_BRICK_STAIRS);
            insertAfter(event, Items.MOSSY_COBBLESTONE_WALL, NMLBlocks.COBBLESTONE_BRICKS);

            insertAfter(event, Items.SMOOTH_STONE_SLAB, NMLBlocks.MUNDANE_TILES);
            insertBefore(event, Items.PACKED_MUD, NMLBlocks.EARTHEN_TILES);

            insertAfter(event, Items.SPRUCE_BUTTON, NMLBlocks.PINE_BUTTON);
            insertAfter(event, Items.SPRUCE_BUTTON, NMLBlocks.PINE_PRESSURE_PLATE);
            insertAfter(event, Items.SPRUCE_BUTTON, NMLBlocks.PINE_TRAPDOOR);
            insertAfter(event, Items.SPRUCE_BUTTON, NMLBlocks.PINE_DOOR);
            insertAfter(event, Items.SPRUCE_BUTTON, NMLBlocks.PINE_FENCE_GATE);
            insertAfter(event, Items.SPRUCE_BUTTON, NMLBlocks.PINE_FENCE);
            insertAfter(event, Items.SPRUCE_BUTTON, NMLBlocks.TRIMMED_PINE_PLANKS);
            insertAfter(event, Items.SPRUCE_BUTTON, NMLBlocks.PINE_BOOKSHELF);
            insertAfter(event, Items.SPRUCE_BUTTON, NMLBlocks.PINE_SLAB);
            insertAfter(event, Items.SPRUCE_BUTTON, NMLBlocks.PINE_STAIRS);
            insertAfter(event, Items.SPRUCE_BUTTON, NMLBlocks.PINE_PLANKS);
            insertAfter(event, Items.SPRUCE_BUTTON, NMLBlocks.STRIPPED_PINE_WOOD);
            insertAfter(event, Items.SPRUCE_BUTTON, NMLBlocks.STRIPPED_PINE_LOG);
            insertAfter(event, Items.SPRUCE_BUTTON, NMLBlocks.PINE_WOOD);
            insertAfter(event, Items.SPRUCE_BUTTON, NMLBlocks.PINE_LOG);
            NMLBlocks.PINE_CABINET.ifPresent(event::accept);

            insertAfter(event, Items.DARK_OAK_BUTTON, NMLBlocks.WALNUT_BUTTON);
            insertAfter(event, Items.DARK_OAK_BUTTON, NMLBlocks.WALNUT_PRESSURE_PLATE);
            insertAfter(event, Items.DARK_OAK_BUTTON, NMLBlocks.WALNUT_TRAPDOOR);
            insertAfter(event, Items.DARK_OAK_BUTTON, NMLBlocks.WALNUT_DOOR);
            insertAfter(event, Items.DARK_OAK_BUTTON, NMLBlocks.WALNUT_FENCE_GATE);
            insertAfter(event, Items.DARK_OAK_BUTTON, NMLBlocks.WALNUT_FENCE);
            insertAfter(event, Items.DARK_OAK_BUTTON, NMLBlocks.TRIMMED_WALNUT_PLANKS);
            insertAfter(event, Items.DARK_OAK_BUTTON, NMLBlocks.WALNUT_BOOKSHELF);
            insertAfter(event, Items.DARK_OAK_BUTTON, NMLBlocks.WALNUT_SLAB);
            insertAfter(event, Items.DARK_OAK_BUTTON, NMLBlocks.WALNUT_STAIRS);
            insertAfter(event, Items.DARK_OAK_BUTTON, NMLBlocks.WALNUT_PLANKS);
            insertAfter(event, Items.DARK_OAK_BUTTON, NMLBlocks.STRIPPED_WALNUT_WOOD);
            insertAfter(event, Items.DARK_OAK_BUTTON, NMLBlocks.STRIPPED_WALNUT_LOG);
            insertAfter(event, Items.DARK_OAK_BUTTON, NMLBlocks.WALNUT_WOOD);
            insertAfter(event, Items.DARK_OAK_BUTTON, NMLBlocks.WALNUT_LOG);
            NMLBlocks.WALNUT_CABINET.ifPresent(event::accept);

            insertBefore(event, Items.DARK_OAK_LOG, NMLBlocks.MAPLE_LOG);
            insertBefore(event, Items.DARK_OAK_LOG, NMLBlocks.MAPLE_WOOD);
            insertBefore(event, Items.DARK_OAK_LOG, NMLBlocks.STRIPPED_MAPLE_LOG);
            insertBefore(event, Items.DARK_OAK_LOG, NMLBlocks.STRIPPED_MAPLE_WOOD);
            insertBefore(event, Items.DARK_OAK_LOG, NMLBlocks.MAPLE_PLANKS);
            insertBefore(event, Items.DARK_OAK_LOG, NMLBlocks.MAPLE_STAIRS);
            insertBefore(event, Items.DARK_OAK_LOG, NMLBlocks.MAPLE_SLAB);
            insertBefore(event, Items.DARK_OAK_LOG, NMLBlocks.MAPLE_BOOKSHELF);
            insertBefore(event, Items.DARK_OAK_LOG, NMLBlocks.TRIMMED_MAPLE_PLANKS);
            insertBefore(event, Items.DARK_OAK_LOG, NMLBlocks.MAPLE_FENCE);
            insertBefore(event, Items.DARK_OAK_LOG, NMLBlocks.MAPLE_FENCE_GATE);
            insertBefore(event, Items.DARK_OAK_LOG, NMLBlocks.MAPLE_DOOR);
            insertBefore(event, Items.DARK_OAK_LOG, NMLBlocks.MAPLE_TRAPDOOR);
            insertBefore(event, Items.DARK_OAK_LOG, NMLBlocks.MAPLE_PRESSURE_PLATE);
            insertBefore(event, Items.DARK_OAK_LOG, NMLBlocks.MAPLE_BUTTON);
            NMLBlocks.MAPLE_CABINET.ifPresent(event::accept);

            insertBefore(event, Items.MANGROVE_LOG, NMLBlocks.WILLOW_LOG);
            insertBefore(event, Items.MANGROVE_LOG, NMLBlocks.WILLOW_WOOD);
            insertBefore(event, Items.MANGROVE_LOG, NMLBlocks.STRIPPED_WILLOW_LOG);
            insertBefore(event, Items.MANGROVE_LOG, NMLBlocks.STRIPPED_WILLOW_WOOD);
            insertBefore(event, Items.MANGROVE_LOG, NMLBlocks.WILLOW_PLANKS);
            insertBefore(event, Items.MANGROVE_LOG, NMLBlocks.WILLOW_STAIRS);
            insertBefore(event, Items.MANGROVE_LOG, NMLBlocks.WILLOW_SLAB);
            insertBefore(event, Items.MANGROVE_LOG, NMLBlocks.WILLOW_BOOKSHELF);
            insertBefore(event, Items.MANGROVE_LOG, NMLBlocks.TRIMMED_WILLOW_PLANKS);
            insertBefore(event, Items.MANGROVE_LOG, NMLBlocks.WILLOW_FENCE);
            insertBefore(event, Items.MANGROVE_LOG, NMLBlocks.WILLOW_FENCE_GATE);
            insertBefore(event, Items.MANGROVE_LOG, NMLBlocks.WILLOW_DOOR);
            insertBefore(event, Items.MANGROVE_LOG, NMLBlocks.WILLOW_TRAPDOOR);
            insertBefore(event, Items.MANGROVE_LOG, NMLBlocks.WILLOW_PRESSURE_PLATE);
            insertBefore(event, Items.MANGROVE_LOG, NMLBlocks.WILLOW_BUTTON);
            NMLBlocks.WILLOW_CABINET.ifPresent(event::accept);

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
            insertBefore(event, Items.DEAD_BUSH, NMLBlocks.DRIED_GRASS);
            insertBefore(event, Items.CRIMSON_ROOTS, NMLBlocks.MYCELIUM_GROWTHS);
            insertBefore(event, Items.CRIMSON_ROOTS, NMLBlocks.MYCELIUM_SPROUTS);
            insertAfter(event, Items.LARGE_FERN, NMLBlocks.CATTAIL);
            insertAfter(event, Items.LILY_PAD, NMLBlocks.DUCKWEED);
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
            NMLBlocks.FIELD_MUSHROOM_COLONY.ifPresent(event::accept);
            insertAfter(event, Items.RED_MUSHROOM, NMLBlocks.SHELF_MUSHROOM);
            insertAfter(event, Items.RED_MUSHROOM, NMLBlocks.FIELD_MUSHROOM);
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
            insertAfter(event, Items.HAY_BLOCK, NMLBlocks.SALMON_BARREL);
            insertAfter(event, Items.HAY_BLOCK, NMLBlocks.COD_BARREL);
            insertAfter(event, Items.HAY_BLOCK, NMLBlocks.PEAR_CRATE);
            insertAfter(event, Items.HAY_BLOCK, NMLBlocks.APPLE_CRATE);

//            event.accept(NMLBlocks.REMAINS);

            insertAfter(event, Items.SPRUCE_LOG, NMLBlocks.PINE_LOG);
            insertAfter(event, Items.SPRUCE_LEAVES, NMLBlocks.PINE_LEAVES);
            insertAfter(event, Items.SPRUCE_SAPLING, NMLBlocks.PINE_SAPLING);

            insertBefore(event, Items.DARK_OAK_LOG, NMLBlocks.MAPLE_LOG);
            insertBefore(event, Items.DARK_OAK_LEAVES, NMLBlocks.MAPLE_LEAVES);
            insertBefore(event, Items.DARK_OAK_SAPLING, NMLBlocks.MAPLE_SAPLING);
            insertBefore(event, Items.DARK_OAK_LEAVES, NMLBlocks.RED_MAPLE_LEAVES);
            insertBefore(event, Items.DARK_OAK_SAPLING, NMLBlocks.RED_MAPLE_SAPLING);

            insertAfter(event, Items.DARK_OAK_LOG, NMLBlocks.WALNUT_LOG);
            insertAfter(event, Items.DARK_OAK_LEAVES, NMLBlocks.WALNUT_LEAVES);
            insertAfter(event, Items.DARK_OAK_SAPLING, NMLBlocks.WALNUT_SAPLING);

            insertBefore(event, Items.MANGROVE_LOG, NMLBlocks.WILLOW_LOG);
            insertBefore(event, Items.MANGROVE_LEAVES, NMLBlocks.WILLOW_LEAVES);
            insertBefore(event, Items.MANGROVE_PROPAGULE, NMLBlocks.WILLOW_SAPLING);

        }

        if (tab == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            insertAfter(event, Items.BOOKSHELF, NMLBlocks.WARPED_BOOKSHELF);
            insertAfter(event, Items.BOOKSHELF, NMLBlocks.CRIMSON_BOOKSHELF);
            insertAfter(event, Items.BOOKSHELF, NMLBlocks.CHERRY_BOOKSHELF);
            insertAfter(event, Items.BOOKSHELF, NMLBlocks.BAMBOO_BOOKSHELF);
            insertAfter(event, Items.BOOKSHELF, NMLBlocks.MANGROVE_BOOKSHELF);
            insertAfter(event, Items.BOOKSHELF, NMLBlocks.WILLOW_BOOKSHELF);
            insertAfter(event, Items.BOOKSHELF, NMLBlocks.WALNUT_BOOKSHELF);
            insertAfter(event, Items.BOOKSHELF, NMLBlocks.DARK_OAK_BOOKSHELF);
            insertAfter(event, Items.BOOKSHELF, NMLBlocks.MAPLE_BOOKSHELF);
            insertAfter(event, Items.BOOKSHELF, NMLBlocks.ACACIA_BOOKSHELF);
            insertAfter(event, Items.BOOKSHELF, NMLBlocks.JUNGLE_BOOKSHELF);
            insertAfter(event, Items.BOOKSHELF, NMLBlocks.BIRCH_BOOKSHELF);
            insertAfter(event, Items.BOOKSHELF, NMLBlocks.PINE_BOOKSHELF);
            insertAfter(event, Items.BOOKSHELF, NMLBlocks.SPRUCE_BOOKSHELF);
            insertAfter(event, Items.SPRUCE_HANGING_SIGN, NMLItems.PINE_HANGING_SIGN);
            insertAfter(event, Items.SPRUCE_HANGING_SIGN, NMLItems.PINE_SIGN);
            insertAfter(event, Items.DARK_OAK_HANGING_SIGN, NMLItems.WALNUT_HANGING_SIGN);
            insertAfter(event, Items.DARK_OAK_HANGING_SIGN, NMLItems.WALNUT_SIGN);
            insertBefore(event, Items.DARK_OAK_SIGN, NMLItems.MAPLE_SIGN);
            insertBefore(event, Items.DARK_OAK_SIGN, NMLItems.MAPLE_HANGING_SIGN);
            insertBefore(event, Items.MANGROVE_SIGN, NMLItems.WILLOW_SIGN);
            insertBefore(event, Items.MANGROVE_SIGN, NMLItems.WILLOW_HANGING_SIGN);
            insertAfter(event, Items.REDSTONE_TORCH, NMLItems.SCONCE_SOUL_TORCH);
            insertAfter(event, Items.REDSTONE_TORCH, NMLItems.SCONCE_TORCH);
            insertAfter(event, Items.CAULDRON, NMLBlocks.TAP);
            insertBefore(event, Items.SCAFFOLDING, NMLBlocks.WOODEN_SCAFFOLDING);
        }

        if (tab == CreativeModeTabs.FOOD_AND_DRINKS) {
            insertAfter(event, Items.COOKED_BEEF, NMLItems.HORSE_STEAK);
            insertAfter(event, Items.COOKED_BEEF, NMLItems.RAW_HORSE);
            insertAfter(event, Items.COOKED_MUTTON, NMLItems.COOKED_VENISON);
            insertAfter(event, Items.COOKED_MUTTON, NMLItems.RAW_VENISON);
            insertAfter(event, Items.COOKED_RABBIT, NMLItems.COOKED_FROG_LEG);
            insertAfter(event, Items.COOKED_RABBIT, NMLItems.FROG_LEG);
            insertAfter(event, Items.HONEY_BOTTLE, NMLItems.MAPLE_SYRUP_BOTTLE);
            insertAfter(event, Items.PUMPKIN_PIE, NMLItems.PEAR_COBBLER);
            insertAfter(event, Items.PUMPKIN_PIE, NMLItems.PANCAKE);
            insertAfter(event, Items.ENCHANTED_GOLDEN_APPLE, NMLItems.SYRUPED_PEAR);
            insertAfter(event, Items.ENCHANTED_GOLDEN_APPLE, NMLItems.PEAR);
            insertAfter(event, Items.APPLE, NMLItems.HONEYED_APPLE);
            insertAfter(event, Items.MELON_SLICE, NMLItems.WALNUTS);
            insertAfter(event, Items.MUSHROOM_STEW, NMLItems.MASHED_POTATOES_WITH_MUSHROOMS);
            insertAfter(event, Items.POISONOUS_POTATO, NMLItems.GRILLED_MUSHROOMS);
        }

        if (tab == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            insertAfter(event, Items.SPRUCE_CHEST_BOAT, NMLItems.PINE_CHEST_BOAT);
            insertAfter(event, Items.SPRUCE_CHEST_BOAT, NMLItems.PINE_BOAT);
            insertBefore(event, Items.DARK_OAK_BOAT, NMLItems.MAPLE_BOAT);
            insertBefore(event, Items.DARK_OAK_BOAT, NMLItems.MAPLE_CHEST_BOAT);
            insertBefore(event, Items.MANGROVE_BOAT, NMLItems.WILLOW_BOAT);
            insertBefore(event, Items.MANGROVE_BOAT, NMLItems.WILLOW_CHEST_BOAT);
            insertAfter(event, Items.DARK_OAK_CHEST_BOAT, NMLItems.WALNUT_CHEST_BOAT);
            insertAfter(event, Items.DARK_OAK_CHEST_BOAT, NMLItems.WALNUT_BOAT);
            if (!event.getFlags().contains(FeatureFlags.BUNDLE)) event.accept(Items.BUNDLE);
        }

        if (tab == CreativeModeTabs.COMBAT) {
            insertAfter(event, Items.WIND_CHARGE, NMLItems.FIREBOMB);
            insertBefore(event, Items.TNT, NMLItems.EXPLOSIVE);
            insertAfter(event, Items.EGG, NMLItems.RESIN_OIL_BOTTLE);
        }

        if (tab == CreativeModeTabs.INGREDIENTS) {
            insertAfter(event, Items.HONEYCOMB, NMLItems.RESIN);
            insertAfter(event, NMLItems.RESIN, NMLItems.RESIN_OIL_BOTTLE);
        }

        if (tab == CreativeModeTabs.REDSTONE_BLOCKS) {
            insertAfter(event, Blocks.LIGHTNING_ROD, NMLBlocks.SPIKE_TRAP);
        }

        if (tab == CreativeModeTabs.SPAWN_EGGS) {
            insertAfter(event, Blocks.SPAWNER, NMLBlocks.MONSTER_ANCHOR);
        }
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