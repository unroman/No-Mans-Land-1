package com.farcr.nomansland.common.registry;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.client.ambience.fogmodifiers.FogModifier;
import com.farcr.nomansland.common.world.feature.decorator.PondDecoratorType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class NMLRegistries {
    public static final ResourceKey<Registry<PondDecoratorType<?>>> POND_DECORATOR_TYPE_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "worldgen/ponddecorator"));
    public static final Registry<PondDecoratorType<?>> POND_DECORATOR_TYPE = new RegistryBuilder<>(POND_DECORATOR_TYPE_KEY).create();

    public static final ResourceKey<Registry<FogModifier>> FOG_MODIFIER_TYPE_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "client/fogmodifier"));
    public static final Registry<FogModifier> FOG_MODIFIERS = new RegistryBuilder<>(FOG_MODIFIER_TYPE_KEY).create();
}
