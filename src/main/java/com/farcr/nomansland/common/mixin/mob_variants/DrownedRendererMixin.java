package com.farcr.nomansland.common.mixin.mob_variants;

import com.farcr.nomansland.common.mixinduck.DrownedDuck;
import net.minecraft.client.renderer.entity.DrownedRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DrownedRenderer.class)
public class DrownedRendererMixin {
    @Inject(method = "getTextureLocation", at = @At("RETURN"), cancellable = true)
    private void getVariantTextureLocation(Zombie entity, CallbackInfoReturnable<ResourceLocation> cir) {
        if (entity.getType() == EntityType.DROWNED)
            cir.setReturnValue(((DrownedDuck)entity).nml$getDrownedVariant().value().texture().withPath((path) -> "textures/" + path + ".png"));
    }
}
