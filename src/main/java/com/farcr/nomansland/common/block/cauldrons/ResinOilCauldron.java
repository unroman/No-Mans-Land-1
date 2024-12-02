package com.farcr.nomansland.common.block.cauldrons;

import com.farcr.nomansland.common.registry.NMLParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class ResinOilCauldron extends NMLCauldronBlock {
    public ResinOilCauldron() {
        super(NMLCauldronType.RESIN_OIL);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        BlockState stateUnder = level.getBlockState(pos.below());
        if (stateUnder.is(BlockTags.FIRE) || stateUnder.is(BlockTags.CAMPFIRES)) {
            double d0 = pos.getX() + 0.5 + random.nextInt(-40, 40)*0.01;
            double d1 = pos.getY() + 0.5 + state.getValue(LEVEL)*0.15;
            double d2 = pos.getZ() + 0.5 + random.nextInt(-40, 40)*0.01;
            level.addParticle(NMLParticleTypes.RESIN_OIL_BUBBLE.get(), d0, d1, d2, 0.0, 0.0, 0.0);
        }
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity.isOnFire() && this.isEntityInsideContent(state, pos, entity) && entity.getY() < pos.getY() + 0.6) {
            entity.setRemainingFireTicks(entity.getRemainingFireTicks()+50*state.getValue(LEVEL));
            level.setBlockAndUpdate(pos, Blocks.CAULDRON.defaultBlockState());
            for (int i = 0; i <= state.getValue(LEVEL)*50; i++) {
                level.addParticle(ParticleTypes.FLAME,
                        entity.getX() + level.random.nextInt(-10, 10) * 0.005,
                        entity.getY() + 0.7 + level.random.nextInt(-10, 10) * 0.05 - i*0.2,
                        entity.getZ() + level.random.nextInt(-10, 10) * 0.01,
                        level.random.nextInt(-10, 10) * 0.005, state.getValue(LEVEL)*0.2, level.random.nextInt(-10, 10) * 0.005);
                level.addParticle(ParticleTypes.SMALL_FLAME,
                        entity.getX() + level.random.nextInt(-10, 10) * 0.005,
                        entity.getY() + 0.7 + level.random.nextInt(-10, 10) * 0.05 - i*0.2,
                        entity.getZ() + level.random.nextInt(-10, 10) * 0.005,
                        level.random.nextInt(-10, 10) * 0.005, state.getValue(LEVEL)*0.2, level.random.nextInt(-10, 10) * 0.005);
                level.addParticle(ParticleTypes.SMOKE,
                        entity.getX() + level.random.nextInt(-10, 10) * 0.005,
                        entity.getY() + 0.7 + level.random.nextInt(-10, 10) * 0.05 - i*0.2,
                        entity.getZ() + level.random.nextInt(-10, 10) * 0.005,
                        level.random.nextInt(-10, 10) * 0.005, state.getValue(LEVEL)*0.2, level.random.nextInt(-10, 10) * 0.005);
                level.addParticle(ParticleTypes.LARGE_SMOKE,
                        entity.getX() + level.random.nextInt(-10, 10) * 0.005,
                        entity.getY() + 0.7 + level.random.nextInt(-10, 10) * 0.05 - i*0.2,
                        entity.getZ() + level.random.nextInt(-10, 10) * 0.005,
                        level.random.nextInt(-10, 10) * 0.005, state.getValue(LEVEL)*0.2, level.random.nextInt(-10, 10) * 0.005);
            }

            entity.hurt(entity.damageSources().inFire(), state.getValue(LEVEL)*3);
            level.playSound(entity, pos, SoundEvents.GENERIC_BURN, SoundSource.BLOCKS, state.getValue(LEVEL)*0.5F, 0.5F);
            level.playSound(entity, pos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, state.getValue(LEVEL)*0.5F, 0.5F);

            // burn blocks above it

            // place a big fire particle on each side of the cauldron (size depending on how many layers the cauldron had)
        }
    }
}
