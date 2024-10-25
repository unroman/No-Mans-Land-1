package com.farcr.nomansland.common.mixin;

import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.VineBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(VineBlock.class)
public class VineBlockMixin {
    @Redirect(method = "randomTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/Direction;getRandom(Lnet/minecraft/util/RandomSource;)Lnet/minecraft/core/Direction;"))
    private Direction randomTick(RandomSource random) {
        Direction direction = Util.getRandom(Direction.values(), random);
        while (direction != Direction.DOWN && direction != Direction.UP) {
            direction = Util.getRandom(Direction.values(), random);
        }
        return direction;
    }
}
