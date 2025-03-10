package com.farcr.nomansland.common.mixin.mob_variants;

import com.farcr.nomansland.common.entity.mob_variant.LlamaVariant;
import com.farcr.nomansland.common.mixinduck.LlamaDuck;
import net.minecraft.client.renderer.entity.LlamaRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.horse.Llama;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LlamaRenderer.class)
public abstract class LlamaRendererMixin {
    @Inject(method = "getTextureLocation(Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/resources/ResourceLocation;", at = @At("RETURN"), cancellable = true)
    private void getTextureLocationFromVariant(Entity entity, CallbackInfoReturnable<ResourceLocation> cir) {
        LlamaVariant variant = ((LlamaDuck)entity).nml$getLlamaVariant().value();
        Llama llama = (Llama) entity;
        ResourceLocation texture = llama.isBaby() ? variant.babyTexture() : variant.texture();
        cir.setReturnValue(texture.withPath((path) -> "textures/" + path + ".png"));
    }
}
