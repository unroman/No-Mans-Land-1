package com.farcr.nomansland.common.item;

import com.farcr.nomansland.common.registry.NMLParticleTypes;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class ThrownOilBottle extends ThrownPotion {
    public ThrownOilBottle(Level level, double x, double y, double z) {
        super(level, x, y, z);
    }

    public ThrownOilBottle(Level level, LivingEntity shooter) {
        super(level, shooter);
    }

    @Override
    protected void onHit(HitResult result) {HitResult.Type hitresult$type = result.getType();
        // Copied from Projectile.onHit
        if (hitresult$type == HitResult.Type.ENTITY) {
            EntityHitResult entityhitresult = (EntityHitResult)result;
            Entity entity = entityhitresult.getEntity();
            if (entity.getType().is(EntityTypeTags.REDIRECTABLE_PROJECTILE) && entity instanceof Projectile) {
                Projectile projectile = (Projectile)entity;
                projectile.deflect(ProjectileDeflection.AIM_DEFLECT, this.getOwner(), this.getOwner(), true);
            }

            this.onHitEntity(entityhitresult);
            this.level().gameEvent(GameEvent.PROJECTILE_LAND, result.getLocation(), GameEvent.Context.of(this, (BlockState)null));
        } else if (hitresult$type == HitResult.Type.BLOCK) {
            BlockHitResult blockhitresult = (BlockHitResult)result;
            this.onHitBlock(blockhitresult);
            BlockPos blockpos = blockhitresult.getBlockPos();
            this.level().gameEvent(GameEvent.PROJECTILE_LAND, blockpos, GameEvent.Context.of(this, this.level().getBlockState(blockpos)));
        }
        // Copied from ThrownPotion.onHit
        if (!this.level().isClientSide) {
            ItemStack itemstack = this.getItem();
            PotionContents potioncontents = (PotionContents)itemstack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
            if (potioncontents.is(Potions.WATER)) {
                this.applyWater();
            } else if (potioncontents.hasEffects()) {
                if (this.isLingering()) {
                    this.makeAreaOfEffectCloud(potioncontents);
                } else {
                    this.applySplash(potioncontents.getAllEffects(), result.getType() == HitResult.Type.ENTITY ? ((EntityHitResult)result).getEntity() : null);
                }
            }
            this.discard();

            // Copied from LevelRenderer.levelEvent
            ServerLevel serverLevel = (ServerLevel) level();
            double d24;
            double d25;
            float f1;
            Vec3 vec3 = Vec3.atBottomCenterOf(this.blockPosition());

            for(int j = 0; j < 8; ++j) {
                serverLevel.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Items.SPLASH_POTION)), vec3.x, vec3.y, vec3.z, 0, random.nextGaussian() * 0.15, random.nextDouble() * 0.2, random.nextGaussian() * 0.15, 1);
            }

            ParticleOptions particleoptions = NMLParticleTypes.OIL_SPLASH.get();

            for(int i2 = 0; i2 < 100; ++i2) {
                double d10 = random.nextDouble() * 4.0;
                double d15 = random.nextDouble() * Math.PI * 2.0;
                double d20 = Math.cos(d15) * d10;
                d24 = 0.01 + random.nextDouble() * 0.5;
                d25 = Math.sin(d15) * d10;
                serverLevel.sendParticles(particleoptions, vec3.x + d20 * 0.1, vec3.y + 0.3, vec3.z + d25 * 0.1, 0, d20, d24, d25, 1);
            }

            serverLevel.playSound(null, this.blockPosition(), SoundEvents.SPLASH_POTION_BREAK, SoundSource.NEUTRAL, 1.0F, random.nextFloat() * 0.1F + 0.9f);
        }
    }
}
