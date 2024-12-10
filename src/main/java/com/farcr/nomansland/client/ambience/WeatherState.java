package com.farcr.nomansland.client.ambience;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

// probably make this a registry, at some point.
// or something like that... idk if there are any
// plans for better NML weather.
// would be interesting!
public enum WeatherState {
    CLEAR(false, false, false, false),

    RAIN(true, false, false, true),
    THUNDER(true, true, false, true),

    SNOW(true, false, true, true),
    THUNDER_SNOW(true, true, true, true),

    DESERT_RAIN(true, false, false, false),
    DESERT_THUNDERSTORM(true, true, false, false);

    public final boolean inclement, lightning, snowy, precipitation;

    WeatherState(boolean inclement, boolean lightning, boolean snowy, boolean precipitation) {
        this.inclement = inclement;
        this.lightning = lightning;
        this.snowy = snowy;
        this.precipitation = precipitation;
    }

    public static WeatherState fromWorld(Level level, BlockPos pos) {
        // if the dimension has no sky or isn't "natural," it's always clear.
        if (!level.dimensionType().natural() || !level.dimensionType().hasSkyLight() || level.dimensionType().hasCeiling()) return CLEAR;

        Biome biome = level.getBiome(pos).value();
        if (!level.isRaining()) return CLEAR;

        Biome.Precipitation precipitation = biome.getPrecipitationAt(pos);
        boolean lightning = level.isThundering();
        if (precipitation == Biome.Precipitation.RAIN) return lightning ? THUNDER : RAIN;
        if (precipitation == Biome.Precipitation.SNOW) return lightning ? THUNDER_SNOW : SNOW;
        if (precipitation == Biome.Precipitation.NONE) return lightning ? DESERT_THUNDERSTORM : DESERT_RAIN;
        return CLEAR;
    }
}
