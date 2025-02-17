package com.farcr.nomansland.common.mixin.mob_variants;

import com.farcr.nomansland.common.entity.mob_variant.DolphinVariant;
import com.farcr.nomansland.common.mixinduck.DrownedDuck;
import net.minecraft.client.renderer.entity.DolphinRenderer;
import net.minecraft.client.renderer.entity.DrownedRenderer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.monster.Zombie;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DrownedRenderer.class)
public class DrownedRendererMixin {
    @Inject(method = "getTextureLocation", at = @At("RETURN"), cancellable = true)
    private void getTextureLocation(Zombie entity, CallbackInfoReturnable<ResourceLocation> cir) {
        cir.setReturnValue(((DrownedDuck)entity).noMansLand$getDrownedVariant().value().texture().withPath((path) -> "textures/" + path + ".png"));
    }
}
