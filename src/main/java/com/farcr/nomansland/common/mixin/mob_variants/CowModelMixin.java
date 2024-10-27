package com.farcr.nomansland.common.mixin.mob_variants;

import com.farcr.nomansland.client.model.NMLCowModel;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CowModel.class)
public class CowModelMixin {
    @Inject(method = "createBodyLayer", at = @At("RETURN"), cancellable = true)
    private static void createBodyLayer(CallbackInfoReturnable<LayerDefinition> cir) {
        cir.setReturnValue(NMLCowModel.createBodyLayer());
    }
}
