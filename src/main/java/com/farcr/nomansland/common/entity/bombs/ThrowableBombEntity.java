package com.farcr.nomansland.common.entity.bombs;

import com.farcr.nomansland.common.registry.NMLTags;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class ThrowableBombEntity extends ThrowableProjectile {

    private static final EntityDataAccessor<Boolean> DATA_SHOULD_FUSE_ID = SynchedEntityData.defineId(ThrowableBombEntity.class, EntityDataSerializers.BOOLEAN);

    private float oRoll;
    private float roll;
    private int oFuse;
    private int fuse;
    private int maxFuse = -1;

    protected ThrowableBombEntity(EntityType<? extends ThrowableProjectile> entityType, Level level) {
        super(entityType, level);
    }

    protected ThrowableBombEntity(EntityType<? extends ThrowableProjectile> entityType, double d, double e, double f, Level level) {
        super(entityType, d, e, f, level);
    }

    protected ThrowableBombEntity(EntityType<? extends ThrowableProjectile> entityType, LivingEntity livingEntity, Level level) {
        super(entityType, livingEntity, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_SHOULD_FUSE_ID, false);
    }

    @Override
    protected void updateRotation() {
        Vec3 deltaMovement = this.getDeltaMovement();
        double distanceSq = deltaMovement.horizontalDistanceSqr();
        double distance = Math.sqrt(distanceSq);
        this.setXRot(lerpRotation(this.xRotO, (float) (Mth.atan2(deltaMovement.y, distance) * 180.0F / (float) Math.PI)));
        if (distanceSq > 0.01) {
            this.setYRot(lerpRotation(this.yRotO, (float) (Mth.atan2(deltaMovement.x, deltaMovement.z) * 180.0F / (float) Math.PI)));
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Fuse", this.fuse);
        compound.putInt("MaxFuse", this.maxFuse);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.fuse = compound.getInt("Fuse");
        this.maxFuse = compound.getInt("MaxFuse");
        this.entityData.set(DATA_SHOULD_FUSE_ID, this.maxFuse >= 0);
    }

    @Override
    public void tick() {
        super.tick();

        Level level = this.level();
        if (level.isClientSide()) {
            this.oFuse = this.fuse;
            this.oRoll = this.roll;

            if (this.shouldFuse()) {
                this.fuse++;
                if (this.fuse >= this.maxFuse) {
                    this.fuse = this.maxFuse;
                }
            }

            double distance = this.getDeltaMovement().lengthSqr();
            if (distance > 0.01) {
                this.roll += Math.sqrt(distance) * 45;
            }

            if (!this.onGround()) {
                level.addParticle(this.getParticle(), this.getX(), this.getY() + this.getBbHeight(), this.getZ(), 0, 0, 0);
            }
        } else {
            if (this.shouldFuse()) {
                this.fuse++;
                if (this.fuse >= this.maxFuse) {
                    this.explode();
                }
            }

            if (level.getBlockState(this.blockPosition()).is(NMLTags.BOMB_EXPLODE)) {
                this.explode();
            }
        }
    }

    public boolean shouldFuse() {
        return this.entityData.get(DATA_SHOULD_FUSE_ID);
    }

    public int getMaxFuse() {
        return this.maxFuse;
    }

    public void startFuse(int maxFuse) {
        this.maxFuse = maxFuse;
        this.entityData.set(DATA_SHOULD_FUSE_ID, maxFuse >= 0);
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.TNT_PRIMED, SoundSource.PLAYERS, 1.0F, 1.0F);
    }

    protected abstract void explode();

    protected abstract ParticleOptions getParticle();

    public float getRoll(float partialTicks) {
        return Mth.lerp(partialTicks, this.oRoll, this.roll);
    }

    public float getSwelling(float partialTicks) {
        return Mth.lerp(partialTicks, this.oFuse, this.fuse) / (float) (this.getMaxFuse() - 2);
    }
}