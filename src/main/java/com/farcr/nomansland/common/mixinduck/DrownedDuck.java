package com.farcr.nomansland.common.mixinduck;

import com.farcr.nomansland.common.entity.mob_variant.DrownedVariant;
import net.minecraft.core.Holder;

public interface DrownedDuck {

    Holder<DrownedVariant> nml$getDrownedVariant();

    void nml$setDrownedVariant(Holder<DrownedVariant> drownedVariantHolder);
}
