package com.farcr.nomansland.data.event;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.entity.mob_variant.*;
import com.farcr.nomansland.common.registry.NMLMobVariants;
import com.farcr.nomansland.data.Advancements;
import com.farcr.nomansland.data.assets.Lang;
import com.farcr.nomansland.data.tags.BlockTags;
import com.farcr.nomansland.data.tags.DamageTypeTags;
import com.farcr.nomansland.data.tags.EntityTypeTags;
import com.farcr.nomansland.data.tags.ItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
@EventBusSubscriber(modid = NoMansLand.MODID, bus = EventBusSubscriber.Bus.MOD)
public class DataEvents {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper helper = event.getExistingFileHelper();
        RegistrySetBuilder registrySetBuilder = new RegistrySetBuilder();

        BlockTags blockTags = new BlockTags(output, lookupProvider, helper);
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new ItemTags(output, lookupProvider, blockTags.contentsGetter(), helper));
        generator.addProvider(event.includeServer(), new EntityTypeTags(output, lookupProvider, helper));
        generator.addProvider(event.includeServer(), new DamageTypeTags(output, lookupProvider, helper));

        DatapackBuiltinEntriesProvider datapackProvider = new DatapackBuiltinEntriesProvider(output, lookupProvider, registrySetBuilder, Set.of(NoMansLand.MODID));
        CompletableFuture<HolderLookup.Provider> builtinLookupProvider = datapackProvider.getRegistryProvider();
        generator.addProvider(event.includeServer(), datapackProvider);

        generator.addProvider(event.includeServer(), new Advancements(output, lookupProvider, helper));

//        generator.addProvider(event.includeClient(), new ItemModels(output, helper));
        generator.addProvider(event.includeClient(), new Lang(output));
    }

    @SubscribeEvent
    public static void registerDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(NMLMobVariants.COD_VARIANT_KEY, CodVariant.DIRECT_CODEC, CodVariant.DIRECT_CODEC);
        event.dataPackRegistry(NMLMobVariants.COW_VARIANT_KEY, CowVariant.DIRECT_CODEC, CowVariant.DIRECT_CODEC);
        event.dataPackRegistry(NMLMobVariants.DOLPHIN_VARIANT_KEY, DolphinVariant.DIRECT_CODEC, DolphinVariant.DIRECT_CODEC);
        event.dataPackRegistry(NMLMobVariants.GOAT_VARIANT_KEY, GoatVariant.DIRECT_CODEC, GoatVariant.DIRECT_CODEC);
        event.dataPackRegistry(NMLMobVariants.FOX_VARIANT_KEY, FoxVariant.DIRECT_CODEC, FoxVariant.DIRECT_CODEC);
        event.dataPackRegistry(NMLMobVariants.PIG_VARIANT_KEY, PigVariant.DIRECT_CODEC, PigVariant.DIRECT_CODEC);
        event.dataPackRegistry(NMLMobVariants.SALMON_VARIANT_KEY, SalmonVariant.DIRECT_CODEC, SalmonVariant.DIRECT_CODEC);
        event.dataPackRegistry(NMLMobVariants.SHEEP_VARIANT_KEY, SheepVariant.DIRECT_CODEC, SheepVariant.DIRECT_CODEC);
        event.dataPackRegistry(NMLMobVariants.TURTLE_VARIANT_KEY, TurtleVariant.DIRECT_CODEC, TurtleVariant.DIRECT_CODEC);
        event.dataPackRegistry(NMLMobVariants.CHICKEN_VARIANT_KEY, ChickenVariant.DIRECT_CODEC, ChickenVariant.DIRECT_CODEC);
        event.dataPackRegistry(NMLMobVariants.CAMEL_VARIANT_KEY, CamelVariant.DIRECT_CODEC, CamelVariant.DIRECT_CODEC);
    }
}
