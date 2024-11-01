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

public record FoxVariant(ResourceLocation texture, int weight, ResourceLocation sleepingTexture, Optional<HolderSet<Biome>> biomes) implements MobVariant {

    public static final Codec<FoxVariant> DIRECT_CODEC = RecordCodecBuilder.create((record) -> record.group(
                    ResourceLocation.CODEC.fieldOf("texture").forGetter(FoxVariant::texture),
                    Codec.INT.fieldOf("weight").forGetter(FoxVariant::weight),
                    ResourceLocation.CODEC.fieldOf("sleeping_texture").forGetter(FoxVariant::sleepingTexture),
                    RegistryCodecs.homogeneousList(Registries.BIOME).optionalFieldOf("biomes").forGetter(FoxVariant::biomes))
            .apply(record, FoxVariant::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, FoxVariant> DIRECT_STREAM_CODEC =
            StreamCodec.composite(
                    ResourceLocation.STREAM_CODEC, FoxVariant::texture,
                    ByteBufCodecs.INT, FoxVariant::weight,
                    ResourceLocation.STREAM_CODEC, FoxVariant::sleepingTexture,
                    ByteBufCodecs.optional(ByteBufCodecs.holderSet(Registries.BIOME)), FoxVariant::biomes,
                    FoxVariant::new
            );

    public static final Codec<Holder<FoxVariant>> CODEC =
            RegistryFileCodec.create(NMLMobVariants.FOX_VARIANT_KEY, DIRECT_CODEC);

    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<FoxVariant>> STREAM_CODEC =
            ByteBufCodecs.holder(NMLMobVariants.FOX_VARIANT_KEY, DIRECT_STREAM_CODEC);
}


