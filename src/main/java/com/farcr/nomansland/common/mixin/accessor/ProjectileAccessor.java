package com.farcr.nomansland.common.mixin.accessor;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Projectile.class)
public interface ProjectileAccessor {

    @Invoker("getOwner")
    Entity invokeGetOwner();
}
