package com.farcr.nomansland.client.renderer;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.entity.bombs.ExplosiveEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.resources.model.ModelResourceLocation;

public class ExplosiveRenderer extends ThrowableBombRenderer<ExplosiveEntity> {

    private static final ModelResourceLocation MODEL = ModelResourceLocation.standalone(NoMansLand.location("entity/explosive"));

    public ExplosiveRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ModelResourceLocation getModelLocation(ExplosiveEntity entity) {
        return MODEL;
    }
}

