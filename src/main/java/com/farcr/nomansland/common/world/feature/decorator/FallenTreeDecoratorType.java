package com.farcr.nomansland.common.world.feature.decorator;

import com.mojang.serialization.MapCodec;

public class FallenTreeDecoratorType<P extends FallenTreeDecorator> {
    private final MapCodec<P> codec;

    public FallenTreeDecoratorType(MapCodec<P> codec) { this.codec = codec; }

    public MapCodec<P> codec() { return this.codec; }
}
