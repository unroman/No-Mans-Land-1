package com.farcr.nomansland.common.entity.mob_variant;

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

public record CamelVariant(ResourceLocation texture, int weight, ResourceLocation babyTexture, Optional<HolderSet<Biome>> biomes) implements MobVariant {

    public static final Codec<CamelVariant> DIRECT_CODEC = RecordCodecBuilder.create((record) -> record.group(
                    ResourceLocation.CODEC.fieldOf("texture").forGetter(CamelVariant::texture),
                    Codec.INT.fieldOf("weight").forGetter(CamelVariant::weight),
                    ResourceLocation.CODEC.fieldOf("baby_texture").forGetter(CamelVariant::babyTexture),
                    RegistryCodecs.homogeneousList(Registries.BIOME).optionalFieldOf("biomes").forGetter(CamelVariant::biomes))
            .apply(record, CamelVariant::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, CamelVariant> DIRECT_STREAM_CODEC =
            StreamCodec.composite(
                    ResourceLocation.STREAM_CODEC, CamelVariant::texture,
                    ByteBufCodecs.INT, CamelVariant::weight,
                    ResourceLocation.STREAM_CODEC, CamelVariant::babyTexture,
                    ByteBufCodecs.optional(ByteBufCodecs.holderSet(Registries.BIOME)), CamelVariant::biomes,
                    CamelVariant::new
            );

    public static final Codec<Holder<CamelVariant>> CODEC =
            RegistryFileCodec.create(NMLMobVariants.CAMEL_VARIANT_KEY, DIRECT_CODEC);

    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<CamelVariant>> STREAM_CODEC =
            ByteBufCodecs.holder(NMLMobVariants.CAMEL_VARIANT_KEY, DIRECT_STREAM_CODEC);
}