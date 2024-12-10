package com.farcr.nomansland.common.block;

import com.farcr.nomansland.common.registry.NMLTags;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseCoralWallFanBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;

public class ShelfMushroomBlock extends BaseCoralWallFanBlock {

    private static final Map<Direction, VoxelShape> SHAPES = Maps.newEnumMap(ImmutableMap.of(
            Direction.NORTH, Block.box(4, 6, 9, 12, 9, 16.0),
            Direction.SOUTH, Block.box(4, 6, 0, 12, 9, 7),
            Direction.WEST, Block.box(9, 6, 4, 16, 9, 12),
            Direction.EAST, Block.box(0, 6, 4, 7, 9, 12)));

    public ShelfMushroomBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, Boolean.valueOf(false)));
    }

    public static boolean isAcceptableNeighbour(BlockGetter blockReader, BlockPos neighborPos) {
        BlockState blockstate = blockReader.getBlockState(neighborPos);
        return blockstate.is(BlockTags.LOGS) || blockstate.is(NMLTags.MUSHROOM_BLOCKS);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction direction = state.getValue(FACING);
        BlockPos blockpos = pos.relative(direction.getOpposite());
        BlockState blockstate = level.getBlockState(blockpos);
        return blockstate.is(BlockTags.LOGS) || blockstate.is(NMLTags.MUSHROOM_BLOCKS);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state.getValue(FACING));
    }
}
