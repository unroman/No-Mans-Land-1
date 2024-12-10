package com.farcr.nomansland.common.registry;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.entity.mob_variant.*;
import com.farcr.nomansland.common.entity.mob_variant.deer.DeerAntlersVariant;
import com.farcr.nomansland.common.entity.mob_variant.deer.DeerPatternVariant;
import com.farcr.nomansland.common.entity.mob_variant.deer.DeerVariant;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class NMLMobVariants {

    public static final ResourceKey<Registry<DeerVariant>> DEER_VARIANT_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "mob_variants/deer/base"));
    public static final ResourceKey<Registry<DeerAntlersVariant>> DEER_ANTLERS_VARIANT_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "mob_variants/deer/antlers"));
    public static final ResourceKey<Registry<DeerPatternVariant>> DEER_PATTERN_VARIANT_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "mob_variants/deer/pattern"));

    public static final ResourceKey<Registry<GlowSquidVariant>> GLOW_SQUID_VARIANT_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "mob_variants/glow_squid"));
    public static final ResourceKey<Registry<SquidVariant>> SQUID_VARIANT_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "mob_variants/squid"));
    public static final ResourceKey<Registry<BillhookBassVariant>> BILLHOOK_BASS_VARIANT_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "mob_variants/billhook_bass"));
    public static final ResourceKey<Registry<CodVariant>> COD_VARIANT_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "mob_variants/cod"));
    public static final ResourceKey<Registry<CowVariant>> COW_VARIANT_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "mob_variants/cow"));
    public static final ResourceKey<Registry<LlamaVariant>> LLAMA_VARIANT_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "mob_variants/llama"));
    public static final ResourceKey<Registry<MooshroomVariant>> MOOSHROOM_VARIANT_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "mob_variants/mooshroom"));
    public static final ResourceKey<Registry<CamelVariant>> CAMEL_VARIANT_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "mob_variants/camel"));
    public static final ResourceKey<Registry<DolphinVariant>> DOLPHIN_VARIANT_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "mob_variants/dolphin"));
    public static final ResourceKey<Registry<FoxVariant>> FOX_VARIANT_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "mob_variants/fox"));
    public static final ResourceKey<Registry<GoatVariant>> GOAT_VARIANT_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "mob_variants/goat"));
    public static final ResourceKey<Registry<PigVariant>> PIG_VARIANT_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "mob_variants/pig/base"));
    public static final ResourceKey<Registry<ChickenVariant>> CHICKEN_VARIANT_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "mob_variants/chicken"));
    public static final ResourceKey<Registry<SalmonVariant>> SALMON_VARIANT_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "mob_variants/salmon"));
    public static final ResourceKey<Registry<SheepVariant>> SHEEP_VARIANT_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "mob_variants/sheep"));
    public static final ResourceKey<Registry<TurtleVariant>> TURTLE_VARIANT_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "mob_variants/turtle"));
    public static final ResourceKey<Registry<PigOverlayVariant>> PIG_OVERLAY_VARIANT_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "mob_variants/pig/overlay"));

    public static final DeferredRegister<FrogVariant> FROG_VARIANTS = DeferredRegister.create(BuiltInRegistries.FROG_VARIANT, NoMansLand.MODID);
    public static final DeferredHolder<FrogVariant, FrogVariant> MUD = FROG_VARIANTS.register("mud", () -> new FrogVariant(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "textures/entity/mob_variants/mud_frog.png")));

    public static ResourceKey<? extends Registry<MobVariant>> getVariantOfType(EntityType<?> entityType) {
        return getVariantKey(entityType.toShortString());
    }

    public static ResourceKey<? extends Registry<MobVariant>> getVariantKey(String name) {
        return ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "mob_variants/" + name));
    }

    public static Holder<? extends MobVariant> getVariantForSpawn(Entity entity) {
        return getVariantForSpawn(entity, getVariantOfType(entity.getType()));
    }

    public static Holder<? extends MobVariant> getVariantForSpawn(Entity entity, ResourceKey<? extends Registry<MobVariant>> variantRegistry) {
        Level level = entity.level();
        RandomSource random = entity.getRandom();

        Registry<MobVariant> registry = entity.registryAccess().registryOrThrow(variantRegistry);
        List<Holder.Reference<MobVariant>> possibleVariants = registry.holders()
                .filter((v) -> v.value().biomes().isPresent() && v.value().biomes().get().contains(level.getBiome(entity.blockPosition())))
                .toList();
        List<Holder.Reference<MobVariant>> defaultVariants = registry.holders()
                .filter((v) -> v.value().biomes().isEmpty() || v.is(ResourceKey.create(variantRegistry, ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "default"))))
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
