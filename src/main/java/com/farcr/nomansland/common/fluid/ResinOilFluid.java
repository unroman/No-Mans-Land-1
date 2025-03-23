package com.farcr.nomansland.common.fluid;

import com.farcr.nomansland.common.registry.NMLFluids;

public class ResinOilFluid extends VirtualFluid {
    public ResinOilFluid(boolean source) {
        super(new Properties(NMLFluids.RESIN_OIL_TYPE, NMLFluids.RESIN_OIL, NMLFluids.FLOWING_RESIN_OIL), source);
    }
}
