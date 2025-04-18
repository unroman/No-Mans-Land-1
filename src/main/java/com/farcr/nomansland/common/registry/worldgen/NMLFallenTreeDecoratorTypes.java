package com.farcr.nomansland.common.registry.worldgen;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.registry.NMLRegistries;
import com.farcr.nomansland.common.world.feature.decorator.ClusterSideFallenTreeDecorator;
import com.farcr.nomansland.common.world.feature.decorator.ClusterTopFallenTreeDecorator;
import com.farcr.nomansland.common.world.feature.decorator.FallenTreeDecorator;
import com.farcr.nomansland.common.world.feature.decorator.FallenTreeDecoratorType;
import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class NMLFallenTreeDecoratorTypes {
    public static final DeferredRegister<FallenTreeDecoratorType<?>> FALLEN_TREE_DECORATOR_TYPES =
            DeferredRegister.create(NMLRegistries.FALLEN_TREE_DECORATOR_TYPE, NoMansLand.MODID);

    public static final Supplier<FallenTreeDecoratorType<ClusterTopFallenTreeDecorator>>
            CLUSTER_TOP = register("cluster_top", ClusterTopFallenTreeDecorator.CODEC);
    public static final Supplier<FallenTreeDecoratorType<ClusterSideFallenTreeDecorator>>
            CLUSTER_SIDE = register("cluster_side", ClusterSideFallenTreeDecorator.CODEC);

    private static <P extends FallenTreeDecorator> DeferredHolder<FallenTreeDecoratorType<?>, FallenTreeDecoratorType<P>> register (String name, MapCodec<P> codec) {
        return FALLEN_TREE_DECORATOR_TYPES.register(name, () -> new FallenTreeDecoratorType<>(codec));
    }
}
