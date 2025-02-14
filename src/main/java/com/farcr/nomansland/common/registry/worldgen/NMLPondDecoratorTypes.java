package com.farcr.nomansland.common.registry.worldgen;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.registry.NMLRegistries;
import com.farcr.nomansland.common.world.feature.decorator.*;
import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NMLPondDecoratorTypes {
    public static final DeferredRegister<PondDecoratorType<?>> POND_DECORATOR_TYPES =
            DeferredRegister.create(NMLRegistries.POND_DECORATOR_TYPE, NoMansLand.MODID);

    public static final DeferredHolder<PondDecoratorType<?>, PondDecoratorType<ClusterOnWaterPondDecorator>> CLUSTER_ON_WATER = register("cluster_on_water", ClusterOnWaterPondDecorator.CODEC);
    public static final DeferredHolder<PondDecoratorType<?>, PondDecoratorType<StackedPlantPondDecorator>> STACKED_PLANT = register("stacked_plant", StackedPlantPondDecorator.CODEC);
    public static final DeferredHolder<PondDecoratorType<?>, PondDecoratorType<FeatureClusterPondDecorator>> FEATURE_CLUSTER = register("feature_cluster", FeatureClusterPondDecorator.CODEC);

    private static <P extends PondDecorator> DeferredHolder<PondDecoratorType<?>, PondDecoratorType<P>> register (String name, MapCodec<P> codec) {
        return POND_DECORATOR_TYPES.register(name, () -> new PondDecoratorType<>(codec));
    }
}
