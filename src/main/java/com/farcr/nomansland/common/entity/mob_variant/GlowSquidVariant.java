package com.farcr.nomansland.common.entity.mob_variant;

import com.farcr.nomansland.common.registry.entities.NMLMobVariants;
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

public record GlowSquidVariant(ResourceLocation texture, int weight, Optional<HolderSet<Biome>> biomes) implements MobVariant {

    public static final Codec<GlowSquidVariant> DIRECT_CODEC = RecordCodecBuilder.create((record) -> record.group(
                    ResourceLocation.CODEC.fieldOf("texture").forGetter(GlowSquidVariant::texture),
                    Codec.INT.fieldOf("weight").forGetter(GlowSquidVariant::weight),
                    RegistryCodecs.homogeneousList(Registries.BIOME).optionalFieldOf("biomes").forGetter(GlowSquidVariant::biomes))
            .apply(record, GlowSquidVariant::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, GlowSquidVariant> DIRECT_STREAM_CODEC =
            StreamCodec.composite(
                    ResourceLocation.STREAM_CODEC, GlowSquidVariant::texture,
                    ByteBufCodecs.INT, GlowSquidVariant::weight,
                    ByteBufCodecs.optional(ByteBufCodecs.holderSet(Registries.BIOME)), GlowSquidVariant::biomes,
                    GlowSquidVariant::new
            );

    public static final Codec<Holder<GlowSquidVariant>> CODEC =
            RegistryFileCodec.create(NMLMobVariants.GLOW_SQUID_VARIANT_KEY, DIRECT_CODEC);

    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<GlowSquidVariant>> STREAM_CODEC =
            ByteBufCodecs.holder(NMLMobVariants.GLOW_SQUID_VARIANT_KEY, DIRECT_STREAM_CODEC);
}


