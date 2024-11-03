package com.farcr.nomansland.common.registry;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.entity.mob_variant.*;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class NMLMobVariants {

    public static final ResourceKey<Registry<CodVariant>> COD_VARIANT_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "mob_variants/cod"));
    public static final ResourceKey<Registry<CowVariant>> COW_VARIANT_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "mob_variants/cow"));
    public static final ResourceKey<Registry<MooshroomVariant>> MOOSHROOM_VARIANT_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "mob_variants/mooshroom"));
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

    public static ResourceKey<? extends Registry<MobVariant>> getVariantOfType(EntityType<?> entityType) {
        return ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "mob_variants/" + entityType.toShortString()));
    }

    public static Holder<? extends MobVariant> getVariantForSpawn(Entity entity) {
        Level level = entity.level();
        RandomSource random = entity.getRandom();
        ResourceKey<? extends Registry<MobVariant>> key = getVariantOfType(entity.getType());

        Registry<MobVariant> registry = entity.registryAccess().registryOrThrow(key);
        List<Holder.Reference<MobVariant>> possibleVariants = registry.holders()
                .filter((v) -> v.value().biomes().isPresent() && v.value().biomes().get().contains(level.getBiome(entity.blockPosition())))
                .toList();
        List<Holder.Reference<MobVariant>> defaultVariants = registry.holders()
                .filter((v) -> v.value().biomes().isEmpty() || v.is(ResourceKey.create(key, ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "default"))))
                .toList();
        Holder<? extends MobVariant> selectedVariant = defaultVariants.get(random.nextInt(defaultVariants.size()));
        if (!possibleVariants.isEmpty()) {
            double completeWeight = 0.0;
            for (Holder.Reference<MobVariant> variant : possibleVariants)
                completeWeight += variant.value().weight();
            double r = Math.random() * completeWeight;
            double countWeight = 0.0;
            for (Holder.Reference<MobVariant> variant : possibleVariants) {
                countWeight += variant.value().weight();
                if (countWeight >= r) {
                    selectedVariant = variant;
                    break;
                }
            }
        }
        return selectedVariant;
    }

    public static Holder<? extends MobVariant> getOffspringWithVariant(AgeableMob parent1, AgeableMob parent2) {
        return parent1.getRandom().nextBoolean() ?
                        ((VariantHolder<Holder<? extends MobVariant>>) parent1).getVariant() :
                        ((VariantHolder<Holder<? extends MobVariant>>) parent2).getVariant();
    }
}
