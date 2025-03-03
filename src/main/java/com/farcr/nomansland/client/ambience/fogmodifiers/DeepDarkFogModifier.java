package com.farcr.nomansland.client.ambience.fogmodifiers;

import com.farcr.nomansland.NMLConfig;
import com.farcr.nomansland.client.ambience.FogModifierHandler;
import net.minecraft.world.level.biome.Biomes;

public class DeepDarkFogModifier extends FogModifier {
    @Override
    public float getFogEndAddend() {
        return -0.3F;
    }

    @Override
    public float getFogStartAddend() {
        return -0.1F;
    }

    @Override
    public boolean active(FogModifierHandler.FogContext context) {
        return context.biome().is(Biomes.DEEP_DARK) && NMLConfig.FOG_MODIFIERS.get() && NMLConfig.DEEP_DARK_FOG_MODIFIER.get() && context.darknessFactor() == 0;
    }
}
