package com.farcr.nomansland.common.entity.variant;

import com.farcr.nomansland.common.registry.NMLVariants;
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

import java.util.Objects;
import java.util.Optional;

public record CamelVariant(ResourceLocation texture, ResourceLocation babyTexture, Optional<HolderSet<Biome>> biomes) {
    public static final Codec<CamelVariant> DIRECT_CODEC = RecordCodecBuilder.create((record) -> record.group(
                    ResourceLocation.CODEC.fieldOf("texture").forGetter(CamelVariant::texture),
                    ResourceLocation.CODEC.fieldOf("baby_texture").forGetter(CamelVariant::babyTexture),
                    RegistryCodecs.homogeneousList(Registries.BIOME).optionalFieldOf("biomes").forGetter(CamelVariant::biomes))
            .apply(record, CamelVariant::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, CamelVariant> DIRECT_STREAM_CODEC;
    public static final Codec<Holder<CamelVariant>> CODEC;
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<CamelVariant>> STREAM_CODEC;

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else {
            boolean equals;
            if (other instanceof CamelVariant v) {
                equals = Objects.equals(this.texture, v.texture) && Objects.equals(this.biomes, v.biomes);
            } else {
                equals = false;
            }

            return equals;
        }
    }

    static {
        DIRECT_STREAM_CODEC = StreamCodec.composite(ResourceLocation.STREAM_CODEC, CamelVariant::texture, ResourceLocation.STREAM_CODEC, CamelVariant::babyTexture, ByteBufCodecs.optional(ByteBufCodecs.holderSet(Registries.BIOME)), CamelVariant::biomes, CamelVariant::new);
        CODEC = RegistryFileCodec.create(NMLVariants.CAMEL_VARIANT_KEY, DIRECT_CODEC);
        STREAM_CODEC = ByteBufCodecs.holder(NMLVariants.CAMEL_VARIANT_KEY, DIRECT_STREAM_CODEC);
    }
}