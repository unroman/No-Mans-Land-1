package com.farcr.nomansland.common.mixinduck;

import com.farcr.nomansland.common.entity.mob_variant.MooshroomVariant;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.state.BlockState;

public interface MooshroomDuck {

    Holder<MooshroomVariant> nml$getMooshroomVariant();

    BlockState nml$getMushroomBlock(Holder<MooshroomVariant> mooshroomVariantHolder);

    void nml$setMooshroomVariant(Holder<MooshroomVariant> mooshroomVariantHolder);
}
