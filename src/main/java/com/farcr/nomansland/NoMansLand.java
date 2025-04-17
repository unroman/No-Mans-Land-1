package com.farcr.nomansland;

import com.farcr.nomansland.common.block.tap.TapInteraction;
import com.farcr.nomansland.common.definitions.BlockDefinition;
import com.farcr.nomansland.common.definitions.ItemLikeDefinition;
import com.farcr.nomansland.common.event.CreativeModeTabEventHandler;
import com.farcr.nomansland.common.integration.BBIntegration;
import com.farcr.nomansland.common.integration.CIntegration;
import com.farcr.nomansland.common.integration.FDIntegration;
import com.farcr.nomansland.common.integration.Mods;
import com.farcr.nomansland.common.registry.*;
import com.farcr.nomansland.common.registry.blocks.NMLBlocks;
import com.farcr.nomansland.common.registry.blocks.NMLFlammables;
import com.farcr.nomansland.common.registry.entities.NMLEffects;
import com.farcr.nomansland.common.registry.entities.NMLEntities;
import com.farcr.nomansland.common.registry.entities.NMLMobVariants;
import com.farcr.nomansland.common.registry.items.NMLCreativeTabs;
import com.farcr.nomansland.common.registry.items.NMLItems;
import com.farcr.nomansland.common.registry.worldgen.*;
import com.farcr.nomansland.common.world.generation.NMLBiomePlacements;
import com.farcr.nomansland.common.world.generation.NMLSurfaceRules;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;

import static com.farcr.nomansland.common.registry.items.NMLItems.*;

@Mod(NoMansLand.MODID)
public class NoMansLand {
    public static final String MODID = "nomansland";

    public NoMansLand(IEventBus modEventBus, ModContainer modContainer) {

        NMLItems.ITEMS.register(modEventBus);
        NMLBlocks.BLOCKS.register(modEventBus);
        NMLEntities.ENTITIES.register(modEventBus);
        NMLFeatures.FEATURES.register(modEventBus);
        NMLFoliagePlacerTypes.FOLIAGE_PLACER_TYPES.register(modEventBus);
        NMLTrunkPlacerTypes.TRUNK_PLACER_TYPES.register(modEventBus);
        NMLSounds.SOUND_EVENTS.register(modEventBus);
        NMLCreativeTabs.CREATIVE_TABS.register(modEventBus);
        NMLParticleTypes.PARTICLE_TYPES.register(modEventBus);
        NMLBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        NMLLootModifiers.LOOT_MODIFIERS.register(modEventBus);
        NMLTreeDecoratorTypes.TREE_DECORATOR_TYPES.register(modEventBus);
        NMLPondDecoratorTypes.POND_DECORATOR_TYPES.register(modEventBus);
        NMLBoulderDecoratorTypes.BOULDER_DECORATOR_TYPES.register(modEventBus);
        NMLFallenTreeDecoratorTypes.FALLEN_TREE_DECORATOR_TYPES.register(modEventBus);
        NMLFogModifiers.FOG_MODIFIERS.register(modEventBus);
        NMLMobVariants.FROG_VARIANTS.register(modEventBus);
        NMLMobVariants.MOB_VARIANT_TYPES.register(modEventBus);
        NMLEffects.MOB_EFFECTS.register(modEventBus);
        NMLStructureProcessorTypes.STRUCTURE_PROCESSOR_TYPES.register(modEventBus);
        NMLCriteriaTriggers.TRIGGERS.register(modEventBus);
        NMLRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
        NMLRecipeSerializers.RECIPE_TYPES.register(modEventBus);
        NMLFluids.FLUID_TYPES.register(modEventBus);
        NMLFluids.FLUIDS.register(modEventBus);

        if (Mods.FARMERSDELIGHT.isLoaded()) {
            FDIntegration.register();
            modEventBus.addListener(FDIntegration::addBlockEntities);
        }

        if (Mods.BLOCKBOX.isLoaded()) BBIntegration.register();
        if (Mods.CREATE.isLoaded()) CIntegration.register();

        NeoForge.EVENT_BUS.register(new CreativeModeTabEventHandler());
        modEventBus.addListener(NMLBlockEntities::addBlockEntities);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::registerRegistries);
        modEventBus.addListener(this::registerDatapackRegistries);

        modContainer.registerConfig(ModConfig.Type.COMMON, NMLConfig.COMMON_CONFIG);
        modContainer.registerConfig(ModConfig.Type.CLIENT, NMLConfig.CLIENT_CONFIG);

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            if (NMLConfig.BIOMES.get()) NMLBiomePlacements.register();
            NMLSurfaceRules.register();
            NMLFlammables.register();

            for (BlockDefinition<?> definition : NMLBlocks.BLOCK_DEFINITIONS) {
                if (definition.get() instanceof FlowerPotBlock flowerPotBlock) {
                    flowerPotBlock.getEmptyPot().addPlant(BuiltInRegistries.BLOCK.getKey(flowerPotBlock.getPotted()), () -> flowerPotBlock);
                }
            }
        });
    }

    private void registerRegistries(final NewRegistryEvent event) {
        event.register(NMLRegistries.POND_DECORATOR_TYPE);
        event.register(NMLRegistries.BOULDER_DECORATOR_TYPE);
        event.register(NMLRegistries.FALLEN_TREE_DECORATOR_TYPE);
        event.register(NMLRegistries.FOG_MODIFIERS);
    }

    private void registerDatapackRegistries(final DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(NMLRegistries.TAP_INTERACTION_KEY, TapInteraction.DIRECT_CODEC, TapInteraction.DIRECT_CODEC);
    }
}
