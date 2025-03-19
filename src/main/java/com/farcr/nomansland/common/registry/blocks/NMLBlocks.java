package com.farcr.nomansland.common.registry.blocks;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.block.*;
import com.farcr.nomansland.common.block.cauldrons.MilkCauldron;
import com.farcr.nomansland.common.block.cauldrons.NMLCauldronBlock;
import com.farcr.nomansland.common.block.cauldrons.NMLCauldronType;
import com.farcr.nomansland.common.block.cauldrons.ResinOilCauldron;
import com.farcr.nomansland.common.block.fruit_trees.FruitBlock;
import com.farcr.nomansland.common.block.fruit_trees.FruitLeavesBlock;
import com.farcr.nomansland.common.block.fruit_trees.FruitType;
import com.farcr.nomansland.common.block.tap.TapBlock;
import com.farcr.nomansland.common.block.torches.*;
import com.farcr.nomansland.common.registry.items.NMLItems;
import com.farcr.nomansland.common.registry.worldgen.NMLTreeGrowers;
import com.farcr.nomansland.common.world.tree.HugeMushrooms;
import com.google.common.collect.Sets;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.LinkedHashSet;
import java.util.function.Supplier;

import static net.minecraft.world.level.block.state.BlockBehaviour.Properties.of;
import static net.minecraft.world.level.block.state.BlockBehaviour.Properties.ofFullCopy;

