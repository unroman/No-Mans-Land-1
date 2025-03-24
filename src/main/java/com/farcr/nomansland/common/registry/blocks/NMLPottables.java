package com.farcr.nomansland.common.registry.blocks;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;

public class NMLPottables {

    public static void register() {
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(NMLBlocks.PINE_SAPLING.getId(), NMLBlocks.POTTED_PINE_SAPLING.block());
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(NMLBlocks.YELLOW_BIRCH_SAPLING.getId(), NMLBlocks.POTTED_YELLOW_BIRCH_SAPLING.block());
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(NMLBlocks.AUTUMNAL_OAK_SAPLING.getId(), NMLBlocks.POTTED_AUTUMNAL_OAK_SAPLING.block());
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(NMLBlocks.PALE_CHERRY_SAPLING.getId(), NMLBlocks.POTTED_PALE_CHERRY_SAPLING.block());
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(NMLBlocks.MAPLE_SAPLING.getId(), NMLBlocks.POTTED_MAPLE_SAPLING.block());
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(NMLBlocks.RED_MAPLE_SAPLING.getId(), NMLBlocks.POTTED_RED_MAPLE_SAPLING.block());
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(NMLBlocks.WALNUT_SAPLING.getId(), NMLBlocks.POTTED_WALNUT_SAPLING.block());
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(NMLBlocks.WILLOW_SAPLING.getId(), NMLBlocks.POTTED_WILLOW_SAPLING.block());
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(NMLBlocks.FIELD_MUSHROOM.getId(), NMLBlocks.POTTED_FIELD_MUSHROOM.block());
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(NMLBlocks.ACONITE.getId(), NMLBlocks.POTTED_ACONITE.block());
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(NMLBlocks.THISTLE.getId(), NMLBlocks.POTTED_THISTLE.block());
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(NMLBlocks.YELLOW_LUPINE.getId(), NMLBlocks.POTTED_YELLOW_LUPINE.block());
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(NMLBlocks.BLUE_LUPINE.getId(), NMLBlocks.POTTED_BLUE_LUPINE.block());
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(NMLBlocks.RED_LUPINE.getId(), NMLBlocks.POTTED_RED_LUPINE.block());
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(NMLBlocks.PINK_LUPINE.getId(), NMLBlocks.POTTED_PINK_LUPINE.block());
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(NMLBlocks.WILD_MINT.getId(), NMLBlocks.POTTED_WILD_MINT.block());
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(NMLBlocks.PICKLEWEED.getId(), NMLBlocks.POTTED_PICKLEWEED.block());
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(NMLBlocks.AUTUMN_CROCUS.getId(), NMLBlocks.POTTED_AUTUMN_CROCUS.block());
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(NMLBlocks.SUCCULENT.getId(), NMLBlocks.POTTED_SUCCULENT.block());
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(NMLBlocks.BARREL_CACTUS.getId(), NMLBlocks.POTTED_BARREL_CACTUS.block());
    }
}
