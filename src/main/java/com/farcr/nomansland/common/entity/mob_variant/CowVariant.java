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

public record CowVariant(ResourceLocation texture, int weight, ResourceLocation babyTexture,  Optional<HolderSet<Biome>> biomes) implements MobVariant {

    public static final Codec<CowVariant> DIRECT_CODEC = RecordCodecBuilder.create((record) -> record.group(
                    ResourceLocation.CODEC.fieldOf("texture").forGetter(CowVariant::texture),
                    Codec.INT.fieldOf("weight").forGetter(CowVariant::weight),
                    ResourceLocation.CODEC.fieldOf("baby_texture").forGetter(CowVariant::babyTexture),
                    RegistryCodecs.homogeneousList(Registries.BIOME).optionalFieldOf("biomes").forGetter(CowVariant::biomes))
            .apply(record, CowVariant::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, CowVariant> DIRECT_STREAM_CODEC =
            StreamCodec.composite(
                    ResourceLocation.STREAM_CODEC, CowVariant::texture,
                    ByteBufCodecs.INT, CowVariant::weight,
                    ResourceLocation.STREAM_CODEC, CowVariant::babyTexture,
                    ByteBufCodecs.optional(ByteBufCodecs.holderSet(Registries.BIOME)), CowVariant::biomes,
                    CowVariant::new
            );

    public static final Codec<Holder<CowVariant>> CODEC =
            RegistryFileCodec.create(NMLMobVariants.COW_VARIANT_KEY, DIRECT_CODEC);

    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<CowVariant>> STREAM_CODEC =
            ByteBufCodecs.holder(NMLMobVariants.COW_VARIANT_KEY, DIRECT_STREAM_CODEC);
}


