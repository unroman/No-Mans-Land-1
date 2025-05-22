package com.farcr.nomansland.common.block.cauldrons;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class WitchStewBlockItem extends BlockItem {
    public WitchStewBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    protected boolean placeBlock(BlockPlaceContext context, BlockState state) {
        state = state.setValue(WitchStewCauldron.LEVEL, 4);
        return super.placeBlock(context, state);
    }
}
