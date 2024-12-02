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

public record BillhookBassVariant(ResourceLocation texture, int weight, Optional<HolderSet<Biome>> biomes) implements MobVariant {

    public static final Codec<BillhookBassVariant> DIRECT_CODEC = RecordCodecBuilder.create((record) -> record.group(
                    ResourceLocation.CODEC.fieldOf("texture").forGetter(BillhookBassVariant::texture),
                    Codec.INT.fieldOf("weight").forGetter(BillhookBassVariant::weight),
                    RegistryCodecs.homogeneousList(Registries.BIOME).optionalFieldOf("biomes").forGetter(BillhookBassVariant::biomes))
            .apply(record, BillhookBassVariant::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, BillhookBassVariant> DIRECT_STREAM_CODEC =
            StreamCodec.composite(
                    ResourceLocation.STREAM_CODEC, BillhookBassVariant::texture,
                    ByteBufCodecs.INT, BillhookBassVariant::weight,
                    ByteBufCodecs.optional(ByteBufCodecs.holderSet(Registries.BIOME)), BillhookBassVariant::biomes,
                    BillhookBassVariant::new
            );

    public static final Codec<Holder<BillhookBassVariant>> CODEC =
            RegistryFileCodec.create(NMLMobVariants.BILLHOOK_BASS_VARIANT_KEY, DIRECT_CODEC);

    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<BillhookBassVariant>> STREAM_CODEC =
            ByteBufCodecs.holder(NMLMobVariants.BILLHOOK_BASS_VARIANT_KEY, DIRECT_STREAM_CODEC);
}


