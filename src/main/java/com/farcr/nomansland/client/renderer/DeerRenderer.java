package com.farcr.nomansland.client.renderer;

import com.farcr.nomansland.client.NMLModelLayers;
import com.farcr.nomansland.client.model.deer.DeerAntlersLayer;
import com.farcr.nomansland.client.model.deer.DeerModel;
import com.farcr.nomansland.client.model.deer.DeerPatternLayer;
import com.farcr.nomansland.common.entity.deer.Deer;
import com.farcr.nomansland.common.entity.mob_variant.deer.DeerVariant;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DeerRenderer extends MobRenderer<Deer, DeerModel<Deer>> {
    public DeerRenderer(EntityRendererProvider.Context context) {
        super(context, new DeerModel<>(context.bakeLayer(NMLModelLayers.DEER_LAYER)), 0.4F);
        this.addLayer(new DeerAntlersLayer(this, new DeerModel<>(context.bakeLayer(NMLModelLayers.DEER_ANTLERS_LAYER))));
        this.addLayer(new DeerPatternLayer(this, new DeerModel<>(context.bakeLayer(NMLModelLayers.DEER_PATTERN_LAYER))));
    }

    @Override
    public ResourceLocation getTextureLocation(Deer deer) {
        DeerVariant variant = deer.getVariant().value();
        ResourceLocation texture = deer.isBaby() ? variant.babyTexture() : variant.texture();
        return texture.withPath((path) -> "textures/" + path + ".png");
    }
}
