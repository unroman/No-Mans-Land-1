package com.farcr.nomansland.client.renderer;

import com.farcr.nomansland.client.NMLModelLayers;
import com.farcr.nomansland.client.model.GooseModel;
import com.farcr.nomansland.common.entity.goose.Goose;
import com.farcr.nomansland.common.entity.goose.GooseVariant;
import dev.tazer.mixed_litter.MLRegistries;
import dev.tazer.mixed_litter.variants.MobVariant;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;

import static dev.tazer.mixed_litter.VariantUtil.getVariants;

public class GooseRenderer extends MobRenderer<Goose, GooseModel<Goose>> {
    public GooseRenderer(EntityRendererProvider.Context context) {
        super(context, new GooseModel<>(context.bakeLayer(NMLModelLayers.GOOSE_LAYER)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(Goose goose) {
        GooseVariant variant = null;

        for (Holder<MobVariant> animalVariantHolder : getVariants(goose)) {
            if (animalVariantHolder.value() instanceof GooseVariant gooseVariant) {
                variant = gooseVariant;
                break;
            }
        }

        if (variant == null) variant = (GooseVariant) goose.registryAccess().registryOrThrow(MLRegistries.ANIMAL_VARIANT_KEY).holders()
                .filter(mobVariantReference -> mobVariantReference.value() instanceof GooseVariant).findAny().orElseThrow().value();
        ResourceLocation texture = variant.texture;
        return texture.withPath(path -> "textures/" + path + ".png");
    }
}
