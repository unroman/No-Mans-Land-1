package com.farcr.nomansland.common.registry.worldgen;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.world.feature.foliageplacer.CypressFoliagePlacer;
import com.farcr.nomansland.common.world.feature.foliageplacer.PineFoliagePlacer;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NMLFoliagePlacerTypes {
    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACER_TYPES =
            DeferredRegister.create(Registries.FOLIAGE_PLACER_TYPE, NoMansLand.MODID);

    public static final DeferredHolder<FoliagePlacerType<?>, FoliagePlacerType<PineFoliagePlacer>> PINE_FOLIAGE_PLACER = register("pine_foliage_placer", PineFoliagePlacer.CODEC);
    public static final DeferredHolder<FoliagePlacerType<?>, FoliagePlacerType<CypressFoliagePlacer>> CYPRESS_FOLIAGE_PLACER = register("cypress_foliage_placer", CypressFoliagePlacer.CODEC);

    private static <P extends FoliagePlacer> DeferredHolder<FoliagePlacerType<?>, FoliagePlacerType<P>> register (String name, MapCodec<P> codec) {
        return FOLIAGE_PLACER_TYPES.register(name, () -> new FoliagePlacerType<>(codec));
    }
}
