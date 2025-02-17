package com.farcr.nomansland.common.mixinduck;

import com.farcr.nomansland.common.entity.mob_variant.FoxVariant;
import com.farcr.nomansland.common.entity.mob_variant.HuskVariant;
import net.minecraft.core.Holder;

public interface HuskDuck {

    Holder<HuskVariant> noMansLand$getHuskVariant();

    void noMansLand$setHuskVariant(Holder<HuskVariant> huskVariantHolder);
}
