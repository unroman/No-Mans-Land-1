package com.farcr.nomansland.common.mixinduck;

import com.farcr.nomansland.common.entity.mob_variant.MooshroomVariant;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.state.BlockState;

public interface MooshroomDuck {

    Holder<MooshroomVariant> noMansLand$getMooshroomVariant();

    BlockState noMansLand$getMushroomBlock(Holder<MooshroomVariant> mooshroomVariantHolder);

    void noMansLand$setMooshroomVariant(Holder<MooshroomVariant> mooshroomVariantHolder);
}
