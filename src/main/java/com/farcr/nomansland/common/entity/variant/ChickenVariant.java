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

public record ChickenVariant(ResourceLocation texture, ResourceLocation babyTexture, Optional<HolderSet<Biome>> biomes) {
    public static final Codec<ChickenVariant> DIRECT_CODEC = RecordCodecBuilder.create((record) -> record.group(
                    ResourceLocation.CODEC.fieldOf("texture").forGetter(ChickenVariant::texture),
                    ResourceLocation.CODEC.fieldOf("baby_texture").forGetter(ChickenVariant::babyTexture),
                    RegistryCodecs.homogeneousList(Registries.BIOME).optionalFieldOf("biomes").forGetter(ChickenVariant::biomes))
            .apply(record, ChickenVariant::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, ChickenVariant> DIRECT_STREAM_CODEC;
    public static final Codec<Holder<ChickenVariant>> CODEC;
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<ChickenVariant>> STREAM_CODEC;

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else {
            boolean equals;
            if (other instanceof ChickenVariant v) {
                equals = Objects.equals(this.texture, v.texture) && Objects.equals(this.biomes, v.biomes);
            } else {
                equals = false;
            }

            return equals;
        }
    }

    static {
        DIRECT_STREAM_CODEC = StreamCodec.composite(ResourceLocation.STREAM_CODEC, ChickenVariant::texture, ResourceLocation.STREAM_CODEC, ChickenVariant::babyTexture, ByteBufCodecs.optional(ByteBufCodecs.holderSet(Registries.BIOME)), ChickenVariant::biomes, ChickenVariant::new);
        CODEC = RegistryFileCodec.create(NMLVariants.CHICKEN_VARIANT_KEY, DIRECT_CODEC);
        STREAM_CODEC = ByteBufCodecs.holder(NMLVariants.CHICKEN_VARIANT_KEY, DIRECT_STREAM_CODEC);
    }
}