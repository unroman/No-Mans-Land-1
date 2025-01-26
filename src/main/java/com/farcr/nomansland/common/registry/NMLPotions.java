package com.farcr.nomansland.common.registry;

import com.farcr.nomansland.NoMansLand;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NMLPotions {
    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(Registries.POTION, NoMansLand.MODID);

    public static final DeferredHolder<Potion, Potion> FLAMMABLE = POTIONS.register("flammable", () -> new Potion(new MobEffectInstance(NMLEffects.FLAMMABLE, 100, 0)));
}