public class NMLBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(NoMansLand.MODID);
    public static LinkedHashSet<DeferredHolder<Item, BlockItem>> CREATIVE_TAB_ITEMS = Sets.newLinkedHashSet();

    public static final DeferredBlock<VineBlock> CUT_VINE = BLOCKS.register("cut_vine",
            () -> new VineBlock(of().mapColor(MapColor.PLANT).replaceable().noCollission().strength(0.2F).sound(SoundType.VINE).ignitedByLava().pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<CutSugarCaneBlock> CUT_SUGAR_CANE = BLOCKS.register("cut_sugar_cane",
            () -> new CutSugarCaneBlock(of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)));

    //Decorations
    public static final DeferredBlock<SconceTorchBlock> SCONCE_TORCH = BLOCKS.register("sconce_torch",
            () -> new SconceTorchBlock(ParticleTypes.FLAME, ofFullCopy(Blocks.TORCH).sound(SoundType.LANTERN)));
    public static final DeferredBlock<ExtinguishedSconceTorchBlock> EXTINGUISHED_SCONCE_TORCH = BLOCKS.register("extinguished_sconce_torch",
            () -> new ExtinguishedSconceTorchBlock(ParticleTypes.FLAME, of().noCollission().instabreak().sound(SoundType.LANTERN).pushReaction(PushReaction.DESTROY).noLootTable(), NMLBlocks.SCONCE_TORCH.get()));
    public static final DeferredBlock<SconceWallTorchBlock> SCONCE_WALL_TORCH = BLOCKS.register("sconce_wall_torch",
            () -> new SconceWallTorchBlock(ParticleTypes.FLAME, ofFullCopy(Blocks.WALL_TORCH).sound(SoundType.LANTERN).lootFrom(SCONCE_TORCH)));
    public static final DeferredBlock<ExtinguishedSconceWallTorchBlock> EXTINGUISHED_SCONCE_WALL_TORCH = BLOCKS.register("extinguished_sconce_wall_torch",
            () -> new ExtinguishedSconceWallTorchBlock(of().noCollission().instabreak().sound(SoundType.LANTERN).pushReaction(PushReaction.DESTROY).noLootTable(), NMLBlocks.SCONCE_WALL_TORCH.get(), ParticleTypes.FLAME));
    public static final DeferredBlock<SconceTorchBlock> SCONCE_SOUL_TORCH = BLOCKS.register("sconce_soul_torch",
            () -> new SconceTorchBlock(ParticleTypes.SOUL_FIRE_FLAME, ofFullCopy(Blocks.SOUL_TORCH).sound(SoundType.LANTERN)));
    public static final DeferredBlock<ExtinguishedSconceTorchBlock> EXTINGUISHED_SCONCE_SOUL_TORCH = BLOCKS.register("extinguished_sconce_soul_torch",
            () -> new ExtinguishedSconceTorchBlock(ParticleTypes.SOUL_FIRE_FLAME, of().noCollission().instabreak().sound(SoundType.LANTERN).pushReaction(PushReaction.DESTROY).noLootTable(), NMLBlocks.SCONCE_SOUL_TORCH.get()));
    public static final DeferredBlock<SconceWallTorchBlock> SCONCE_SOUL_WALL_TORCH = BLOCKS.register("sconce_soul_wall_torch",
            () -> new SconceWallTorchBlock(ParticleTypes.SOUL_FIRE_FLAME, ofFullCopy(Blocks.SOUL_WALL_TORCH).sound(SoundType.LANTERN).lootFrom(SCONCE_SOUL_TORCH)));
    public static final DeferredBlock<ExtinguishedSconceWallTorchBlock> EXTINGUISHED_SCONCE_SOUL_WALL_TORCH = BLOCKS.register("extinguished_sconce_soul_wall_torch",
            () -> new ExtinguishedSconceWallTorchBlock(of().noCollission().instabreak().sound(SoundType.LANTERN).pushReaction(PushReaction.DESTROY).noLootTable(), NMLBlocks.SCONCE_SOUL_WALL_TORCH.get(), ParticleTypes.SOUL_FIRE_FLAME));
    public static final DeferredBlock<ExtinguishedTorchBlock> EXTINGUISHED_TORCH = BLOCKS.register("extinguished_torch",
            () -> new ExtinguishedTorchBlock(ParticleTypes.FLAME, of().noCollission().instabreak().sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY).noLootTable(), Blocks.TORCH));
    public static final DeferredBlock<ExtinguishedWallTorchBlock> EXTINGUISHED_WALL_TORCH = BLOCKS.register("extinguished_wall_torch",
            () -> new ExtinguishedWallTorchBlock(ParticleTypes.FLAME, of().noCollission().instabreak().sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY).noLootTable(), Blocks.WALL_TORCH));
    public static final DeferredBlock<ExtinguishedTorchBlock> EXTINGUISHED_SOUL_TORCH = BLOCKS.register("extinguished_soul_torch",
            () -> new ExtinguishedTorchBlock(ParticleTypes.SOUL_FIRE_FLAME, of().noCollission().instabreak().sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY).noLootTable(), Blocks.SOUL_TORCH));
    public static final DeferredBlock<ExtinguishedWallTorchBlock> EXTINGUISHED_SOUL_WALL_TORCH = BLOCKS.register("extinguished_soul_wall_torch",
            () -> new ExtinguishedWallTorchBlock(ParticleTypes.SOUL_FIRE_FLAME, of().noCollission().instabreak().sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY).noLootTable(), Blocks.SOUL_WALL_TORCH));

    public static final DeferredBlock<WoodenScaffoldingBlock> WOODEN_SCAFFOLDING = BLOCKS.register("wooden_scaffolding",
            () -> new WoodenScaffoldingBlock(Block.Properties.ofFullCopy(Blocks.SCAFFOLDING).noCollission().sound(SoundType.CHERRY_WOOD)));

    public static final DeferredBlock<NMLCauldronBlock> RESIN_CAULDRON = BLOCKS.register("resin_cauldron",
            () -> new NMLCauldronBlock(NMLCauldronType.RESIN));

    public static final DeferredBlock<ResinOilCauldron> RESIN_OIL_CAULDRON = BLOCKS.register("resin_oil_cauldron", ResinOilCauldron::new);

    public static final DeferredBlock<NMLCauldronBlock> HONEY_CAULDRON = BLOCKS.register("honey_cauldron",
            () -> new NMLCauldronBlock(NMLCauldronType.HONEY));

    public static final DeferredBlock<MilkCauldron> MILK_CAULDRON = BLOCKS.register("milk_cauldron", MilkCauldron::new);

    public static final DeferredBlock<NMLCauldronBlock> MAPLE_SYRUP_CAULDRON = BLOCKS.register("maple_syrup_cauldron",
            () -> new NMLCauldronBlock(NMLCauldronType.MAPLE));


    //Plants and Other Natural Decorations
    public static final DeferredBlock<GrassSproutsBlock> GRASS_SPROUTS = registerBlock("grass_sprouts",
            () -> new GrassSproutsBlock(Block.Properties.ofFullCopy(Blocks.FERN).offsetType(OffsetType.XZ)));
    public static final DeferredBlock<SimpleFoliageBlock> OAT_GRASS = registerBlock("oat_grass",
            () -> new SimpleFoliageBlock(Block.Properties.ofFullCopy(Blocks.FERN).offsetType(OffsetType.XYZ)));
    public static final DeferredBlock<DesertFoliageBlock> SHORT_BEACHGRASS = registerBlock("short_beachgrass",
            () -> new DesertFoliageBlock(Block.Properties.ofFullCopy(Blocks.DEAD_BUSH).mapColor(MapColor.SAND).offsetType(OffsetType.XYZ)));
    public static final DeferredBlock<DesertFoliageBlock> TALL_BEACHGRASS = registerBlock("tall_beachgrass",
            () -> new DesertFoliageBlock(Block.Properties.ofFullCopy(Blocks.DEAD_BUSH).mapColor(MapColor.SAND).offsetType(OffsetType.XYZ)));
    public static final DeferredBlock<DesertFoliageBlock> DRIED_GRASS = registerBlock("dried_grass",
            () -> new DesertFoliageBlock(Block.Properties.ofFullCopy(Blocks.DEAD_BUSH).offsetType(OffsetType.XZ)));
    public static final DeferredBlock<FrostedGrassBlock> FROSTED_GRASS = registerBlock("frosted_grass",
            () -> new FrostedGrassBlock(of().mapColor(MapColor.SNOW).replaceable().noCollission().instabreak().sound(SoundType.GRASS).offsetType(OffsetType.XYZ).ignitedByLava().pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<SimpleFoliageBlock> FIDDLEHEAD = registerBlock("fiddlehead",
            () -> new SimpleFoliageBlock(Block.Properties.ofFullCopy(Blocks.FERN).offsetType(OffsetType.XYZ)));
    public static final DeferredBlock<SimpleFoliageBlock> MYCELIUM_SPROUTS = registerBlock("mycelium_sprouts",
            () -> new SimpleFoliageBlock(Block.Properties.ofFullCopy(Blocks.SHORT_GRASS).mapColor(MapColor.COLOR_PURPLE).offsetType(OffsetType.XYZ)));
    public static final DeferredBlock<CaveFoliageBlock> CAVE_WEEDS = registerBlock("cave_weeds",
            () -> new CaveFoliageBlock(Block.Properties.ofFullCopy(Blocks.SHORT_GRASS).mapColor(MapColor.TERRACOTTA_GRAY).offsetType(OffsetType.XYZ)));
    public static final DeferredBlock<SimpleFoliageBlock> MYCELIUM_GROWTHS = registerBlock("mycelium_growths",
            () -> new SimpleFoliageBlock(Block.Properties.ofFullCopy(Blocks.SHORT_GRASS).mapColor(MapColor.COLOR_PURPLE).offsetType(OffsetType.XYZ)));
    public static final DeferredBlock<WaterPlantBlock> CATTAIL = registerBlock("cattail",
            () -> new WaterPlantBlock(Block.Properties.ofFullCopy(Blocks.TALL_GRASS).offsetType(OffsetType.XYZ)));
    public static final DeferredBlock<WaterPlantBlock> REEDS = registerBlock("reeds",
            () -> new WaterPlantBlock(Block.Properties.ofFullCopy(Blocks.TALL_GRASS).offsetType(OffsetType.XYZ)));
    public static final DeferredBlock<WaterSurfacePlant> DUCKWEED = BLOCKS.register("duckweed",
            () -> new WaterSurfacePlant(Block.Properties.ofFullCopy(Blocks.LILY_PAD).noCollission().offsetType(OffsetType.XYZ)));
    public static final DeferredBlock<WaterSurfacePlant> WATER_MOSAIC = BLOCKS.register("water_mosaic",
            () -> new WaterSurfacePlant(Block.Properties.ofFullCopy(Blocks.LILY_PAD).noCollission().offsetType(OffsetType.XYZ)));
    public static final DeferredBlock<BeardMossBlock> BEARD_MOSS = registerBlock("beard_moss",
            () -> new BeardMossBlock(Block.Properties.of().mapColor(MapColor.PLANT).instabreak().sound(SoundType.MOSS).noOcclusion().noCollission().offsetType(OffsetType.XZ)));
    public static final DeferredBlock<LeavesBlock> YELLOW_BIRCH_LEAVES = registerBlock("yellow_birch_leaves",
            () -> new LeavesBlock(ofFullCopy(Blocks.BIRCH_LEAVES).isViewBlocking((s, g, p) -> false).isSuffocating(((s, g, p) -> false))));
    public static final DeferredBlock<SaplingBlock> YELLOW_BIRCH_SAPLING = registerBlock("yellow_birch_sapling",
            () -> new SaplingBlock(NMLTreeGrowers.YELLOW_BIRCH, ofFullCopy(Blocks.BIRCH_SAPLING)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_YELLOW_BIRCH_SAPLING = BLOCKS.register("potted_yellow_birch_sapling",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), NMLBlocks.YELLOW_BIRCH_SAPLING,
                    ofFullCopy(Blocks.POTTED_BIRCH_SAPLING).noOcclusion()));
    public static final DeferredBlock<LeavesBlock> AUTUMNAL_OAK_LEAVES = registerBlock("autumnal_oak_leaves",
            () -> new LeavesBlock(ofFullCopy(Blocks.OAK_LEAVES).isViewBlocking((s, g, p) -> false).isSuffocating(((s, g, p) -> false))));
    public static final DeferredBlock<SaplingBlock> AUTUMNAL_OAK_SAPLING = registerBlock("autumnal_oak_sapling",
            () -> (new SaplingBlock(NMLTreeGrowers.AUTUMNAL_OAK, ofFullCopy(Blocks.OAK_SAPLING))));
    public static final DeferredBlock<FlowerPotBlock> POTTED_AUTUMNAL_OAK_SAPLING = BLOCKS.register("potted_autumnal_oak_sapling",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), NMLBlocks.AUTUMNAL_OAK_SAPLING,
                    ofFullCopy(Blocks.POTTED_OAK_SAPLING).noOcclusion()));
    public static final DeferredBlock<PaleCherryLeavesBlock> PALE_CHERRY_LEAVES = registerBlock("pale_cherry_leaves",
            () -> new PaleCherryLeavesBlock(ofFullCopy(Blocks.CHERRY_LEAVES).isViewBlocking((s, g, p) -> false).isSuffocating(((s, g, p) -> false))));
    public static final DeferredBlock<SaplingBlock> PALE_CHERRY_SAPLING = registerBlock("pale_cherry_sapling",
            () -> new SaplingBlock(NMLTreeGrowers.PALE_CHERRY, ofFullCopy(Blocks.CHERRY_SAPLING)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_PALE_CHERRY_SAPLING = BLOCKS.register("potted_pale_cherry_sapling",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), NMLBlocks.PALE_CHERRY_SAPLING,
                    ofFullCopy(Blocks.POTTED_CHERRY_SAPLING).noOcclusion()));
    public static final DeferredBlock<LeavesBlock> FROSTED_LEAVES = registerBlock("frosted_leaves",
            () -> new LeavesBlock(ofFullCopy(Blocks.OAK_LEAVES).isViewBlocking((s, g, p) -> false).isSuffocating(((s, g, p) -> false))));
    public static final DeferredBlock<FlowerBlock> ACONITE = registerBlock("aconite",
            () -> new FlowerBlock(MobEffects.MOVEMENT_SLOWDOWN, 20, ofFullCopy(Blocks.POPPY)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_ACONITE = BLOCKS.register("potted_aconite",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), NMLBlocks.ACONITE,
                    ofFullCopy(Blocks.POTTED_POPPY).noOcclusion()));
    public static final DeferredBlock<FlowerBlock> THISTLE = registerBlock("thistle",
            () -> new FlowerBlock(MobEffects.SATURATION, 3, ofFullCopy(Blocks.POPPY)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_THISTLE = BLOCKS.register("potted_thistle",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), NMLBlocks.THISTLE,
                    ofFullCopy(Blocks.POTTED_POPPY).noOcclusion()));
    public static final DeferredBlock<FlowerBlock> BLUE_LUPINE = registerBlock("blue_lupine",
            () -> new FlowerBlock(MobEffects.ABSORPTION, 5, ofFullCopy(Blocks.POPPY)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_BLUE_LUPINE = BLOCKS.register("potted_blue_lupine",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), NMLBlocks.BLUE_LUPINE,
                    ofFullCopy(Blocks.POTTED_POPPY).noOcclusion()));
    public static final DeferredBlock<FlowerBlock> RED_LUPINE = registerBlock("red_lupine",
            () -> new FlowerBlock(MobEffects.ABSORPTION, 5, ofFullCopy(Blocks.POPPY)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_RED_LUPINE = BLOCKS.register("potted_red_lupine",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), NMLBlocks.RED_LUPINE,
                    ofFullCopy(Blocks.POTTED_POPPY).noOcclusion()));
    public static final DeferredBlock<FlowerBlock> YELLOW_LUPINE = registerBlock("yellow_lupine",
            () -> new FlowerBlock(MobEffects.ABSORPTION, 5, ofFullCopy(Blocks.POPPY)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_YELLOW_LUPINE = BLOCKS.register("potted_yellow_lupine",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), NMLBlocks.YELLOW_LUPINE,
                    ofFullCopy(Blocks.POTTED_POPPY).noOcclusion()));
    public static final DeferredBlock<FlowerBlock> PINK_LUPINE = registerBlock("pink_lupine",
            () -> new FlowerBlock(MobEffects.ABSORPTION, 5, ofFullCopy(Blocks.POPPY)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_PINK_LUPINE = BLOCKS.register("potted_pink_lupine",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), NMLBlocks.PINK_LUPINE,
                    ofFullCopy(Blocks.POTTED_POPPY).noOcclusion()));
    public static final DeferredBlock<FlowerBlock> AUTUMN_CROCUS = registerBlock("autumn_crocus",
            () -> new FlowerBlock(MobEffects.BLINDNESS, 10, ofFullCopy(Blocks.POPPY)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_AUTUMN_CROCUS = BLOCKS.register("potted_autumn_crocus",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), NMLBlocks.AUTUMN_CROCUS,
                    ofFullCopy(Blocks.POTTED_POPPY).noOcclusion()));
    public static final DeferredBlock<FlowerBlock> WILD_MINT = registerBlock("wild_mint",
            () -> new FlowerBlock(MobEffects.SATURATION, 1, ofFullCopy(Blocks.POPPY)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_WILD_MINT = BLOCKS.register("potted_wild_mint",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), NMLBlocks.WILD_MINT,
                    ofFullCopy(Blocks.POTTED_POPPY).noOcclusion()));
    public static final DeferredBlock<FlowerBlock> PICKLEWEED = registerBlock("pickleweed",
            () -> new FlowerBlock(MobEffects.SATURATION, 1, ofFullCopy(Blocks.POPPY)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_PICKLEWEED = BLOCKS.register("potted_pickleweed",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), NMLBlocks.PICKLEWEED,
                    ofFullCopy(Blocks.POTTED_POPPY).noOcclusion()));
    public static final DeferredBlock<FlatFlowerBlock> RAFFLESIA = registerBlock("rafflesia",
            () -> new FlatFlowerBlock(MobEffects.HUNGER, 60, ofFullCopy(Blocks.POPPY)));
    public static final DeferredBlock<DesertFoliageBlock> BARREL_CACTUS = registerBlock("barrel_cactus",
            () -> new DesertFoliageBlock(of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.BIG_DRIPLEAF).offsetType(OffsetType.XZ).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_BARREL_CACTUS = BLOCKS.register("potted_barrel_cactus",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), NMLBlocks.BARREL_CACTUS,
                    ofFullCopy(Blocks.POTTED_POPPY).noOcclusion()));
    public static final DeferredBlock<DesertFoliageBlock> SUCCULENT = registerBlock("succulent",
            () -> new DesertFoliageBlock(of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.FLOWERING_AZALEA).offsetType(OffsetType.XZ).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_SUCCULENT = BLOCKS.register("potted_succulent",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), NMLBlocks.SUCCULENT,
                    ofFullCopy(Blocks.POTTED_POPPY).noOcclusion()));
    public static final DeferredBlock<FlowerbedBlock> CLOVER_PATCH = registerBlock("clover_patch",
            () -> new FlowerbedBlock(MobEffects.LUCK, 5, Block.Properties.of().mapColor(MapColor.PLANT).replaceable().noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<FlowerbedBlock> WHITE_FLOWERBED = registerBlock("white_flowerbed",
            () -> new FlowerbedBlock(MobEffects.MOVEMENT_SLOWDOWN, 10, Block.Properties.of().mapColor(MapColor.PLANT).replaceable().noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<FlowerbedBlock> YELLOW_FLOWERBED = registerBlock("yellow_flowerbed",
            () -> new FlowerbedBlock(MobEffects.MOVEMENT_SLOWDOWN, 5, Block.Properties.of().mapColor(MapColor.PLANT).replaceable().noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<FlowerbedBlock> RED_FLOWERBED = registerBlock("red_flowerbed",
            () -> new FlowerbedBlock(MobEffects.HEAL, 1, Block.Properties.of().mapColor(MapColor.PLANT).replaceable().noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<FlowerbedBlock> BLUE_FLOWERBED = registerBlock("blue_flowerbed",
            () -> new FlowerbedBlock(MobEffects.DAMAGE_RESISTANCE, 10, Block.Properties.of().mapColor(MapColor.PLANT).replaceable().noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<FlowerbedBlock> VIOLET_FLOWERBED = registerBlock("violet_flowerbed",
            () -> new FlowerbedBlock(MobEffects.DAMAGE_RESISTANCE, 5, Block.Properties.of().mapColor(MapColor.PLANT).replaceable().noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<GroundPickupBlock> PEBBLES = registerBlock("pebbles",
            () -> new GroundPickupBlock(of().mapColor(MapColor.STONE).noCollission().instabreak().sound(SoundType.STONE).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<GroundPickupBlock> SEASHELLS = registerBlock("seashells",
            () -> new GroundPickupBlock(of().mapColor(MapColor.NONE).noCollission().instabreak().sound(SoundType.CALCITE).pushReaction(PushReaction.DESTROY).offsetType(OffsetType.XZ)));
    //Underground
    public static final DeferredBlock<AmethystBlock> QUARTZITE = registerBlock("quartzite",
            () -> new AmethystBlock(of().mapColor(MapColor.TERRACOTTA_WHITE).strength(1.3F).sound(SoundType.NETHER_GOLD_ORE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<BuddingQuartziteBlock> BUDDING_QUARTZITE = registerBlock("budding_quartzite",
            () -> new BuddingQuartziteBlock(of().mapColor(MapColor.TERRACOTTA_WHITE).randomTicks().strength(1.3F).sound(SoundType.NETHER_GOLD_ORE).pushReaction(PushReaction.DESTROY).noLootTable()));
    public static final DeferredBlock<AmethystClusterBlock> QUARTZITE_CLUSTER = registerBlock("quartzite_cluster",
            () -> new AmethystClusterBlock(7, 3, of().mapColor(MapColor.TERRACOTTA_WHITE).forceSolidOn().noOcclusion().randomTicks().sound(SoundType.NETHER_GOLD_ORE).strength(1.3F).lightLevel((p_152632_) -> 5).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<AmethystClusterBlock> SMALL_QUARTZITE_BUD = registerBlock("small_quartzite_bud",
            () -> new AmethystClusterBlock(3, 4, ofFullCopy(NMLBlocks.QUARTZITE_CLUSTER.get()).sound(SoundType.NETHER_GOLD_ORE).forceSolidOn().lightLevel((p_187409_) -> 1).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<AmethystClusterBlock> MEDIUM_QUARTZITE_BUD = registerBlock("medium_quartzite_bud",
            () -> new AmethystClusterBlock(4, 3, ofFullCopy(NMLBlocks.QUARTZITE_CLUSTER.get()).sound(SoundType.NETHER_GOLD_ORE).forceSolidOn().lightLevel((p_152617_) -> 2).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<AmethystClusterBlock> LARGE_QUARTZITE_BUD = registerBlock("large_quartzite_bud",
            () -> new AmethystClusterBlock(5, 3, ofFullCopy(NMLBlocks.QUARTZITE_CLUSTER.get()).sound(SoundType.NETHER_GOLD_ORE).forceSolidOn().lightLevel((p_152629_) -> 4).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> SILT = registerBlock("silt",
            () -> new Block(of().mapColor(MapColor.TERRACOTTA_GRAY).sound(SoundType.ROOTED_DIRT).strength(0.6F)));
    public static final DeferredBlock<PathBlock> SILT_PATH = registerBlock("silt_path",
            () -> new PathBlock(ofFullCopy(NMLBlocks.SILT.get()), NMLBlocks.SILT.get(), false));
    //Paths
    public static final DeferredBlock<PathBlock> DIRT_PATH = registerBlock("dirt_path",
            () -> new PathBlock(ofFullCopy(Blocks.DIRT), Blocks.DIRT, false));
    public static final DeferredBlock<PathBlock> MYCELIUM_PATH = registerBlock("mycelium_path",
            () -> new PathBlock(of().mapColor(MapColor.COLOR_PURPLE).strength(0.5F).sound(SoundType.GRASS), Blocks.DIRT, false));
    public static final DeferredBlock<PathBlock> PODZOL_PATH = registerBlock("podzol_path",
            () -> new PathBlock(ofFullCopy(Blocks.PODZOL), Blocks.PODZOL, false));
    public static final DeferredBlock<PathBlock> SNOWY_GRASS_PATH = registerBlock("snowy_grass_path",
            () -> new PathBlock(of().mapColor(MapColor.SNOW).strength(0.5F).sound(SoundType.GRASS), Blocks.DIRT, false));
    public static final DeferredBlock<PathBlock> SNOW_PATH = registerBlock("snow_path",
            () -> new PathBlock(ofFullCopy(Blocks.SNOW_BLOCK), Blocks.SNOW_BLOCK, false));
    public static final DeferredBlock<PathBlock> GRAVEL_PATH = registerBlock("gravel_path",
            () -> new PathBlock(ofFullCopy(Blocks.GRAVEL), Blocks.GRAVEL, true));
    public static final DeferredBlock<PathBlock> SAND_PATH = registerBlock("sand_path",
            () -> new PathBlock(ofFullCopy(Blocks.SAND), Blocks.SAND, true));
    public static final DeferredBlock<PathBlock> RED_SAND_PATH = registerBlock("red_sand_path",
            () -> new PathBlock(ofFullCopy(Blocks.RED_SAND), Blocks.RED_SAND, true));
    //Dungeon
    public static final DeferredBlock<RemainsBlock> REMAINS = BLOCKS.register("remains",
            () -> new RemainsBlock(Blocks.COARSE_DIRT, of().mapColor(MapColor.DIRT).strength(0.25F).sound(SoundType.SUSPICIOUS_SAND).pushReaction(PushReaction.DESTROY), SoundEvents.BRUSH_SAND, SoundEvents.BRUSH_SAND_COMPLETED));
    public static final DeferredBlock<MonsterAnchorBlock> MONSTER_ANCHOR = registerBlock("monster_anchor",
            () -> new MonsterAnchorBlock(ofFullCopy(Blocks.SPAWNER).strength(7, 7).sound(SoundType.TRIAL_SPAWNER).noOcclusion()));
    //Tiles
    public static final DeferredBlock<Block> MUNDANE_TILES = registerBlock("mundane_tiles",
            () -> new Block(ofFullCopy(Blocks.STONE_BRICKS)));
    public static final DeferredBlock<StairBlock> MUNDANE_TILE_STAIRS = registerBlock("mundane_tile_stairs",
            () -> new StairBlock(MUNDANE_TILES.get().defaultBlockState(), ofFullCopy(NMLBlocks.MUNDANE_TILES.get())));
    public static final DeferredBlock<SlabBlock> MUNDANE_TILE_SLAB = registerBlock("mundane_tile_slab",
            () -> new SlabBlock(ofFullCopy(NMLBlocks.MUNDANE_TILES.get())));
    public static final DeferredBlock<Block> EARTHEN_TILES = registerBlock("earthen_tiles",
            () -> new Block(ofFullCopy(Blocks.STONE_BRICKS)));
    public static final DeferredBlock<StairBlock> EARTHEN_TILE_STAIRS = registerBlock("earthen_tile_stairs",
            () -> new StairBlock(EARTHEN_TILES.get().defaultBlockState(), ofFullCopy(NMLBlocks.EARTHEN_TILES.get())));
    public static final DeferredBlock<SlabBlock> EARTHEN_TILE_SLAB = registerBlock("earthen_tile_slab",
            () -> new SlabBlock(ofFullCopy(NMLBlocks.MUNDANE_TILES.get())));
    //Stone
    public static final DeferredBlock<Block> FADED_STONE_BRICKS = registerBlock("faded_stone_bricks",
            () -> new Block(ofFullCopy(Blocks.STONE_BRICKS)));
    public static final DeferredBlock<Block> POLISHED_STONE = registerBlock("polished_stone",
            () -> new Block(ofFullCopy(Blocks.STONE_BRICKS)));
    public static final DeferredBlock<StairBlock> POLISHED_STONE_STAIRS = registerBlock("polished_stone_stairs",
            () -> new StairBlock(POLISHED_STONE.get().defaultBlockState(), ofFullCopy(NMLBlocks.POLISHED_STONE.get())));
    public static final DeferredBlock<SlabBlock> POLISHED_STONE_SLAB = registerBlock("polished_stone_slab",
            () -> new SlabBlock(ofFullCopy(NMLBlocks.POLISHED_STONE.get())));
    public static final DeferredBlock<Block> COBBLESTONE_BRICKS = registerBlock("cobblestone_bricks",
            () -> new Block(ofFullCopy(Blocks.COBBLESTONE)));
    public static final DeferredBlock<StairBlock> COBBLESTONE_BRICK_STAIRS = registerBlock("cobblestone_brick_stairs",
            () -> new StairBlock(COBBLESTONE_BRICKS.get().defaultBlockState(), ofFullCopy(NMLBlocks.COBBLESTONE_BRICKS.get())));
    public static final DeferredBlock<SlabBlock> COBBLESTONE_BRICK_SLAB = registerBlock("cobblestone_brick_slab",
            () -> new SlabBlock(ofFullCopy(NMLBlocks.COBBLESTONE_BRICKS.get())));
    public static final DeferredBlock<WallBlock> COBBLESTONE_BRICK_WALL = registerBlock("cobblestone_brick_wall",
            () -> new WallBlock(ofFullCopy(NMLBlocks.COBBLESTONE_BRICKS.get())));
    public static final DeferredBlock<Block> CRACKED_COBBLESTONE_BRICKS = registerBlock("cracked_cobblestone_bricks",
            () -> new Block(ofFullCopy(NMLBlocks.COBBLESTONE_BRICKS.get())));
    public static final DeferredBlock<Block> MOSSY_COBBLESTONE_BRICKS = registerBlock("mossy_cobblestone_bricks",
            () -> new Block(ofFullCopy(Blocks.MOSSY_COBBLESTONE)));
    public static final DeferredBlock<StairBlock> MOSSY_COBBLESTONE_BRICK_STAIRS = registerBlock("mossy_cobblestone_brick_stairs",
            () -> new StairBlock(MOSSY_COBBLESTONE_BRICKS.get().defaultBlockState(), ofFullCopy(NMLBlocks.MOSSY_COBBLESTONE_BRICKS.get())));
    public static final DeferredBlock<SlabBlock> MOSSY_COBBLESTONE_BRICK_SLAB = registerBlock("mossy_cobblestone_brick_slab",
            () -> new SlabBlock(ofFullCopy(NMLBlocks.MOSSY_COBBLESTONE_BRICKS.get())));
    public static final DeferredBlock<WallBlock> MOSSY_COBBLESTONE_BRICK_WALL = registerBlock("mossy_cobblestone_brick_wall",
            () -> new WallBlock(ofFullCopy(NMLBlocks.MOSSY_COBBLESTONE_BRICKS.get())));
    //Bricks
    public static final DeferredBlock<Block> COARSE_BRICKS = registerBlock("coarse_bricks",
            () -> new Block(ofFullCopy(Blocks.BRICKS)));
    public static final DeferredBlock<StairBlock> COARSE_BRICK_STAIRS = registerBlock("coarse_brick_stairs",
            () -> new StairBlock(COARSE_BRICKS.get().defaultBlockState(), ofFullCopy(NMLBlocks.COARSE_BRICKS.get())));
    public static final DeferredBlock<SlabBlock> COARSE_BRICK_SLAB = registerBlock("coarse_brick_slab",
            () -> new SlabBlock(ofFullCopy(NMLBlocks.COARSE_BRICKS.get())));
    public static final DeferredBlock<Block> COARSE_BRICK_WALL = registerBlock("coarse_brick_wall",
            () -> new WallBlock(ofFullCopy(NMLBlocks.COARSE_BRICKS.get())));
    public static final DeferredBlock<Block> MOSSY_COARSE_BRICKS = registerBlock("mossy_coarse_bricks",
            () -> new Block(ofFullCopy(NMLBlocks.COARSE_BRICKS.get())));
    public static final DeferredBlock<Block> MOSSY_COARSE_BRICK_STAIRS = registerBlock("mossy_coarse_brick_stairs",
            () -> new StairBlock(COARSE_BRICKS.get().defaultBlockState(), ofFullCopy(NMLBlocks.COARSE_BRICKS.get())));
    public static final DeferredBlock<Block> MOSSY_COARSE_BRICK_SLAB = registerBlock("mossy_coarse_brick_slab",
            () -> new SlabBlock(ofFullCopy(NMLBlocks.COARSE_BRICKS.get())));
    public static final DeferredBlock<Block> MOSSY_COARSE_BRICK_WALL = registerBlock("mossy_coarse_brick_wall",
            () -> new WallBlock(ofFullCopy(NMLBlocks.COARSE_BRICKS.get())));
    //Trimmed Planks and Bookshelves
    public static final DeferredBlock<Block> TRIMMED_OAK_PLANKS = registerBlock("trimmed_oak_planks",
            () -> new TrimmedPlankBlock(ofFullCopy(Blocks.OAK_PLANKS)));
    public static final DeferredBlock<Block> SPRUCE_BOOKSHELF = registerBlock("spruce_bookshelf",
            () -> new Block(ofFullCopy(Blocks.BOOKSHELF)));
    public static final DeferredBlock<Block> TRIMMED_SPRUCE_PLANKS = registerBlock("trimmed_spruce_planks",
            () -> new TrimmedPlankBlock(ofFullCopy(Blocks.SPRUCE_PLANKS)));
    public static final DeferredBlock<Block> BIRCH_BOOKSHELF = registerBlock("birch_bookshelf",
            () -> new Block(ofFullCopy(Blocks.BOOKSHELF)));
    public static final DeferredBlock<Block> TRIMMED_BIRCH_PLANKS = registerBlock("trimmed_birch_planks",
            () -> new TrimmedPlankBlock(ofFullCopy(Blocks.BIRCH_PLANKS)));
    public static final DeferredBlock<Block> JUNGLE_BOOKSHELF = registerBlock("jungle_bookshelf",
            () -> new Block(ofFullCopy(Blocks.BOOKSHELF)));
    public static final DeferredBlock<Block> TRIMMED_JUNGLE_PLANKS = registerBlock("trimmed_jungle_planks",
            () -> new TrimmedPlankBlock(ofFullCopy(Blocks.JUNGLE_PLANKS)));
    public static final DeferredBlock<Block> DARK_OAK_BOOKSHELF = registerBlock("dark_oak_bookshelf",
            () -> new Block(ofFullCopy(Blocks.BOOKSHELF)));
    public static final DeferredBlock<Block> TRIMMED_DARK_OAK_PLANKS = registerBlock("trimmed_dark_oak_planks",
            () -> new TrimmedPlankBlock(ofFullCopy(Blocks.DARK_OAK_PLANKS)));
    public static final DeferredBlock<Block> ACACIA_BOOKSHELF = registerBlock("acacia_bookshelf",
            () -> new Block(ofFullCopy(Blocks.BOOKSHELF)));
    public static final DeferredBlock<Block> TRIMMED_ACACIA_PLANKS = registerBlock("trimmed_acacia_planks",
            () -> new TrimmedPlankBlock(ofFullCopy(Blocks.ACACIA_PLANKS)));
    public static final DeferredBlock<Block> MANGROVE_BOOKSHELF = registerBlock("mangrove_bookshelf",
            () -> new Block(ofFullCopy(Blocks.BOOKSHELF)));
    public static final DeferredBlock<Block> TRIMMED_MANGROVE_PLANKS = registerBlock("trimmed_mangrove_planks",
            () -> new TrimmedPlankBlock(ofFullCopy(Blocks.MANGROVE_PLANKS)));
    public static final DeferredBlock<Block> CHERRY_BOOKSHELF = registerBlock("cherry_bookshelf",
            () -> new Block(ofFullCopy(Blocks.BOOKSHELF)));
    public static final DeferredBlock<Block> TRIMMED_CHERRY_PLANKS = registerBlock("trimmed_cherry_planks",
            () -> new TrimmedPlankBlock(ofFullCopy(Blocks.CHERRY_PLANKS)));
    public static final DeferredBlock<Block> WARPED_BOOKSHELF = registerBlock("warped_bookshelf",
            () -> new Block(ofFullCopy(Blocks.BOOKSHELF)));
    public static final DeferredBlock<Block> TRIMMED_WARPED_PLANKS = registerBlock("trimmed_warped_planks",
            () -> new TrimmedPlankBlock(ofFullCopy(Blocks.WARPED_PLANKS)));
    public static final DeferredBlock<Block> CRIMSON_BOOKSHELF = registerBlock("crimson_bookshelf",
            () -> new Block(ofFullCopy(Blocks.BOOKSHELF)));
    public static final DeferredBlock<Block> TRIMMED_CRIMSON_PLANKS = registerBlock("trimmed_crimson_planks",
            () -> new TrimmedPlankBlock(ofFullCopy(Blocks.CRIMSON_PLANKS)));
    public static final DeferredBlock<Block> BAMBOO_BOOKSHELF = registerBlock("bamboo_bookshelf",
            () -> new Block(ofFullCopy(Blocks.BOOKSHELF)));
    public static final DeferredBlock<Block> TRIMMED_BAMBOO_PLANKS = registerBlock("trimmed_bamboo_planks",
            () -> new TrimmedPlankBlock(ofFullCopy(Blocks.BAMBOO_PLANKS)));
    //Pine
    public static final DeferredBlock<Block> PINE_PLANKS = registerBlock("pine_planks",
            () -> new Block(ofFullCopy(Blocks.OAK_PLANKS)));
    public static final DeferredBlock<Block> PINE_STAIRS = registerBlock("pine_stairs",
            () -> new StairBlock(PINE_PLANKS.get().defaultBlockState(), ofFullCopy(NMLBlocks.PINE_PLANKS.get())));
    public static final DeferredBlock<Block> PINE_SLAB = registerBlock("pine_slab",
            () -> new SlabBlock(ofFullCopy(NMLBlocks.PINE_PLANKS.get())));
    public static final DeferredBlock<Block> TRIMMED_PINE_PLANKS = registerBlock("trimmed_pine_planks",
            () -> new TrimmedPlankBlock(ofFullCopy(NMLBlocks.PINE_PLANKS.get())));
    public static final DeferredBlock<Block> PINE_LOG = registerBlock("pine_log",
            () -> new LogBlock(ofFullCopy(Blocks.OAK_LOG)));
    public static final DeferredBlock<Block> PINE_WOOD = registerBlock("pine_wood",
            () -> new LogBlock(ofFullCopy(Blocks.OAK_WOOD)));
    public static final DeferredBlock<Block> STRIPPED_PINE_LOG = registerBlock("stripped_pine_log",
            () -> new LogBlock(ofFullCopy(Blocks.STRIPPED_OAK_LOG)));
    public static final DeferredBlock<Block> STRIPPED_PINE_WOOD = registerBlock("stripped_pine_wood",
            () -> new LogBlock(ofFullCopy(Blocks.STRIPPED_OAK_WOOD)));
    public static final DeferredBlock<Block> PINE_FENCE = registerBlock("pine_fence",
            () -> new FenceBlock(ofFullCopy(Blocks.OAK_FENCE)));
    public static final DeferredBlock<Block> PINE_FENCE_GATE = registerBlock("pine_fence_gate",
            () -> new FenceGateBlock(NMLWoodTypes.PINE, ofFullCopy(Blocks.OAK_FENCE_GATE)));
    public static final DeferredBlock<Block> PINE_LEAVES = registerBlock("pine_leaves",
            () -> new LeavesBlock(ofFullCopy(Blocks.OAK_LEAVES).isViewBlocking((s, g, p) -> false).isSuffocating(((s, g, p) -> false))));
    public static final DeferredBlock<Block> PINE_SAPLING = registerBlock("pine_sapling",
            () -> new SaplingBlock(NMLTreeGrowers.PINE, ofFullCopy(Blocks.OAK_SAPLING)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_PINE_SAPLING = BLOCKS.register("potted_pine_sapling",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), NMLBlocks.PINE_SAPLING,
                    ofFullCopy(Blocks.POTTED_OAK_SAPLING).noOcclusion()));
    public static final DeferredBlock<Block> PINE_BUTTON = registerBlock("pine_button",
            () -> new ButtonBlock(NMLBlockSetTypes.PINE, 15, ofFullCopy(Blocks.OAK_BUTTON)));
    public static final DeferredBlock<Block> PINE_PRESSURE_PLATE = registerBlock("pine_pressure_plate",
            () -> new PressurePlateBlock(NMLBlockSetTypes.PINE, ofFullCopy(Blocks.OAK_PRESSURE_PLATE)));
    public static final DeferredBlock<Block> PINE_DOOR = registerBlock("pine_door",
            () -> new DoorBlock(NMLBlockSetTypes.PINE, ofFullCopy(Blocks.OAK_DOOR)));
    public static final DeferredBlock<Block> PINE_TRAPDOOR = registerBlock("pine_trapdoor",
            () -> new TrapDoorBlock(NMLBlockSetTypes.PINE, ofFullCopy(Blocks.OAK_TRAPDOOR)));
    public static final DeferredBlock<Block> PINE_BOOKSHELF = registerBlock("pine_bookshelf",
            () -> new Block(ofFullCopy(Blocks.BOOKSHELF)));
    public static final DeferredBlock<StandingSignBlock> PINE_SIGN = BLOCKS.register("pine_sign",
            () -> new StandingSignBlock(NMLWoodTypes.PINE, ofFullCopy(Blocks.OAK_SIGN)));
    public static final DeferredBlock<WallSignBlock> PINE_WALL_SIGN = BLOCKS.register("pine_wall_sign",
            () -> new WallSignBlock(NMLWoodTypes.PINE, ofFullCopy(Blocks.OAK_WALL_SIGN).lootFrom(PINE_SIGN)));
    public static final DeferredBlock<CeilingHangingSignBlock> PINE_HANGING_SIGN = BLOCKS.register("pine_hanging_sign",
            () -> new CeilingHangingSignBlock(NMLWoodTypes.PINE, ofFullCopy(Blocks.OAK_HANGING_SIGN)));
    public static final DeferredBlock<WallHangingSignBlock> PINE_HANGING_WALL_SIGN = BLOCKS.register("pine_wall_hanging_sign",
            () -> new WallHangingSignBlock(NMLWoodTypes.PINE, ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN).lootFrom(PINE_HANGING_SIGN)));

    //Maple
    public static final DeferredBlock<Block> MAPLE_PLANKS = registerBlock("maple_planks",
            () -> new Block(ofFullCopy(Blocks.OAK_PLANKS)));
    public static final DeferredBlock<Block> MAPLE_STAIRS = registerBlock("maple_stairs",
            () -> new StairBlock(MAPLE_PLANKS.get().defaultBlockState(), ofFullCopy(NMLBlocks.MAPLE_PLANKS.get())));
    public static final DeferredBlock<Block> MAPLE_SLAB = registerBlock("maple_slab",
            () -> new SlabBlock(ofFullCopy(NMLBlocks.MAPLE_PLANKS.get())));
    public static final DeferredBlock<Block> TRIMMED_MAPLE_PLANKS = registerBlock("trimmed_maple_planks",
            () -> new TrimmedPlankBlock(ofFullCopy(NMLBlocks.MAPLE_PLANKS.get())));
    public static final DeferredBlock<Block> MAPLE_LOG = registerBlock("maple_log",
            () -> new LogBlock(ofFullCopy(Blocks.OAK_LOG)));
    public static final DeferredBlock<Block> MAPLE_WOOD = registerBlock("maple_wood",
            () -> new LogBlock(ofFullCopy(Blocks.OAK_WOOD)));
    public static final DeferredBlock<Block> STRIPPED_MAPLE_LOG = registerBlock("stripped_maple_log",
            () -> new LogBlock(ofFullCopy(Blocks.STRIPPED_OAK_LOG)));
    public static final DeferredBlock<Block> STRIPPED_MAPLE_WOOD = registerBlock("stripped_maple_wood",
            () -> new LogBlock(ofFullCopy(Blocks.STRIPPED_OAK_WOOD)));
    public static final DeferredBlock<Block> MAPLE_FENCE = registerBlock("maple_fence",
            () -> new FenceBlock(ofFullCopy(Blocks.OAK_FENCE)));
    public static final DeferredBlock<Block> MAPLE_FENCE_GATE = registerBlock("maple_fence_gate",
            () -> new FenceGateBlock(NMLWoodTypes.MAPLE, ofFullCopy(Blocks.OAK_FENCE_GATE)));
    public static final DeferredBlock<Block> MAPLE_LEAVES = registerBlock("maple_leaves",
            () -> new LeavesBlock(ofFullCopy(Blocks.OAK_LEAVES).isViewBlocking((s, g, p) -> false).isSuffocating(((s, g, p) -> false))));
    public static final DeferredBlock<Block> MAPLE_SAPLING = registerBlock("maple_sapling",
            () -> new SaplingBlock(NMLTreeGrowers.MAPLE, ofFullCopy(Blocks.OAK_SAPLING)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_MAPLE_SAPLING = BLOCKS.register("potted_maple_sapling",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), NMLBlocks.MAPLE_SAPLING,
                    ofFullCopy(Blocks.POTTED_OAK_SAPLING).noOcclusion()));
    public static final DeferredBlock<Block> RED_MAPLE_LEAVES = registerBlock("red_maple_leaves",
            () -> new LeavesBlock(ofFullCopy(Blocks.OAK_LEAVES).isViewBlocking((s, g, p) -> false).isSuffocating(((s, g, p) -> false))));
    public static final DeferredBlock<Block> RED_MAPLE_SAPLING = registerBlock("red_maple_sapling",
            () -> new SaplingBlock(NMLTreeGrowers.RED_MAPLE, ofFullCopy(Blocks.OAK_SAPLING)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_RED_MAPLE_SAPLING = BLOCKS.register("potted_red_maple_sapling",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), NMLBlocks.RED_MAPLE_SAPLING,
                    ofFullCopy(Blocks.POTTED_OAK_SAPLING).noOcclusion()));
    public static final DeferredBlock<Block> MAPLE_BUTTON = registerBlock("maple_button",
            () -> new ButtonBlock(NMLBlockSetTypes.MAPLE, 15, ofFullCopy(Blocks.OAK_BUTTON)));
    public static final DeferredBlock<Block> MAPLE_PRESSURE_PLATE = registerBlock("maple_pressure_plate",
            () -> new PressurePlateBlock(NMLBlockSetTypes.MAPLE,
                    ofFullCopy(Blocks.OAK_PRESSURE_PLATE)));
    public static final DeferredBlock<Block> MAPLE_DOOR = registerBlock("maple_door",
            () -> new DoorBlock(NMLBlockSetTypes.MAPLE, ofFullCopy(Blocks.OAK_DOOR)));
    public static final DeferredBlock<Block> MAPLE_TRAPDOOR = registerBlock("maple_trapdoor",
            () -> new TrapDoorBlock(NMLBlockSetTypes.MAPLE, ofFullCopy(Blocks.OAK_TRAPDOOR)));
    public static final DeferredBlock<Block> MAPLE_BOOKSHELF = registerBlock("maple_bookshelf",
            () -> new Block(ofFullCopy(Blocks.BOOKSHELF)));
    public static final DeferredBlock<StandingSignBlock> MAPLE_SIGN = BLOCKS.register("maple_sign",
            () -> new StandingSignBlock(NMLWoodTypes.MAPLE, ofFullCopy(Blocks.OAK_SIGN)));
    public static final DeferredBlock<WallSignBlock> MAPLE_WALL_SIGN = BLOCKS.register("maple_wall_sign",
            () -> new WallSignBlock(NMLWoodTypes.MAPLE, ofFullCopy(Blocks.OAK_WALL_SIGN).lootFrom(MAPLE_SIGN)));
    public static final DeferredBlock<CeilingHangingSignBlock> MAPLE_HANGING_SIGN = BLOCKS.register("maple_hanging_sign",
            () -> new CeilingHangingSignBlock(NMLWoodTypes.MAPLE, ofFullCopy(Blocks.OAK_HANGING_SIGN)));
    public static final DeferredBlock<WallHangingSignBlock> MAPLE_HANGING_WALL_SIGN = BLOCKS.register("maple_wall_hanging_sign",
            () -> new WallHangingSignBlock(NMLWoodTypes.MAPLE, ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN).lootFrom(MAPLE_HANGING_SIGN)));

    //Walnut
    public static final DeferredBlock<Block> WALNUT_PLANKS = registerBlock("walnut_planks",
            () -> new Block(ofFullCopy(Blocks.OAK_PLANKS)));
    public static final DeferredBlock<Block> WALNUT_STAIRS = registerBlock("walnut_stairs",
            () -> new StairBlock(WALNUT_PLANKS.get().defaultBlockState(), ofFullCopy(NMLBlocks.WALNUT_PLANKS.get())));
    public static final DeferredBlock<Block> WALNUT_SLAB = registerBlock("walnut_slab",
            () -> new SlabBlock(ofFullCopy(NMLBlocks.WALNUT_PLANKS.get())));
    public static final DeferredBlock<Block> TRIMMED_WALNUT_PLANKS = registerBlock("trimmed_walnut_planks",
            () -> new TrimmedPlankBlock(ofFullCopy(NMLBlocks.WALNUT_PLANKS.get())));
    public static final DeferredBlock<Block> WALNUT_LOG = registerBlock("walnut_log",
            () -> new LogBlock(ofFullCopy(Blocks.OAK_LOG)));
    public static final DeferredBlock<Block> WALNUT_WOOD = registerBlock("walnut_wood",
            () -> new LogBlock(ofFullCopy(Blocks.OAK_WOOD)));
    public static final DeferredBlock<Block> STRIPPED_WALNUT_LOG = registerBlock("stripped_walnut_log",
            () -> new LogBlock(ofFullCopy(Blocks.STRIPPED_OAK_LOG)));
    public static final DeferredBlock<Block> STRIPPED_WALNUT_WOOD = registerBlock("stripped_walnut_wood",
            () -> new LogBlock(ofFullCopy(Blocks.STRIPPED_OAK_WOOD)));
    public static final DeferredBlock<Block> WALNUT_FENCE = registerBlock("walnut_fence",
            () -> new FenceBlock(ofFullCopy(Blocks.OAK_FENCE)));
    public static final DeferredBlock<Block> WALNUT_FENCE_GATE = registerBlock("walnut_fence_gate",
            () -> new FenceGateBlock(NMLWoodTypes.WALNUT, ofFullCopy(Blocks.OAK_FENCE_GATE)));
    public static final DeferredBlock<Block> WALNUT_LEAVES = registerBlock("walnut_leaves",
            () -> new LeavesBlock(ofFullCopy(Blocks.OAK_LEAVES).isViewBlocking((s, g, p) -> false).isSuffocating(((s, g, p) -> false))));
    public static final DeferredBlock<Block> WALNUT_SAPLING = registerBlock("walnut_sapling",
            () -> new SaplingBlock(NMLTreeGrowers.WALNUT, ofFullCopy(Blocks.OAK_SAPLING)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_WALNUT_SAPLING = BLOCKS.register("potted_walnut_sapling",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), NMLBlocks.WALNUT_SAPLING,
                    ofFullCopy(Blocks.POTTED_OAK_SAPLING).noOcclusion()));
    public static final DeferredBlock<Block> WALNUT_BUTTON = registerBlock("walnut_button",
            () -> new ButtonBlock(NMLBlockSetTypes.WALNUT, 15, ofFullCopy(Blocks.OAK_BUTTON)));
    public static final DeferredBlock<Block> WALNUT_PRESSURE_PLATE = registerBlock("walnut_pressure_plate",
            () -> new PressurePlateBlock(NMLBlockSetTypes.WALNUT, ofFullCopy(Blocks.OAK_PRESSURE_PLATE)));
    public static final DeferredBlock<Block> WALNUT_DOOR = registerBlock("walnut_door",
            () -> new DoorBlock(NMLBlockSetTypes.WALNUT, ofFullCopy(Blocks.OAK_DOOR)));
    public static final DeferredBlock<Block> WALNUT_TRAPDOOR = registerBlock("walnut_trapdoor",
            () -> new TrapDoorBlock(NMLBlockSetTypes.WALNUT, ofFullCopy(Blocks.OAK_TRAPDOOR)));
    public static final DeferredBlock<Block> WALNUT_BOOKSHELF = registerBlock("walnut_bookshelf",
            () -> new Block(ofFullCopy(Blocks.BOOKSHELF)));
    public static final DeferredBlock<StandingSignBlock> WALNUT_SIGN = BLOCKS.register("walnut_sign",
            () -> new StandingSignBlock(NMLWoodTypes.WALNUT, ofFullCopy(Blocks.OAK_SIGN)));
    public static final DeferredBlock<WallSignBlock> WALNUT_WALL_SIGN = BLOCKS.register("walnut_wall_sign",
            () -> new WallSignBlock(NMLWoodTypes.WALNUT, ofFullCopy(Blocks.OAK_WALL_SIGN).lootFrom(WALNUT_SIGN)));
    public static final DeferredBlock<CeilingHangingSignBlock> WALNUT_HANGING_SIGN = BLOCKS.register("walnut_hanging_sign",
            () -> new CeilingHangingSignBlock(NMLWoodTypes.WALNUT, ofFullCopy(Blocks.OAK_HANGING_SIGN)));
    public static final DeferredBlock<WallHangingSignBlock> WALNUT_HANGING_WALL_SIGN = BLOCKS.register("walnut_wall_hanging_sign",
            () -> new WallHangingSignBlock(NMLWoodTypes.WALNUT, ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN).lootFrom(WALNUT_HANGING_SIGN)));

//Willow
    public static final DeferredBlock<Block> WILLOW_PLANKS = registerBlock("willow_planks",
        () -> new Block(ofFullCopy(Blocks.OAK_PLANKS)));
    public static final DeferredBlock<StairBlock> WILLOW_STAIRS = registerBlock("willow_stairs",
            () -> new StairBlock(WILLOW_PLANKS.get().defaultBlockState(), ofFullCopy(NMLBlocks.WILLOW_PLANKS.get())));
    public static final DeferredBlock<SlabBlock> WILLOW_SLAB = registerBlock("willow_slab",
            () -> new SlabBlock(ofFullCopy(NMLBlocks.WILLOW_PLANKS.get())));
    public static final DeferredBlock<TrimmedPlankBlock> TRIMMED_WILLOW_PLANKS = registerBlock("trimmed_willow_planks",
            () -> new TrimmedPlankBlock(ofFullCopy(NMLBlocks.WILLOW_PLANKS.get())));
    public static final DeferredBlock<LogBlock> WILLOW_LOG = registerBlock("willow_log",
            () -> new LogBlock(ofFullCopy(Blocks.OAK_LOG)));
    public static final DeferredBlock<LogBlock> WILLOW_WOOD = registerBlock("willow_wood",
            () -> new LogBlock(ofFullCopy(Blocks.OAK_WOOD)));
    public static final DeferredBlock<LogBlock> STRIPPED_WILLOW_LOG = registerBlock("stripped_willow_log",
            () -> new LogBlock(ofFullCopy(Blocks.STRIPPED_OAK_LOG)));
    public static final DeferredBlock<LogBlock> STRIPPED_WILLOW_WOOD = registerBlock("stripped_willow_wood",
            () -> new LogBlock(ofFullCopy(Blocks.STRIPPED_OAK_WOOD)));
    public static final DeferredBlock<FenceBlock> WILLOW_FENCE = registerBlock("willow_fence",
            () -> new FenceBlock(ofFullCopy(Blocks.OAK_FENCE)));
    public static final DeferredBlock<FenceGateBlock> WILLOW_FENCE_GATE = registerBlock("willow_fence_gate",
            () -> new FenceGateBlock(NMLWoodTypes.WILLOW, ofFullCopy(Blocks.OAK_FENCE_GATE)));
    public static final DeferredBlock<LeavesBlock> WILLOW_LEAVES = registerBlock("willow_leaves",
            () -> new LeavesBlock(ofFullCopy(Blocks.OAK_LEAVES).isViewBlocking((s, g, p) -> false).isSuffocating(((s, g, p) -> false))));
    public static final DeferredBlock<SaplingBlock> WILLOW_SAPLING = registerBlock("willow_sapling",
            () -> new SaplingBlock(NMLTreeGrowers.WILLOW, ofFullCopy(Blocks.OAK_SAPLING)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_WILLOW_SAPLING = BLOCKS.register("potted_willow_sapling",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), NMLBlocks.WILLOW_SAPLING,
                    ofFullCopy(Blocks.POTTED_OAK_SAPLING).noOcclusion()));
    public static final DeferredBlock<ButtonBlock> WILLOW_BUTTON = registerBlock("willow_button",
            () -> new ButtonBlock(NMLBlockSetTypes.WILLOW, 15, ofFullCopy(Blocks.OAK_BUTTON)));
    public static final DeferredBlock<PressurePlateBlock> WILLOW_PRESSURE_PLATE = registerBlock("willow_pressure_plate",
            () -> new PressurePlateBlock(NMLBlockSetTypes.WILLOW, ofFullCopy(Blocks.OAK_PRESSURE_PLATE)));
    public static final DeferredBlock<DoorBlock> WILLOW_DOOR = registerBlock("willow_door",
            () -> new DoorBlock(NMLBlockSetTypes.WILLOW, ofFullCopy(Blocks.OAK_DOOR)));
    public static final DeferredBlock<TrapDoorBlock> WILLOW_TRAPDOOR = registerBlock("willow_trapdoor",
            () -> new TrapDoorBlock(NMLBlockSetTypes.WILLOW, ofFullCopy(Blocks.OAK_TRAPDOOR)));
    public static final DeferredBlock<Block> WILLOW_BOOKSHELF = registerBlock("willow_bookshelf",
            () -> new Block(ofFullCopy(Blocks.BOOKSHELF)));
    public static final DeferredBlock<StandingSignBlock> WILLOW_SIGN = BLOCKS.register("willow_sign",
            () -> new StandingSignBlock(NMLWoodTypes.WILLOW, ofFullCopy(Blocks.OAK_SIGN)));
    public static final DeferredBlock<WallSignBlock> WILLOW_WALL_SIGN = BLOCKS.register("willow_wall_sign",
            () -> new WallSignBlock(NMLWoodTypes.WILLOW, ofFullCopy(Blocks.OAK_WALL_SIGN).lootFrom(WILLOW_SIGN)));
    public static final DeferredBlock<CeilingHangingSignBlock> WILLOW_HANGING_SIGN = BLOCKS.register("willow_hanging_sign",
            () -> new CeilingHangingSignBlock(NMLWoodTypes.WILLOW, ofFullCopy(Blocks.OAK_HANGING_SIGN)));
    public static final DeferredBlock<WallHangingSignBlock> WILLOW_HANGING_WALL_SIGN = BLOCKS.register("willow_wall_hanging_sign",
            () -> new WallHangingSignBlock(NMLWoodTypes.WILLOW, ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN).lootFrom(WILLOW_HANGING_SIGN)));

    public static final DeferredBlock<TapBlock> TAP = registerBlock("tap",
            () -> new TapBlock(of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().noOcclusion().strength(2.0F).randomTicks().pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<SpikeTrapBlock> SPIKE_TRAP = registerBlock("spike_trap",
            () -> new SpikeTrapBlock(of().mapColor(MapColor.METAL).strength(1.5F, 6.0F).requiresCorrectToolForDrops().noOcclusion()));


    //Storage
    public static final DeferredBlock<Block> COD_BARREL = registerBlock("cod_barrel",
            () -> new Block(ofFullCopy(Blocks.BARREL)));
    public static final DeferredBlock<Block> SALMON_BARREL = registerBlock("salmon_barrel",
            () -> new Block(ofFullCopy(NMLBlocks.COD_BARREL.get())));
    public static final DeferredBlock<Block> BILLHOOK_BASS_BARREL = registerBlock("billhook_bass_barrel",
            () -> new Block(ofFullCopy(NMLBlocks.COD_BARREL.get())));
    public static final DeferredBlock<Block> PUFFERFISH_BARREL = registerBlock("pufferfish_barrel",
            () -> new Block(ofFullCopy(NMLBlocks.COD_BARREL.get())));
    public static final DeferredBlock<Block> TROPICAL_FISH_BARREL = registerBlock("tropical_fish_barrel",
            () -> new Block(ofFullCopy(NMLBlocks.COD_BARREL.get())));
//    public static final DeferredBlock<Block> CAVE_CARP_BARREL = registerBlock("cave_carp_barrel",
//            () -> new Block(ofFullCopy(NMLBlocks.COD_BARREL.get())));
    public static final DeferredBlock<Block> APPLE_CRATE = registerBlock("apple_crate",
            () -> new Block(ofFullCopy(Blocks.BARREL)));
    public static final DeferredBlock<Block> PEAR_CRATE = registerBlock("pear_crate",
            () -> new Block(ofFullCopy(Blocks.BARREL)));

    //Mushrooms
    public static final DeferredBlock<SurfaceMushroomBlock> FIELD_MUSHROOM = BLOCKS.register("field_mushroom",
            () -> new SurfaceMushroomBlock((HugeMushrooms.HUGE_FIELD_MUSHROOM), (ofFullCopy(Blocks.RED_MUSHROOM).mapColor(MapColor.TERRACOTTA_WHITE))));
    public static final DeferredBlock<FlowerPotBlock> POTTED_FIELD_MUSHROOM = BLOCKS.register("potted_field_mushroom",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), NMLBlocks.FIELD_MUSHROOM,
                    ofFullCopy(Blocks.POTTED_RED_MUSHROOM).noOcclusion()));
    public static final DeferredBlock<HugeMushroomBlock> FIELD_MUSHROOM_BLOCK = registerBlock("field_mushroom_block",
            () -> new HugeMushroomBlock((ofFullCopy(Blocks.RED_MUSHROOM_BLOCK).mapColor(MapColor.TERRACOTTA_WHITE))));

    public static final DeferredBlock<ShelfMushroomBlock> SHELF_MUSHROOM = registerBlock("shelf_mushroom",
            () -> new ShelfMushroomBlock((ofFullCopy(Blocks.BROWN_MUSHROOM))));
    public static final DeferredBlock<SlabBlock> SHELF_MUSHROOM_BLOCK = registerBlock("shelf_mushroom_block",
            () -> new SlabBlock((ofFullCopy(Blocks.BROWN_MUSHROOM_BLOCK))));

    // Fruity Stuff
    public static final DeferredBlock<Block> APPLE_FRUIT = BLOCKS.register("apple_fruit",
            () -> new FruitBlock(of()
                    .mapColor(MapColor.PLANT)
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.AZALEA)
                    .pushReaction(PushReaction.DESTROY)
                    .offsetType(OffsetType.XYZ)
                    .dynamicShape()
                    ,FruitType.APPLE_OAK));

    public static final DeferredBlock<Block> APPLE_FRUIT_LEAVES = registerBlock("apple_fruit_leaves",
            () -> new FruitLeavesBlock(ofFullCopy(Blocks.OAK_LEAVES).isViewBlocking((s, g, p) -> false).isSuffocating(((s, g, p) -> false)), FruitType.APPLE_OAK));

    public static final DeferredBlock<Block> PEAR_FRUIT = BLOCKS.register("pear_fruit",
            () -> new FruitBlock(of()
                    .mapColor(MapColor.PLANT)
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.AZALEA)
                    .pushReaction(PushReaction.DESTROY)
                    .offsetType(OffsetType.XYZ)
                    .dynamicShape()
                    ,FruitType.PEAR_AUTUMNAL_OAK));

    public static final DeferredBlock<Block> PEAR_FRUIT_LEAVES = registerBlock("pear_fruit_leaves",
            () -> new FruitLeavesBlock(ofFullCopy(NMLBlocks.AUTUMNAL_OAK_LEAVES.get()).isViewBlocking((s, g, p) -> false).isSuffocating(((s, g, p) -> false)), FruitType.PEAR_AUTUMNAL_OAK));


    @SuppressWarnings("unchecked")
    public static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<? extends Block> block) {
        DeferredBlock<Block> toReturn = BLOCKS.register(name, block);
        CREATIVE_TAB_ITEMS.add(registerBlockItem(name, toReturn));
        return (DeferredBlock<T>) toReturn;
    }

    public static DeferredHolder<Item, BlockItem> registerBlockItem(String name, Supplier<? extends Block> block) {
        return NMLItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
}
