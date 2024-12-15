package com.farcr.nomansland.common.item;


import com.farcr.nomansland.common.entity.bombs.ThrowableBombEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.Vec3;

import java.util.OptionalInt;

public abstract class ThrowableBombItem extends Item {

    protected static final int DEFAULT_THROW_TIME = 10;
    private static final int COOLDOWN_TIME = 40;

    public ThrowableBombItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 7200;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack arg) {
        return UseAnim.BOW;
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remainingTicks) {
        int timeUsed = this.getUseDuration(stack, entity) - remainingTicks;
        if (timeUsed >= DEFAULT_THROW_TIME && !entity.isShiftKeyDown()) entity.releaseUsingItem();
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int remainingTicks) {
        if (this.getUseDuration(stack, entity) - remainingTicks < DEFAULT_THROW_TIME) {
            return;
        }

        if (!level.isClientSide()) {
            ThrowableBombEntity bomb = this.createBomb(entity, level);
            bomb.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 0.8F, 1.0F);
            level.addFreshEntity(bomb);
        }

        level.playSound(entity instanceof Player player ? player : null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.SPLASH_POTION_THROW, SoundSource.PLAYERS, 1.0F, 1.0F / (level.random.nextFloat() * 0.4F + 1.2F));
        if (!(entity instanceof Player player) || !player.isCreative()) {
            stack.shrink(1);
        }

        if (entity instanceof Player player) {
            player.awardStat(Stats.ITEM_USED.get(this));
            player.getCooldowns().addCooldown(stack.getItem(), COOLDOWN_TIME);
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    public abstract ThrowableBombEntity createBomb(LivingEntity entity, Level level);

    public abstract ThrowableBombEntity createBomb(Level level, BlockPos pos);
}