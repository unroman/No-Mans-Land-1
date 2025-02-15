package com.farcr.nomansland.common.mixin;

import com.farcr.nomansland.common.mixinduck.LivingEntityDuck;
import com.farcr.nomansland.common.registry.entities.NMLEffects;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends EntityMixin implements LivingEntityDuck {
    @Shadow public abstract float getHealth();

    @Shadow public float yBodyRot;

    @Shadow public abstract boolean hasEffect(Holder<MobEffect> effect);

    @Unique
    private boolean nomansland$skipDroppingDeathLoot = false;

    @Override
    public void noMansLand$skipDroppingDeathLoot() {
        this.nomansland$skipDroppingDeathLoot = true;
    }

    @Inject(method = "dropAllDeathLoot", at = @At("HEAD"), cancellable = true)
    private void dropAllDeathLoot(ServerLevel p_level, DamageSource damageSource, CallbackInfo ci) {
        if (this.nomansland$skipDroppingDeathLoot) {
            ci.cancel();
        }
    }

    @ModifyVariable(method = "travel", at = @At("STORE"), ordinal = 0)
    public float friction(float value) {
            if (hasEffect(NMLEffects.FLAMMABLE)) value = 0.9898F;
        return value;
    }
}
