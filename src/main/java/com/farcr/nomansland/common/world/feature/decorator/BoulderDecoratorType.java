package com.farcr.nomansland.common.world.feature.decorator;

import com.mojang.serialization.MapCodec;

public record BoulderDecoratorType<P extends BoulderDecorator>(MapCodec<P> codec) {
}
