package com.farcr.nomansland.common.blockentity;

import com.farcr.nomansland.common.entity.bombs.ThrowableBombEntity;
import com.farcr.nomansland.common.item.ThrowableBombItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.Vec3;

public class BombDispenseBehavior extends DefaultDispenseItemBehavior {

    private final ThrowableBombItem throwableBombItem;

    public BombDispenseBehavior(Item bomb) {
        if (bomb instanceof ThrowableBombItem throwableBombItem) {
            this.throwableBombItem = throwableBombItem;
        } else {
            String name = String.valueOf(bomb);
            throw new IllegalArgumentException(name + " not instance of " + ThrowableBombItem.class.getSimpleName());
        }
    }

    public ItemStack execute(BlockSource source, ItemStack item) {
        Level level = source.level();
        Direction direction = source.state().getValue(DispenserBlock.FACING);
        Position position = DispenserBlock.getDispensePosition(source, 0.7, new Vec3(0.0, 0.1, 0.0));
        ThrowableBombEntity bomb = throwableBombItem.createBomb(level, BlockPos.containing(position));
        bomb.shoot(direction.getStepX(), direction.getStepY(), direction.getStepZ(), 0.5F, 6);
        level.addFreshEntity(bomb);
        item.shrink(1);
        return item;
    }

    protected void playSound(BlockSource source) {
        source.level().levelEvent(1002, source.pos(), 0);
    }
}
