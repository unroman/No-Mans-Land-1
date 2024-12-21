package com.farcr.nomansland.common.world.feature.decorator;

import com.mojang.serialization.MapCodec;

public class BoulderDecoratorType<P extends BoulderDecorator> {
    private final MapCodec<P> codec;

    public BoulderDecoratorType(MapCodec<P> codec) { this.codec = codec; }

    public MapCodec<P> codec() { return this.codec; }
}
