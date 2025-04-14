package com.farcr.nomansland.common.mixin;

import com.farcr.nomansland.common.entity.billhook_bass.BillhookBass;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.animal.AbstractFish;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFish.class)
public abstract class AbstractFishMixin extends EntityMixin {

    @Inject(method = "registerGoals", at = @At("TAIL"))
    private void registerGoals(CallbackInfo ci) {
        ((AbstractFish) (Object) this).goalSelector.addGoal(3, new AvoidEntityGoal<>(((AbstractFish) (Object) this), BillhookBass.class, 5.0F, 1.6, 1.4));
    }
}
