package com.farcr.nomansland.common.world.feature.decorator;

import com.mojang.serialization.MapCodec;

public record FallenTreeDecoratorType<P extends FallenTreeDecorator>(MapCodec<P> codec) {
}
