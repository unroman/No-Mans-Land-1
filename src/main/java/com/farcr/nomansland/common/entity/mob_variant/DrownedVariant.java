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

public record DrownedVariant(ResourceLocation texture, int weight, ResourceLocation outerLayerTexture, Optional<HolderSet<Biome>> biomes) implements MobVariant {

    public static final Codec<DrownedVariant> DIRECT_CODEC = RecordCodecBuilder.create((record) -> record.group(
                    ResourceLocation.CODEC.fieldOf("texture").forGetter(DrownedVariant::texture),
                    Codec.INT.fieldOf("weight").forGetter(DrownedVariant::weight),
                    ResourceLocation.CODEC.fieldOf("outer_layer_texture").forGetter(DrownedVariant::outerLayerTexture),
                    RegistryCodecs.homogeneousList(Registries.BIOME).optionalFieldOf("biomes").forGetter(DrownedVariant::biomes))
            .apply(record, DrownedVariant::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, DrownedVariant> DIRECT_STREAM_CODEC =
            StreamCodec.composite(
                    ResourceLocation.STREAM_CODEC, DrownedVariant::texture,
                    ByteBufCodecs.INT, DrownedVariant::weight,
                    ResourceLocation.STREAM_CODEC, DrownedVariant::outerLayerTexture,
                    ByteBufCodecs.optional(ByteBufCodecs.holderSet(Registries.BIOME)), DrownedVariant::biomes,
                    DrownedVariant::new
            );

    public static final Codec<Holder<DrownedVariant>> CODEC =
            RegistryFileCodec.create(NMLMobVariants.DROWNED_VARIANT_KEY, DIRECT_CODEC);

    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<DrownedVariant>> STREAM_CODEC =
            ByteBufCodecs.holder(NMLMobVariants.DROWNED_VARIANT_KEY, DIRECT_STREAM_CODEC);
}


