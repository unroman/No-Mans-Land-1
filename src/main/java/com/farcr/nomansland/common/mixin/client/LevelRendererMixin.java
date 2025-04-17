package com.farcr.nomansland.common.mixin.client;

import com.farcr.nomansland.NMLConfig;
import com.farcr.nomansland.common.registry.NMLParticleTypes;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.particles.ParticleOptions;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@OnlyIn(Dist.CLIENT)
@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

    @ModifyArg(method = "levelEvent", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V", ordinal = 4), index = 0)
    private ParticleOptions tryTurnSpawnerFlameMalevolent(ParticleOptions particle) {
        if (NMLConfig.MALEVOLENT_SPAWNER.get()) {
            return NMLParticleTypes.MALEVOLENT_FLAME.get();
        }

        return particle;
    }
}
