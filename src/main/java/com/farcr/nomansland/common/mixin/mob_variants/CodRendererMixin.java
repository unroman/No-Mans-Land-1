package com.farcr.nomansland.common.mixin.mob_variants;

import com.farcr.nomansland.common.entity.mob_variant.CodVariant;
import net.minecraft.client.renderer.entity.CodRenderer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.VariantHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CodRenderer.class)
public class CodRendererMixin {
    @Inject(method = "getTextureLocation(Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/resources/ResourceLocation;", at = @At("RETURN"), cancellable = true)
    private void getVariantTextureLocation(Entity entity, CallbackInfoReturnable<ResourceLocation> cir) {
        if (entity.getType() == EntityType.COD)
            cir.setReturnValue(((VariantHolder<Holder<CodVariant>>)entity).getVariant().value().texture().withPath((path) -> "textures/" + path + ".png"));
    }
}
