package com.farcr.nomansland.common.registry;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.fluid.ResinOilFluid;
import com.farcr.nomansland.common.fluid.VirtualFluid;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class NMLFluids {
    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, NoMansLand.MODID);

    public static final Supplier<FluidType> RESIN_OIL_TYPE = FLUID_TYPES.register("resin_oil", () -> new FluidType(FluidType.Properties.create().viscosity(2000).density(1400)));

    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(BuiltInRegistries.FLUID, NoMansLand.MODID);
    public static final Supplier<VirtualFluid> RESIN_OIL = FLUIDS.register("resin_oil",
            () -> new ResinOilFluid(true));

    public static final Supplier<VirtualFluid> FLOWING_RESIN_OIL = FLUIDS.register("flowing_resin_oil",
            () -> new ResinOilFluid(false));
}
