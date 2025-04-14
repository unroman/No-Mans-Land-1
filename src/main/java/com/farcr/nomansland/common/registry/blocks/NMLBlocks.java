package com.farcr.nomansland.common.registry.blocks;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.block.*;
import com.farcr.nomansland.common.block.cauldrons.*;
import com.farcr.nomansland.common.block.fruit_trees.FruitBlock;
import com.farcr.nomansland.common.block.fruit_trees.FruitLeavesBlock;
import com.farcr.nomansland.common.block.fruit_trees.FruitType;
import com.farcr.nomansland.common.block.tap.TapBlock;
import com.farcr.nomansland.common.block.torches.*;
import com.farcr.nomansland.common.definitions.BlockDefinition;
import com.farcr.nomansland.common.registry.items.NMLItems;
import com.farcr.nomansland.common.registry.worldgen.NMLFeatures;
import com.farcr.nomansland.common.registry.worldgen.NMLTreeGrowers;
import com.farcr.nomansland.datagen.loot.*;
import com.google.common.collect.Sets;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.Supplier;

import static net.minecraft.world.level.block.state.BlockBehaviour.Properties.of;
import static net.minecraft.world.level.block.state.BlockBehaviour.Properties.ofFullCopy;

public class NMLBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(NoMansLand.MODID);
    public static LinkedHashSet<DeferredHolder<Item, BlockItem>> CREATIVE_TAB_ITEMS = Sets.newLinkedHashSet();
    public static List<BlockDefinition<?>> BLOCK_DEFINITIONS = new ArrayList<>();

    public static final BlockDefinition<VineBlock> CUT_VINE = registerBlockNoItem("cut_vine",
            () -> new VineBlock(of().mapColor(MapColor.PLANT).replaceable().noCollission().strength(0.2F).sound(SoundType.VINE).ignitedByLava().pushReaction(PushReaction.DESTROY)), new BlockProperties(new OtherShearsBlockLootType(() -> Blocks.VINE), false));
    public static final BlockDefinition<CutSugarCaneBlock> CUT_SUGAR_CANE = registerBlockNoItem("cut_sugar_cane",
            () -> new CutSugarCaneBlock(of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)), new BlockProperties(new OtherBlockLootType(() -> Blocks.SUGAR_CANE), false));

    //Decorations
    public static final BlockDefinition<SconceTorchBlock> SCONCE_TORCH = registerBlockNoItem("sconce_torch",
            () -> new SconceTorchBlock(ParticleTypes.FLAME, ofFullCopy(Blocks.TORCH).sound(SoundType.LANTERN)), new BlockProperties(new SelfBlockLootType(), false));
    public static final BlockDefinition<ExtinguishedSconceTorchBlock> EXTINGUISHED_SCONCE_TORCH = registerBlockNoItem("extinguished_sconce_torch",
            () -> new ExtinguishedSconceTorchBlock(ParticleTypes.FLAME, of().noCollission().instabreak().sound(SoundType.LANTERN).pushReaction(PushReaction.DESTROY).noLootTable(), NMLBlocks.SCONCE_TORCH.get()), true);
    public static final BlockDefinition<SconceWallTorchBlock> SCONCE_WALL_TORCH = registerBlockNoItem("sconce_wall_torch",
            () -> new SconceWallTorchBlock(ParticleTypes.FLAME, ofFullCopy(Blocks.WALL_TORCH).sound(SoundType.LANTERN).lootFrom(SCONCE_TORCH)), true);
    public static final BlockDefinition<ExtinguishedSconceWallTorchBlock> EXTINGUISHED_SCONCE_WALL_TORCH = registerBlockNoItem("extinguished_sconce_wall_torch",
            () -> new ExtinguishedSconceWallTorchBlock(of().noCollission().instabreak().sound(SoundType.LANTERN).pushReaction(PushReaction.DESTROY).noLootTable(), NMLBlocks.SCONCE_WALL_TORCH.get(), ParticleTypes.FLAME), true);
    public static final BlockDefinition<SconceTorchBlock> SCONCE_SOUL_TORCH = registerBlockNoItem("sconce_soul_torch",
            () -> new SconceTorchBlock(ParticleTypes.SOUL_FIRE_FLAME, ofFullCopy(Blocks.SOUL_TORCH).sound(SoundType.LANTERN)), new BlockProperties(new SelfBlockLootType(), false));
    public static final BlockDefinition<ExtinguishedSconceTorchBlock> EXTINGUISHED_SCONCE_SOUL_TORCH = registerBlockNoItem("extinguished_sconce_soul_torch",
            () -> new ExtinguishedSconceTorchBlock(ParticleTypes.SOUL_FIRE_FLAME, of().noCollission().instabreak().sound(SoundType.LANTERN).pushReaction(PushReaction.DESTROY).noLootTable(), NMLBlocks.SCONCE_SOUL_TORCH.get()), true);
    public static final BlockDefinition<SconceWallTorchBlock> SCONCE_SOUL_WALL_TORCH = registerBlockNoItem("sconce_soul_wall_torch",
            () -> new SconceWallTorchBlock(ParticleTypes.SOUL_FIRE_FLAME, ofFullCopy(Blocks.SOUL_WALL_TORCH).sound(SoundType.LANTERN).lootFrom(SCONCE_SOUL_TORCH)), true);
    public static final BlockDefinition<ExtinguishedSconceWallTorchBlock> EXTINGUISHED_SCONCE_SOUL_WALL_TORCH = registerBlockNoItem("extinguished_sconce_soul_wall_torch",
            () -> new ExtinguishedSconceWallTorchBlock(of().noCollission().instabreak().sound(SoundType.LANTERN).pushReaction(PushReaction.DESTROY).noLootTable(), NMLBlocks.SCONCE_SOUL_WALL_TORCH.get(), ParticleTypes.SOUL_FIRE_FLAME), true);
    public static final BlockDefinition<ExtinguishedTorchBlock> EXTINGUISHED_TORCH = registerBlockNoItem("extinguished_torch",
            () -> new ExtinguishedTorchBlock(ParticleTypes.FLAME, of().noCollission().instabreak().sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY).noLootTable(), Blocks.TORCH), true);
    public static final BlockDefinition<ExtinguishedWallTorchBlock> EXTINGUISHED_WALL_TORCH = registerBlockNoItem("extinguished_wall_torch",
            () -> new ExtinguishedWallTorchBlock(ParticleTypes.FLAME, of().noCollission().instabreak().sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY).noLootTable(), Blocks.WALL_TORCH), true);
    public static final BlockDefinition<ExtinguishedTorchBlock> EXTINGUISHED_SOUL_TORCH = registerBlockNoItem("extinguished_soul_torch",
            () -> new ExtinguishedTorchBlock(ParticleTypes.SOUL_FIRE_FLAME, of().noCollission().instabreak().sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY).noLootTable(), Blocks.SOUL_TORCH), true);
    public static final BlockDefinition<ExtinguishedWallTorchBlock> EXTINGUISHED_SOUL_WALL_TORCH = registerBlockNoItem("extinguished_soul_wall_torch",
            () -> new ExtinguishedWallTorchBlock(ParticleTypes.SOUL_FIRE_FLAME, of().noCollission().instabreak().sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY).noLootTable(), Blocks.SOUL_WALL_TORCH), true);

    public static final BlockDefinition<WoodenScaffoldingBlock> WOODEN_SCAFFOLDING = registerBlockNoItem("wooden_scaffolding",
            () -> new WoodenScaffoldingBlock(Block.Properties.ofFullCopy(Blocks.SCAFFOLDING).noCollission().sound(SoundType.CHERRY_WOOD)), new BlockProperties(new SelfBlockLootType(), false));

    public static final BlockDefinition<ResinCauldron> RESIN_CAULDRON = registerBlockNoItem("resin_cauldron", ResinCauldron::new, true);

    public static final BlockDefinition<ResinOilCauldron> RESIN_OIL_CAULDRON = registerBlockNoItem("resin_oil_cauldron", ResinOilCauldron::new, BlockProperties.cauldron());

    public static final BlockDefinition<HoneyCauldron> HONEY_CAULDRON = registerBlockNoItem("honey_cauldron", HoneyCauldron::new, BlockProperties.cauldron());

    public static final BlockDefinition<MilkCauldron> MILK_CAULDRON = registerBlockNoItem("milk_cauldron", MilkCauldron::new, BlockProperties.cauldron());

    public static final BlockDefinition<MapleSyrupCauldron> MAPLE_SYRUP_CAULDRON = registerBlockNoItem("maple_syrup_cauldron", MapleSyrupCauldron::new, BlockProperties.cauldron());


    //Plants and Other Natural Decorations
    public static final BlockDefinition<GrassSproutsBlock> GRASS_SPROUTS = registerBlock("grass_sprouts",
            () -> new GrassSproutsBlock(Block.Properties.ofFullCopy(Blocks.FERN).offsetType(OffsetType.XZ)));
    public static final BlockDefinition<SimpleFoliageBlock> OAT_GRASS = registerBlock("oat_grass",
            () -> new SimpleFoliageBlock(Block.Properties.ofFullCopy(Blocks.FERN).offsetType(OffsetType.XYZ)));
    public static final BlockDefinition<DesertFoliageBlock> SHORT_BEACHGRASS = registerBlock("short_beachgrass",
            () -> new DesertFoliageBlock(Block.Properties.ofFullCopy(Blocks.DEAD_BUSH).mapColor(MapColor.SAND).offsetType(OffsetType.XYZ)));
    public static final BlockDefinition<DesertFoliageBlock> TALL_BEACHGRASS = registerBlock("tall_beachgrass",
            () -> new DesertFoliageBlock(Block.Properties.ofFullCopy(Blocks.DEAD_BUSH).mapColor(MapColor.SAND).offsetType(OffsetType.XYZ)));
    public static final BlockDefinition<DesertFoliageBlock> DRIED_GRASS = registerBlock("dried_grass",
            () -> new DesertFoliageBlock(Block.Properties.ofFullCopy(Blocks.DEAD_BUSH).offsetType(OffsetType.XZ)), new BlockProperties(new ShearsBlockLootType(), false));
    public static final BlockDefinition<FrostedGrassBlock> FROSTED_GRASS = registerBlock("frosted_grass",
            () -> new FrostedGrassBlock(of().mapColor(MapColor.SNOW).replaceable().noCollission().instabreak().sound(SoundType.GRASS).offsetType(OffsetType.XYZ).ignitedByLava().pushReaction(PushReaction.DESTROY)));
    public static final BlockDefinition<SimpleFoliageBlock> FIDDLEHEAD = registerBlock("fiddlehead",
            () -> new SimpleFoliageBlock(Block.Properties.ofFullCopy(Blocks.FERN).offsetType(OffsetType.XYZ)));
    public static final BlockDefinition<SimpleFoliageBlock> MYCELIUM_SPROUTS = registerBlock("mycelium_sprouts",
            () -> new SimpleFoliageBlock(Block.Properties.ofFullCopy(Blocks.SHORT_GRASS).mapColor(MapColor.COLOR_PURPLE).offsetType(OffsetType.XYZ)), new BlockProperties(new ShearsBlockLootType(), false));
    public static final BlockDefinition<CaveFoliageBlock> CAVE_WEEDS = registerBlock("cave_weeds",
            () -> new CaveFoliageBlock(Block.Properties.ofFullCopy(Blocks.SHORT_GRASS).mapColor(MapColor.TERRACOTTA_GRAY).offsetType(OffsetType.XYZ)));
    public static final BlockDefinition<SimpleFoliageBlock> MYCELIUM_GROWTHS = registerBlock("mycelium_growths",
            () -> new SimpleFoliageBlock(Block.Properties.ofFullCopy(Blocks.SHORT_GRASS).mapColor(MapColor.COLOR_PURPLE).offsetType(OffsetType.XYZ)), new BlockProperties(new ShearsBlockLootType(), false));
    public static final BlockDefinition<WaterPlantBlock> CATTAIL = registerBlock("cattail",
            () -> new WaterPlantBlock(Block.Properties.ofFullCopy(Blocks.TALL_GRASS).offsetType(OffsetType.XYZ)));
    public static final BlockDefinition<WaterPlantBlock> REEDS = registerBlock("reeds",
            () -> new WaterPlantBlock(Block.Properties.ofFullCopy(Blocks.TALL_GRASS).offsetType(OffsetType.XYZ)));
    public static final BlockDefinition<WaterSurfacePlant> DUCKWEED = registerBlockNoItem("duckweed",
            () -> new WaterSurfacePlant(Block.Properties.ofFullCopy(Blocks.LILY_PAD).noCollission().offsetType(OffsetType.XYZ)), new BlockProperties(new ShearsBlockLootType(), false));
    public static final BlockDefinition<WaterSurfacePlant> WATER_MOSAIC = registerBlockNoItem("water_mosaic",
            () -> new WaterSurfacePlant(Block.Properties.ofFullCopy(Blocks.LILY_PAD).noCollission().offsetType(OffsetType.XYZ)), new BlockProperties(new ShearsBlockLootType(), false));
    public static final BlockDefinition<BeardMossBlock> BEARD_MOSS = registerBlock("beard_moss",
            () -> new BeardMossBlock(Block.Properties.of().mapColor(MapColor.PLANT).instabreak().sound(SoundType.MOSS).noOcclusion().noCollission().offsetType(OffsetType.XZ)), new BlockProperties(new ShearsBlockLootType(), false));
    public static final BlockDefinition<LeavesBlock> YELLOW_BIRCH_LEAVES = registerBlock("yellow_birch_leaves",
            () -> new LeavesBlock(ofFullCopy(Blocks.BIRCH_LEAVES).isViewBlocking((s, g, p) -> false).isSuffocating(((s, g, p) -> false))));
    public static final BlockDefinition<SaplingBlock> YELLOW_BIRCH_SAPLING = registerBlock("yellow_birch_sapling",
            () -> new SaplingBlock(NMLTreeGrowers.YELLOW_BIRCH, ofFullCopy(Blocks.BIRCH_SAPLING)), BlockProperties.sapling());
    public static final BlockDefinition<FlowerPotBlock> POTTED_YELLOW_BIRCH_SAPLING = registerBlockNoItem("potted_yellow_birch_sapling",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), NMLBlocks.YELLOW_BIRCH_SAPLING,
                    ofFullCopy(Blocks.POTTED_BIRCH_SAPLING).noOcclusion()), BlockProperties.flowerPot(YELLOW_BIRCH_SAPLING));
    public static final BlockDefinition<LeavesBlock> AUTUMNAL_OAK_LEAVES = registerBlock("autumnal_oak_leaves",
            () -> new LeavesBlock(ofFullCopy(Blocks.OAK_LEAVES).isViewBlocking((s, g, p) -> false).isSuffocating(((s, g, p) -> false))));
    public static final BlockDefinition<SaplingBlock> AUTUMNAL_OAK_SAPLING = registerBlock("autumnal_oak_sapling",
            () -> (new SaplingBlock(NMLTreeGrowers.AUTUMNAL_OAK, ofFullCopy(Blocks.OAK_SAPLING))), BlockProperties.sapling());
    public static final BlockDefinition<FlowerPotBlock> POTTED_AUTUMNAL_OAK_SAPLING = registerBlockNoItem("potted_autumnal_oak_sapling",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), NMLBlocks.AUTUMNAL_OAK_SAPLING,
                    ofFullCopy(Blocks.POTTED_OAK_SAPLING).noOcclusion()), BlockProperties.flowerPot(AUTUMNAL_OAK_SAPLING));
    public static final BlockDefinition<PaleCherryLeavesBlock> PALE_CHERRY_LEAVES = registerBlock("pale_cherry_leaves",
            () -> new PaleCherryLeavesBlock(ofFullCopy(Blocks.CHERRY_LEAVES).isViewBlocking((s, g, p) -> false).isSuffocating(((s, g, p) -> false))));
    public static final BlockDefinition<SaplingBlock> PALE_CHERRY_SAPLING = registerBlock("pale_cherry_sapling",
            () -> new SaplingBlock(NMLTreeGrowers.PALE_CHERRY, ofFullCopy(Blocks.CHERRY_SAPLING)), BlockProperties.sapling());
    public static final BlockDefinition<FlowerPotBlock> POTTED_PALE_CHERRY_SAPLING = registerBlockNoItem("potted_pale_cherry_sapling",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), NMLBlocks.PALE_CHERRY_SAPLING,
                    ofFullCopy(Blocks.POTTED_CHERRY_SAPLING).noOcclusion()), BlockProperties.flowerPot(PALE_CHERRY_SAPLING));
    public static final BlockDefinition<LeavesBlock> FROSTED_LEAVES = registerBlock("frosted_leaves",
            () -> new LeavesBlock(ofFullCopy(Blocks.OAK_LEAVES).isViewBlocking((s, g, p) -> false).isSuffocating(((s, g, p) -> false))));
    public static final BlockDefinition<FlowerBlock> ACONITE = registerBlock("aconite",
            () -> new FlowerBlock(MobEffects.MOVEMENT_SLOWDOWN, 20, ofFullCopy(Blocks.POPPY)), BlockProperties.smallFlower());
    public static final BlockDefinition<FlowerPotBlock> POTTED_ACONITE = registerBlockNoItem("potted_aconite",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), NMLBlocks.ACONITE,
                    ofFullCopy(Blocks.POTTED_POPPY).noOcclusion()), BlockProperties.flowerPot(ACONITE));
    public static final BlockDefinition<FlowerBlock> THISTLE = registerBlock("thistle",
            () -> new FlowerBlock(MobEffects.SATURATION, 3, ofFullCopy(Blocks.POPPY)), BlockProperties.smallFlower());
    public static final BlockDefinition<FlowerPotBlock> POTTED_THISTLE = registerBlockNoItem("potted_thistle",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), NMLBlocks.THISTLE,
                    ofFullCopy(Blocks.POTTED_POPPY).noOcclusion()), BlockProperties.flowerPot(THISTLE));
    public static final BlockDefinition<FlowerBlock> BLUE_LUPINE = registerBlock("blue_lupine",
            () -> new FlowerBlock(MobEffects.ABSORPTION, 5, ofFullCopy(Blocks.POPPY)), BlockProperties.smallFlower());
    public static final BlockDefinition<FlowerPotBlock> POTTED_BLUE_LUPINE = registerBlockNoItem("potted_blue_lupine",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), NMLBlocks.BLUE_LUPINE,
                    ofFullCopy(Blocks.POTTED_POPPY).noOcclusion()), BlockProperties.flowerPot(BLUE_LUPINE));
    public static final BlockDefinition<FlowerBlock> RED_LUPINE = registerBlock("red_lupine",
            () -> new FlowerBlock(MobEffects.ABSORPTION, 5, ofFullCopy(Blocks.POPPY)), BlockProperties.smallFlower());
    public static final BlockDefinition<FlowerPotBlock> POTTED_RED_LUPINE = registerBlockNoItem("potted_red_lupine",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), NMLBlocks.RED_LUPINE,
                    ofFullCopy(Blocks.POTTED_POPPY).noOcclusion()), BlockProperties.flowerPot(RED_LUPINE));
    public static final BlockDefinition<FlowerBlock> YELLOW_LUPINE = registerBlock("yellow_lupine",
            () -> new FlowerBlock(MobEffects.ABSORPTION, 5, ofFullCopy(Blocks.POPPY)), BlockProperties.smallFlower());
    public static final BlockDefinition<FlowerPotBlock> POTTED_YELLOW_LUPINE = registerBlockNoItem("potted_yellow_lupine",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), NMLBlocks.YELLOW_LUPINE,
                    ofFullCopy(Blocks.POTTED_POPPY).noOcclusion()), BlockProperties.flowerPot(YELLOW_LUPINE));
    public static final BlockDefinition<FlowerBlock> PINK_LUPINE = registerBlock("pink_lupine",
            () -> new FlowerBlock(MobEffects.ABSORPTION, 5, ofFullCopy(Blocks.POPPY)), BlockProperties.smallFlower());
    public static final BlockDefinition<FlowerPotBlock> POTTED_PINK_LUPINE = registerBlockNoItem("potted_pink_lupine",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), NMLBlocks.PINK_LUPINE,
                    ofFullCopy(Blocks.POTTED_POPPY).noOcclusion()), BlockProperties.flowerPot(PINK_LUPINE));
    public static final BlockDefinition<FlowerBlock> AUTUMN_CROCUS = registerBlock("autumn_crocus",
            () -> new FlowerBlock(MobEffects.BLINDNESS, 10, ofFullCopy(Blocks.POPPY)), BlockProperties.smallFlower());
    public static final BlockDefinition<FlowerPotBlock> POTTED_AUTUMN_CROCUS = registerBlockNoItem("potted_autumn_crocus",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), NMLBlocks.AUTUMN_CROCUS,
                    ofFullCopy(Blocks.POTTED_POPPY).noOcclusion()), BlockProperties.flowerPot(AUTUMN_CROCUS));
    public static final BlockDefinition<FlowerBlock> WILD_MINT = registerBlock("wild_mint",
            () -> new FlowerBlock(MobEffects.SATURATION, 1, ofFullCopy(Blocks.POPPY)), BlockProperties.smallFlower());
    public static final BlockDefinition<FlowerPotBlock> POTTED_WILD_MINT = registerBlockNoItem("potted_wild_mint",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), NMLBlocks.WILD_MINT,
                    ofFullCopy(Blocks.POTTED_POPPY).noOcclusion()), BlockProperties.flowerPot(WILD_MINT));
    public static final BlockDefinition<FlowerBlock> PICKLEWEED = registerBlock("pickleweed",
            () -> new FlowerBlock(MobEffects.SATURATION, 1, ofFullCopy(Blocks.POPPY)), BlockProperties.smallFlower());
    public static final BlockDefinition<FlowerPotBlock> POTTED_PICKLEWEED = registerBlockNoItem("potted_pickleweed",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), NMLBlocks.PICKLEWEED,
                    ofFullCopy(Blocks.POTTED_POPPY).noOcclusion()), BlockProperties.flowerPot(PICKLEWEED));
    public static final BlockDefinition<FlatFlowerBlock> RAFFLESIA = registerBlock("rafflesia",
            () -> new FlatFlowerBlock(MobEffects.HUNGER, 60, ofFullCopy(Blocks.POPPY)), BlockProperties.smallFlower());
    public static final BlockDefinition<DesertFoliageBlock> BARREL_CACTUS = registerBlock("barrel_cactus",
            () -> new DesertFoliageBlock(of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.BIG_DRIPLEAF).offsetType(OffsetType.XZ).pushReaction(PushReaction.DESTROY)), new BlockProperties(new SelfBlockLootType(), false));
    public static final BlockDefinition<FlowerPotBlock> POTTED_BARREL_CACTUS = registerBlockNoItem("potted_barrel_cactus",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), NMLBlocks.BARREL_CACTUS,
                    ofFullCopy(Blocks.POTTED_POPPY).noOcclusion()), BlockProperties.flowerPot(BARREL_CACTUS));
    public static final BlockDefinition<DesertFoliageBlock> SUCCULENT = registerBlock("succulent",
            () -> new DesertFoliageBlock(of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.FLOWERING_AZALEA).offsetType(OffsetType.XZ).pushReaction(PushReaction.DESTROY)), new BlockProperties(new SelfBlockLootType(), false));
    public static final BlockDefinition<FlowerPotBlock> POTTED_SUCCULENT = registerBlockNoItem("potted_succulent",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), NMLBlocks.SUCCULENT,
                    ofFullCopy(Blocks.POTTED_POPPY).noOcclusion()), BlockProperties.flowerPot(SUCCULENT));
    public static final BlockDefinition<FlowerbedBlock> CLOVER_PATCH = registerBlock("clover_patch",
            () -> new FlowerbedBlock(MobEffects.LUCK, 5, Block.Properties.of().mapColor(MapColor.PLANT).replaceable().noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)), BlockProperties.flowerbed());
    public static final BlockDefinition<FlowerbedBlock> WHITE_FLOWERBED = registerBlock("white_flowerbed",
            () -> new FlowerbedBlock(MobEffects.MOVEMENT_SLOWDOWN, 10, Block.Properties.of().mapColor(MapColor.PLANT).replaceable().noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)), BlockProperties.flowerbed());
    public static final BlockDefinition<FlowerbedBlock> YELLOW_FLOWERBED = registerBlock("yellow_flowerbed",
            () -> new FlowerbedBlock(MobEffects.MOVEMENT_SLOWDOWN, 5, Block.Properties.of().mapColor(MapColor.PLANT).replaceable().noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)), BlockProperties.flowerbed());
    public static final BlockDefinition<FlowerbedBlock> RED_FLOWERBED = registerBlock("red_flowerbed",
            () -> new FlowerbedBlock(MobEffects.HEAL, 1, Block.Properties.of().mapColor(MapColor.PLANT).replaceable().noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)), BlockProperties.flowerbed());
    public static final BlockDefinition<FlowerbedBlock> BLUE_FLOWERBED = registerBlock("blue_flowerbed",
            () -> new FlowerbedBlock(MobEffects.DAMAGE_RESISTANCE, 10, Block.Properties.of().mapColor(MapColor.PLANT).replaceable().noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)), BlockProperties.flowerbed());
    public static final BlockDefinition<FlowerbedBlock> VIOLET_FLOWERBED = registerBlock("violet_flowerbed",
            () -> new FlowerbedBlock(MobEffects.DAMAGE_RESISTANCE, 5, Block.Properties.of().mapColor(MapColor.PLANT).replaceable().noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)), BlockProperties.flowerbed());

    public static final BlockDefinition<GroundPickupBlock> PEBBLES = registerBlock("pebbles",
            () -> new GroundPickupBlock(of().mapColor(MapColor.STONE).noCollission().instabreak().sound(SoundType.STONE).pushReaction(PushReaction.DESTROY)));
    public static final BlockDefinition<GroundPickupBlock> SEASHELLS = registerBlock("seashells",
            () -> new GroundPickupBlock(of().mapColor(MapColor.NONE).noCollission().instabreak().sound(SoundType.CALCITE).pushReaction(PushReaction.DESTROY).offsetType(OffsetType.XZ)), new BlockProperties(new SelfBlockLootType(), false));
    //Underground
    public static final BlockDefinition<AmethystBlock> QUARTZITE = registerBlock("quartzite",
            () -> new AmethystBlock(of().mapColor(MapColor.TERRACOTTA_WHITE).strength(1.3F).sound(SoundType.NETHER_GOLD_ORE).requiresCorrectToolForDrops()));
    public static final BlockDefinition<BuddingQuartziteBlock> BUDDING_QUARTZITE = registerBlock("budding_quartzite",
            () -> new BuddingQuartziteBlock(of().mapColor(MapColor.TERRACOTTA_WHITE).randomTicks().strength(1.3F).sound(SoundType.NETHER_GOLD_ORE).pushReaction(PushReaction.DESTROY).noLootTable()));
    public static final BlockDefinition<AmethystClusterBlock> QUARTZITE_CLUSTER = registerBlock("quartzite_cluster",
            () -> new AmethystClusterBlock(7, 3, of().mapColor(MapColor.TERRACOTTA_WHITE).forceSolidOn().noOcclusion().randomTicks().sound(SoundType.NETHER_GOLD_ORE).strength(1.3F).lightLevel((p_152632_) -> 5).pushReaction(PushReaction.DESTROY)));
    public static final BlockDefinition<AmethystClusterBlock> SMALL_QUARTZITE_BUD = registerBlock("small_quartzite_bud",
            () -> new AmethystClusterBlock(3, 4, ofFullCopy(NMLBlocks.QUARTZITE_CLUSTER.get()).sound(SoundType.NETHER_GOLD_ORE).forceSolidOn().lightLevel((p_187409_) -> 1).pushReaction(PushReaction.DESTROY)));
    public static final BlockDefinition<AmethystClusterBlock> MEDIUM_QUARTZITE_BUD = registerBlock("medium_quartzite_bud",
            () -> new AmethystClusterBlock(4, 3, ofFullCopy(NMLBlocks.QUARTZITE_CLUSTER.get()).sound(SoundType.NETHER_GOLD_ORE).forceSolidOn().lightLevel((p_152617_) -> 2).pushReaction(PushReaction.DESTROY)));
    public static final BlockDefinition<AmethystClusterBlock> LARGE_QUARTZITE_BUD = registerBlock("large_quartzite_bud",
            () -> new AmethystClusterBlock(5, 3, ofFullCopy(NMLBlocks.QUARTZITE_CLUSTER.get()).sound(SoundType.NETHER_GOLD_ORE).forceSolidOn().lightLevel((p_152629_) -> 4).pushReaction(PushReaction.DESTROY)));
    public static final BlockDefinition<Block> SILT = registerBlock("silt",
            () -> new Block(of().mapColor(MapColor.TERRACOTTA_GRAY).sound(SoundType.ROOTED_DIRT).strength(0.6F)), BlockProperties.dirtLike());
    public static final BlockDefinition<PathBlock> SILT_PATH = registerBlock("silt_path",
            () -> new PathBlock(ofFullCopy(NMLBlocks.SILT.get()), NMLBlocks.SILT.get(), false), BlockProperties.simplePath(SILT));
    public static final BlockDefinition<Block> COARSE_SILT = registerBlock("coarse_silt",
            () -> new Block(ofFullCopy(NMLBlocks.SILT.get())), BlockProperties.dirtLike());
    //Paths
    public static final BlockDefinition<PathBlock> DIRT_PATH = registerBlock("dirt_path",
            () -> new PathBlock(ofFullCopy(Blocks.DIRT), Blocks.DIRT, false), BlockProperties.simplePath(Blocks.DIRT));
    public static final BlockDefinition<PathBlock> MYCELIUM_PATH = registerBlock("mycelium_path",
            () -> new PathBlock(of().mapColor(MapColor.COLOR_PURPLE).strength(0.5F).sound(SoundType.GRASS), Blocks.DIRT, false), BlockProperties.simplePath(Blocks.DIRT));
    public static final BlockDefinition<PathBlock> PODZOL_PATH = registerBlock("podzol_path",
            () -> new PathBlock(ofFullCopy(Blocks.PODZOL), Blocks.PODZOL, false), BlockProperties.simplePath(Blocks.DIRT));
    public static final BlockDefinition<PathBlock> SNOWY_GRASS_PATH = registerBlock("snowy_grass_path",
            () -> new PathBlock(of().mapColor(MapColor.SNOW).strength(0.5F).sound(SoundType.GRASS), Blocks.DIRT, false), BlockProperties.simplePath(Blocks.DIRT));
    public static final BlockDefinition<PathBlock> SNOW_PATH = registerBlock("snow_path",
            () -> new PathBlock(ofFullCopy(Blocks.SNOW_BLOCK), Blocks.SNOW_BLOCK, false), BlockProperties.path());
    public static final BlockDefinition<PathBlock> GRAVEL_PATH = registerBlock("gravel_path",
            () -> new PathBlock(ofFullCopy(Blocks.GRAVEL), Blocks.GRAVEL, true), BlockProperties.path());
    public static final BlockDefinition<PathBlock> SAND_PATH = registerBlock("sand_path",
            () -> new PathBlock(ofFullCopy(Blocks.SAND), Blocks.SAND, true), BlockProperties.simplePath(Blocks.SAND));
    public static final BlockDefinition<PathBlock> RED_SAND_PATH = registerBlock("red_sand_path",
            () -> new PathBlock(ofFullCopy(Blocks.RED_SAND), Blocks.RED_SAND, true), BlockProperties.simplePath(Blocks.RED_SAND));
    //Dungeon
    public static final BlockDefinition<RemainsBlock> REMAINS = registerBlockNoItem("remains",
            () -> new RemainsBlock(Blocks.COARSE_DIRT, SoundEvents.BRUSH_SAND, SoundEvents.BRUSH_SAND_COMPLETED, of().mapColor(MapColor.DIRT).strength(0.25F).sound(SoundType.SUSPICIOUS_SAND).pushReaction(PushReaction.DESTROY)), false);
    public static final BlockDefinition<MonsterAnchorBlock> MONSTER_ANCHOR = registerBlock("monster_anchor",
            () -> new MonsterAnchorBlock(ofFullCopy(Blocks.SPAWNER).strength(7, 7).sound(SoundType.TRIAL_SPAWNER).noOcclusion()));
    public static final BlockDefinition<WardingEffigyBlock> WARDING_EFFIGY = registerBlock("warding_effigy",
            () -> new WardingEffigyBlock(of()
                    .strength(1.5F)
                    .noOcclusion()
                    .pushReaction(PushReaction.DESTROY)
                    .sound(SoundType.CHERRY_WOOD)
            ));

    //Tiles
    public static final BlockDefinition<Block> MUNDANE_TILES = registerBlock("mundane_tiles",
            () -> new Block(ofFullCopy(Blocks.STONE_BRICKS)), BlockProperties.stoneLike());
    public static final BlockDefinition<StairBlock> MUNDANE_TILE_STAIRS = registerBlock("mundane_tile_stairs",
            () -> new StairBlock(MUNDANE_TILES.get().defaultBlockState(), ofFullCopy(NMLBlocks.MUNDANE_TILES.get())), BlockProperties.stoneLikeStairs());
    public static final BlockDefinition<SlabBlock> MUNDANE_TILE_SLAB = registerBlock("mundane_tile_slab",
            () -> new SlabBlock(ofFullCopy(NMLBlocks.MUNDANE_TILES.get())), BlockProperties.stoneLikeSlab());
    public static final BlockDefinition<Block> EARTHEN_TILES = registerBlock("earthen_tiles",
            () -> new Block(ofFullCopy(Blocks.PACKED_MUD)), BlockProperties.stoneLike());
    public static final BlockDefinition<StairBlock> EARTHEN_TILE_STAIRS = registerBlock("earthen_tile_stairs",
            () -> new StairBlock(EARTHEN_TILES.get().defaultBlockState(), ofFullCopy(NMLBlocks.EARTHEN_TILES.get())), BlockProperties.stoneLikeStairs());
    public static final BlockDefinition<SlabBlock> EARTHEN_TILE_SLAB = registerBlock("earthen_tile_slab",
            () -> new SlabBlock(ofFullCopy(NMLBlocks.EARTHEN_TILES.get())), BlockProperties.stoneLikeSlab());

    public static final BlockDefinition<Block> DROSS_TILES = registerBlock("dross_tiles",
            () -> new Block(ofFullCopy(Blocks.PACKED_MUD)), BlockProperties.stoneLike());
    public static final BlockDefinition<StairBlock> DROSS_TILE_STAIRS = registerBlock("dross_tile_stairs",
            () -> new StairBlock(DROSS_TILES.get().defaultBlockState(), ofFullCopy(NMLBlocks.DROSS_TILES.get())), BlockProperties.stoneLikeStairs());
    public static final BlockDefinition<SlabBlock> DROSS_TILE_SLAB = registerBlock("dross_tile_slab",
            () -> new SlabBlock(ofFullCopy(NMLBlocks.DROSS_TILES.get())), BlockProperties.stoneLikeSlab());

    public static final BlockDefinition<Block> SILT_BRICKS = registerBlock("silt_bricks",
            () -> new Block(ofFullCopy(Blocks.PACKED_MUD)), BlockProperties.stoneLike());
    public static final BlockDefinition<StairBlock> SILT_BRICK_STAIRS = registerBlock("silt_brick_stairs",
            () -> new StairBlock(SILT_BRICKS.get().defaultBlockState(), ofFullCopy(NMLBlocks.SILT_BRICKS.get())), BlockProperties.stoneLikeStairs());
    public static final BlockDefinition<SlabBlock> SILT_BRICK_SLAB = registerBlock("silt_brick_slab",
            () -> new SlabBlock(ofFullCopy(NMLBlocks.SILT_BRICKS.get())), BlockProperties.stoneLikeSlab());
    //Stone
    public static final BlockDefinition<Block> FADED_STONE_BRICKS = registerBlock("faded_stone_bricks",
            () -> new Block(ofFullCopy(Blocks.STONE_BRICKS)), BlockProperties.stoneLike());
    public static final BlockDefinition<Block> POLISHED_STONE = registerBlock("polished_stone",
            () -> new Block(ofFullCopy(Blocks.STONE_BRICKS)), BlockProperties.stoneLike());
    public static final BlockDefinition<StairBlock> POLISHED_STONE_STAIRS = registerBlock("polished_stone_stairs",
            () -> new StairBlock(POLISHED_STONE.get().defaultBlockState(), ofFullCopy(NMLBlocks.POLISHED_STONE.get())), BlockProperties.stoneLikeStairs());
    public static final BlockDefinition<SlabBlock> POLISHED_STONE_SLAB = registerBlock("polished_stone_slab",
            () -> new SlabBlock(ofFullCopy(NMLBlocks.POLISHED_STONE.get())), BlockProperties.stoneLikeSlab());
    public static final BlockDefinition<Block> COBBLESTONE_BRICKS = registerBlock("cobblestone_bricks",
            () -> new Block(ofFullCopy(Blocks.COBBLESTONE)), BlockProperties.stoneLike());
    public static final BlockDefinition<StairBlock> COBBLESTONE_BRICK_STAIRS = registerBlock("cobblestone_brick_stairs",
            () -> new StairBlock(COBBLESTONE_BRICKS.get().defaultBlockState(), ofFullCopy(NMLBlocks.COBBLESTONE_BRICKS.get())), BlockProperties.stoneLikeStairs());
    public static final BlockDefinition<SlabBlock> COBBLESTONE_BRICK_SLAB = registerBlock("cobblestone_brick_slab",
            () -> new SlabBlock(ofFullCopy(NMLBlocks.COBBLESTONE_BRICKS.get())), BlockProperties.stoneLikeSlab());
    public static final BlockDefinition<WallBlock> COBBLESTONE_BRICK_WALL = registerBlock("cobblestone_brick_wall",
            () -> new WallBlock(ofFullCopy(NMLBlocks.COBBLESTONE_BRICKS.get())), BlockProperties.stoneLikeWall());
    public static final BlockDefinition<Block> CRACKED_COBBLESTONE_BRICKS = registerBlock("cracked_cobblestone_bricks",
            () -> new Block(ofFullCopy(NMLBlocks.COBBLESTONE_BRICKS.get())), BlockProperties.stoneLike());
    public static final BlockDefinition<Block> MOSSY_COBBLESTONE_BRICKS = registerBlock("mossy_cobblestone_bricks",
            () -> new Block(ofFullCopy(Blocks.MOSSY_COBBLESTONE)), BlockProperties.stoneLike());
    public static final BlockDefinition<StairBlock> MOSSY_COBBLESTONE_BRICK_STAIRS = registerBlock("mossy_cobblestone_brick_stairs",
            () -> new StairBlock(MOSSY_COBBLESTONE_BRICKS.get().defaultBlockState(), ofFullCopy(NMLBlocks.MOSSY_COBBLESTONE_BRICKS.get())), BlockProperties.stoneLikeStairs());
    public static final BlockDefinition<SlabBlock> MOSSY_COBBLESTONE_BRICK_SLAB = registerBlock("mossy_cobblestone_brick_slab",
            () -> new SlabBlock(ofFullCopy(NMLBlocks.MOSSY_COBBLESTONE_BRICKS.get())), BlockProperties.stoneLikeSlab());
    public static final BlockDefinition<WallBlock> MOSSY_COBBLESTONE_BRICK_WALL = registerBlock("mossy_cobblestone_brick_wall",
            () -> new WallBlock(ofFullCopy(NMLBlocks.MOSSY_COBBLESTONE_BRICKS.get())), BlockProperties.stoneLikeWall());
    //Bricks
    public static final BlockDefinition<Block> COARSE_BRICKS = registerBlock("coarse_bricks",
            () -> new Block(ofFullCopy(Blocks.BRICKS)), BlockProperties.stoneLike());
    public static final BlockDefinition<StairBlock> COARSE_BRICK_STAIRS = registerBlock("coarse_brick_stairs",
            () -> new StairBlock(COARSE_BRICKS.get().defaultBlockState(), ofFullCopy(NMLBlocks.COARSE_BRICKS.get())), BlockProperties.stoneLikeStairs());
    public static final BlockDefinition<SlabBlock> COARSE_BRICK_SLAB = registerBlock("coarse_brick_slab",
            () -> new SlabBlock(ofFullCopy(NMLBlocks.COARSE_BRICKS.get())), BlockProperties.stoneLikeSlab());
    public static final BlockDefinition<Block> COARSE_BRICK_WALL = registerBlock("coarse_brick_wall",
            () -> new WallBlock(ofFullCopy(NMLBlocks.COARSE_BRICKS.get())), BlockProperties.stoneLikeWall());
    public static final BlockDefinition<Block> MOSSY_COARSE_BRICKS = registerBlock("mossy_coarse_bricks",
            () -> new Block(ofFullCopy(NMLBlocks.COARSE_BRICKS.get())), BlockProperties.stoneLike());
    public static final BlockDefinition<Block> MOSSY_COARSE_BRICK_STAIRS = registerBlock("mossy_coarse_brick_stairs",
            () -> new StairBlock(COARSE_BRICKS.get().defaultBlockState(), ofFullCopy(NMLBlocks.COARSE_BRICKS.get())), BlockProperties.stoneLikeStairs());
    public static final BlockDefinition<Block> MOSSY_COARSE_BRICK_SLAB = registerBlock("mossy_coarse_brick_slab",
            () -> new SlabBlock(ofFullCopy(NMLBlocks.COARSE_BRICKS.get())), BlockProperties.stoneLikeSlab());
    public static final BlockDefinition<Block> MOSSY_COARSE_BRICK_WALL = registerBlock("mossy_coarse_brick_wall",
            () -> new WallBlock(ofFullCopy(NMLBlocks.COARSE_BRICKS.get())), BlockProperties.stoneLikeWall());
    //Trimmed Planks and Bookshelves
    public static final BlockDefinition<Block> TRIMMED_OAK_PLANKS = registerBlock("trimmed_oak_planks",
            () -> new TrimmedPlankBlock(ofFullCopy(Blocks.OAK_PLANKS)), BlockProperties.trimmedPlanks(Blocks.OAK_PLANKS));
    public static final BlockDefinition<Block> SPRUCE_BOOKSHELF = registerBlock("spruce_bookshelf",
            () -> new Block(ofFullCopy(Blocks.BOOKSHELF)), BlockProperties.bookshelf(Blocks.SPRUCE_PLANKS));
    public static final BlockDefinition<Block> TRIMMED_SPRUCE_PLANKS = registerBlock("trimmed_spruce_planks",
            () -> new TrimmedPlankBlock(ofFullCopy(Blocks.SPRUCE_PLANKS)), BlockProperties.trimmedPlanks(Blocks.SPRUCE_PLANKS));
    public static final BlockDefinition<Block> BIRCH_BOOKSHELF = registerBlock("birch_bookshelf",
            () -> new Block(ofFullCopy(Blocks.BOOKSHELF)), BlockProperties.bookshelf(Blocks.BIRCH_PLANKS));
    public static final BlockDefinition<Block> TRIMMED_BIRCH_PLANKS = registerBlock("trimmed_birch_planks",
            () -> new TrimmedPlankBlock(ofFullCopy(Blocks.BIRCH_PLANKS)), BlockProperties.trimmedPlanks(Blocks.BIRCH_PLANKS));
    public static final BlockDefinition<Block> JUNGLE_BOOKSHELF = registerBlock("jungle_bookshelf",
            () -> new Block(ofFullCopy(Blocks.BOOKSHELF)), BlockProperties.bookshelf(Blocks.JUNGLE_PLANKS));
    public static final BlockDefinition<Block> TRIMMED_JUNGLE_PLANKS = registerBlock("trimmed_jungle_planks",
            () -> new TrimmedPlankBlock(ofFullCopy(Blocks.JUNGLE_PLANKS)), BlockProperties.trimmedPlanks(Blocks.JUNGLE_PLANKS));
    public static final BlockDefinition<Block> DARK_OAK_BOOKSHELF = registerBlock("dark_oak_bookshelf",
            () -> new Block(ofFullCopy(Blocks.BOOKSHELF)), BlockProperties.bookshelf(Blocks.DARK_OAK_PLANKS));
    public static final BlockDefinition<Block> TRIMMED_DARK_OAK_PLANKS = registerBlock("trimmed_dark_oak_planks",
            () -> new TrimmedPlankBlock(ofFullCopy(Blocks.DARK_OAK_PLANKS)), BlockProperties.trimmedPlanks(Blocks.DARK_OAK_PLANKS));
    public static final BlockDefinition<Block> ACACIA_BOOKSHELF = registerBlock("acacia_bookshelf",
            () -> new Block(ofFullCopy(Blocks.BOOKSHELF)), BlockProperties.bookshelf(Blocks.ACACIA_PLANKS));
    public static final BlockDefinition<Block> TRIMMED_ACACIA_PLANKS = registerBlock("trimmed_acacia_planks",
            () -> new TrimmedPlankBlock(ofFullCopy(Blocks.ACACIA_PLANKS)), BlockProperties.trimmedPlanks(Blocks.ACACIA_PLANKS));
    public static final BlockDefinition<Block> MANGROVE_BOOKSHELF = registerBlock("mangrove_bookshelf",
            () -> new Block(ofFullCopy(Blocks.BOOKSHELF)), BlockProperties.bookshelf(Blocks.MANGROVE_PLANKS));
    public static final BlockDefinition<Block> TRIMMED_MANGROVE_PLANKS = registerBlock("trimmed_mangrove_planks",
            () -> new TrimmedPlankBlock(ofFullCopy(Blocks.MANGROVE_PLANKS)), BlockProperties.trimmedPlanks(Blocks.MANGROVE_PLANKS));
    public static final BlockDefinition<Block> CHERRY_BOOKSHELF = registerBlock("cherry_bookshelf",
            () -> new Block(ofFullCopy(Blocks.BOOKSHELF)), BlockProperties.bookshelf(Blocks.CHERRY_PLANKS));
    public static final BlockDefinition<Block> TRIMMED_CHERRY_PLANKS = registerBlock("trimmed_cherry_planks",
            () -> new TrimmedPlankBlock(ofFullCopy(Blocks.CHERRY_PLANKS)), BlockProperties.trimmedPlanks(Blocks.CHERRY_PLANKS));
    public static final BlockDefinition<Block> WARPED_BOOKSHELF = registerBlock("warped_bookshelf",
            () -> new Block(ofFullCopy(Blocks.BOOKSHELF)), BlockProperties.bookshelf(Blocks.WARPED_PLANKS));
    public static final BlockDefinition<Block> TRIMMED_WARPED_PLANKS = registerBlock("trimmed_warped_planks",
            () -> new TrimmedPlankBlock(ofFullCopy(Blocks.WARPED_PLANKS)), BlockProperties.trimmedPlanks(Blocks.WARPED_PLANKS));
    public static final BlockDefinition<Block> CRIMSON_BOOKSHELF = registerBlock("crimson_bookshelf",
            () -> new Block(ofFullCopy(Blocks.BOOKSHELF)), BlockProperties.bookshelf(Blocks.CRIMSON_PLANKS));
    public static final BlockDefinition<Block> TRIMMED_CRIMSON_PLANKS = registerBlock("trimmed_crimson_planks",
            () -> new TrimmedPlankBlock(ofFullCopy(Blocks.CRIMSON_PLANKS)), BlockProperties.trimmedPlanks(Blocks.CRIMSON_PLANKS));
    public static final BlockDefinition<Block> BAMBOO_BOOKSHELF = registerBlock("bamboo_bookshelf",
            () -> new Block(ofFullCopy(Blocks.BOOKSHELF)), BlockProperties.bookshelf(Blocks.BAMBOO_PLANKS));
    public static final BlockDefinition<Block> TRIMMED_BAMBOO_PLANKS = registerBlock("trimmed_bamboo_planks",
            () -> new TrimmedPlankBlock(ofFullCopy(Blocks.BAMBOO_PLANKS)), BlockProperties.trimmedPlanks(Blocks.BAMBOO_PLANKS));

    //Pine
    public static final Woodset PINE = new Woodset(NMLWoodTypes.PINE, NMLBlockSetTypes.PINE, "pine");
    public static final BlockDefinition<Block> PINE_LEAVES = registerBlock("pine_leaves",
            () -> new LeavesBlock(ofFullCopy(Blocks.OAK_LEAVES).isViewBlocking((s, g, p) -> false).isSuffocating(((s, g, p) -> false))));
    public static final BlockDefinition<Block> PINE_SAPLING = registerBlock("pine_sapling",
            () -> new SaplingBlock(NMLTreeGrowers.PINE, ofFullCopy(Blocks.OAK_SAPLING)), BlockProperties.sapling());
    public static final BlockDefinition<FlowerPotBlock> POTTED_PINE_SAPLING = registerBlockNoItem("potted_pine_sapling",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), NMLBlocks.PINE_SAPLING,
                    ofFullCopy(Blocks.POTTED_OAK_SAPLING).noOcclusion()), BlockProperties.flowerPot(PINE_SAPLING));

    //Maple
    public static final Woodset MAPLE = new Woodset(NMLWoodTypes.MAPLE, NMLBlockSetTypes.MAPLE, "maple");
    public static final BlockDefinition<Block> MAPLE_LEAVES = registerBlock("maple_leaves",
            () -> new LeavesBlock(ofFullCopy(Blocks.OAK_LEAVES).isViewBlocking((s, g, p) -> false).isSuffocating(((s, g, p) -> false))));
    public static final BlockDefinition<Block> MAPLE_SAPLING = registerBlock("maple_sapling",
            () -> new SaplingBlock(NMLTreeGrowers.MAPLE, ofFullCopy(Blocks.OAK_SAPLING)), BlockProperties.sapling());
    public static final BlockDefinition<FlowerPotBlock> POTTED_MAPLE_SAPLING = registerBlockNoItem("potted_maple_sapling",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), NMLBlocks.MAPLE_SAPLING,
                    ofFullCopy(Blocks.POTTED_OAK_SAPLING).noOcclusion()), BlockProperties.flowerPot(MAPLE_SAPLING));
    public static final BlockDefinition<Block> RED_MAPLE_LEAVES = registerBlock("red_maple_leaves",
            () -> new LeavesBlock(ofFullCopy(Blocks.OAK_LEAVES).isViewBlocking((s, g, p) -> false).isSuffocating(((s, g, p) -> false))));
    public static final BlockDefinition<Block> RED_MAPLE_SAPLING = registerBlock("red_maple_sapling",
            () -> new SaplingBlock(NMLTreeGrowers.RED_MAPLE, ofFullCopy(Blocks.OAK_SAPLING)), BlockProperties.sapling());
    public static final BlockDefinition<FlowerPotBlock> POTTED_RED_MAPLE_SAPLING = registerBlockNoItem("potted_red_maple_sapling",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), NMLBlocks.RED_MAPLE_SAPLING,
                    ofFullCopy(Blocks.POTTED_OAK_SAPLING).noOcclusion()), BlockProperties.flowerPot(RED_MAPLE_SAPLING));

    //Walnut
    public static final Woodset WALNUT = new Woodset(NMLWoodTypes.WALNUT, NMLBlockSetTypes.WALNUT, "walnut");
    public static final BlockDefinition<Block> WALNUT_LEAVES = registerBlock("walnut_leaves",
            () -> new LeavesBlock(ofFullCopy(Blocks.OAK_LEAVES).isViewBlocking((s, g, p) -> false).isSuffocating(((s, g, p) -> false))));
    public static final BlockDefinition<Block> WALNUT_SAPLING = registerBlock("walnut_sapling",
            () -> new SaplingBlock(NMLTreeGrowers.WALNUT, ofFullCopy(Blocks.OAK_SAPLING)), BlockProperties.sapling());
    public static final BlockDefinition<FlowerPotBlock> POTTED_WALNUT_SAPLING = registerBlockNoItem("potted_walnut_sapling",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), NMLBlocks.WALNUT_SAPLING,
                    ofFullCopy(Blocks.POTTED_OAK_SAPLING).noOcclusion()), BlockProperties.flowerPot(WALNUT_SAPLING));

