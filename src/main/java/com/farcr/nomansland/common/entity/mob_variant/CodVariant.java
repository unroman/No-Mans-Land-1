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

public record CodVariant(ResourceLocation texture, int weight, Optional<HolderSet<Biome>> biomes) implements MobVariant {

    public static final Codec<CodVariant> DIRECT_CODEC = RecordCodecBuilder.create((record) -> record.group(
                    ResourceLocation.CODEC.fieldOf("texture").forGetter(CodVariant::texture),
                    Codec.INT.fieldOf("weight").forGetter(CodVariant::weight),
                    RegistryCodecs.homogeneousList(Registries.BIOME).optionalFieldOf("biomes").forGetter(CodVariant::biomes))
            .apply(record, CodVariant::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, CodVariant> DIRECT_STREAM_CODEC =
            StreamCodec.composite(
                    ResourceLocation.STREAM_CODEC, CodVariant::texture,
                    ByteBufCodecs.INT, CodVariant::weight,
                    ByteBufCodecs.optional(ByteBufCodecs.holderSet(Registries.BIOME)), CodVariant::biomes,
                    CodVariant::new
            );

    public static final Codec<Holder<CodVariant>> CODEC =
            RegistryFileCodec.create(NMLMobVariants.COD_VARIANT_KEY, DIRECT_CODEC);

    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<CodVariant>> STREAM_CODEC =
            ByteBufCodecs.holder(NMLMobVariants.COD_VARIANT_KEY, DIRECT_STREAM_CODEC);
}

