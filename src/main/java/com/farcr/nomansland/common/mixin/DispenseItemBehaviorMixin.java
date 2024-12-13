package com.farcr.nomansland.common.mixin;

import com.farcr.nomansland.common.entity.bombs.FirebombEntity;
import com.farcr.nomansland.common.entity.bombs.ThrowableBombEntity;
import com.farcr.nomansland.common.item.ExplosiveItem;
import com.farcr.nomansland.common.item.FirebombItem;
import com.farcr.nomansland.common.registry.NMLItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DispenseItemBehavior.class)
public class DispenseItemBehaviorMixin {
    @Inject(method = "bootStrap", at = @At("HEAD"))
    private static void bootStrap(CallbackInfo ci) {
        DispenserBlock.registerBehavior(NMLItems.FIREBOMB, new DefaultDispenseItemBehavior() {
            protected ItemStack execute(BlockSource blockSource, ItemStack stack) {
                Level level = blockSource.level();
                BlockPos blockpos = blockSource.pos().relative(blockSource.state().getValue(DispenserBlock.FACING));
                FirebombItem firebombItem = (FirebombItem) stack.getItem();
                ThrowableBombEntity throwableBombEntity = firebombItem.createBomb(null, level);
                level.playSound(null, throwableBombEntity.getX(), throwableBombEntity.getY(), throwableBombEntity.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1, 1);
                level.gameEvent(null, GameEvent.ENTITY_PLACE, blockpos);
                stack.shrink(1);
                return stack;
            }
        });

        DispenserBlock.registerBehavior(NMLItems.EXPLOSIVE, new DefaultDispenseItemBehavior() {
            protected ItemStack execute(BlockSource blockSource, ItemStack stack) {
                Level level = blockSource.level();
                BlockPos blockpos = blockSource.pos().relative(blockSource.state().getValue(DispenserBlock.FACING));
                ExplosiveItem explosiveItem = (ExplosiveItem) stack.getItem();
                ThrowableBombEntity throwableBombEntity = explosiveItem.createBomb(null, level);
                level.playSound(null, throwableBombEntity.getX(), throwableBombEntity.getY(), throwableBombEntity.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1, 1);
                level.gameEvent(null, GameEvent.ENTITY_PLACE, blockpos);
                stack.shrink(1);
                return stack;
            }
        });
    }
}
