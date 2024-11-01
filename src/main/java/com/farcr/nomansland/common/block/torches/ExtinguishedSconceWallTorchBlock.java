package com.farcr.nomansland.common.block.torches;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;

public class ExtinguishedSconceWallTorchBlock extends ExtinguishedWallTorchBlock {
    private static final Map<Direction, VoxelShape> AABBS = Maps.newEnumMap(ImmutableMap.of(
            Direction.NORTH, Block.box(5.5D, 1.5D, 11.0D, 10.5D, 13.5D, 16.0D),
            Direction.SOUTH, Block.box(5.5D, 1.5D, 0.0D, 10.5D, 13.5D, 5.0D),
            Direction.WEST, Block.box(11.0D, 1.5D, 5.5D, 16.0D, 13.5D, 10.5D),
            Direction.EAST, Block.box(0.0D, 1.5D, 5.5D, 5.0D, 13.5D, 10.5D)
    ));

    public ExtinguishedSconceWallTorchBlock(Properties properties, Block litBlock, SimpleParticleType pFlameParticle) {
        super(pFlameParticle, properties, litBlock);
    }

    public static VoxelShape getShape(BlockState state) {
        return AABBS.get(state.getValue(FACING));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return getShape(state);
    }
}
