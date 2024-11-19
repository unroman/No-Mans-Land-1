package com.farcr.nomansland.common.mixin.mob_variants;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.entity.mob_variant.GlowSquidVariant;
import com.farcr.nomansland.common.entity.mob_variant.SquidVariant;
import com.farcr.nomansland.common.mixin.MobMixin;
import com.farcr.nomansland.common.mixinduck.GlowSquidDuck;
import com.farcr.nomansland.common.registry.NMLDataSerializers;
import com.farcr.nomansland.common.registry.NMLMobVariants;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Squid.class)
public abstract class SquidMixin extends MobMixin implements VariantHolder<Holder<SquidVariant>>, GlowSquidDuck {

    @Unique
    private static final EntityDataAccessor<Holder<SquidVariant>> DATA_VARIANT_ID = SynchedEntityData.defineId(Squid.class, NMLDataSerializers.SQUID_VARIANT.get());
    private static final EntityDataAccessor<Holder<GlowSquidVariant>> DATA_GLOWING_VARIANT_ID = SynchedEntityData.defineId(Squid.class, NMLDataSerializers.GLOW_SQUID_VARIANT.get());
    @Unique
    private static final String VARIANT_KEY = "Variant";
    @Unique
    private static final String GLOWING_VARIANT_KEY = "GlowingVariant";

    @Unique
    private static final ResourceKey<SquidVariant> DEFAULT_VARIANT = ResourceKey.create(NMLMobVariants.SQUID_VARIANT_KEY, ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "default"));

    private static final ResourceKey<GlowSquidVariant> DEFAULT_GLOWING_VARIANT = ResourceKey.create(NMLMobVariants.GLOW_SQUID_VARIANT_KEY, ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "default"));

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(DATA_VARIANT_ID, this.registryAccess().registryOrThrow(NMLMobVariants.SQUID_VARIANT_KEY).getHolderOrThrow(DEFAULT_VARIANT));
        builder.define(DATA_GLOWING_VARIANT_ID, this.registryAccess().registryOrThrow(NMLMobVariants.GLOW_SQUID_VARIANT_KEY).getHolderOrThrow(DEFAULT_GLOWING_VARIANT));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
        this.getVariant().unwrapKey().ifPresent((variant) -> {
            compound.putString(VARIANT_KEY, variant.location().toString());
        });
        this.noMansLand$getGlowSquidVariant().unwrapKey().ifPresent((variant) -> {
            compound.putString(GLOWING_VARIANT_KEY, variant.location().toString());
        });
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
        Optional.ofNullable(ResourceLocation.tryParse(compound.getString(VARIANT_KEY)))
                .map((string) -> ResourceKey.create(NMLMobVariants.SQUID_VARIANT_KEY, string))
                .flatMap((variant) -> this.registryAccess().registryOrThrow(NMLMobVariants.SQUID_VARIANT_KEY).getHolder(variant))
                .ifPresent(this::setVariant);
        Optional.ofNullable(ResourceLocation.tryParse(compound.getString(GLOWING_VARIANT_KEY)))
                .map((string) -> ResourceKey.create(NMLMobVariants.GLOW_SQUID_VARIANT_KEY, string))
                .flatMap((variant) -> this.registryAccess().registryOrThrow(NMLMobVariants.GLOW_SQUID_VARIANT_KEY).getHolder(variant))
                .ifPresent(this::noMansLand$setGlowSquidVariant);
    }

    @Override
    protected void finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, SpawnGroupData spawnGroupData, CallbackInfoReturnable<SpawnGroupData> cir) {
        if (this.getType() == EntityType.GLOW_SQUID)
            this.noMansLand$setGlowSquidVariant((Holder<GlowSquidVariant>) NMLMobVariants.getVariantForSpawn(((GlowSquid) (Object) this)));
        else
            this.setVariant((Holder<SquidVariant>) NMLMobVariants.getVariantForSpawn(((Squid) (Object) this)));    }

    @Override
    public Holder<SquidVariant> getVariant() {
        return this.entityData.get(DATA_VARIANT_ID);
    }

    @Override
    public void setVariant(Holder<SquidVariant> variantHolder) {
        this.entityData.set(DATA_VARIANT_ID, variantHolder);
    }

    @Override
    public Holder<GlowSquidVariant> noMansLand$getGlowSquidVariant() {
        return this.entityData.get(DATA_GLOWING_VARIANT_ID);
    }

    @Override
    public void noMansLand$setGlowSquidVariant(Holder<GlowSquidVariant> glowSquidVariantHolder) {
        this.entityData.set(DATA_GLOWING_VARIANT_ID, glowSquidVariantHolder);
    }
}
