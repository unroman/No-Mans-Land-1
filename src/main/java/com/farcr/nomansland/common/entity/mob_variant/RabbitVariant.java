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

public record RabbitVariant(ResourceLocation texture, int weight, ResourceLocation babyTexture, Optional<HolderSet<Biome>> biomes) implements MobVariant {

    public static final Codec<RabbitVariant> DIRECT_CODEC = RecordCodecBuilder.create((record) -> record.group(
                    ResourceLocation.CODEC.fieldOf("texture").forGetter(RabbitVariant::texture),
                    Codec.INT.fieldOf("weight").forGetter(RabbitVariant::weight),
                    ResourceLocation.CODEC.fieldOf("baby_texture").forGetter(RabbitVariant::babyTexture),
                    RegistryCodecs.homogeneousList(Registries.BIOME).optionalFieldOf("biomes").forGetter(RabbitVariant::biomes))
            .apply(record, RabbitVariant::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, RabbitVariant> DIRECT_STREAM_CODEC =
            StreamCodec.composite(
                    ResourceLocation.STREAM_CODEC, RabbitVariant::texture,
                    ByteBufCodecs.INT, RabbitVariant::weight,
                    ResourceLocation.STREAM_CODEC, RabbitVariant::babyTexture,
                    ByteBufCodecs.optional(ByteBufCodecs.holderSet(Registries.BIOME)), RabbitVariant::biomes,
                    RabbitVariant::new
            );

    public static final Codec<Holder<RabbitVariant>> CODEC =
            RegistryFileCodec.create(NMLMobVariants.RABBIT_VARIANT_KEY, DIRECT_CODEC);

    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<RabbitVariant>> STREAM_CODEC =
            ByteBufCodecs.holder(NMLMobVariants.RABBIT_VARIANT_KEY, DIRECT_STREAM_CODEC);
}