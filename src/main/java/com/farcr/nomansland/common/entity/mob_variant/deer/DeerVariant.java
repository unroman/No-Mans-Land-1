package com.farcr.nomansland.common.entity.mob_variant.deer;

import com.farcr.nomansland.common.entity.mob_variant.MobVariant;
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

public record DeerVariant(ResourceLocation texture, int weight, ResourceLocation babyTexture, Optional<HolderSet<Biome>> biomes) implements MobVariant {

    public static final Codec<DeerVariant> DIRECT_CODEC = RecordCodecBuilder.create((record) -> record.group(
                    ResourceLocation.CODEC.fieldOf("texture").forGetter(DeerVariant::texture),
                    Codec.INT.fieldOf("weight").forGetter(DeerVariant::weight),
                    ResourceLocation.CODEC.fieldOf("baby_texture").forGetter(DeerVariant::babyTexture),
                    RegistryCodecs.homogeneousList(Registries.BIOME).optionalFieldOf("biomes").forGetter(DeerVariant::biomes))
            .apply(record, DeerVariant::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, DeerVariant> DIRECT_STREAM_CODEC =
            StreamCodec.composite(
                    ResourceLocation.STREAM_CODEC, DeerVariant::texture,
                    ByteBufCodecs.INT, DeerVariant::weight,
                    ResourceLocation.STREAM_CODEC, DeerVariant::babyTexture,
                    ByteBufCodecs.optional(ByteBufCodecs.holderSet(Registries.BIOME)), DeerVariant::biomes,
                    DeerVariant::new
            );

    public static final Codec<Holder<DeerVariant>> CODEC =
            RegistryFileCodec.create(NMLMobVariants.DEER_VARIANT_KEY, DIRECT_CODEC);

    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<DeerVariant>> STREAM_CODEC =
            ByteBufCodecs.holder(NMLMobVariants.DEER_VARIANT_KEY, DIRECT_STREAM_CODEC);
}


