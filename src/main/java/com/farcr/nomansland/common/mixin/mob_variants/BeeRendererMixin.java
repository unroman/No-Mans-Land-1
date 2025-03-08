package com.farcr.nomansland.common.mixin.mob_variants;

import com.farcr.nomansland.common.entity.mob_variant.BeeVariant;
import net.minecraft.client.renderer.entity.BeeRenderer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.animal.Bee;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BeeRenderer.class)
public abstract class BeeRendererMixin {
    @Inject(method = "getTextureLocation(Lnet/minecraft/world/entity/animal/Bee;)Lnet/minecraft/resources/ResourceLocation;", at = @At("RETURN"), cancellable = true)
    private void getTextureLocationFromVariant(Bee entity, CallbackInfoReturnable<ResourceLocation> cir) {
        BeeVariant variant = ((VariantHolder<Holder<BeeVariant>>)entity).getVariant().value();
        ResourceLocation texture;

        if (entity.isAngry())
            texture = entity.hasNectar() ? variant.angryNectarTexture() : variant.angryTexture();
        else
            texture = entity.hasNectar() ? variant.nectarTexture() : variant.texture();

        cir.setReturnValue(texture.withPath((path) -> "textures/" + path + ".png"));
    }
}
