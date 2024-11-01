package com.farcr.nomansland.common.block.torches;

import com.farcr.nomansland.common.registry.NMLTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class ExtinguishedWallTorchBlock extends WallTorchBlock {

    public final Block litBlock;

    public ExtinguishedWallTorchBlock(SimpleParticleType flameParticle, Properties properties, Block litBlock) {
        super(flameParticle, properties);
        this.litBlock = litBlock;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
    }

    @Override
    public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (stack.is(NMLTags.FIRESTARTERS)) {
            level.playSound(player,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    SoundEvents.FLINTANDSTEEL_USE,
                    SoundSource.BLOCKS,
                    1.0F,
                    (level.random.nextFloat() - level.random.nextFloat()) * 0.8F + 1.8F);
            level.setBlock(pos, litBlock.withPropertiesOf(state), 3);
            if (!level.isClientSide) {
                Direction direction = state.getValue(FACING).getOpposite();
                ServerLevel serverLevel = (ServerLevel) level;
                serverLevel.sendParticles(flameParticle, pos.getX() + 0.5 + 0.2 * direction.getStepX(), pos.getY() + 0.92, pos.getZ() + 0.5 + 0.2 * direction.getStepZ(), 5, 0, 0, 0, 0);
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    protected void spawnAfterBreak(BlockState state, ServerLevel level, BlockPos pos, ItemStack stack, boolean dropExperience) {
        Direction direction = state.getValue(FACING).getOpposite();
        double dx = pos.getX() + 0.5;
        double dy = pos.getY() + 0.7;
        double dz = pos.getZ() + 0.5;
        level.sendParticles(ParticleTypes.SMOKE, dx + 0.2 * direction.getStepX(), dy + 0.22, dz + 0.2 * direction.getStepZ(), level.random.nextInt(2, 7), 0, 0, 0, 0);
    }

    @Override
    protected void onProjectileHit(Level level, BlockState state, BlockHitResult hit, Projectile projectile) {
        if (!level.isClientSide && projectile.isOnFire()) {
            level.setBlock(hit.getBlockPos(), this.litBlock.withPropertiesOf(state), 11);
        }
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
        return litBlock.getCloneItemStack(state, target, level, pos, player);
    }
}
