//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.farcr.nomansland.common.world.feature.decorator;

import com.mojang.serialization.MapCodec;

public class PondDecoratorType<P extends PondDecorator> {
    private final MapCodec<P> codec;

    public PondDecoratorType(MapCodec<P> codec) {
        this.codec = codec;
    }

    public MapCodec<P> codec() {
        return this.codec;
    }
}
