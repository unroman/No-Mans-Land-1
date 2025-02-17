package com.farcr.nomansland.common.mixin.mob_variants;

import com.farcr.nomansland.common.entity.mob_variant.DolphinVariant;
import com.farcr.nomansland.common.entity.mob_variant.ZombieVariant;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.DolphinRenderer;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.monster.Zombie;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractZombieRenderer.class)
public class ZombieRendererMixin {
    @Inject(method = "getTextureLocation(Lnet/minecraft/world/entity/monster/Zombie;)Lnet/minecraft/resources/ResourceLocation;", at = @At("RETURN"), cancellable = true)
    private void getTextureLocation(Zombie entity, CallbackInfoReturnable<ResourceLocation> cir) {
        if (entity.getType() == EntityType.ZOMBIE)
            cir.setReturnValue(((VariantHolder<Holder<ZombieVariant>>)entity).getVariant().value().texture().withPath((path) -> "textures/" + path + ".png"));
    }
}
