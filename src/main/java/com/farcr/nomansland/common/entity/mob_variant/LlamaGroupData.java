package com.farcr.nomansland.common.entity.mob_variant;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.AgeableMob;

public class LlamaGroupData extends AgeableMob.AgeableMobGroupData {

    public final Holder<LlamaVariant> variant;

    public LlamaGroupData(Holder<LlamaVariant> variant) {
        super(true);
        this.variant = variant;
    }
}
