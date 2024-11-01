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

public record PigVariant(ResourceLocation texture, int weight, ResourceLocation babyTexture, Optional<HolderSet<Biome>> biomes) implements MobVariant {

    public static final Codec<PigVariant> DIRECT_CODEC = RecordCodecBuilder.create((record) -> record.group(
                    ResourceLocation.CODEC.fieldOf("texture").forGetter(PigVariant::texture),
                    Codec.INT.fieldOf("weight").forGetter(PigVariant::weight),
                    ResourceLocation.CODEC.fieldOf("baby_texture").forGetter(PigVariant::babyTexture),
                    RegistryCodecs.homogeneousList(Registries.BIOME).optionalFieldOf("biomes").forGetter(PigVariant::biomes))
            .apply(record, PigVariant::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, PigVariant> DIRECT_STREAM_CODEC =
            StreamCodec.composite(
                    ResourceLocation.STREAM_CODEC, PigVariant::texture,
                    ByteBufCodecs.INT, PigVariant::weight,
                    ResourceLocation.STREAM_CODEC, PigVariant::babyTexture,
                    ByteBufCodecs.optional(ByteBufCodecs.holderSet(Registries.BIOME)), PigVariant::biomes,
                    PigVariant::new
            );

    public static final Codec<Holder<PigVariant>> CODEC =
            RegistryFileCodec.create(NMLMobVariants.PIG_VARIANT_KEY, DIRECT_CODEC);

    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<PigVariant>> STREAM_CODEC =
            ByteBufCodecs.holder(NMLMobVariants.PIG_VARIANT_KEY, DIRECT_STREAM_CODEC);
}


