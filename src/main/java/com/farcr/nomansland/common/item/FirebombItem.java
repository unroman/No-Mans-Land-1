package com.farcr.nomansland.common.item;

import com.farcr.nomansland.common.entity.bombs.FirebombEntity;
import com.farcr.nomansland.common.entity.bombs.ThrowableBombEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FirebombItem extends ThrowableBombItem {

    public FirebombItem(Properties properties) {
        super(properties);
    }

    @Override
    public ThrowableBombEntity createBomb(LivingEntity entity, Level level) {
        return new FirebombEntity(entity, level);
    }

    @Override
    public ThrowableBombEntity createBomb(Level level, BlockPos pos) {
        return new FirebombEntity(level, pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remainingTicks) {
        super.onUseTick(level, entity, stack, remainingTicks);
        int timeUsed = this.getUseDuration(stack, entity) - remainingTicks;
        if (timeUsed == DEFAULT_THROW_TIME && entity.isShiftKeyDown()) entity.playSound(SoundEvents.UI_BUTTON_CLICK.value(), 1, 1.3F);
    }
}