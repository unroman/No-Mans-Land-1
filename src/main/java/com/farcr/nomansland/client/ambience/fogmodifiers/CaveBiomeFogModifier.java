package com.farcr.nomansland.client.ambience.fogmodifiers;

import com.farcr.nomansland.NMLConfig;
import com.farcr.nomansland.client.ambience.FogModifierHandler;
import net.neoforged.neoforge.common.Tags;

public class CaveBiomeFogModifier extends FogModifier {
    @Override
    public float getFogEndAddend() {
        return -0.4F;
    }

    @Override
    public float getFogStartAddend() {
        return -0.05F;
    }

    @Override
    public boolean active(FogModifierHandler.FogContext context) {
        return context.biome().is(Tags.Biomes.IS_UNDERGROUND) && NMLConfig.FOG_MODIFIERS.get();
    }
}
