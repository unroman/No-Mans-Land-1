package com.farcr.nomansland.common.registry.blocks;

import com.farcr.nomansland.common.registry.NMLTags;
import com.farcr.nomansland.datagen.loot.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;

import java.util.List;

public record BlockProperties(BlockLootType loot, boolean customLang, List<TagKey<Block>> blockTags, List<TagKey<Item>> itemTags) {
    public BlockProperties(BlockLootType loot, boolean customLang) {
        this(loot, customLang, List.of(), List.of());
    }
    // MISC
    public static BlockProperties simplePath(Block block) {
        return new BlockProperties(new OtherBlockLootType(() -> block), false, List.of(NMLTags.PATHS), List.of());
    } // paths
    public static BlockProperties simplePath(BlockDefinition<?> block) {
        return new BlockProperties(new OtherBlockLootType(() -> block.block().get()), false, List.of(NMLTags.PATHS), List.of());
    } // paths
    public static BlockProperties path() {
        return new BlockProperties(new CustomBlockLootType(), false, List.of(NMLTags.PATHS), List.of());
    } // paths
    public static BlockProperties fishBarrel() {
        return new BlockProperties(new SelfBlockLootType(), false, List.of(NMLTags.FISH_BARRELS), List.of());
    } // fish_barrel
    public static BlockProperties dirtLike() {
        return new BlockProperties(new SelfBlockLootType(), false, List.of(BlockTags.MINEABLE_WITH_SHOVEL), List.of());
    } // shovel
    public static BlockProperties cauldron() {
        return new BlockProperties(new OtherBlockLootType(()-> Blocks.CAULDRON), true, List.of(BlockTags.CAULDRONS), List.of());
    } // cauldrons

    // PLANTS
    public static BlockProperties smallFlower() {
        return new BlockProperties(new SelfBlockLootType(), false, List.of(BlockTags.SMALL_FLOWERS), List.of(ItemTags.SMALL_FLOWERS));
    } // small_flowers
    public static BlockProperties flowerbed() {
        return new BlockProperties(new ShearsBlockLootType(), false, List.of(BlockTags.SMALL_FLOWERS), List.of(ItemTags.SMALL_FLOWERS));
    } // small_flowers
    public static BlockProperties sapling() {
        return new BlockProperties(new SelfBlockLootType(), false, List.of(BlockTags.SAPLINGS), List.of(ItemTags.SAPLINGS));
    } // saplings
    public static BlockProperties flowerPot(BlockDefinition<?> plant) {
        return new BlockProperties(new FlowerPotBlockLootType(() -> plant.block().get()), false, List.of(BlockTags.FLOWER_POTS), List.of());
    } // flower_pots

    // STONE
    public static BlockProperties stoneLike() {
        return new BlockProperties(new SelfBlockLootType(), false, List.of(BlockTags.MINEABLE_WITH_PICKAXE), List.of());
    } // pickaxe
    public static BlockProperties stoneLikeStairs() {
        return new BlockProperties(new SelfBlockLootType(), false, List.of(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.STAIRS), List.of(ItemTags.STAIRS));
    } // pickaxe, stairs
    public static BlockProperties stoneLikeSlab() {
        return new BlockProperties(new SlabBlockLootType(), false, List.of(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.SLABS), List.of(ItemTags.SLABS));
    } // pickaxe, slabs
    public static BlockProperties stoneLikeWall() {
        return new BlockProperties(new SelfBlockLootType(), false, List.of(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.WALLS), List.of(ItemTags.WALLS));
    } // pickaxe, walls

    // WOOD
    public static BlockProperties log() {
        return new BlockProperties(new SelfBlockLootType(), false);
    } // no easily-defined tags - logs are weird!
    public static BlockProperties planks() {
        return new BlockProperties(new SelfBlockLootType(), false, List.of(BlockTags.PLANKS), List.of(ItemTags.PLANKS));
    } // planks
    public static BlockProperties woodenButton(BlockDefinition<?> planks) {
        return new BlockProperties(new SelfBlockLootType(), false, List.of(BlockTags.WOODEN_BUTTONS), List.of(ItemTags.WOODEN_BUTTONS));
    } // wooden_buttons
    public static BlockProperties woodenDoor(BlockDefinition<?> planks) {
        return new BlockProperties(new DoorBlockLootType(), false, List.of(BlockTags.WOODEN_DOORS), List.of(ItemTags.WOODEN_DOORS));
    } // wooden_doors
    public static BlockProperties woodenFence(BlockDefinition<?> planks) {
        return new BlockProperties(new SelfBlockLootType(), false, List.of(BlockTags.WOODEN_FENCES), List.of(ItemTags.WOODEN_FENCES));
    } // wooden_fences
    public static BlockProperties fenceGate(BlockDefinition<?> planks) {
        return new BlockProperties(new SelfBlockLootType(), false, List.of(BlockTags.FENCE_GATES), List.of(ItemTags.FENCE_GATES));
    } // fence_gates
    public static BlockProperties woodenPressurePlate(BlockDefinition<?> planks) {
        return new BlockProperties(new SelfBlockLootType(), false, List.of(BlockTags.WOODEN_PRESSURE_PLATES), List.of(ItemTags.WOODEN_PRESSURE_PLATES));
    } // wooden_pressure_plates
    public static BlockProperties woodenSlab(BlockDefinition<?> planks) {
        return new BlockProperties(new SlabBlockLootType(), false, List.of(BlockTags.WOODEN_SLABS), List.of(ItemTags.WOODEN_SLABS));
    } // wooden_slabs
    public static BlockProperties woodenStairs(BlockDefinition<?> planks) {
        return new BlockProperties(new SelfBlockLootType(), false, List.of(BlockTags.WOODEN_STAIRS), List.of(ItemTags.WOODEN_STAIRS));
    } // wooden_stairs
    public static BlockProperties woodenTrapdoor(BlockDefinition<?> planks) {
        return new BlockProperties(new SelfBlockLootType(), false, List.of(BlockTags.WOODEN_TRAPDOORS), List.of(ItemTags.WOODEN_TRAPDOORS));
    } // wooden_trapdoors
    public static BlockProperties bookshelf(BlockDefinition<?> planks) {
        return new BlockProperties(new BookshelfBlockLootType(), false, List.of(Tags.Blocks.BOOKSHELVES), List.of(Tags.Items.BOOKSHELVES));
    } // bookshelves
    public static BlockProperties bookshelf(Block planks) {
        return new BlockProperties(new BookshelfBlockLootType(), false, List.of(Tags.Blocks.BOOKSHELVES), List.of(Tags.Items.BOOKSHELVES));
    } // bookshelves
    public static BlockProperties trimmedPlanks(BlockDefinition<?> planks) {
        return new BlockProperties(new SelfBlockLootType(), false, List.of(NMLTags.TRIMMED_PLANKS), List.of());
    } // trimmed_planks
    public static BlockProperties trimmedPlanks(Block planks) {
        return new BlockProperties(new SelfBlockLootType(), false, List.of(NMLTags.TRIMMED_PLANKS), List.of());
    } // trimmed_planks
    public static BlockProperties sign() {
        return new BlockProperties(new SelfBlockLootType(), false);
    } // hard to determine
    public static BlockProperties hangingSign() {
        return new BlockProperties(new SelfBlockLootType(), false);
    } // also hard to determine
}
