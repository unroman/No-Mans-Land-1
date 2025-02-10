package com.farcr.nomansland.common.entity.mob_variant.group;

import com.farcr.nomansland.common.entity.mob_variant.MobVariant;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.AgeableMob;

public class VariantGroupData extends AgeableMob.AgeableMobGroupData {

    public final Holder<? extends MobVariant> variant;

    public VariantGroupData(Holder<? extends MobVariant> variant) {
        super(true);
        this.variant = variant;
    }
}
