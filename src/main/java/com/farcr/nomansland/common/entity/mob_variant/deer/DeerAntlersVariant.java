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

public record DeerAntlersVariant(ResourceLocation texture, int weight, Optional<HolderSet<Biome>> biomes) implements MobVariant {

    public static final Codec<DeerAntlersVariant> DIRECT_CODEC = RecordCodecBuilder.create((record) -> record.group(
                    ResourceLocation.CODEC.fieldOf("texture").forGetter(DeerAntlersVariant::texture),
                    Codec.INT.fieldOf("weight").forGetter(DeerAntlersVariant::weight),
                    RegistryCodecs.homogeneousList(Registries.BIOME).optionalFieldOf("biomes").forGetter(DeerAntlersVariant::biomes))
            .apply(record, DeerAntlersVariant::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, DeerAntlersVariant> DIRECT_STREAM_CODEC =
            StreamCodec.composite(
                    ResourceLocation.STREAM_CODEC, DeerAntlersVariant::texture,
                    ByteBufCodecs.INT, DeerAntlersVariant::weight,
                    ByteBufCodecs.optional(ByteBufCodecs.holderSet(Registries.BIOME)), DeerAntlersVariant::biomes,
                    DeerAntlersVariant::new
            );

    public static final Codec<Holder<DeerAntlersVariant>> CODEC =
            RegistryFileCodec.create(NMLMobVariants.DEER_ANTLERS_VARIANT_KEY, DIRECT_CODEC);

    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<DeerAntlersVariant>> STREAM_CODEC =
            ByteBufCodecs.holder(NMLMobVariants.DEER_ANTLERS_VARIANT_KEY, DIRECT_STREAM_CODEC);
}


