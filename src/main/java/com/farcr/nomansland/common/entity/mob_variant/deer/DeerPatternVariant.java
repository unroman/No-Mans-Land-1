package com.farcr.nomansland.common.entity.mob_variant.deer;

import com.farcr.nomansland.common.entity.mob_variant.MobVariant;
import com.farcr.nomansland.common.registry.NMLMobVariants;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

import java.util.Optional;

public record DeerPatternVariant(ResourceLocation texture, int weight, ResourceLocation babyTexture, Optional<HolderSet<Biome>> biomes) implements MobVariant {

    public static final Codec<DeerPatternVariant> DIRECT_CODEC = RecordCodecBuilder.create((record) -> record.group(
                    ResourceLocation.CODEC.fieldOf("texture").forGetter(DeerPatternVariant::texture),
                    Codec.INT.fieldOf("weight").forGetter(DeerPatternVariant::weight),
                    ResourceLocation.CODEC.fieldOf("baby_texture").forGetter(DeerPatternVariant::babyTexture),
                    RegistryCodecs.homogeneousList(Registries.BIOME).optionalFieldOf("biomes").forGetter(DeerPatternVariant::biomes))
            .apply(record, DeerPatternVariant::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, DeerPatternVariant> DIRECT_STREAM_CODEC =
            StreamCodec.composite(
                    ResourceLocation.STREAM_CODEC, DeerPatternVariant::texture,
                    ByteBufCodecs.INT, DeerPatternVariant::weight,
                    ResourceLocation.STREAM_CODEC, DeerPatternVariant::babyTexture,
                    ByteBufCodecs.optional(ByteBufCodecs.holderSet(Registries.BIOME)), DeerPatternVariant::biomes,
                    DeerPatternVariant::new
            );

    public static final Codec<Holder<DeerPatternVariant>> CODEC =
            RegistryFileCodec.create(NMLMobVariants.DEER_PATTERN_VARIANT_KEY, DIRECT_CODEC);

    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<DeerPatternVariant>> STREAM_CODEC =
            ByteBufCodecs.holder(NMLMobVariants.DEER_PATTERN_VARIANT_KEY, DIRECT_STREAM_CODEC);
}


