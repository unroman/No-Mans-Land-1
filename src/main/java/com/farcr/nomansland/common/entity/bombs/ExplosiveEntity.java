package com.farcr.nomansland.common.entity.bombs;

import com.farcr.nomansland.common.registry.NMLBlocks;
import com.farcr.nomansland.common.registry.NMLEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import static net.minecraft.world.level.block.WallTorchBlock.FACING;

public class ExplosiveEntity extends ThrowableBombEntity {

    private BlockPos hitPos;

    public ExplosiveEntity(EntityType<? extends ThrowableProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public ExplosiveEntity(LivingEntity livingEntity, Level level) {
        super(NMLEntities.EXPLOSIVE.get(), livingEntity, level);
    }

    public ExplosiveEntity(Level level, double x, double y, double z) {
        super(NMLEntities.EXPLOSIVE.get(), x, y, z, level);
    }

    private void spawnParticles(ParticleOptions particle, int amount) {
        for (int i = 0; i < amount; i++) {
            double theta = this.random.nextFloat() * 2 * Math.PI;
            double alpha = this.random.nextFloat() * 2 * Math.PI;
            double cos = Math.cos(alpha);
            double xVelocity = Math.sin(theta) * cos * (this.random.nextFloat() * 0.3 + 0.7);
            double yVelocity = cos * Math.cos(theta) * (this.random.nextFloat() * 0.3 + 0.7);
            double zVelocity = Math.sin(alpha) * (this.random.nextFloat() * 0.3 + 0.7);
            this.level().addParticle(particle, this.getX(), this.getY(), this.getZ(), xVelocity * 0.6, yVelocity * 0.6, zVelocity * 0.6);
        }
    }

    @Override
    public void handleEntityEvent(byte b) {
        if (b == 0) {
            this.spawnParticles(ParticleTypes.SMOKE, 320);

            for (int i = 0; i < 40; i++) {
                double theta = this.random.nextFloat() * 2 * Math.PI;
                double alpha = this.random.nextFloat() * 2 * Math.PI;
                double cos = Math.cos(alpha);
                double xVelocity = Math.sin(theta) * cos * (this.random.nextFloat() * 0.3 + 0.7);
                double yVelocity = cos * Math.cos(theta) * (this.random.nextFloat() * 0.3 + 0.7);
                double zVelocity = Math.sin(alpha) * (this.random.nextFloat() * 0.3 + 0.7);
                this.level().addParticle(ParticleTypes.FLAME, false, this.getX(), this.getY(), this.getZ(), xVelocity * 0.1, yVelocity * 0.1, zVelocity * 0.1);
            }
        } else if (b == 1) {
            this.spawnParticles(ParticleTypes.SMOKE, 400);
        } else {
            super.handleEntityEvent(b);
        }
    }

    @Override
    protected void explode() {
        Level level = this.level();

        level.explode(this, Explosion.getDefaultDamageSource(level, this), null, this.getX(), this.getY(0.0625), this.getZ(), 3F, false, Level.ExplosionInteraction.TNT);
        // Light nearby campfires on fire
        BlockPos.withinManhattan(this.blockPosition(), 6, 4, 6).forEach(pos -> {
            BlockState state = level.getBlockState(pos);
            if (state.is(BlockTags.CAMPFIRES) && state.hasProperty(CampfireBlock.LIT) && !state.getValue(CampfireBlock.LIT)) {
                level.setBlockAndUpdate(pos, state.setValue(CampfireBlock.LIT, true));
            }
            if (state.is(Blocks.TNT)) {
                TntBlock.explode(level, pos);
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);
            }
            if (state.is(NMLBlocks.EXTINGUISHED_TORCH.get())) {
                level.setBlockAndUpdate(pos, Blocks.TORCH.defaultBlockState());
            } else if (state.is(NMLBlocks.EXTINGUISHED_WALL_TORCH.get())) {
                level.setBlockAndUpdate(pos, Blocks.WALL_TORCH.defaultBlockState().setValue(FACING, state.getValue(FACING)));
            } else if (state.is(NMLBlocks.EXTINGUISHED_SOUL_TORCH.get())) {
                level.setBlockAndUpdate(pos, Blocks.SOUL_TORCH.defaultBlockState());
            } else if (state.is(NMLBlocks.EXTINGUISHED_SOUL_WALL_TORCH.get())) {
                level.setBlockAndUpdate(pos, Blocks.SOUL_WALL_TORCH.defaultBlockState().setValue(FACING, state.getValue(FACING)));
            } else if (state.is(NMLBlocks.EXTINGUISHED_SCONCE_TORCH.get())) {
                level.setBlockAndUpdate(pos, NMLBlocks.SCONCE_TORCH.get().defaultBlockState());
            } else if (state.is(NMLBlocks.EXTINGUISHED_SCONCE_WALL_TORCH.get())) {
                level.setBlockAndUpdate(pos, NMLBlocks.SCONCE_WALL_TORCH.get().defaultBlockState().setValue(FACING, state.getValue(FACING)));
            } else if (state.is(NMLBlocks.EXTINGUISHED_SCONCE_SOUL_TORCH.get())) {
                level.setBlockAndUpdate(pos, NMLBlocks.SCONCE_SOUL_TORCH.get().defaultBlockState());
            } else if (state.is(NMLBlocks.EXTINGUISHED_SCONCE_SOUL_WALL_TORCH.get())) {

                level.setBlockAndUpdate(pos, NMLBlocks.SCONCE_SOUL_WALL_TORCH.get().defaultBlockState().setValue(FACING, state.getValue(FACING)));
            }

        });
        level.broadcastEntityEvent(this, (byte) (this.isInWater() ? 1 : 0));
        this.discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        Vec3 pos = this.position();
        Vec3 resultPos = result.getLocation();
        Vec3 dir = pos.vectorTo(resultPos).normalize();
        this.setDeltaMovement(Vec3.ZERO);
        this.setPos(new Vec3(resultPos.x - dir.x * this.getBbWidth() * 0.01, resultPos.y - dir.y * this.getBbHeight() * 0.01, resultPos.z - dir.z * this.getBbWidth() * 0.01));
        this.setNoGravity(true);
        if (!this.shouldFuse()) {
            this.startFuse(100);
        }
        this.hitPos = result.getBlockPos();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        this.setDeltaMovement(this.getDeltaMovement().scale(-0.1));
        if (!this.shouldFuse()) {
            this.startFuse(100);
        }
    }

    @Override
    public void tick() {
        if (this.hitPos != null && this.level().getBlockState(this.hitPos).getCollisionShape(this.level(), this.hitPos).isEmpty()) {
            this.hitPos = null;
            this.setNoGravity(false);
        }

        super.tick();
    }

    @Override
    protected ParticleOptions getParticle() {
        return ParticleTypes.SMOKE;
    }
}
