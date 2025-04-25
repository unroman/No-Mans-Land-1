package com.farcr.nomansland.client.renderer;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.entity.bombs.FirebombEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.resources.model.ModelResourceLocation;

public class FirebombRenderer extends ThrowableBombRenderer<FirebombEntity> {

    private static final ModelResourceLocation MODEL = ModelResourceLocation.standalone(NoMansLand.location("entity/firebomb"));

    public FirebombRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ModelResourceLocation getModelLocation(FirebombEntity entity) {
        return MODEL;
    }
}