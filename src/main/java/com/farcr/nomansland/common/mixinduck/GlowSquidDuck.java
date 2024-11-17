package com.farcr.nomansland.common.mixinduck;

import com.farcr.nomansland.common.entity.mob_variant.GlowSquidVariant;
import net.minecraft.core.Holder;

public interface GlowSquidDuck {

    Holder<GlowSquidVariant> noMansLand$getGlowSquidVariant();

    void noMansLand$setGlowSquidVariant(Holder<GlowSquidVariant> glowSquidVariantHolder);
}
