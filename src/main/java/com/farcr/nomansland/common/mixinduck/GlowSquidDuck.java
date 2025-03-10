package com.farcr.nomansland.common.mixinduck;

import com.farcr.nomansland.common.entity.mob_variant.GlowSquidVariant;
import net.minecraft.core.Holder;

public interface GlowSquidDuck {

    Holder<GlowSquidVariant> nml$getGlowSquidVariant();

    void nml$setGlowSquidVariant(Holder<GlowSquidVariant> glowSquidVariantHolder);
}
