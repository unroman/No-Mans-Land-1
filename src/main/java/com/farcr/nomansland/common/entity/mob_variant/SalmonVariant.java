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

public record SalmonVariant(ResourceLocation texture, int weight, Optional<HolderSet<Biome>> biomes) implements MobVariant {

    public static final Codec<SalmonVariant> DIRECT_CODEC = RecordCodecBuilder.create((record) -> record.group(
                    ResourceLocation.CODEC.fieldOf("texture").forGetter(SalmonVariant::texture),
                    Codec.INT.fieldOf("weight").forGetter(SalmonVariant::weight),
                    RegistryCodecs.homogeneousList(Registries.BIOME).optionalFieldOf("biomes").forGetter(SalmonVariant::biomes))
            .apply(record, SalmonVariant::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, SalmonVariant> DIRECT_STREAM_CODEC =
            StreamCodec.composite(
                    ResourceLocation.STREAM_CODEC, SalmonVariant::texture,
                    ByteBufCodecs.INT, SalmonVariant::weight,
                    ByteBufCodecs.optional(ByteBufCodecs.holderSet(Registries.BIOME)), SalmonVariant::biomes,
                    SalmonVariant::new
            );

    public static final Codec<Holder<SalmonVariant>> CODEC =
            RegistryFileCodec.create(NMLMobVariants.SALMON_VARIANT_KEY, DIRECT_CODEC);

    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<SalmonVariant>> STREAM_CODEC =
            ByteBufCodecs.holder(NMLMobVariants.SALMON_VARIANT_KEY, DIRECT_STREAM_CODEC);
}


