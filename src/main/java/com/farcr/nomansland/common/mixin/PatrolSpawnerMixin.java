package com.farcr.nomansland.common.mixin;

import com.farcr.nomansland.common.saved_data.WardedSpacesData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.PatrolSpawner;
import net.minecraft.world.level.saveddata.SavedData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;

@Mixin(PatrolSpawner.class)
public class PatrolSpawnerMixin {
    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;isCloseToVillage(Lnet/minecraft/core/BlockPos;I)Z"))
    private boolean isWarded(ServerLevel instance, BlockPos pos, int sections) {
        WardedSpacesData wardedSpacesData = instance.getDataStorage().computeIfAbsent(new SavedData.Factory<>(
                () -> new WardedSpacesData(new ArrayList<>(), new ArrayList<>()), WardedSpacesData::load), WardedSpacesData.NAME);

        if (wardedSpacesData.isWarded(pos)) {
            return true;
        } else return instance.isCloseToVillage(pos, sections);
    }
}
