package com.farcr.nomansland.common.mixin.mob_variants;

import com.farcr.nomansland.client.model.vanilla.NMLSquidModel;
import net.minecraft.client.model.SquidModel;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SquidModel.class)
public class SquidModelMixin<T extends Entity> {
    @Inject(method = "createBodyLayer", at = @At("RETURN"), cancellable = true)
    private static void createBodyLayer(CallbackInfoReturnable<LayerDefinition> cir) {
        cir.setReturnValue(NMLSquidModel.createBodyLayer());
    }
}
