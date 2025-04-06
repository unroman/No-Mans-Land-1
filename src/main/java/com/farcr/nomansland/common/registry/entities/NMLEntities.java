package com.farcr.nomansland.common.registry.entities;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.entity.BoatEntity;
import com.farcr.nomansland.common.entity.ChestBoatEntity;
import com.farcr.nomansland.common.entity.billhook_bass.BillhookBass;
import com.farcr.nomansland.common.entity.bombs.ExplosiveEntity;
import com.farcr.nomansland.common.entity.bombs.FirebombEntity;
import com.farcr.nomansland.common.entity.deer.Deer;
import com.farcr.nomansland.common.entity.goose.Goose;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class NMLEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(Registries.ENTITY_TYPE, NoMansLand.MODID);

    public static final Supplier<EntityType<BoatEntity>> BOAT =
            ENTITIES.register("boat", () -> EntityType.Builder.<BoatEntity>of(BoatEntity::new, MobCategory.MISC)
                    .sized(1.375f, 0.5625f).build("boat"));
    public static final Supplier<EntityType<ChestBoatEntity>> CHEST_BOAT =
            ENTITIES.register("chest_boat", () -> EntityType.Builder.<ChestBoatEntity>of(ChestBoatEntity::new, MobCategory.MISC)
                    .sized(1.375f, 0.5625f).build("chest_boat"));

    public static final Supplier<EntityType<FirebombEntity>> FIREBOMB =
            ENTITIES.register("firebomb", () -> EntityType.Builder.<FirebombEntity>of(FirebombEntity::new, MobCategory.MISC)
                    .sized(0.375F, 0.375F).clientTrackingRange(4).updateInterval(20).build("fire_bomb"));

    public static final Supplier<EntityType<ExplosiveEntity>> EXPLOSIVE =
            ENTITIES.register("explosive", () -> EntityType.Builder.<ExplosiveEntity>of(ExplosiveEntity::new, MobCategory.MISC)
                    .sized(0.3F, 0.3F).clientTrackingRange(4).updateInterval(20).build("explosive"));

    public static final Supplier<EntityType<BillhookBass>> BILLHOOK_BASS =
            ENTITIES.register("billhook_bass", () -> EntityType.Builder.of(BillhookBass::new, MobCategory.WATER_CREATURE)
            .sized(0.7F, 0.5F).build("billhook_bass"));

    public static final Supplier<EntityType<Deer>> DEER =
            ENTITIES.register("deer", () -> EntityType.Builder.of(Deer::new, MobCategory.CREATURE)
                    .sized(0.8F, 1.4F).build("deer"));

    public static final Supplier<EntityType<Goose>> GOOSE =
            ENTITIES.register("goose", () -> EntityType.Builder.of(Goose::new, MobCategory.CREATURE)
                    .sized(0.8F, 1F).build("goose"));

//    public static final Supplier<EntityType<BuriedEntity>> BURIED =
//            ENTITIES.register("buried", () -> EntityType.Builder.of(BuriedEntity::new, MobCategory.MONSTER)
//                    .sized(1.0f, 1.0f).clientTrackingRange(8).build("buried"));
//
//    public static final Supplier<EntityType<MooseEntity>> MOOSE =
//            ENTITIES.register("moose", () -> EntityType.Builder.of(MooseEntity::new, MobCategory.CREATURE)
//                    .sized(2.5f, 2.5f).build("moose"));

}
