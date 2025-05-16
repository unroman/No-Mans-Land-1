package com.farcr.nomansland.common.mixin.caves;

import com.farcr.nomansland.common.registry.worldgen.NMLBiomes;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.neoforged.neoforge.common.Tags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = MultiNoiseBiomeSource.class, priority = 1100)
public class MultiNoiseBiomeSourceMixin {

    @Inject(method = "getNoiseBiome(IIILnet/minecraft/world/level/biome/Climate$Sampler;)Lnet/minecraft/core/Holder;", at = @At("RETURN"), cancellable = true)
    private void addCaveBiomes(int x, int y, int z, Climate.Sampler sampler, CallbackInfoReturnable<Holder<Biome>> cir) {
        if (!cir.getReturnValue().is(Tags.Biomes.IS_CAVE)) {
            long depth = sampler.sample(x, y, z).depth();
            if (depth > 0.5) {
                cir.setReturnValue(NMLBiomes.CAVE_DEPTHS_HOLDER);
            } else if (depth > 0) {
                cir.setReturnValue(NMLBiomes.CAVES_HOLDER);
            }
        }
    }
}
