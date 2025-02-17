package com.farcr.nomansland.common.mixinduck;

import com.farcr.nomansland.common.entity.mob_variant.DrownedVariant;
import com.farcr.nomansland.common.entity.mob_variant.HuskVariant;
import net.minecraft.core.Holder;

public interface DrownedDuck {

    Holder<DrownedVariant> noMansLand$getDrownedVariant();

    void noMansLand$setDrownedVariant(Holder<DrownedVariant> drownedVariantHolder);
}
