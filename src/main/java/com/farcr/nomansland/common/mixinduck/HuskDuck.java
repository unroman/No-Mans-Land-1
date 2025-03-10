package com.farcr.nomansland.common.mixinduck;

import com.farcr.nomansland.common.entity.mob_variant.HuskVariant;
import net.minecraft.core.Holder;

public interface HuskDuck {

    Holder<HuskVariant> nml$getHuskVariant();

    void nml$setHuskVariant(Holder<HuskVariant> huskVariantHolder);
}
