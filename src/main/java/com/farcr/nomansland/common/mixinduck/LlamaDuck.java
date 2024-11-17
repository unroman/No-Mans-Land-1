package com.farcr.nomansland.common.mixinduck;

import com.farcr.nomansland.common.entity.mob_variant.LlamaVariant;
import net.minecraft.core.Holder;

public interface LlamaDuck {

    Holder<LlamaVariant> noMansLand$getLlamaVariant();

    void noMansLand$setLlamaVariant(Holder<LlamaVariant> llamaVariantHolder);
}
