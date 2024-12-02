package com.farcr.nomansland.common.entity.deer;

import com.farcr.nomansland.common.entity.mob_variant.deer.DeerAntlersVariant;
import com.farcr.nomansland.common.entity.mob_variant.deer.DeerPatternVariant;
import com.farcr.nomansland.common.entity.mob_variant.deer.DeerVariant;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.VariantHolder;

public interface DeerVariantHolder extends VariantHolder<Holder<DeerVariant>> {

    Holder<DeerAntlersVariant> getAntlersVariant();

    void setAntlersVariant(Holder<DeerAntlersVariant> antlersVariantHolder);

    Holder<DeerPatternVariant> getPatternVariant();

    void setPatternVariant(Holder<DeerPatternVariant> patternVariantHolder);
}
