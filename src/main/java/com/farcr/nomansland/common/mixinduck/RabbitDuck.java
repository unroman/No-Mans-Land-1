package com.farcr.nomansland.common.mixinduck;

import com.farcr.nomansland.common.entity.mob_variant.RabbitVariant;
import net.minecraft.core.Holder;

public interface RabbitDuck {

    Holder<RabbitVariant> nml$getRabbitVariant();

    void nml$setRabbitVariant(Holder<RabbitVariant> rabbitVariantHolder);
}
