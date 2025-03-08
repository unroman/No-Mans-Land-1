package com.farcr.nomansland.common.mixin.mob_variants;

import com.farcr.nomansland.common.mixinduck.HuskDuck;
import net.minecraft.client.renderer.entity.HuskRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Zombie;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HuskRenderer.class)
public class HuskRendererMixin {
    @Inject(method = "getTextureLocation(Lnet/minecraft/world/entity/monster/Zombie;)Lnet/minecraft/resources/ResourceLocation;", at = @At("RETURN"), cancellable = true)
    private void getTextureLocationFromVariant(Zombie entity, CallbackInfoReturnable<ResourceLocation> cir) {
        cir.setReturnValue(((HuskDuck)entity).noMansLand$getHuskVariant().value().texture().withPath((path) -> "textures/" + path + ".png"));
    }
}
