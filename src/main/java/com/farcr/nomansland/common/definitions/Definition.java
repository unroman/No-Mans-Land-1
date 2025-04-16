package com.farcr.nomansland.common.definitions;

import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DeferredHolder;

public class Definition<R, T extends R> extends DeferredHolder<R, T> {

    private final boolean customLang;

    protected Definition(ResourceKey<R> key, boolean customLang) {
        super(key);
        this.customLang = customLang;
    }

    protected Definition(ResourceKey<R> key) {
        this(key, false);
    }


    public String langKey() {
        return getRegisteredName().replaceAll(":", ".");
    }

    public String langName() {
        String processed = getRegisteredName().split(":")[1].replace("_", " ");

        String[] words = processed.split(" ");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1))
                        .append(" ");
            }
        }

        return result.toString().trim();
    }

    public boolean hasCustomLang() {
        return customLang;
    }
}
