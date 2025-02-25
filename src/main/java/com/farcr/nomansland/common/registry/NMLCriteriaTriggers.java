package com.farcr.nomansland.common.registry;

import com.farcr.nomansland.NoMansLand;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.critereon.DistanceTrigger;
import net.minecraft.advancements.critereon.KilledTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NMLCriteriaTriggers {
    public static final DeferredRegister<CriterionTrigger<?>> TRIGGERS =
            DeferredRegister.create(BuiltInRegistries.TRIGGER_TYPES, NoMansLand.MODID);

    public static final DeferredHolder<CriterionTrigger<?>, KilledTrigger> KILL_MOB_NEAR_MONSTER_ANCHOR = TRIGGERS.register("kill_mob_near_monster_anchor", KilledTrigger::new);
    public static final DeferredHolder<CriterionTrigger<?>, DistanceTrigger> CLIMB_UP_HEIGHT = TRIGGERS.register("climb_up_height", DistanceTrigger::new);
}
