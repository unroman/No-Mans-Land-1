package com.farcr.nomansland.common.mixin;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(RandomStrollGoal.class)
public class RandomStrollGoalMixin {
    @Shadow @Final protected PathfinderMob mob;
}
