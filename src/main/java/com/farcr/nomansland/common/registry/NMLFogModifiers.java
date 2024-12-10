package com.farcr.nomansland.common.registry;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.client.ambience.fogmodifiers.DeepDarkFogModifier;
import com.farcr.nomansland.client.ambience.fogmodifiers.FogLooksGoodNowModifier;
import com.farcr.nomansland.client.ambience.fogmodifiers.FogModifier;
import com.farcr.nomansland.client.ambience.fogmodifiers.FoggyBiomeFogModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NMLFogModifiers {
    public static final DeferredRegister<FogModifier> FOG_MODIFIERS = DeferredRegister.create(NMLRegistries.FOG_MODIFIERS, NoMansLand.MODID);

    public static final DeferredHolder<FogModifier, FogLooksGoodNowModifier> FOG_LOOKS_GOOD_NOW_MODIFIER = FOG_MODIFIERS.register("flgn_modifier", FogLooksGoodNowModifier::new);
    public static final DeferredHolder<FogModifier, FoggyBiomeFogModifier> FOGGY_BIOME_MODIFIER = FOG_MODIFIERS.register("foggy_biome_modifier", FoggyBiomeFogModifier::new);
    public static final DeferredHolder<FogModifier, DeepDarkFogModifier> DEEP_DARK_MODIFIER = FOG_MODIFIERS.register("deep_dark_modifier", DeepDarkFogModifier::new);
}
