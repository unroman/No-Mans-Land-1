package com.farcr.nomansland.client.renderer;

import com.farcr.nomansland.client.NMLModelLayers;
import com.farcr.nomansland.client.model.BassModel;
import com.farcr.nomansland.common.entity.BillhookBass;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BassRenderer extends MobRenderer<BillhookBass, BassModel<BillhookBass>> {
    public BassRenderer(EntityRendererProvider.Context context) {
        super(context, new BassModel<>(context.bakeLayer(NMLModelLayers.BASS_LAYER)), 0.4F);
    }

    @Override
    public ResourceLocation getTextureLocation(BillhookBass billhookBass) {
        return billhookBass.getVariant().value().texture().withPath((path) -> "textures/" + path + ".png");
    }
}
