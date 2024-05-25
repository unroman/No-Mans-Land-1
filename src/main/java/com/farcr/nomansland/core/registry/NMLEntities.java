package com.farcr.nomansland.core.registry;

import com.farcr.nomansland.core.NoMansLand;
import com.farcr.nomansland.core.content.entity.BoatEntity;
import com.farcr.nomansland.core.content.entity.BuriedEntity;
import com.farcr.nomansland.core.content.entity.ChestBoatEntity;
import com.farcr.nomansland.core.content.entity.MooseEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class NMLEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, NoMansLand.MODID);

    public static final RegistryObject<EntityType<BoatEntity>> BOAT =
            ENTITY_TYPES.register("boat", () -> EntityType.Builder.<BoatEntity>of(BoatEntity::new, MobCategory.MISC)
                    .sized(1.375f, 0.5625f).build("boat"));
    public static final RegistryObject<EntityType<ChestBoatEntity>> CHEST_BOAT =
            ENTITY_TYPES.register("chest_boat", () -> EntityType.Builder.<ChestBoatEntity>of(ChestBoatEntity::new, MobCategory.MISC)
                    .sized(1.375f, 0.5625f).build("chest_boat"));

    public static final RegistryObject<EntityType<BuriedEntity>> BURIED =
            ENTITY_TYPES.register("buried", () -> EntityType.Builder.<BuriedEntity>of(BuriedEntity::new, MobCategory.MONSTER)
                    .sized(1.0f, 1.0f).clientTrackingRange(8).build("buried"));
                    
    public static final RegistryObject<EntityType<MooseEntity>> MOOSE =
            ENTITY_TYPES.register("moose", () -> EntityType.Builder.of(MooseEntity::new, MobCategory.CREATURE)
                    .sized(2.5f, 2.5f).build("moose"));

    public static void register(IEventBus eventBus) { ENTITY_TYPES.register(eventBus);}
}
