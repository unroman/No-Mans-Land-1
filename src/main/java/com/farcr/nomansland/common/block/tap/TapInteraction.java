package com.farcr.nomansland.common.block.tap;

import com.farcr.nomansland.common.registry.NMLRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.List;
import java.util.Optional;

public record TapInteraction(List<BlockStateProvider> sources, Block cauldron, float rate, Optional<ParticleType<?>> particleType) {

    public static final Codec<TapInteraction> DIRECT_CODEC = RecordCodecBuilder.create((record) -> record.group(
                    Codec.list(BlockStateProvider.CODEC).fieldOf("sources").forGetter(TapInteraction::sources),
                    BuiltInRegistries.BLOCK.byNameCodec().fieldOf("cauldron").forGetter(TapInteraction::cauldron),
                    Codec.floatRange(0, 10F).fieldOf("rate").forGetter(TapInteraction::rate),
                    BuiltInRegistries.PARTICLE_TYPE.byNameCodec().optionalFieldOf("particle").forGetter(TapInteraction::particleType))
            .apply(record, TapInteraction::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, TapInteraction> DIRECT_STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.fromCodec(Codec.list(BlockStateProvider.CODEC)), TapInteraction::sources,
                    ByteBufCodecs.fromCodec(BuiltInRegistries.BLOCK.byNameCodec()), TapInteraction::cauldron,
                    ByteBufCodecs.fromCodec(Codec.floatRange(0.01F, 10F)), TapInteraction::rate,
                    ByteBufCodecs.optional(ByteBufCodecs.fromCodec(BuiltInRegistries.PARTICLE_TYPE.byNameCodec())), TapInteraction::particleType,
                    TapInteraction::new
            );

    public static final Codec<Holder<TapInteraction>> CODEC =
            RegistryFileCodec.create(NMLRegistries.TAP_INTERACTION_KEY, DIRECT_CODEC);

    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<TapInteraction>> STREAM_CODEC =
            ByteBufCodecs.holder(NMLRegistries.TAP_INTERACTION_KEY, DIRECT_STREAM_CODEC);
}
