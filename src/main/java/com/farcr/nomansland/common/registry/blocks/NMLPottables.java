package com.farcr.nomansland.common.registry.blocks;

import com.farcr.nomansland.common.definitions.BlockDefinition;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.FlowerPotBlock;

public class NMLPottables {

    public static void register() {
        for (BlockDefinition<?> definition : NMLBlocks.BLOCK_DEFINITIONS) {
            if (definition.get() instanceof FlowerPotBlock flowerPotBlock) {
                flowerPotBlock.getEmptyPot().addPlant(BuiltInRegistries.BLOCK.createIntrusiveHolder(flowerPotBlock.getPotted()).key().location(), () -> flowerPotBlock);
            }
        }

//        FlowerPotBlock flowerPotBlock = ((FlowerPotBlock) Blocks.FLOWER_POT);
//        flowerPotBlock.addPlant(NMLBlocks.PINE_SAPLING.getId(), NMLBlocks.POTTED_PINE_SAPLING);
//        flowerPotBlock.addPlant(NMLBlocks.YELLOW_BIRCH_SAPLING.getId(), NMLBlocks.POTTED_YELLOW_BIRCH_SAPLING);
//        flowerPotBlock.addPlant(NMLBlocks.AUTUMNAL_OAK_SAPLING.getId(), NMLBlocks.POTTED_AUTUMNAL_OAK_SAPLING);
//        flowerPotBlock.addPlant(NMLBlocks.PALE_CHERRY_SAPLING.getId(), NMLBlocks.POTTED_PALE_CHERRY_SAPLING);
//        flowerPotBlock.addPlant(NMLBlocks.MAPLE_SAPLING.getId(), NMLBlocks.POTTED_MAPLE_SAPLING);
//        flowerPotBlock.addPlant(NMLBlocks.RED_MAPLE_SAPLING.getId(), NMLBlocks.POTTED_RED_MAPLE_SAPLING);
//        flowerPotBlock.addPlant(NMLBlocks.WALNUT_SAPLING.getId(), NMLBlocks.POTTED_WALNUT_SAPLING);
//        flowerPotBlock.addPlant(NMLBlocks.WILLOW_SAPLING.getId(), NMLBlocks.POTTED_WILLOW_SAPLING);
//        flowerPotBlock.addPlant(NMLBlocks.FIELD_MUSHROOM.getId(), NMLBlocks.POTTED_FIELD_MUSHROOM);
//        flowerPotBlock.addPlant(NMLBlocks.ACONITE.getId(), NMLBlocks.POTTED_ACONITE);
//        flowerPotBlock.addPlant(NMLBlocks.THISTLE.getId(), NMLBlocks.POTTED_THISTLE);
//        flowerPotBlock.addPlant(NMLBlocks.YELLOW_LUPINE.getId(), NMLBlocks.POTTED_YELLOW_LUPINE);
//        flowerPotBlock.addPlant(NMLBlocks.BLUE_LUPINE.getId(), NMLBlocks.POTTED_BLUE_LUPINE);
//        flowerPotBlock.addPlant(NMLBlocks.RED_LUPINE.getId(), NMLBlocks.POTTED_RED_LUPINE);
//        flowerPotBlock.addPlant(NMLBlocks.PINK_LUPINE.getId(), NMLBlocks.POTTED_PINK_LUPINE);
//        flowerPotBlock.addPlant(NMLBlocks.WILD_MINT.getId(), NMLBlocks.POTTED_WILD_MINT);
//        flowerPotBlock.addPlant(NMLBlocks.PICKLEWEED.getId(), NMLBlocks.POTTED_PICKLEWEED);
//        flowerPotBlock.addPlant(NMLBlocks.AUTUMN_CROCUS.getId(), NMLBlocks.POTTED_AUTUMN_CROCUS);
//        flowerPotBlock.addPlant(NMLBlocks.SUCCULENT.getId(), NMLBlocks.POTTED_SUCCULENT);
//        flowerPotBlock.addPlant(NMLBlocks.BARREL_CACTUS.getId(), NMLBlocks.POTTED_BARREL_CACTUS);
    }
}
