package com.farcr.nomansland.common.mixin;

import com.farcr.nomansland.common.registry.NMLBlocks;
import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(VineBlock.class)
public class VineBlockMixin {
    @Redirect(method = "randomTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/Direction;getRandom(Lnet/minecraft/util/RandomSource;)Lnet/minecraft/core/Direction;"))
    private Direction getRandom(RandomSource random) {
        Direction direction = Util.getRandom(Direction.values(), random);
        while (direction != Direction.DOWN && direction != Direction.UP) {
            direction = Util.getRandom(Direction.values(), random);
        }
        return direction;
    }

    @Redirect(method = "canSupportAtFace", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z"))
    private boolean canSupportAtFace(BlockState instance, Block block) {
        if (block == NMLBlocks.CUT_VINE.get() && instance.is(Blocks.VINE)) return true;
        if (instance.is(NMLBlocks.CUT_VINE)) return false;
        return instance.is(block);
    }

    @Redirect(method = "getUpdatedState", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z"))
    private boolean is(BlockState instance, Block block) {
        if (block == NMLBlocks.CUT_VINE.get() && instance.is(Blocks.VINE)) return true;
        if (instance.is(NMLBlocks.CUT_VINE)) return false;
        return instance.is(block);
    }
}
