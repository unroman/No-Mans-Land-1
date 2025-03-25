package com.farcr.nomansland.datagen;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.datagen.tags.NMLBlockTagsProvider;
import com.farcr.nomansland.datagen.tags.NMLItemTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = NoMansLand.MODID)
public class DataGenEvents {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        boolean server = event.includeServer();
        boolean client = event.includeClient();

        // Loot tables
        generator.addProvider(
                server,
                new LootTableProvider(
                        generator.getPackOutput(),
                        Set.of(),
                        List.of(new LootTableProvider.SubProviderEntry(NMLBlockLootSubProvider::new, LootContextParamSets.BLOCK)),
                        lookupProvider
                )
        );

        // Tags
        generator.addProvider(server, new NMLBlockTagsProvider(packOutput, lookupProvider, NoMansLand.MODID, event.getExistingFileHelper()));
        //generator.addProvider(server, new NMLItemTagsProvider(packOutput, lookupProvider));

        // Lang
        generator.addProvider(
                client,
                new NMLLanguageProvider(packOutput)
        );
    }
}
