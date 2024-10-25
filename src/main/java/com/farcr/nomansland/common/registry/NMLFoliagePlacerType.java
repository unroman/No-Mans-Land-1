package com.farcr.nomansland.common.registry;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.world.feature.foliageplacer.CypressFoliagePlacer;
import com.farcr.nomansland.common.world.feature.foliageplacer.PineFoliagePlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NMLFoliagePlacerType {
    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACER_TYPES =
            DeferredRegister.create(Registries.FOLIAGE_PLACER_TYPE, NoMansLand.MODID);

    public static final DeferredHolder<FoliagePlacerType<?>, FoliagePlacerType<PineFoliagePlacer>> PINE_FOLIAGE_PLACER = FOLIAGE_PLACER_TYPES.register("pine_foliage_placer", () -> new FoliagePlacerType<>(PineFoliagePlacer.CODEC));
    public static final DeferredHolder<FoliagePlacerType<?>, FoliagePlacerType<CypressFoliagePlacer>> CYPRESS_FOLIAGE_PLACER = FOLIAGE_PLACER_TYPES.register("cypress_foliage_placer", () -> new FoliagePlacerType<>(CypressFoliagePlacer.CODEC));
}
