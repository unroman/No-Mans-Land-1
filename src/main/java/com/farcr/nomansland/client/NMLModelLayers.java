package com.farcr.nomansland.client;

import com.farcr.nomansland.NoMansLand;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class NMLModelLayers {

    public static final ModelLayerLocation BURIED_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "buried"), "main");

    public static final ModelLayerLocation MOOSE_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "moose/maple"), "main");

    public static final ModelLayerLocation BASS_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "bass"), "main");

    public static final ModelLayerLocation DEER_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "deer"), "main");

    public static final ModelLayerLocation GOOSE_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "goose"), "main");
}
