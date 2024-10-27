package com.farcr.nomansland.common.mixinduck;

import com.farcr.nomansland.common.entity.mob_variant.CowVariant;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.state.BlockState;

public interface MooshroomDuck {

    Holder<CowVariant> noMansLand$getVariant();

    BlockState noMansLand$getMushroomBlock(Holder<CowVariant> mooshroomVariantHolder);

    void noMansLand$setVariant(Holder<CowVariant> mooshroomVariantHolder);

    boolean noMansLand$isMooshroomVariant(Holder<CowVariant> cowVariantHolder);
}
