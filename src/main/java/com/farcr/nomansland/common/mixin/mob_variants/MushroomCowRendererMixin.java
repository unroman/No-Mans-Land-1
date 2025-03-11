package com.farcr.nomansland.common.mixin.mob_variants;

import com.farcr.nomansland.common.entity.mob_variant.MooshroomVariant;
import com.farcr.nomansland.common.mixinduck.MooshroomDuck;
import net.minecraft.client.renderer.entity.MushroomCowRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.MushroomCow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MushroomCowRenderer.class)
public class MushroomCowRendererMixin {
    @Inject(method = "getTextureLocation(Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/resources/ResourceLocation;", at = @At("RETURN"), cancellable = true)
    private void getVariantTextureLocation(Entity entity, CallbackInfoReturnable<ResourceLocation> cir) {
        if (entity.getType() == EntityType.MOOSHROOM) {
            MooshroomVariant variant = ((MooshroomDuck) entity).nml$getMooshroomVariant().value();
            MushroomCow mooshroom = (MushroomCow) entity;
            ResourceLocation texture = mooshroom.isBaby() ? variant.babyTexture() : variant.texture();
            cir.setReturnValue(texture.withPath((path) -> "textures/" + path + ".png"));
        }
    }
}
