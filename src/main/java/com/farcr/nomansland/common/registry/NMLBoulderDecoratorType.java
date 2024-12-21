package com.farcr.nomansland.common.registry;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.world.feature.decorator.BoulderDecorator;
import com.farcr.nomansland.common.world.feature.decorator.BoulderDecoratorType;
import com.farcr.nomansland.common.world.feature.decorator.ClusterOnStoneBoulderDecorator;
import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public record NMLBoulderDecoratorType<P extends BoulderDecorator>(MapCodec<P> codec) {
    public static final DeferredRegister<BoulderDecoratorType<?>> BOULDER_DECORATOR_TYPES =
            DeferredRegister.create(NMLRegistries.BOULDER_DECORATOR_TYPE, NoMansLand.MODID);

    public static final DeferredHolder<BoulderDecoratorType<?>, BoulderDecoratorType<ClusterOnStoneBoulderDecorator>> CLUSTER_ON_STONE = register("cluster_on_stone", ClusterOnStoneBoulderDecorator.CODEC);

    private static <P extends BoulderDecorator> DeferredHolder<BoulderDecoratorType<?>, BoulderDecoratorType<P>> register (String name, MapCodec<P> codec) {
        return BOULDER_DECORATOR_TYPES.register(name, () -> new BoulderDecoratorType<>(codec));
    }

    public MapCodec<P> codec() { return this.codec; }
}
