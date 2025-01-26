package com.farcr.nomansland.common.effect;

import com.farcr.nomansland.common.registry.NMLEffects;
import com.farcr.nomansland.data.tags.DamageTypeTags;
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
        super(category, color);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        MobEffectInstance flammableEffectInstance = livingEntity.getEffect(NMLEffects.FLAMMABLE);
        if (flammableEffectInstance != null) {
            DamageSource damageSource = livingEntity.getLastDamageSource();
            if (damageSource != null && (livingEntity.isOnFire() || livingEntity.level().getBlockState(livingEntity.blockPosition()).is(BlockTags.FIRE)) && (damageSource.is(DamageTypes.IN_FIRE) || damageSource.is(DamageTypes.ON_FIRE))) {
                livingEntity.hurt(livingEntity.getLastDamageSource(), 10 + amplifier*4);
                livingEntity.setRemainingFireTicks(livingEntity.getRemainingFireTicks() + flammableEffectInstance.getDuration());
                livingEntity.removeEffect(NMLEffects.FLAMMABLE);
            }

            boolean inWaterOrRain = livingEntity.isInWaterOrRain();

            livingEntity.setDiscardFriction(inWaterOrRain);

            if (inWaterOrRain) {
                flammableEffectInstance.mapDuration(duration -> duration-1);
            }
        }

        return super.applyEffectTick(livingEntity, amplifier);
    }
}
