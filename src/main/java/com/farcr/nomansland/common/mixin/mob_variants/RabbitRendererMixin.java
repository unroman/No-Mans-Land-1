package com.farcr.nomansland.common.mixin.mob_variants;

import com.farcr.nomansland.common.entity.mob_variant.RabbitVariant;
import com.farcr.nomansland.common.mixinduck.RabbitDuck;
import net.minecraft.client.renderer.entity.RabbitRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Rabbit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RabbitRenderer.class)
public abstract class RabbitRendererMixin {
    @Inject(method = "getTextureLocation(Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/resources/ResourceLocation;", at = @At("RETURN"), cancellable = true)
    private void getTextureLocationFromVariant(Entity entity, CallbackInfoReturnable<ResourceLocation> cir) {
        RabbitVariant variant = ((RabbitDuck)entity).nml$getRabbitVariant().value();
        Rabbit rabbit = (Rabbit) entity;
        ResourceLocation texture = rabbit.isBaby() ? variant.babyTexture() : variant.texture();
        cir.setReturnValue(texture.withPath((path) -> "textures/" + path + ".png"));
    }
}
