package com.farcr.nomansland.common.mixin;

import com.farcr.nomansland.common.saved_data.WardedSpacesData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;

@Mixin(WaterAvoidingRandomStrollGoal.class)
public class WaterAvoidingRandomStrollGoalMixin extends RandomStrollGoalMixin {
    @Inject(method = "getPosition", at = @At("HEAD"), cancellable = true)
    private void getPosition(CallbackInfoReturnable<Vec3> cir) {
        if (mob.level() instanceof ServerLevel serverLevel) {
            WardedSpacesData wardedSpacesData = serverLevel.getDataStorage().computeIfAbsent(new SavedData.Factory<>(
                    () -> new WardedSpacesData(new ArrayList<>(), new ArrayList<>()), WardedSpacesData::load), WardedSpacesData.NAME);

            if (wardedSpacesData.isWarded(mob.blockPosition())) {
                Vec3 newPosition = LandRandomPos.getPosAway(mob, 10, 10, mob.position());
                if (newPosition != null) cir.setReturnValue(newPosition);
            }
        }
    }
}
