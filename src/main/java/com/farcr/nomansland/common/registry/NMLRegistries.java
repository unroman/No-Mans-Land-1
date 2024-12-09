package com.farcr.nomansland.common.registry;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.world.feature.decorator.PondDecoratorType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class NMLRegistries {
    public static final ResourceKey<Registry<PondDecoratorType<?>>> POND_DECORATOR_TYPE_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "worldgen/ponddecorator"));
    public static final Registry<PondDecoratorType<?>> POND_DECORATOR_TYPE = new RegistryBuilder<>(POND_DECORATOR_TYPE_KEY).create();


}
