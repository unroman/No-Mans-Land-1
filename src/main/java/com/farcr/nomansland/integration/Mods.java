package com.farcr.nomansland.integration;

import net.neoforged.fml.ModList;

public enum Mods {
    FARMERSDELIGHT;

    private final String id;

    Mods() {
        id = name().toLowerCase();
    }

    public boolean isLoaded() {
        return ModList.get().isLoaded(id);
    }
}
