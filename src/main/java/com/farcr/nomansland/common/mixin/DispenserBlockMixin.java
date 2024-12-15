package com.farcr.nomansland.common.mixin;

import com.farcr.nomansland.common.blockentity.BombDispenseBehavior;
import com.farcr.nomansland.common.item.ThrowableBombItem;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import vectorwing.farmersdelight.common.Configuration;
import vectorwing.farmersdelight.common.block.entity.dispenser.CuttingBoardDispenseBehavior;
import vectorwing.farmersdelight.common.registry.ModBlocks;

@Mixin(DispenserBlock.class)
public class DispenserBlockMixin {
    @Inject(method = "dispenseFrom", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/DispenserBlock;getDispenseMethod(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/core/dispenser/DispenseItemBehavior;"), cancellable = true)
    public void dispenseFrom(ServerLevel level, BlockState state, BlockPos pos, CallbackInfo ci, @Local DispenserBlockEntity dispenser, @Local BlockSource blockSource, @Local int slot, @Local ItemStack stack) {
        if (stack.getItem() instanceof ThrowableBombItem) {
            dispenser.setItem(slot, new BombDispenseBehavior(stack.getItem()).dispense(blockSource, stack));
            ci.cancel();
        }
    }
}
