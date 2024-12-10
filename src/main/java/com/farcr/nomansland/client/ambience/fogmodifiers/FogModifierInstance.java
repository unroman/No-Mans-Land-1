package com.farcr.nomansland.client.ambience.fogmodifiers;

import com.farcr.nomansland.client.ambience.FogModifierHandler;
import net.minecraft.util.Mth;
import org.joml.Vector3f;

public class FogModifierInstance {
    public final FogModifier modifier;
    private boolean active;
    private float interpolationFactor;

    public FogModifierInstance(FogModifier modifier) {
        this.modifier = modifier;
    }

    public void tick() {
        this.interpolationFactor = Mth.lerp(this.modifier.getInterpolationSpeed(), this.interpolationFactor, this.active ? 1 : 0);
    }

    public void update(FogModifierHandler.FogContext context) {
        this.active = this.modifier.active(context);
    }

    public float getFogStartMultiplier() {
        return Mth.lerp(this.interpolationFactor, 1, this.modifier.getFogStartMultiplier());
    }

    public float getFogEndMultiplier() {
        return Mth.lerp(this.interpolationFactor, 1, this.modifier.getFogEndMultiplier());
    }

    public float getFogStartAddend() {
        return Mth.lerp(this.interpolationFactor, 0, this.modifier.getFogStartAddend());
    }

    public float getFogEndAddend() {
        return Mth.lerp(this.interpolationFactor, 0, this.modifier.getFogEndAddend());
    }

    public void multiplyFogColors(Vector3f fogColors) {
        fogColors.mul(Mth.lerp(this.interpolationFactor, 1, this.modifier.getFogRedMultiplier()),
                      Mth.lerp(this.interpolationFactor, 1, this.modifier.getFogGreenMultiplier()),
                      Mth.lerp(this.interpolationFactor, 1, this.modifier.getFogBlueMultiplier()));
    }
}
