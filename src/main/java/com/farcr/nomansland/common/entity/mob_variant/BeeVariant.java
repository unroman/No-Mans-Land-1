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

public record BeeVariant(ResourceLocation texture, ResourceLocation angryTexture, ResourceLocation nectarTexture, ResourceLocation angryNectarTexture, ResourceLocation stingerTexture, Optional<HolderSet<Biome>> biomes) implements MobVariant {

    public static final Codec<BeeVariant> DIRECT_CODEC = RecordCodecBuilder.create((record) -> record.group(
                    ResourceLocation.CODEC.fieldOf("texture").forGetter(BeeVariant::texture),
                    ResourceLocation.CODEC.fieldOf("angry_texture").forGetter(BeeVariant::angryTexture),
                    ResourceLocation.CODEC.fieldOf("nectar_texture").forGetter(BeeVariant::nectarTexture),
                    ResourceLocation.CODEC.fieldOf("angry_nectar_texture").forGetter(BeeVariant::angryNectarTexture),
                    ResourceLocation.CODEC.fieldOf("stinger_texture").forGetter(BeeVariant::stingerTexture),
                    RegistryCodecs.homogeneousList(Registries.BIOME).optionalFieldOf("biomes").forGetter(BeeVariant::biomes))
            .apply(record, BeeVariant::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, BeeVariant> DIRECT_STREAM_CODEC =
            StreamCodec.composite(
                    ResourceLocation.STREAM_CODEC, BeeVariant::texture,
                    ResourceLocation.STREAM_CODEC, BeeVariant::angryTexture,
                    ResourceLocation.STREAM_CODEC, BeeVariant::nectarTexture,
                    ResourceLocation.STREAM_CODEC, BeeVariant::angryNectarTexture,
                    ResourceLocation.STREAM_CODEC, BeeVariant::stingerTexture,
                    ByteBufCodecs.optional(ByteBufCodecs.holderSet(Registries.BIOME)), BeeVariant::biomes,
                    BeeVariant::new
            );

    public static final Codec<Holder<BeeVariant>> CODEC =
            RegistryFileCodec.create(NMLMobVariants.BEE_VARIANT_KEY, DIRECT_CODEC);

    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<BeeVariant>> STREAM_CODEC =
            ByteBufCodecs.holder(NMLMobVariants.BEE_VARIANT_KEY, DIRECT_STREAM_CODEC);

    @Override
    public int weight() {
        return 1;
    }
}


