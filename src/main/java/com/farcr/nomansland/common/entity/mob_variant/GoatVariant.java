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

public record GoatVariant(ResourceLocation texture, int weight, Optional<HolderSet<Biome>> biomes) implements MobVariant {

    public static final Codec<GoatVariant> DIRECT_CODEC = RecordCodecBuilder.create((record) -> record.group(
                    ResourceLocation.CODEC.fieldOf("texture").forGetter(GoatVariant::texture),
                    Codec.INT.fieldOf("weight").forGetter(GoatVariant::weight),
                    RegistryCodecs.homogeneousList(Registries.BIOME).optionalFieldOf("biomes").forGetter(GoatVariant::biomes))
            .apply(record, GoatVariant::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, GoatVariant> DIRECT_STREAM_CODEC =
            StreamCodec.composite(
                    ResourceLocation.STREAM_CODEC, GoatVariant::texture,
                    ByteBufCodecs.INT, GoatVariant::weight,
                    ByteBufCodecs.optional(ByteBufCodecs.holderSet(Registries.BIOME)), GoatVariant::biomes,
                    GoatVariant::new
            );

    public static final Codec<Holder<GoatVariant>> CODEC =
            RegistryFileCodec.create(NMLMobVariants.GOAT_VARIANT_KEY, DIRECT_CODEC);

    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<GoatVariant>> STREAM_CODEC =
            ByteBufCodecs.holder(NMLMobVariants.GOAT_VARIANT_KEY, DIRECT_STREAM_CODEC);
}


