package com.farcr.nomansland.client.renderer;

import com.farcr.nomansland.client.NMLModelLayers;
import com.farcr.nomansland.client.model.deer.DeerAntlersLayer;
import com.farcr.nomansland.client.model.deer.DeerModel;
import com.farcr.nomansland.client.model.deer.DeerPatternLayer;
import com.farcr.nomansland.common.entity.deer.Deer;
import com.farcr.nomansland.common.entity.deer.DeerVariant;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.tazer.mixed_litter.MLRegistries;
import dev.tazer.mixed_litter.variants.MobVariant;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import static dev.tazer.mixed_litter.VariantUtil.getVariants;

@OnlyIn(Dist.CLIENT)
public class DeerRenderer extends MobRenderer<Deer, DeerModel<Deer>> {
    public DeerRenderer(EntityRendererProvider.Context context) {
        super(context, new DeerModel<>(context.bakeLayer(NMLModelLayers.DEER_LAYER)), 0.4F);
        addLayer(new DeerAntlersLayer(this));
        addLayer(new DeerPatternLayer(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Deer deer) {
        DeerVariant variant = null;

        for (Holder<MobVariant> animalVariantHolder : getVariants(deer)) {
            if (animalVariantHolder.value() instanceof DeerVariant deerVariant) {
                variant = deerVariant;
                break;
            }
        }

        if (variant == null) variant = (DeerVariant) deer.registryAccess().registryOrThrow(MLRegistries.ANIMAL_VARIANT_KEY).holders()
                .filter(mobVariantReference -> mobVariantReference.value() instanceof DeerVariant).findAny().orElseThrow().value();
        ResourceLocation texture = deer.isBaby() ? variant.babyTexture : variant.texture;
        return texture.withPath(path -> "textures/" + path + ".png");
    }

    @Override
    protected void scale(Deer deer, PoseStack poseStack, float partialTickTime) {
        poseStack.scale(1.1F, 1.1F, 1.1F);
        super.scale(deer, poseStack, partialTickTime);
    }
}
