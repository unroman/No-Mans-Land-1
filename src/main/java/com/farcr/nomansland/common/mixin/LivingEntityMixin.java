package com.farcr.nomansland.common.mixin;

import com.farcr.nomansland.common.mixinduck.LivingEntityDuck;
import com.farcr.nomansland.common.registry.NMLBlocks;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends EntityMixin implements LivingEntityDuck {
    @Shadow public abstract float getHealth();

    @Shadow public float yBodyRot;

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

    @Redirect(method = "handleOnClimbable", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;isScaffolding(Lnet/minecraft/world/entity/LivingEntity;)Z"))
    private boolean handleOnClimbable(BlockState instance, LivingEntity livingEntity) {
        return instance.is(Blocks.SCAFFOLDING) || instance.is(NMLBlocks.WOODEN_SCAFFOLDING);
    }
}
