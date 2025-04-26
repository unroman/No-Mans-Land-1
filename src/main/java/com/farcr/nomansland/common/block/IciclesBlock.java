package com.farcr.nomansland.common.block;

import com.farcr.nomansland.common.registry.NMLTags;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

import javax.annotation.Nullable;

public class IciclesBlock extends Block implements Fallable {
    public static final MapCodec<IciclesBlock> CODEC = simpleCodec(IciclesBlock::new);

    public static final DirectionProperty TIP_DIRECTION = BlockStateProperties.VERTICAL_DIRECTION;

    public IciclesBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(
            this.stateDefinition
                    .any()
                    .setValue(TIP_DIRECTION, Direction.DOWN)
        );
    }


    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (state.getValue(TIP_DIRECTION) == Direction.UP) {
            // Is the stalagmite damage source ok?
            entity.causeFallDamage(fallDistance + 2.0F, 2.0F, level.damageSources().stalagmite());
            entity.playSound(SoundEvents.PLAYER_HURT_FREEZE);  // TODO: Only play this sound when in survival mode
            this.destroy(level, pos, state); // How do I destroy the block?? This doesn't work
        } else {
            super.fallOn(level, state, pos, entity, fallDistance);
        }
    }

    @Override
    public void onLand(Level level, BlockPos pos, BlockState state, BlockState replaceableState, FallingBlockEntity fallingBlock) {

    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        // TODO: This doesn't really work when you try to place it on sides
        Direction placementDirection = context.getNearestLookingVerticalDirection().getOpposite();
        return this.defaultBlockState().setValue(TIP_DIRECTION, placementDirection);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TIP_DIRECTION);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        boolean belowSupportsIcicle = level.getBlockState(pos.below()).is(NMLTags.SUPPORTS_ICICLE);
        boolean aboveSupportsIcicle = level.getBlockState(pos.above()).is(NMLTags.SUPPORTS_ICICLE);
        return belowSupportsIcicle || aboveSupportsIcicle;
    }
}
