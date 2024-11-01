package com.farcr.nomansland.common.block.torches;

import net.minecraft.core.BlockPos;
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

public class ExtinguishedSconceTorchBlock extends ExtinguishedTorchBlock {

    protected static final VoxelShape AABB = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 12.0D, 10.0D);

    public ExtinguishedSconceTorchBlock(SimpleParticleType flameParticle, Properties properties, Block litBlock) {
        super(flameParticle, properties, litBlock);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return AABB;
    }
}
