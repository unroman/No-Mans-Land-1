package com.farcr.nomansland.common.effect;

import com.farcr.nomansland.common.registry.NMLCriteriaTriggers;
import com.farcr.nomansland.common.registry.NMLDamageTypes;
import com.farcr.nomansland.common.registry.NMLParticleTypes;
import com.farcr.nomansland.common.registry.NMLTags;
import com.farcr.nomansland.common.registry.entities.NMLEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class FlammableEffect extends MobEffect {
    public FlammableEffect(MobEffectCategory category, int color) {
        super(category, color);
        this.particleFactory = mobEffectInstance -> NMLParticleTypes.OIL.get();
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
                livingEntity.removeEffect(NMLEffects.FLAMMABLE);
                int durationLost = livingEntity.isUnderWater() ? 4 : 2;
                if (flammableEffectInstance.getDuration() > durationLost) livingEntity.addEffect(new MobEffectInstance(NMLEffects.FLAMMABLE, flammableEffectInstance.getDuration() - durationLost, flammableEffectInstance.getAmplifier()));
            }
        }

        return super.applyEffectTick(livingEntity, amplifier);
    }

    @Override
    public void onMobHurt(LivingEntity livingEntity, int amplifier, DamageSource damageSource, float amount) {
        Level level = livingEntity.level();

        MobEffectInstance flammableEffectInstance = livingEntity.getEffect(NMLEffects.FLAMMABLE);
        if (flammableEffectInstance != null && (damageSource.is(NMLTags.IGNITES_FLAMMABLE) || (damageSource.getWeaponItem() != null && damageSource.getWeaponItem().is(NMLTags.FIRESTARTERS)))) {
            if (damageSource.getEntity() instanceof ServerPlayer serverPlayer) NMLCriteriaTriggers.IGNITE_FLAMMABLE_ENTITY.get().trigger(serverPlayer, livingEntity, damageSource);

            livingEntity.hurt(NMLDamageTypes.getSimpleDamageSource(level, NMLDamageTypes.COMBUST), 6 + amplifier*4);
            livingEntity.setRemainingFireTicks(livingEntity.getRemainingFireTicks() + flammableEffectInstance.getDuration());
            livingEntity.removeEffect(NMLEffects.FLAMMABLE);

            FireBlock delegate = (FireBlock) Blocks.FIRE;

            if (level.getGameRules().getBoolean(GameRules.RULE_DOFIRETICK)) {
                var positions = BlockPos.betweenClosedStream(livingEntity.getBoundingBox().inflate(1.5, 0, 1.5))
                        .map(BlockPos::immutable).distinct().collect(Collectors.toCollection(ArrayList::new));

                for (BlockPos pos : positions) {
                    if (level.random.nextFloat() < 0.8) continue;

                    if (level.isRaining() && delegate.isNearRain(level, pos)) {
                        continue;
                    }

                    for (Direction d : Direction.values()) {
                        if (level.getBlockState(pos.below()).isFlammable(level, pos.below(), d) && level.getBlockState(pos.below()).canBeReplaced()) {
                            pos = pos.below();
                            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                        }

                        if (BaseFireBlock.canBePlacedAt(level, pos, d)) {
                            BlockState state = BaseFireBlock.getState(level, pos);
                            level.setBlock(pos, state, 3);
                        }
                    }

                }

                level.playSound(null, livingEntity.blockPosition(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS);
            }
        }

        super.onMobHurt(livingEntity, amplifier, damageSource, amount);
    }
}
