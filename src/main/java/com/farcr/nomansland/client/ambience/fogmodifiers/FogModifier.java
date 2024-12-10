package com.farcr.nomansland.client.ambience.fogmodifiers;

import com.farcr.nomansland.client.ambience.FogModifierHandler;

public abstract class FogModifier {
    public float getFogStartMultiplier() { return 1; }

    public float getFogEndMultiplier() { return 1; }

    // EXPRESSED IN MULTIPLES OF CURRENT RENDER DISTANCE
    public float getFogStartAddend() { return 0; }

    // EXPRESSED IN MULTIPLES OF CURRENT RENDER DISTANCE
    public float getFogEndAddend() { return 0; }

    public float getFogRedMultiplier() { return 1; }

    public float getFogGreenMultiplier() { return 1; }

    public float getFogBlueMultiplier() { return 1; }

    public float getInterpolationSpeed() { return 0.05F; }

    abstract boolean active(FogModifierHandler.FogContext context);
}
