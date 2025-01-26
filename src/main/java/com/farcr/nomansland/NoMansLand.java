package com.farcr.nomansland;

import com.farcr.nomansland.common.registry.*;
import com.farcr.nomansland.common.world.generation.NMLBiomePlacements;
import com.farcr.nomansland.common.world.generation.NMLSurfaceRules;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
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
        NMLPotions.POTIONS.register(modEventBus);
        NMLBiomePlacements.register();

        modEventBus.addListener(NMLItems::addCreative);
        modEventBus.addListener(this::commonSetup);

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

}
