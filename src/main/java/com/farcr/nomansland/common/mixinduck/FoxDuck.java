package com.farcr.nomansland.common.mixinduck;

import com.farcr.nomansland.common.entity.mob_variant.FoxVariant;
import net.minecraft.core.Holder;

public interface FoxDuck {

    Holder<FoxVariant> noMansLand$getFoxVariant();

    void noMansLand$setFoxVariant(Holder<FoxVariant> foxVariantHolder);
}
