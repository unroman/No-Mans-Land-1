package com.farcr.nomansland.integration;

import net.neoforged.fml.ModList;

public enum Mods {
    FARMERSDELIGHT,
    BLOCKBOX,
    CREATE;

    private final String id;

    Mods() {
        id = name().toLowerCase();
    }

    public boolean isLoaded() {
        return ModList.get().isLoaded(id);
    }
}
