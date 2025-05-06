package com.farcr.nomansland.common.world.biomemodifiers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeSpecialEffectsBuilder;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;

import java.util.Optional;

public record ChangeColorsBiomeModifier(HolderSet<Biome> biomes, Optional<Integer> fogColor, Optional<Integer> waterColor, Optional<Integer> waterFogColor, Optional<Integer> skyColor, Optional<Integer> grassColor, Optional<Integer> foliageColor) implements BiomeModifier {

    public static final MapCodec<ChangeColorsBiomeModifier> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Biome.LIST_CODEC.fieldOf("biomes").forGetter(ChangeColorsBiomeModifier::biomes),
                    Codec.INT.optionalFieldOf("fog_color").forGetter(ChangeColorsBiomeModifier::fogColor),
                    Codec.INT.optionalFieldOf("water_color").forGetter(ChangeColorsBiomeModifier::waterColor),
                    Codec.INT.optionalFieldOf("water_fog_color").forGetter(ChangeColorsBiomeModifier::waterFogColor),
                    Codec.INT.optionalFieldOf("sky_color").forGetter(ChangeColorsBiomeModifier::skyColor),
                    Codec.INT.optionalFieldOf("grass_color").forGetter(ChangeColorsBiomeModifier::grassColor),
                    Codec.INT.optionalFieldOf("foliage_color").forGetter(ChangeColorsBiomeModifier::foliageColor)
            ).apply(instance, ChangeColorsBiomeModifier::new)
    );

    public ChangeColorsBiomeModifier(HolderSet<Biome> biomes, int fogColor, int waterColor, int waterFogColor, int skyColor, int grassColor, int foliageColor) {
        this(biomes, Optional.of(fogColor), Optional.of(waterColor), Optional.of(waterFogColor), Optional.of(skyColor), Optional.of(grassColor), Optional.of(foliageColor));
    }

    public ChangeColorsBiomeModifier(HolderSet<Biome> biomes, int fogColor, int waterColor, int waterFogColor, int skyColor) {
        this(biomes, Optional.of(fogColor), Optional.of(waterColor), Optional.of(waterFogColor), Optional.of(skyColor), Optional.empty(), Optional.empty());
    }

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.BEFORE_EVERYTHING && biomes.contains(biome)) {
            BiomeSpecialEffectsBuilder effectsBuilder = builder.getSpecialEffects();
            fogColor.ifPresent(effectsBuilder::fogColor);
            waterColor.ifPresent(effectsBuilder::waterColor);
            waterFogColor.ifPresent(effectsBuilder::waterFogColor);
            skyColor.ifPresent(effectsBuilder::skyColor);
            if (effectsBuilder.getGrassColorModifier() != BiomeSpecialEffects.GrassColorModifier.NONE) grassColor.ifPresent(effectsBuilder::grassColorOverride);
            foliageColor.ifPresent(effectsBuilder::foliageColorOverride);
        }
    }

    @Override
    public MapCodec<? extends BiomeModifier> codec() {
        return CODEC;
    }
}
