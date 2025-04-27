package com.farcr.nomansland.common.block;

import com.farcr.nomansland.common.registry.NMLTags;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class IciclesBlock extends Block implements Fallable {
    public static final MapCodec<IciclesBlock> CODEC = simpleCodec(IciclesBlock::new);

    public static final DirectionProperty TIP_DIRECTION = BlockStateProperties.VERTICAL_DIRECTION;

    public IciclesBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(TIP_DIRECTION, Direction.DOWN));
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (state.getValue(TIP_DIRECTION) == Direction.UP) {
            entity.causeFallDamage(fallDistance + 2.0F, 2.0F, level.damageSources().freeze());
            if (entity instanceof LivingEntity livingEntity) livingEntity.setTicksFrozen(40);
            level.destroyBlock(pos, false);

        } else super.fallOn(level, state, pos, entity, fallDistance);
    }

    // TODO: break icicle when it lands
    @Override
    public void onLand(Level level, BlockPos pos, BlockState state, BlockState replaceableState, FallingBlockEntity fallingBlock) {

    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction placementDirection = context.getNearestLookingVerticalDirection().getOpposite();
        return defaultBlockState().setValue(TIP_DIRECTION, placementDirection);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TIP_DIRECTION);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        boolean aboveSupportsIcicle = level.getBlockState(pos.above()).is(NMLTags.SUPPORTS_ICICLE) && state.getValue(TIP_DIRECTION) == Direction.DOWN;
        boolean belowSupportsIcicle = level.getBlockState(pos.below()).is(NMLTags.SUPPORTS_ICICLE) && state.getValue(TIP_DIRECTION) == Direction.UP;

        return aboveSupportsIcicle || belowSupportsIcicle;
    }

    @NotNull
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        if (pState.getValue(TIP_DIRECTION) == Direction.UP)
            return Block.box(3.0D, 0.0D, 3.0D, 13.0D, 7.0D, 14.0D);

        return Block.box(3.0D, 9.0D, 3.0D, 13.0D, 16.0D, 14.0D);
    }
}
