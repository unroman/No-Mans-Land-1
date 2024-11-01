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

public record TurtleVariant(ResourceLocation texture, int weight, Optional<HolderSet<Biome>> biomes) implements MobVariant {

    public static final Codec<TurtleVariant> DIRECT_CODEC = RecordCodecBuilder.create((record) -> record.group(
                    ResourceLocation.CODEC.fieldOf("texture").forGetter(TurtleVariant::texture),
                    Codec.INT.fieldOf("weight").forGetter(TurtleVariant::weight),
                    RegistryCodecs.homogeneousList(Registries.BIOME).optionalFieldOf("biomes").forGetter(TurtleVariant::biomes))
            .apply(record, TurtleVariant::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, TurtleVariant> DIRECT_STREAM_CODEC =
            StreamCodec.composite(
                    ResourceLocation.STREAM_CODEC, TurtleVariant::texture,
                    ByteBufCodecs.INT, TurtleVariant::weight,
                    ByteBufCodecs.optional(ByteBufCodecs.holderSet(Registries.BIOME)), TurtleVariant::biomes,
                    TurtleVariant::new
            );

    public static final Codec<Holder<TurtleVariant>> CODEC =
            RegistryFileCodec.create(NMLMobVariants.TURTLE_VARIANT_KEY, DIRECT_CODEC);

    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<TurtleVariant>> STREAM_CODEC =
            ByteBufCodecs.holder(NMLMobVariants.TURTLE_VARIANT_KEY, DIRECT_STREAM_CODEC);
}