//Willow
    public static final Woodset WILLOW = new Woodset(NMLWoodTypes.WILLOW, NMLBlockSetTypes.WILLOW, "willow");
    public static final BlockDefinition<LeavesBlock> WILLOW_LEAVES = registerBlock("willow_leaves",
            () -> new LeavesBlock(ofFullCopy(Blocks.OAK_LEAVES).isViewBlocking((s, g, p) -> false).isSuffocating(((s, g, p) -> false))));
    public static final BlockDefinition<SaplingBlock> WILLOW_SAPLING = registerBlock("willow_sapling",
            () -> new SaplingBlock(NMLTreeGrowers.WILLOW, ofFullCopy(Blocks.OAK_SAPLING)), BlockProperties.sapling());
    public static final BlockDefinition<FlowerPotBlock> POTTED_WILLOW_SAPLING = registerBlockNoItem("potted_willow_sapling",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), NMLBlocks.WILLOW_SAPLING,
                    ofFullCopy(Blocks.POTTED_OAK_SAPLING).noOcclusion()), BlockProperties.flowerPot(WILLOW_SAPLING));

    public static final BlockDefinition<TapBlock> TAP = registerBlock("tap",
            () -> new TapBlock(of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().noOcclusion().strength(2.0F).randomTicks().pushReaction(PushReaction.DESTROY)), new BlockProperties(new SelfBlockLootType(), false));
    public static final BlockDefinition<SpikeTrapBlock> SPIKE_TRAP = registerBlock("spike_trap",
            () -> new SpikeTrapBlock(of().mapColor(MapColor.METAL).strength(1.5F, 6.0F).requiresCorrectToolForDrops().noOcclusion()), new BlockProperties(new SelfBlockLootType(), false));


    //Storage
    public static final BlockDefinition<Block> COD_BARREL = registerBlock("cod_barrel",
            () -> new Block(ofFullCopy(Blocks.BARREL)), BlockProperties.fishBarrel());
    public static final BlockDefinition<Block> SALMON_BARREL = registerBlock("salmon_barrel",
            () -> new Block(ofFullCopy(NMLBlocks.COD_BARREL.get())), BlockProperties.fishBarrel());
    public static final BlockDefinition<Block> BILLHOOK_BASS_BARREL = registerBlock("billhook_bass_barrel",
            () -> new Block(ofFullCopy(NMLBlocks.COD_BARREL.get())), BlockProperties.fishBarrel());
    public static final BlockDefinition<Block> PUFFERFISH_BARREL = registerBlock("pufferfish_barrel",
            () -> new Block(ofFullCopy(NMLBlocks.COD_BARREL.get())), BlockProperties.fishBarrel());
    public static final BlockDefinition<Block> TROPICAL_FISH_BARREL = registerBlock("tropical_fish_barrel",
            () -> new Block(ofFullCopy(NMLBlocks.COD_BARREL.get())), BlockProperties.fishBarrel());
