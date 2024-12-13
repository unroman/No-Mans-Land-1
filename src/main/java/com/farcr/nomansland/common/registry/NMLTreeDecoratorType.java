package com.farcr.nomansland.common.registry;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.world.feature.BeardMossDecorator;
import com.farcr.nomansland.common.world.feature.decorator.FruitLeavesDecorator;
import com.farcr.nomansland.common.world.feature.decorator.HugeShelfMushroomDecorator;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public record NMLTreeDecoratorType<P extends TreeDecorator>(MapCodec<P> codec) {
    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATOR_TYPES =
            DeferredRegister.create(BuiltInRegistries.TREE_DECORATOR_TYPE, NoMansLand.MODID);

    public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<FruitLeavesDecorator>>
            FRUIT_LEAVES = register("fruit_leaves", FruitLeavesDecorator.CODEC);

    public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<BeardMossDecorator>>
            BEARD_MOSS = register("beard_moss", BeardMossDecorator.CODEC);

    public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<HugeShelfMushroomDecorator>>
            HUGE_SHELF_MUSHROOM = register("huge_shelf_mushroom", HugeShelfMushroomDecorator.CODEC);

    private static <P extends TreeDecorator> DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<P>> register(String name, MapCodec<P> codec) {
        return TREE_DECORATOR_TYPES.register(name, () -> new TreeDecoratorType<>(codec));
    }

    public MapCodec<P> codec() {
        return this.codec;
    }

}
