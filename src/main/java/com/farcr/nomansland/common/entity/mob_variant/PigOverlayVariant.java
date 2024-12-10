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

public record PigOverlayVariant(ResourceLocation texture, int weight, ResourceLocation babyTexture, Optional<HolderSet<Biome>> biomes) implements MobVariant {

    public static final Codec<PigOverlayVariant> DIRECT_CODEC = RecordCodecBuilder.create((record) -> record.group(
                    ResourceLocation.CODEC.fieldOf("texture").forGetter(PigOverlayVariant::texture),
                    Codec.INT.fieldOf("weight").forGetter(PigOverlayVariant::weight),
                    ResourceLocation.CODEC.fieldOf("baby_texture").forGetter(PigOverlayVariant::babyTexture),
                    RegistryCodecs.homogeneousList(Registries.BIOME).optionalFieldOf("biomes").forGetter(PigOverlayVariant::biomes))
            .apply(record, PigOverlayVariant::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, PigOverlayVariant> DIRECT_STREAM_CODEC =
            StreamCodec.composite(
                    ResourceLocation.STREAM_CODEC, PigOverlayVariant::texture,
                    ByteBufCodecs.INT, PigOverlayVariant::weight,
                    ResourceLocation.STREAM_CODEC, PigOverlayVariant::babyTexture,
                    ByteBufCodecs.optional(ByteBufCodecs.holderSet(Registries.BIOME)), PigOverlayVariant::biomes,
                    PigOverlayVariant::new
            );

    public static final Codec<Holder<PigOverlayVariant>> CODEC =
            RegistryFileCodec.create(NMLMobVariants.PIG_OVERLAY_VARIANT_KEY, DIRECT_CODEC);

    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<PigOverlayVariant>> STREAM_CODEC =
            ByteBufCodecs.holder(NMLMobVariants.PIG_OVERLAY_VARIANT_KEY, DIRECT_STREAM_CODEC);
}