//    public static final BlockDefinition<Block> CAVE_CARP_BARREL = registerBlock("cave_carp_barrel",
//            () -> new Block(ofFullCopy(NMLBlocks.COD_BARREL.get())));
    public static final BlockDefinition<Block> APPLE_CRATE = registerBlock("apple_crate",
            () -> new Block(ofFullCopy(Blocks.BARREL)), new BlockProperties(new SelfBlockLootType(), false));
    public static final BlockDefinition<Block> PEAR_CRATE = registerBlock("pear_crate",
            () -> new Block(ofFullCopy(Blocks.BARREL)), new BlockProperties(new SelfBlockLootType(), false));

    //Mushrooms
    public static final BlockDefinition<SurfaceMushroomBlock> FIELD_MUSHROOM = registerBlockNoItem("field_mushroom",
            () -> new SurfaceMushroomBlock((NMLFeatures.HUGE_FIELD_MUSHROOM), (ofFullCopy(Blocks.RED_MUSHROOM).mapColor(MapColor.TERRACOTTA_WHITE))), new BlockProperties(new SelfBlockLootType(), false));
    public static final BlockDefinition<FlowerPotBlock> POTTED_FIELD_MUSHROOM = registerBlockNoItem("potted_field_mushroom",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), NMLBlocks.FIELD_MUSHROOM,
                    ofFullCopy(Blocks.POTTED_RED_MUSHROOM).noOcclusion()), BlockProperties.flowerPot(FIELD_MUSHROOM));
    public static final BlockDefinition<HugeMushroomBlock> FIELD_MUSHROOM_BLOCK = registerBlock("field_mushroom_block",
            () -> new HugeMushroomBlock((ofFullCopy(Blocks.RED_MUSHROOM_BLOCK).mapColor(MapColor.TERRACOTTA_WHITE))));

    public static final BlockDefinition<ShelfMushroomBlock> SHELF_MUSHROOM = registerBlock("shelf_mushroom",
            () -> new ShelfMushroomBlock((ofFullCopy(Blocks.BROWN_MUSHROOM))));
    public static final BlockDefinition<SlabBlock> SHELF_MUSHROOM_BLOCK = registerBlock("shelf_mushroom_block",
            () -> new SlabBlock((ofFullCopy(Blocks.BROWN_MUSHROOM_BLOCK))));

    // Fruity Stuff
    public static final BlockDefinition<Block> APPLE_FRUIT = registerBlockNoItem("apple_fruit",
            () -> new FruitBlock(of()
                    .mapColor(MapColor.PLANT)
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.AZALEA)
                    .pushReaction(PushReaction.DESTROY)
                    .offsetType(OffsetType.XYZ)
                    .dynamicShape()
                    ,FruitType.APPLE_OAK), true);

    public static final BlockDefinition<Block> APPLE_FRUIT_LEAVES = registerBlock("apple_fruit_leaves",
            () -> new FruitLeavesBlock(ofFullCopy(Blocks.OAK_LEAVES).isViewBlocking((s, g, p) -> false).isSuffocating(((s, g, p) -> false)), FruitType.APPLE_OAK));

    public static final BlockDefinition<Block> PEAR_FRUIT = registerBlockNoItem("pear_fruit",
            () -> new FruitBlock(of()
                    .mapColor(MapColor.PLANT)
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.AZALEA)
                    .pushReaction(PushReaction.DESTROY)
                    .offsetType(OffsetType.XYZ)
                    .dynamicShape()
                    ,FruitType.PEAR_AUTUMNAL_OAK), true);

    public static final BlockDefinition<Block> PEAR_FRUIT_LEAVES = registerBlock("pear_fruit_leaves",
            () -> new FruitLeavesBlock(ofFullCopy(NMLBlocks.AUTUMNAL_OAK_LEAVES.get()).isViewBlocking((s, g, p) -> false).isSuffocating(((s, g, p) -> false)), FruitType.PEAR_AUTUMNAL_OAK));


    @SuppressWarnings("unchecked")
    public static <T extends Block> BlockDefinition<T> registerBlockNoItem(String name, Supplier<? extends Block> block, BlockProperties properties) {
        DeferredBlock<T> deferred = (DeferredBlock<T>) BLOCKS.register(name, block);
        BlockDefinition<T> definition = (BlockDefinition<T>) BlockDefinition.fromHolder(deferred, properties);
        BLOCK_DEFINITIONS.add(definition);
        return definition;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Block> BlockDefinition<T> registerBlockNoItem(String name, Supplier<? extends Block> block, boolean customLang) {
        DeferredBlock<T> deferred = (DeferredBlock<T>) BLOCKS.register(name, block);
        BlockDefinition<T> definition = (BlockDefinition<T>) BlockDefinition.fromHolder(deferred, new BlockProperties(new CustomBlockLootType(), customLang));
        BLOCK_DEFINITIONS.add(definition);
        return definition;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Block> BlockDefinition<T> registerBlock(String name, Supplier<? extends Block> block, BlockProperties properties) {
        DeferredBlock<T> deferred = (DeferredBlock<T>) BLOCKS.register(name, block);
        BlockDefinition<T> definition = (BlockDefinition<T>) BlockDefinition.fromHolder(deferred, properties);
        CREATIVE_TAB_ITEMS.add(registerBlockItem(name, deferred));
        BLOCK_DEFINITIONS.add(definition);
        return definition;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Block> BlockDefinition<T> registerBlock(String name, Supplier<? extends Block> block) {
        DeferredBlock<T> deferred = (DeferredBlock<T>) BLOCKS.register(name, block);
        BlockDefinition<T> definition = (BlockDefinition<T>) BlockDefinition.fromHolder(deferred);
        CREATIVE_TAB_ITEMS.add(registerBlockItem(name, deferred));
        BLOCK_DEFINITIONS.add(definition);
        return definition;
    }

    public static DeferredHolder<Item, BlockItem> registerBlockItem(String name, Supplier<? extends Block> block) {
        return NMLItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static class Woodset {
        private final BlockDefinition<Block> PLANKS;
        private final BlockDefinition<Block> STAIRS;
        private final BlockDefinition<Block> SLAB;
        private final BlockDefinition<Block> TRIMMED_PLANKS;
        private final BlockDefinition<Block> LOG;
        private final BlockDefinition<Block> WOOD;
        private final BlockDefinition<Block> STRIPPED_LOG;
        private final BlockDefinition<Block> STRIPPED_WOOD;
        private final BlockDefinition<Block> FENCE;
        private final BlockDefinition<Block> FENCE_GATE;
        private final BlockDefinition<Block> BUTTON;
        private final BlockDefinition<Block> PRESSURE_PLATE;
        private final BlockDefinition<Block> DOOR;
        private final BlockDefinition<Block> TRAPDOOR;
        private final BlockDefinition<Block> BOOKSHELF;
        private final BlockDefinition<StandingSignBlock> SIGN;
        private final BlockDefinition<WallSignBlock> WALL_SIGN;
        private final BlockDefinition<CeilingHangingSignBlock> HANGING_SIGN;
        private final BlockDefinition<WallHangingSignBlock> HANGING_WALL_SIGN;

        public Woodset(WoodType woodType, BlockSetType blockSetType, String name) {
            PLANKS = registerBlock(name + "_planks",
                    () -> new Block(ofFullCopy(Blocks.OAK_PLANKS)), BlockProperties.planks());
            STAIRS = registerBlock(name + "_stairs",
                    () -> new StairBlock(PLANKS.get().defaultBlockState(), ofFullCopy(PLANKS.get())), BlockProperties.woodenStairs(PLANKS));
            SLAB = registerBlock(name + "_slab",
                    () -> new SlabBlock(ofFullCopy(PLANKS.get())), BlockProperties.woodenSlab(PLANKS));
            TRIMMED_PLANKS = registerBlock("trimmed_" + name + "_planks",
                    () -> new TrimmedPlankBlock(ofFullCopy(PLANKS.get())), BlockProperties.trimmedPlanks(PLANKS));
            LOG = registerBlock(name + "_log",
                    () -> new LogBlock(ofFullCopy(Blocks.OAK_LOG)), BlockProperties.log());
            WOOD = registerBlock(name + "_wood",
                    () -> new LogBlock(ofFullCopy(Blocks.OAK_WOOD)), BlockProperties.log());
            STRIPPED_LOG = registerBlock("stripped_" + name + "_log",
                    () -> new LogBlock(ofFullCopy(Blocks.STRIPPED_OAK_LOG)), BlockProperties.log());
            STRIPPED_WOOD = registerBlock("stripped_" + name + "_wood",
                    () -> new LogBlock(ofFullCopy(Blocks.STRIPPED_OAK_WOOD)), BlockProperties.log());
            FENCE = registerBlock(name + "_fence",
                    () -> new FenceBlock(ofFullCopy(Blocks.OAK_FENCE)), BlockProperties.woodenFence(PLANKS));
            FENCE_GATE = registerBlock(name + "_fence_gate",
                    () -> new FenceGateBlock(woodType, ofFullCopy(Blocks.OAK_FENCE_GATE)), BlockProperties.fenceGate(PLANKS));
            BUTTON = registerBlock(name + "_button",
                    () -> new ButtonBlock(blockSetType, 15, ofFullCopy(Blocks.OAK_BUTTON)), BlockProperties.woodenButton(PLANKS));
            PRESSURE_PLATE = registerBlock(name + "_pressure_plate",
                    () -> new PressurePlateBlock(blockSetType,
                            ofFullCopy(Blocks.OAK_PRESSURE_PLATE)), BlockProperties.woodenPressurePlate(PLANKS));
            DOOR = registerBlock(name + "_door",
                    () -> new DoorBlock(blockSetType, ofFullCopy(Blocks.OAK_DOOR)), BlockProperties.woodenDoor(PLANKS));
            TRAPDOOR = registerBlock(name + "_trapdoor",
                    () -> new TrapDoorBlock(blockSetType, ofFullCopy(Blocks.OAK_TRAPDOOR)), BlockProperties.woodenTrapdoor(PLANKS));
            BOOKSHELF = registerBlock(name + "_bookshelf",
                    () -> new Block(ofFullCopy(Blocks.BOOKSHELF)), BlockProperties.bookshelf(PLANKS));
            SIGN = registerBlockNoItem(name + "_sign",
                    () -> new StandingSignBlock(woodType, ofFullCopy(Blocks.OAK_SIGN)), BlockProperties.sign());
            WALL_SIGN = registerBlockNoItem(name + "_wall_sign",
                    () -> new WallSignBlock(woodType, ofFullCopy(Blocks.OAK_WALL_SIGN).lootFrom(SIGN)), true);
            HANGING_SIGN = registerBlockNoItem(name + "_hanging_sign",
                    () -> new CeilingHangingSignBlock(woodType, ofFullCopy(Blocks.OAK_HANGING_SIGN)), BlockProperties.hangingSign());
            HANGING_WALL_SIGN = registerBlockNoItem(name + "_wall_hanging_sign",
                    () -> new WallHangingSignBlock(woodType, ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN).lootFrom(HANGING_SIGN)), true);
        }

        public BlockDefinition<Block> planks() { return PLANKS; }
        public BlockDefinition<Block> stairs() { return STAIRS; }
        public BlockDefinition<Block> slab() { return SLAB; }
        public BlockDefinition<Block> trimmedPlanks() { return TRIMMED_PLANKS; }
        public BlockDefinition<Block> log() { return LOG; }
        public BlockDefinition<Block> wood() { return WOOD; }
        public BlockDefinition<Block> strippedLog() { return STRIPPED_LOG; }
        public BlockDefinition<Block> strippedWood() { return STRIPPED_WOOD; }
        public BlockDefinition<Block> fence() { return FENCE; }
        public BlockDefinition<Block> fenceGate() { return FENCE_GATE; }
        public BlockDefinition<Block> button() { return BUTTON; }
        public BlockDefinition<Block> pressurePlate() { return PRESSURE_PLATE; }
        public BlockDefinition<Block> door() { return DOOR; }
        public BlockDefinition<Block> trapdoor() { return TRAPDOOR; }
        public BlockDefinition<Block> bookshelf() { return BOOKSHELF; }
        public BlockDefinition<StandingSignBlock> sign() { return SIGN; }
        public BlockDefinition<WallSignBlock> wallSign() { return WALL_SIGN; }
        public BlockDefinition<CeilingHangingSignBlock> hangingSign() { return HANGING_SIGN; }
        public BlockDefinition<WallHangingSignBlock> hangingWallSign() { return HANGING_WALL_SIGN; }

        public void setFlammables() {
            FireBlock fireBlock = (FireBlock) Blocks.FIRE;
            fireBlock.setFlammable(PLANKS.get(), 5, 20);
            fireBlock.setFlammable(STAIRS.get(), 5, 20);
            fireBlock.setFlammable(SLAB.get(), 5, 20);
            fireBlock.setFlammable(FENCE.get(), 5, 20);
            fireBlock.setFlammable(BOOKSHELF.get(), 30, 20);
            fireBlock.setFlammable(TRIMMED_PLANKS.get(), 5, 20);
        }

        public @Nullable BlockState checkLogStripping(BlockState state) {
            if (state.is(LOG.get())) return STRIPPED_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS));
            if (state.is(WOOD.get())) return STRIPPED_WOOD.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS));
            return null;
        }
    }
}
