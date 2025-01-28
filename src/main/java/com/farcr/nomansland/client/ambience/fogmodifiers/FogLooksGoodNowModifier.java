package com.farcr.nomansland.client.ambience.fogmodifiers;

import com.farcr.nomansland.NMLConfig;
import com.farcr.nomansland.client.ambience.FogModifierHandler;

public class FogLooksGoodNowModifier extends FogModifier {
    @Override
    public float getFogStartMultiplier() {
        return 0.15F;
    }

    @Override
    public boolean active(FogModifierHandler.FogContext context) {
        return NMLConfig.FOG_MODIFIERS.get();
    }
}
