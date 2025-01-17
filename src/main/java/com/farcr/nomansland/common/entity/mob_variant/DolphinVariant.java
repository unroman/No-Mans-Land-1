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

public record DolphinVariant(ResourceLocation texture, int weight, Optional<HolderSet<Biome>> biomes) implements MobVariant {

    public static final Codec<DolphinVariant> DIRECT_CODEC = RecordCodecBuilder.create((record) -> record.group(
                    ResourceLocation.CODEC.fieldOf("texture").forGetter(DolphinVariant::texture),
                    Codec.INT.fieldOf("weight").forGetter(DolphinVariant::weight),
                    RegistryCodecs.homogeneousList(Registries.BIOME).optionalFieldOf("biomes").forGetter(DolphinVariant::biomes))
            .apply(record, DolphinVariant::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, DolphinVariant> DIRECT_STREAM_CODEC =
            StreamCodec.composite(
                    ResourceLocation.STREAM_CODEC, DolphinVariant::texture,
                    ByteBufCodecs.INT, DolphinVariant::weight,
                    ByteBufCodecs.optional(ByteBufCodecs.holderSet(Registries.BIOME)), DolphinVariant::biomes,
                    DolphinVariant::new
            );

    public static final Codec<Holder<DolphinVariant>> CODEC =
            RegistryFileCodec.create(NMLMobVariants.DOLPHIN_VARIANT_KEY, DIRECT_CODEC);

    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<DolphinVariant>> STREAM_CODEC =
            ByteBufCodecs.holder(NMLMobVariants.DOLPHIN_VARIANT_KEY, DIRECT_STREAM_CODEC);
}


