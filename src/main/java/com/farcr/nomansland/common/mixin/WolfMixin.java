package com.farcr.nomansland.common.mixin;

import com.farcr.nomansland.common.entity.BillhookBass;
import com.farcr.nomansland.common.entity.deer.Deer;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Wolf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Wolf.class)
public class WolfMixin {
    @Inject(method = "registerGoals", at = @At("TAIL"))
    private void registerGoals(CallbackInfo ci) {
        ((Wolf) (Object) this).goalSelector.addGoal(5, new NonTameRandomTargetGoal<>(((Wolf) (Object) this), Deer.class, false, target -> true));
    }
}
