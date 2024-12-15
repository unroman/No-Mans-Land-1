package com.farcr.nomansland.common.mixin.mob_variants;

import com.farcr.nomansland.common.mixinduck.GlowSquidDuck;
import net.minecraft.client.renderer.entity.GlowSquidRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.GlowSquid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GlowSquidRenderer.class)
public class GlowSquidRendererMixin {

    @Inject(method = "getTextureLocation(Lnet/minecraft/world/entity/GlowSquid;)Lnet/minecraft/resources/ResourceLocation;", at = @At("RETURN"), cancellable = true)
    private void getTextureLocation(GlowSquid glowSquid, CallbackInfoReturnable<ResourceLocation> cir) {
        cir.setReturnValue(((GlowSquidDuck) glowSquid).noMansLand$getGlowSquidVariant().value().texture().withPath((path) -> "textures/" + path + ".png"));
    }
}
