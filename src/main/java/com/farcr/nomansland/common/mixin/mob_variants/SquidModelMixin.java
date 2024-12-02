package com.farcr.nomansland.common.mixin.mob_variants;

import com.farcr.nomansland.client.model.NMLSquidModel;
import com.farcr.nomansland.common.mixin.HierarchicalModelMixin;
import net.minecraft.client.model.SquidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SquidModel.class)
public class SquidModelMixin<T extends Entity> extends HierarchicalModelMixin {
    @Shadow @Final private ModelPart root;

    @Inject(method = "createBodyLayer", at = @At("RETURN"), cancellable = true)
    private static void createBodyLayer(CallbackInfoReturnable<LayerDefinition> cir) {
        cir.setReturnValue(NMLSquidModel.createBodyLayer());
    }
}
