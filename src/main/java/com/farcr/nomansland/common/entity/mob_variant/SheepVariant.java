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

public record SheepVariant(ResourceLocation texture, ResourceLocation babyTexture, ResourceLocation furTexture, ResourceLocation babyFurTexture, ResourceLocation shearedFurTexture, Optional<HolderSet<Biome>> biomes) implements MobVariant {

    public static final Codec<SheepVariant> DIRECT_CODEC = RecordCodecBuilder.create((record) -> record.group(
                    ResourceLocation.CODEC.fieldOf("texture").forGetter(SheepVariant::texture),
                    ResourceLocation.CODEC.fieldOf("baby_texture").forGetter(SheepVariant::babyTexture),
                    ResourceLocation.CODEC.fieldOf("fur_texture").forGetter(SheepVariant::furTexture),
                    ResourceLocation.CODEC.fieldOf("baby_fur_texture").forGetter(SheepVariant::babyFurTexture),
                    ResourceLocation.CODEC.fieldOf("sheared_fur_texture").forGetter(SheepVariant::shearedFurTexture),
                    RegistryCodecs.homogeneousList(Registries.BIOME).optionalFieldOf("biomes").forGetter(SheepVariant::biomes))
            .apply(record, SheepVariant::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, SheepVariant> DIRECT_STREAM_CODEC =
            StreamCodec.composite(
                    ResourceLocation.STREAM_CODEC, SheepVariant::texture,
                    ResourceLocation.STREAM_CODEC, SheepVariant::babyTexture,
                    ResourceLocation.STREAM_CODEC, SheepVariant::furTexture,
                    ResourceLocation.STREAM_CODEC, SheepVariant::babyFurTexture,
                    ResourceLocation.STREAM_CODEC, SheepVariant::shearedFurTexture,
                    ByteBufCodecs.optional(ByteBufCodecs.holderSet(Registries.BIOME)), SheepVariant::biomes,
                    SheepVariant::new
            );

    public static final Codec<Holder<SheepVariant>> CODEC =
            RegistryFileCodec.create(NMLMobVariants.SHEEP_VARIANT_KEY, DIRECT_CODEC);

    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<SheepVariant>> STREAM_CODEC =
            ByteBufCodecs.holder(NMLMobVariants.SHEEP_VARIANT_KEY, DIRECT_STREAM_CODEC);

    @Override
    public int weight() {
        return 1;
    }
}


