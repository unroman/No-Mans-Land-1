package com.farcr.nomansland.common.entity.mob_variant;

import com.farcr.nomansland.common.registry.entities.NMLMobVariants;
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

public record ZombieVariant(ResourceLocation texture, int weight, Optional<HolderSet<Biome>> biomes) implements MobVariant {

    public static final Codec<ZombieVariant> DIRECT_CODEC = RecordCodecBuilder.create((record) -> record.group(
                    ResourceLocation.CODEC.fieldOf("texture").forGetter(ZombieVariant::texture),
                    Codec.INT.fieldOf("weight").forGetter(ZombieVariant::weight),
                    RegistryCodecs.homogeneousList(Registries.BIOME).optionalFieldOf("biomes").forGetter(ZombieVariant::biomes))
            .apply(record, ZombieVariant::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, ZombieVariant> DIRECT_STREAM_CODEC =
            StreamCodec.composite(
                    ResourceLocation.STREAM_CODEC, ZombieVariant::texture,
                    ByteBufCodecs.INT, ZombieVariant::weight,
                    ByteBufCodecs.optional(ByteBufCodecs.holderSet(Registries.BIOME)), ZombieVariant::biomes,
                    ZombieVariant::new
            );

    public static final Codec<Holder<ZombieVariant>> CODEC =
            RegistryFileCodec.create(NMLMobVariants.ZOMBIE_VARIANT_KEY, DIRECT_CODEC);

    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<ZombieVariant>> STREAM_CODEC =
            ByteBufCodecs.holder(NMLMobVariants.ZOMBIE_VARIANT_KEY, DIRECT_STREAM_CODEC);
}


