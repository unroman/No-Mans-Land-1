package com.farcr.nomansland.common.registry;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.entity.mob_variant.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.FrogVariant;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NMLMobVariants {

    public static final ResourceKey<Registry<CodVariant>> COD_VARIANT_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "mob_variants/cod"));
    public static final ResourceKey<Registry<CowVariant>> COW_VARIANT_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "mob_variants/cow"));
    public static final ResourceKey<Registry<CamelVariant>> CAMEL_VARIANT_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "mob_variants/camel"));
    public static final ResourceKey<Registry<DolphinVariant>> DOLPHIN_VARIANT_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "mob_variants/dolphin"));
    public static final ResourceKey<Registry<FoxVariant>> FOX_VARIANT_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "mob_variants/fox"));
    public static final ResourceKey<Registry<GoatVariant>> GOAT_VARIANT_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "mob_variants/goat"));
    public static final ResourceKey<Registry<PigVariant>> PIG_VARIANT_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "mob_variants/pig"));
    public static final ResourceKey<Registry<ChickenVariant>> CHICKEN_VARIANT_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "mob_variants/chicken"));
    public static final ResourceKey<Registry<SalmonVariant>> SALMON_VARIANT_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "mob_variants/salmon"));
    public static final ResourceKey<Registry<SheepVariant>> SHEEP_VARIANT_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "mob_variants/sheep"));
    public static final ResourceKey<Registry<TurtleVariant>> TURTLE_VARIANT_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "mob_variants/turtle"));

    public static final DeferredRegister<FrogVariant> FROG_VARIANTS = DeferredRegister.create(BuiltInRegistries.FROG_VARIANT, NoMansLand.MODID);
    public static final DeferredHolder<FrogVariant, FrogVariant> MUD = FROG_VARIANTS.register("mud", () -> new FrogVariant(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "textures/entity/mob_variants/mud_frog.png")));

    public static ResourceKey<? extends Registry<?>> getVariantOfType(EntityType<?> entityType) {
        return ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "mob_variants/" + entityType.toShortString()));
    }
}
