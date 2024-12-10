package com.farcr.nomansland.client.ambience.fogmodifiers;

import com.farcr.nomansland.client.ambience.FogModifierHandler;
import com.farcr.nomansland.common.registry.NMLBiomes;
import com.farcr.nomansland.common.registry.NMLTags;
import net.neoforged.neoforge.common.NeoForgeMod;

public class FoggyBiomeFogModifier extends FogModifier {
    @Override
    public float getFogStartAddend() {
        return -0.3F;
    }

    @Override
    public boolean active(FogModifierHandler.FogContext context) {
        return context.biome().is(NMLTags.HAS_DENSE_FOG);
    }
}
