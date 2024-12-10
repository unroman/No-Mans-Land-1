package com.farcr.nomansland.client.ambience.fogmodifiers;

import com.farcr.nomansland.client.ambience.FogModifierHandler;
import com.farcr.nomansland.common.registry.NMLTags;

public class FoggyBiomeFogModifier extends FogModifier {
    @Override
    public float getFogStartAddend() {
        return -0.2F;
    }

    @Override
    public boolean active(FogModifierHandler.FogContext context) {
        return context.biome().is(NMLTags.HAS_DENSE_FOG);
    }
}
