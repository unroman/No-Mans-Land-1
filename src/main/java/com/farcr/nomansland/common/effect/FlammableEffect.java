package com.farcr.nomansland.common.effect;

import com.farcr.nomansland.common.registry.NMLEffects;
import com.farcr.nomansland.common.registry.NMLParticleTypes;
import com.farcr.nomansland.data.tags.DamageTypeTags;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;

public class FlammableEffect extends MobEffect {
    public FlammableEffect(MobEffectCategory category, int color) {
        super(category, color, ParticleTypes.FLAME);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        MobEffectInstance flammableEffectInstance = livingEntity.getEffect(NMLEffects.FLAMMABLE);
        if (flammableEffectInstance != null) {
            if (livingEntity.isInWaterOrRain() && livingEntity instanceof ServerPlayer) {
                flammableEffectInstance.update(new MobEffectInstance(flammableEffectInstance.getEffect(), flammableEffectInstance.getDuration() - 100, flammableEffectInstance.getAmplifier()));
            }
        }

        return super.applyEffectTick(livingEntity, amplifier);
    }

    @Override
    public void onMobHurt(LivingEntity livingEntity, int amplifier, DamageSource damageSource, float amount) {
        MobEffectInstance flammableEffectInstance = livingEntity.getEffect(NMLEffects.FLAMMABLE);
        if (flammableEffectInstance != null) {
            if ((livingEntity.isOnFire() || livingEntity.level().getBlockState(livingEntity.blockPosition()).is(BlockTags.FIRE)) && (damageSource.is(DamageTypes.IN_FIRE) || damageSource.is(DamageTypes.ON_FIRE))) {
                livingEntity.hurt(damageSource, 10 + amplifier*4);
                livingEntity.setRemainingFireTicks(livingEntity.getRemainingFireTicks() + flammableEffectInstance.getDuration());
                livingEntity.removeEffect(NMLEffects.FLAMMABLE);
            }
        }

        super.onMobHurt(livingEntity, amplifier, damageSource, amount);
    }
}
