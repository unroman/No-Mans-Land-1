package com.farcr.nomansland.common.mixinduck;

import com.farcr.nomansland.common.entity.mob_variant.PigOverlayVariant;
import net.minecraft.core.Holder;

public interface PigDuck {

    Holder<PigOverlayVariant> nml$getPigOverlayVariant();

    void nml$setPigOverlayVariant(Holder<PigOverlayVariant> pigOverlayVariantHolder);
}
