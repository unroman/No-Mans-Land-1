package com.farcr.nomansland.common.mixin.mob_variants;

import com.farcr.nomansland.client.model.PigOverlayLayer;
import com.farcr.nomansland.common.entity.mob_variant.PigVariant;
import com.farcr.nomansland.common.mixin.LivingEntityRendererMixin;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.PigRenderer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.animal.Pig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PigRenderer.class)
public abstract class PigRendererMixin extends LivingEntityRendererMixin<Pig, PigModel<Pig>> {

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(EntityRendererProvider.Context p_174340_, CallbackInfo ci) {
        addLayer(new PigOverlayLayer((PigRenderer) (Object) this));
    }

    @Inject(method = "getTextureLocation(Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/resources/ResourceLocation;", at = @At("RETURN"), cancellable = true)
    private void getTextureLocationFromVariant(Entity entity, CallbackInfoReturnable<ResourceLocation> cir) {
        PigVariant variant = ((VariantHolder<Holder<PigVariant>>)entity).getVariant().value();
        Pig pig = (Pig) entity;
        ResourceLocation texture = pig.isBaby() ? variant.babyTexture() : variant.texture();
        cir.setReturnValue(texture.withPath((path) -> "textures/" + path + ".png"));
    }
}
