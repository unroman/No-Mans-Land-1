package com.farcr.nomansland.common.mixinduck;

import com.farcr.nomansland.common.entity.variant.FoxVariant;
import net.minecraft.core.Holder;

public interface FoxDuck {

    Holder<FoxVariant> noMansLand$getVariant();

    void noMansLand$setVariant(Holder<FoxVariant> foxVariantHolder);
}
