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

public record LlamaVariant(ResourceLocation texture, int weight, ResourceLocation babyTexture, Optional<HolderSet<Biome>> biomes) implements MobVariant {

    public static final Codec<LlamaVariant> DIRECT_CODEC = RecordCodecBuilder.create((record) -> record.group(
                    ResourceLocation.CODEC.fieldOf("texture").forGetter(LlamaVariant::texture),
                    Codec.INT.fieldOf("weight").forGetter(LlamaVariant::weight),
                    ResourceLocation.CODEC.fieldOf("baby_texture").forGetter(LlamaVariant::babyTexture),
                    RegistryCodecs.homogeneousList(Registries.BIOME).optionalFieldOf("biomes").forGetter(LlamaVariant::biomes))
            .apply(record, LlamaVariant::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, LlamaVariant> DIRECT_STREAM_CODEC =
            StreamCodec.composite(
                    ResourceLocation.STREAM_CODEC, LlamaVariant::texture,
                    ByteBufCodecs.INT, LlamaVariant::weight,
                    ResourceLocation.STREAM_CODEC, LlamaVariant::babyTexture,
                    ByteBufCodecs.optional(ByteBufCodecs.holderSet(Registries.BIOME)), LlamaVariant::biomes,
                    LlamaVariant::new
            );

    public static final Codec<Holder<LlamaVariant>> CODEC =
            RegistryFileCodec.create(NMLMobVariants.LLAMA_VARIANT_KEY, DIRECT_CODEC);

    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<LlamaVariant>> STREAM_CODEC =
            ByteBufCodecs.holder(NMLMobVariants.LLAMA_VARIANT_KEY, DIRECT_STREAM_CODEC);
}