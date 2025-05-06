package com.farcr.nomansland.common.item;

import com.farcr.nomansland.common.block.FrostedGrassBlock;
import com.farcr.nomansland.common.registry.blocks.NMLBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;

public class FrostedGrassBlockItem extends BlockItem {
    public FrostedGrassBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    protected boolean placeBlock(BlockPlaceContext context, BlockState state) {
        Level level = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockState currentState = level.getBlockState(blockpos);
        BlockState grassState = state;

        if (currentState.is(Blocks.SNOW) && currentState.getValue(SnowLayerBlock.LAYERS) < 3) {
            grassState = grassState.setValue(FrostedGrassBlock.SNOWLOGGED, true);
        }

        return super.placeBlock(context, grassState);
    }
}
