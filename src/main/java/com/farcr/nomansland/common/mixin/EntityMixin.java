package com.farcr.nomansland.common.mixin;

import com.farcr.nomansland.common.registry.blocks.NMLBlocks;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.Set;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow public abstract void playSound(SoundEvent sound, float volume, float pitch);

    @Shadow public abstract double getY();

    @Shadow public abstract double getY(double scale);

    @Shadow public abstract boolean isInvulnerable();

    @Shadow @Nullable public abstract ItemEntity spawnAtLocation(ItemStack stack, float offsetY);

    @Shadow public abstract float getBbHeight();


    @Shadow public abstract void discard();

    @Shadow public abstract float getXRot();

    @Shadow public abstract float getYRot();

    @Shadow public abstract double getZ();


    @Shadow public abstract double getX();

    @Shadow public abstract boolean hasCustomName();

    @Shadow public abstract boolean isCustomNameVisible();

    @Shadow @Nullable public abstract Component getCustomName();

    @Shadow public abstract Level level();

    @Shadow public abstract EntityType<?> getType();

    @Shadow @Final protected RandomSource random;

    @Shadow public abstract BlockPos blockPosition();

    @Shadow public abstract RegistryAccess registryAccess();

    @Shadow @Final
    protected SynchedEntityData entityData;

    @Shadow private EntityDimensions dimensions;

    @Shadow public abstract EntityDimensions getDimensions(Pose pose);

    @Shadow public abstract Pose getPose();

    @Shadow public abstract boolean removeTag(String tag);

    @Shadow public abstract Set<String> getTags();

    @Shadow public abstract void igniteForSeconds(float seconds);

    @Shadow public abstract BlockPos getOnPos();

    @Shadow protected abstract BlockPos getOnPos(float yOffset);

    @Shadow public abstract BlockState getBlockStateOn();

    @Shadow public float fallDistance;

    @Shadow public abstract Vec3 position();

    @Unique @Nullable
    private Vec3 startingToFallPosition;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(EntityType<?> entityType, Level level, CallbackInfo ci) {
        if (entityType == EntityType.COW) dimensions = getDimensions(getPose()) == null ?  entityType.getDimensions() : getDimensions(getPose());
    }

    @Inject(method = "getOnPosLegacy", at = @At("RETURN"), cancellable = true)
    private void getOnPosLegacy(CallbackInfoReturnable<BlockPos> cir) {
        cir.setReturnValue(getBlockStateOn().is(NMLBlocks.SPIKE_TRAP) ? getOnPos() : getOnPos(0.2F));
    }

    @Inject(method = "resetFallDistance", at = @At("HEAD"))
    private void resetFallDistance(CallbackInfo ci) {
        if (((Entity) (Object) this) instanceof LivingEntity livingEntity && livingEntity.getHealth() > 0 && startingToFallPosition != null && !livingEntity.getPassengers().isEmpty()) {
            livingEntity.getPassengers().forEach(entity -> {
                if (entity instanceof ServerPlayer player && player.getHealth() > 0) {
                    CriteriaTriggers.FALL_FROM_HEIGHT.trigger(player, this.startingToFallPosition);
                }
            });
        }

        startingToFallPosition = null;
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {
        trackStartFallingPosition();
    }

    @Unique
    private void trackStartFallingPosition() {
        if (fallDistance > 0.0F && startingToFallPosition == null) {
            startingToFallPosition = position();
        }
    }
}
