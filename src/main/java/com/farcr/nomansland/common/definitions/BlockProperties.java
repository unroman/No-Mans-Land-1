package com.farcr.nomansland.common.definitions;

import com.farcr.nomansland.datagen.loot.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public record BlockProperties(BlockLootType lootType) {

    public static BlockProperties basic() {
        return new BlockProperties(new CustomBlockLootType());
    }
    // MISC
    public static BlockProperties simplePath(Block block) {
        return new BlockProperties(new OtherBlockLootType(() -> block));
    } // paths
    public static BlockProperties simplePath(BlockDefinition<?> block) {
        return new BlockProperties(new OtherBlockLootType(block::get));
    } // paths
    public static BlockProperties path() {
        return new BlockProperties(new CustomBlockLootType());
    } // paths
    public static BlockProperties fishBarrel() {
        return new BlockProperties(new SelfBlockLootType());
    } // fish_barrel
    public static BlockProperties dirtLike() {
        return new BlockProperties(new SelfBlockLootType());
    } // shovel
    public static BlockProperties cauldron() {
        return new BlockProperties(new OtherBlockLootType(()-> Blocks.CAULDRON));
    } // cauldrons

    // PLANTS
    public static BlockProperties smallFlower() {
        return new BlockProperties(new SelfBlockLootType());
    } // small_flowers
    public static BlockProperties flowerbed() {
        return new BlockProperties(new ShearsBlockLootType());
    } // small_flowers
    public static BlockProperties sapling() {
        return new BlockProperties(new SelfBlockLootType());
    } // saplings
    public static BlockProperties flowerPot(BlockDefinition<?> plant) {
        return new BlockProperties(new FlowerPotBlockLootType(plant::get));
    } // flower_pots

    // STONE
    public static BlockProperties stoneLike() {
        return new BlockProperties(new SelfBlockLootType());
    } // pickaxe
    public static BlockProperties stoneLikeStairs() {
        return new BlockProperties(new SelfBlockLootType());
    } // pickaxe, stairs
    public static BlockProperties stoneLikeSlab() {
        return new BlockProperties(new SlabBlockLootType());
    } // pickaxe, slabs
    public static BlockProperties stoneLikeWall() {
        return new BlockProperties(new SelfBlockLootType());
    } // pickaxe, walls

    // WOOD
    public static BlockProperties log() {
        return new BlockProperties(new SelfBlockLootType());
    } // no easily-defined tags - logs are weird!
    public static BlockProperties planks() {
        return new BlockProperties(new SelfBlockLootType());
    } // planks
    public static BlockProperties woodenButton(BlockDefinition<?> planks) {
        return new BlockProperties(new SelfBlockLootType());
    } // wooden_buttons
    public static BlockProperties woodenDoor(BlockDefinition<?> planks) {
        return new BlockProperties(new DoorBlockLootType());
    } // wooden_doors
    public static BlockProperties woodenFence(BlockDefinition<?> planks) {
        return new BlockProperties(new SelfBlockLootType());
    } // wooden_fences
    public static BlockProperties fenceGate(BlockDefinition<?> planks) {
        return new BlockProperties(new SelfBlockLootType());
    } // fence_gates
    public static BlockProperties woodenPressurePlate(BlockDefinition<?> planks) {
        return new BlockProperties(new SelfBlockLootType());
    } // wooden_pressure_plates
    public static BlockProperties woodenSlab(BlockDefinition<?> planks) {
        return new BlockProperties(new SlabBlockLootType());
    } // wooden_slabs
    public static BlockProperties woodenStairs(BlockDefinition<?> planks) {
        return new BlockProperties(new SelfBlockLootType());
    } // wooden_stairs
    public static BlockProperties woodenTrapdoor(BlockDefinition<?> planks) {
        return new BlockProperties(new SelfBlockLootType());
    } // wooden_trapdoors
    public static BlockProperties bookshelf(BlockDefinition<?> planks) {
        return new BlockProperties(new BookshelfBlockLootType());
    } // bookshelves
    public static BlockProperties bookshelf(Block planks) {
        return new BlockProperties(new BookshelfBlockLootType());
    } // bookshelves
    public static BlockProperties trimmedPlanks(BlockDefinition<?> planks) {
        return new BlockProperties(new SelfBlockLootType());
    } // trimmed_planks
    public static BlockProperties trimmedPlanks(Block planks) {
        return new BlockProperties(new SelfBlockLootType());
    } // trimmed_planks
    public static BlockProperties sign() {
        return new BlockProperties(new SelfBlockLootType());
    } // hard to determine
    public static BlockProperties hangingSign() {
        return new BlockProperties(new SelfBlockLootType());
    } // also hard to determine
}
