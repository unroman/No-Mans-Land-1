package com.farcr.nomansland.common.world.biomemodifiers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.AmbientParticleSettings;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeSpecialEffectsBuilder;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;

import java.util.Optional;

public record ChangeParticleBiomeModifier(HolderSet<Biome> biomes, AmbientParticleSettings particleSettings) implements BiomeModifier {

    public static final MapCodec<ChangeParticleBiomeModifier> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Biome.LIST_CODEC.fieldOf("biomes").forGetter(ChangeParticleBiomeModifier::biomes),
                    AmbientParticleSettings.CODEC.fieldOf("particle").forGetter(ChangeParticleBiomeModifier::particleSettings)
            ).apply(instance, ChangeParticleBiomeModifier::new)
    );

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.BEFORE_EVERYTHING && biomes.contains(biome)) {
            BiomeSpecialEffectsBuilder effectsBuilder = builder.getSpecialEffects();
            effectsBuilder.ambientParticle(particleSettings);
        }
    }

    @Override
    public MapCodec<? extends BiomeModifier> codec() {
        return CODEC;
    }
}
