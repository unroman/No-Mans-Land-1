package com.farcr.nomansland;

import com.farcr.nomansland.common.entity.mob_variant.*;
import com.farcr.nomansland.common.entity.mob_variant.deer.DeerAntlersVariant;
import com.farcr.nomansland.common.entity.mob_variant.deer.DeerPatternVariant;
import com.farcr.nomansland.common.entity.mob_variant.deer.DeerVariant;
import com.farcr.nomansland.common.registry.*;
import com.farcr.nomansland.common.world.generation.NMLBiomePlacements;
import com.farcr.nomansland.common.world.generation.NMLSurfaceRules;
import com.farcr.nomansland.integration.FDIntegration;
import com.farcr.nomansland.integration.Mods;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import org.slf4j.Logger;

@Mod(NoMansLand.MODID)
public class NoMansLand {
    public static final String MODID = "nomansland";
    public static final Logger LOGGER = LogUtils.getLogger();

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
        NMLDataSerializers.ENTITY_DATA_SERIALIZERS.register(modEventBus);
        NMLFogModifiers.FOG_MODIFIERS.register(modEventBus);
        NMLMobVariants.FROG_VARIANTS.register(modEventBus);
        NMLEffects.MOB_EFFECTS.register(modEventBus);
        NMLBiomePlacements.register();

        if (Mods.FARMERSDELIGHT.isLoaded()) FDIntegration.register();

        modEventBus.addListener(NMLItems::addCreative);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::registerRegistries);
        modEventBus.addListener(this::registerDatapackRegistries);

        modContainer.registerConfig(ModConfig.Type.COMMON, NMLConfig.COMMON_CONFIG);
        modContainer.registerConfig(ModConfig.Type.CLIENT, NMLConfig.CLIENT_CONFIG);

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            NMLSurfaceRules.register();
            NMLFlammables.register();
            NMLPottables.register();
        });
    }

    public void registerRegistries(final NewRegistryEvent event) {
        event.register(NMLRegistries.POND_DECORATOR_TYPE);
        event.register(NMLRegistries.BOULDER_DECORATOR_TYPE);
        event.register(NMLRegistries.FALLEN_TREE_DECORATOR_TYPE);
        event.register(NMLRegistries.FOG_MODIFIERS);
    }

    public void registerDatapackRegistries(final DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(NMLMobVariants.PIG_VARIANT_KEY, PigVariant.DIRECT_CODEC, PigVariant.DIRECT_CODEC);
        event.dataPackRegistry(NMLMobVariants.PIG_OVERLAY_VARIANT_KEY, PigOverlayVariant.DIRECT_CODEC, PigOverlayVariant.DIRECT_CODEC);
        event.dataPackRegistry(NMLMobVariants.CHICKEN_VARIANT_KEY, ChickenVariant.DIRECT_CODEC, ChickenVariant.DIRECT_CODEC);
        event.dataPackRegistry(NMLMobVariants.SHEEP_VARIANT_KEY, SheepVariant.DIRECT_CODEC, SheepVariant.DIRECT_CODEC);
        event.dataPackRegistry(NMLMobVariants.GOAT_VARIANT_KEY, GoatVariant.DIRECT_CODEC, GoatVariant.DIRECT_CODEC);
        event.dataPackRegistry(NMLMobVariants.LLAMA_VARIANT_KEY, LlamaVariant.DIRECT_CODEC, LlamaVariant.DIRECT_CODEC);
        event.dataPackRegistry(NMLMobVariants.CAMEL_VARIANT_KEY, CamelVariant.DIRECT_CODEC, CamelVariant.DIRECT_CODEC);
        event.dataPackRegistry(NMLMobVariants.FOX_VARIANT_KEY, FoxVariant.DIRECT_CODEC, FoxVariant.DIRECT_CODEC);
        event.dataPackRegistry(NMLMobVariants.COW_VARIANT_KEY, CowVariant.DIRECT_CODEC, CowVariant.DIRECT_CODEC);
        event.dataPackRegistry(NMLMobVariants.MOOSHROOM_VARIANT_KEY, MooshroomVariant.DIRECT_CODEC, MooshroomVariant.DIRECT_CODEC);
        event.dataPackRegistry(NMLMobVariants.DEER_VARIANT_KEY, DeerVariant.DIRECT_CODEC, DeerVariant.DIRECT_CODEC);
        event.dataPackRegistry(NMLMobVariants.DEER_ANTLERS_VARIANT_KEY, DeerAntlersVariant.DIRECT_CODEC, DeerAntlersVariant.DIRECT_CODEC);
        event.dataPackRegistry(NMLMobVariants.DEER_PATTERN_VARIANT_KEY, DeerPatternVariant.DIRECT_CODEC, DeerPatternVariant.DIRECT_CODEC);
        event.dataPackRegistry(NMLMobVariants.COD_VARIANT_KEY, CodVariant.DIRECT_CODEC, CodVariant.DIRECT_CODEC);
        event.dataPackRegistry(NMLMobVariants.SALMON_VARIANT_KEY, SalmonVariant.DIRECT_CODEC, SalmonVariant.DIRECT_CODEC);
        event.dataPackRegistry(NMLMobVariants.BILLHOOK_BASS_VARIANT_KEY, BillhookBassVariant.DIRECT_CODEC, BillhookBassVariant.DIRECT_CODEC);
        event.dataPackRegistry(NMLMobVariants.DOLPHIN_VARIANT_KEY, DolphinVariant.DIRECT_CODEC, DolphinVariant.DIRECT_CODEC);
        event.dataPackRegistry(NMLMobVariants.TURTLE_VARIANT_KEY, TurtleVariant.DIRECT_CODEC, TurtleVariant.DIRECT_CODEC);
        event.dataPackRegistry(NMLMobVariants.SQUID_VARIANT_KEY, SquidVariant.DIRECT_CODEC, SquidVariant.DIRECT_CODEC);
        event.dataPackRegistry(NMLMobVariants.GLOW_SQUID_VARIANT_KEY, GlowSquidVariant.DIRECT_CODEC, GlowSquidVariant.DIRECT_CODEC);
        event.dataPackRegistry(NMLMobVariants.BEE_VARIANT_KEY, BeeVariant.DIRECT_CODEC, BeeVariant.DIRECT_CODEC);
        event.dataPackRegistry(NMLMobVariants.RABBIT_VARIANT_KEY, RabbitVariant.DIRECT_CODEC, RabbitVariant.DIRECT_CODEC);
    }
}
