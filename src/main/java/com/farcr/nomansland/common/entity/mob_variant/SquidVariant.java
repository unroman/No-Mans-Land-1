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

public record SquidVariant(ResourceLocation texture, int weight, Optional<HolderSet<Biome>> biomes) implements MobVariant {

    public static final Codec<SquidVariant> DIRECT_CODEC = RecordCodecBuilder.create((record) -> record.group(
                    ResourceLocation.CODEC.fieldOf("texture").forGetter(SquidVariant::texture),
                    Codec.INT.fieldOf("weight").forGetter(SquidVariant::weight),
                    RegistryCodecs.homogeneousList(Registries.BIOME).optionalFieldOf("biomes").forGetter(SquidVariant::biomes))
            .apply(record, SquidVariant::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, SquidVariant> DIRECT_STREAM_CODEC =
            StreamCodec.composite(
                    ResourceLocation.STREAM_CODEC, SquidVariant::texture,
                    ByteBufCodecs.INT, SquidVariant::weight,
                    ByteBufCodecs.optional(ByteBufCodecs.holderSet(Registries.BIOME)), SquidVariant::biomes,
                    SquidVariant::new
            );

    public static final Codec<Holder<SquidVariant>> CODEC =
            RegistryFileCodec.create(NMLMobVariants.SQUID_VARIANT_KEY, DIRECT_CODEC);

    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<SquidVariant>> STREAM_CODEC =
            ByteBufCodecs.holder(NMLMobVariants.SQUID_VARIANT_KEY, DIRECT_STREAM_CODEC);
}


