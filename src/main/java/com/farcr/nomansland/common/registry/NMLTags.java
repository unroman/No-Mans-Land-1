package com.farcr.nomansland.common.registry;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.definitions.SharedTag;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class NMLTags {
    public static final TagKey<Item> FIRESTARTERS = createItemTag("firestarters");
    public static final TagKey<Item> DEER_FOOD = createItemTag("deer_food");
    public static final TagKey<Item> MAKES_RESIN_OIL = createItemTag("makes_resin_oil");

    public static final SharedTag MAPLE_LOGS = createSharedTag("maple_logs");
    public static final SharedTag PINE_LOGS = createSharedTag("pine_logs");
    public static final SharedTag WALNUT_LOGS = createSharedTag("walnut_logs");
    public static final SharedTag WILLOW_LOGS = createSharedTag("willow_logs");


    public static final TagKey<Block> MUSHROOM_BLOCKS = createBlockTag("mushroom_blocks");
    public static final TagKey<Block> BONEMEAL_SPREADS = createBlockTag("bonemeal_spreads");
    public static final TagKey<Block> BONEMEAL_SPREADS_UPWARDS = createBlockTag("bonemeal_spreads_upwards");
    public static final TagKey<Block> CONIFEROUS_LOGS = createBlockTag("coniferous_logs");
    public static final TagKey<Biome> HAS_DENSE_FOG = createBiomeTag("has_dense_fog");
    public static final TagKey<Block> BOMB_EXPLODE = createBlockTag("firebomb_explode");
    public static final TagKey<Block> HEAT_SOURCES = createBlockTag("heat_sources");
    public static final TagKey<Block> TRIMMED_PLANKS = createBlockTag("trimmed_planks");
    public static final TagKey<Block> PATHS = createBlockTag("paths");
    public static final TagKey<Block> FISH_BARRELS = createBlockTag("fish_barrels");
    public static final TagKey<EntityType<?>> ANCHOR_BLACKLIST = createEntityTag("anchor_blacklist");
    public static final TagKey<Biome> SPAWNS_MUD_FROGS = createBiomeTag("entity/spawns_mud_frog");
    public static final TagKey<Biome> OLD_GROWTH_FOREST = createBiomeTag("old_growth_forest");
    public static final TagKey<Biome> CAVES = createBiomeTag("caves");
    public static final TagKey<DamageType> IGNITES_FLAMMABLE = createDamageTypeTag("ignites_flammable");

    private static SharedTag createSharedTag(String name) {
        return new SharedTag(name);
    }

    private static TagKey<Item> createItemTag(String name) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, name));
    }

    private static TagKey<Block> createBlockTag(String name) {
        return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, name));
    }

    private static TagKey<Biome> createBiomeTag(String name) {
        return TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, name));
    }

    private static TagKey<EntityType<?>> createEntityTag(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, name));
    }

    private static TagKey<DamageType> createDamageTypeTag(String name) {
        return TagKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, name));
    }
}
