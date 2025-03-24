package com.farcr.nomansland.common.event;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.datagen.loot.NMLBlockLootSubProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = NoMansLand.MODID)
public class DataGenEvents {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        // Loot tables
        event.getGenerator().addProvider(
                event.includeServer(),
                new LootTableProvider(
                        event.getGenerator().getPackOutput(),
                        Set.of(),
                        List.of(
                                new LootTableProvider.SubProviderEntry(NMLBlockLootSubProvider::new, LootContextParamSets.BLOCK)
                        ),
                        event.getLookupProvider()
                )
        );
    }
}
