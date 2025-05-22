package com.farcr.nomansland.common.block;

import net.minecraft.world.item.Item;
import vectorwing.farmersdelight.common.block.FeastBlock;
import vectorwing.farmersdelight.common.item.ConsumableItem;

import java.util.function.Supplier;

public class StallionStripsBlock extends FeastBlock {
    public StallionStripsBlock(Properties properties, Supplier<Item> servingItem, boolean hasLeftovers) {
        super(properties, servingItem, hasLeftovers);
    }
}
