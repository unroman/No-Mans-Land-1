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

public record HuskVariant(ResourceLocation texture, int weight, Optional<HolderSet<Biome>> biomes) implements MobVariant {

    public static final Codec<HuskVariant> DIRECT_CODEC = RecordCodecBuilder.create((record) -> record.group(
                    ResourceLocation.CODEC.fieldOf("texture").forGetter(HuskVariant::texture),
                    Codec.INT.fieldOf("weight").forGetter(HuskVariant::weight),
                    RegistryCodecs.homogeneousList(Registries.BIOME).optionalFieldOf("biomes").forGetter(HuskVariant::biomes))
            .apply(record, HuskVariant::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, HuskVariant> DIRECT_STREAM_CODEC =
            StreamCodec.composite(
                    ResourceLocation.STREAM_CODEC, HuskVariant::texture,
                    ByteBufCodecs.INT, HuskVariant::weight,
                    ByteBufCodecs.optional(ByteBufCodecs.holderSet(Registries.BIOME)), HuskVariant::biomes,
                    HuskVariant::new
            );

    public static final Codec<Holder<HuskVariant>> CODEC =
            RegistryFileCodec.create(NMLMobVariants.HUSK_VARIANT_KEY, DIRECT_CODEC);

    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<HuskVariant>> STREAM_CODEC =
            ByteBufCodecs.holder(NMLMobVariants.HUSK_VARIANT_KEY, DIRECT_STREAM_CODEC);
}


