package com.farcr.nomansland.common.registry;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.world.biomemodifiers.ChangeColorsBiomeModifier;
import com.farcr.nomansland.common.world.biomemodifiers.ChangeMusicBiomeModifier;
import com.farcr.nomansland.common.world.biomemodifiers.ChangeSpawnsBiomeModifier;
import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class NMLBiomeModifiers {
    public static final DeferredRegister<MapCodec<? extends BiomeModifier>> BIOME_MODIFIERS =
            DeferredRegister.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, NoMansLand.MODID);

    public static final Supplier<MapCodec<ChangeColorsBiomeModifier>> CHANGE_COLORS =
            BIOME_MODIFIERS.register("change_colors", () -> ChangeColorsBiomeModifier.CODEC);

    public static final Supplier<MapCodec<ChangeMusicBiomeModifier>> CHANGE_MUSIC =
            BIOME_MODIFIERS.register("change_music", () -> ChangeMusicBiomeModifier.CODEC);

    public static final Supplier<MapCodec<ChangeSpawnsBiomeModifier>> CHANGE_SPAWN =
            BIOME_MODIFIERS.register("change_spawn", () -> ChangeSpawnsBiomeModifier.CODEC);
}
