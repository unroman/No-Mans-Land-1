package com.farcr.nomansland.common.mixinduck;

import com.farcr.nomansland.common.entity.mob_variant.LlamaVariant;
import net.minecraft.core.Holder;

public interface LlamaDuck {

    Holder<LlamaVariant> nml$getLlamaVariant();

    void nml$setLlamaVariant(Holder<LlamaVariant> llamaVariantHolder);
}
