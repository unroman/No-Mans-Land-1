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

public record MooshroomVariant(ResourceLocation texture, int weight, ResourceLocation babyTexture, Optional<HolderSet<Biome>> biomes) implements MobVariant {

    public static final Codec<MooshroomVariant> DIRECT_CODEC = RecordCodecBuilder.create((record) -> record.group(
                    ResourceLocation.CODEC.fieldOf("texture").forGetter(MooshroomVariant::texture),
                    Codec.INT.fieldOf("weight").forGetter(MooshroomVariant::weight),
                    ResourceLocation.CODEC.fieldOf("baby_texture").forGetter(MooshroomVariant::babyTexture),
                    RegistryCodecs.homogeneousList(Registries.BIOME).optionalFieldOf("biomes").forGetter(MooshroomVariant::biomes))
            .apply(record, MooshroomVariant::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, MooshroomVariant> DIRECT_STREAM_CODEC =
            StreamCodec.composite(
                    ResourceLocation.STREAM_CODEC, MooshroomVariant::texture,
                    ByteBufCodecs.INT, MooshroomVariant::weight,
                    ResourceLocation.STREAM_CODEC, MooshroomVariant::babyTexture,
                    ByteBufCodecs.optional(ByteBufCodecs.holderSet(Registries.BIOME)), MooshroomVariant::biomes,
                    MooshroomVariant::new
            );

    public static final Codec<Holder<MooshroomVariant>> CODEC =
            RegistryFileCodec.create(NMLMobVariants.MOOSHROOM_VARIANT_KEY, DIRECT_CODEC);

    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<MooshroomVariant>> STREAM_CODEC =
            ByteBufCodecs.holder(NMLMobVariants.MOOSHROOM_VARIANT_KEY, DIRECT_STREAM_CODEC);
}


