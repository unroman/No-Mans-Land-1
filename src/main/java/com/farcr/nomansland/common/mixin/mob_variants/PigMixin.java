package com.farcr.nomansland.common.mixin.mob_variants;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.entity.mob_variant.PigOverlayVariant;
import com.farcr.nomansland.common.entity.mob_variant.PigVariant;
import com.farcr.nomansland.common.mixin.MobMixin;
import com.farcr.nomansland.common.mixinduck.PigDuck;
import com.farcr.nomansland.common.registry.NMLDataSerializers;
import com.farcr.nomansland.common.registry.NMLMobVariants;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Pig.class)
public abstract class PigMixin extends MobMixin implements VariantHolder<Holder<PigVariant>>, PigDuck {
    @Unique
    private static final EntityDataAccessor<Holder<PigVariant>> DATA_VARIANT_ID = SynchedEntityData.defineId(Pig.class, NMLDataSerializers.PIG_VARIANT.get());
    @Unique
    private static final EntityDataAccessor<Holder<PigOverlayVariant>> DATA_OVERLAY_VARIANT_ID = SynchedEntityData.defineId(Pig.class, NMLDataSerializers.PIG_OVERLAY_VARIANT.get());
    @Unique
    private static final String VARIANT_KEY = "Variant";
    @Unique
    private static final String OVERLAY_VARIANT_KEY = "OverlayVariant";
    @Unique
    private static final ResourceKey<PigVariant> DEFAULT_VARIANT = ResourceKey.create(NMLMobVariants.PIG_VARIANT_KEY, ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "default"));
    @Unique
    private static final ResourceKey<PigOverlayVariant> DEFAULT_OVERLAY_VARIANT = ResourceKey.create(NMLMobVariants.PIG_OVERLAY_VARIANT_KEY, ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "default"));
    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(DATA_VARIANT_ID, this.registryAccess().registryOrThrow(NMLMobVariants.PIG_VARIANT_KEY).getHolderOrThrow(DEFAULT_VARIANT));
        builder.define(DATA_OVERLAY_VARIANT_ID, this.registryAccess().registryOrThrow(NMLMobVariants.PIG_OVERLAY_VARIANT_KEY).getHolderOrThrow(DEFAULT_OVERLAY_VARIANT));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
        this.getVariant().unwrapKey().ifPresent((variant) -> {
            compound.putString(VARIANT_KEY, variant.location().toString());
        });
        this.noMansLand$getPigOverlayVariant().unwrapKey().ifPresent((variant) -> {
            compound.putString(OVERLAY_VARIANT_KEY, variant.location().toString());
        });
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
        Optional.ofNullable(ResourceLocation.tryParse(compound.getString(VARIANT_KEY)))
                .map((string) -> ResourceKey.create(NMLMobVariants.PIG_VARIANT_KEY, string))
                .flatMap((variant) -> this.registryAccess().registryOrThrow(NMLMobVariants.PIG_VARIANT_KEY).getHolder(variant))
                .ifPresent(this::setVariant);
        Optional.ofNullable(ResourceLocation.tryParse(compound.getString(OVERLAY_VARIANT_KEY)))
                .map((string) -> ResourceKey.create(NMLMobVariants.PIG_OVERLAY_VARIANT_KEY, string))
                .flatMap((variant) -> this.registryAccess().registryOrThrow(NMLMobVariants.PIG_OVERLAY_VARIANT_KEY).getHolder(variant))
                .ifPresent(this::noMansLand$setPigOverlayVariant);
    }

    @Override
    protected void finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, SpawnGroupData spawnGroupData, CallbackInfoReturnable<SpawnGroupData> cir) {
        this.setVariant((Holder<PigVariant>) NMLMobVariants.getVariantForSpawn((Pig) (Object) this, NMLMobVariants.getVariantKey("pig/base")));
        this.noMansLand$setPigOverlayVariant((Holder<PigOverlayVariant>) NMLMobVariants.getVariantForSpawn((Pig) (Object) this, NMLMobVariants.getVariantKey("pig/overlay")));
    }

    @Override
    public Holder<PigVariant> getVariant() {
        return this.entityData.get(DATA_VARIANT_ID);
    }

    @Override
    public void setVariant(Holder<PigVariant> variantHolder) {
        this.entityData.set(DATA_VARIANT_ID, variantHolder);
    }

    @Inject(method = "getBreedOffspring*", at = @At("RETURN"), cancellable = true)
    private void getBreedOffspring(ServerLevel level, AgeableMob otherParent, CallbackInfoReturnable<AgeableMob> cir) {
        AgeableMob entity = (AgeableMob) this.getType().create(this.level());
        ((VariantHolder<Holder<PigVariant>>) entity).setVariant(random.nextBoolean() ? ((VariantHolder<Holder<PigVariant>>) entity).getVariant() : ((VariantHolder<Holder<PigVariant>>) entity).getVariant());
        ((PigDuck) entity).noMansLand$setPigOverlayVariant(random.nextBoolean() ? ((PigDuck) entity).noMansLand$getPigOverlayVariant() : ((PigDuck) entity).noMansLand$getPigOverlayVariant());
        cir.setReturnValue(entity);
    }

    @Override
    public Holder<PigOverlayVariant> noMansLand$getPigOverlayVariant() {
        return this.entityData.get(DATA_OVERLAY_VARIANT_ID);
    }

    @Override
    public void noMansLand$setPigOverlayVariant(Holder<PigOverlayVariant> pigOverlayVariantHolder) {
        this.entityData.set(DATA_OVERLAY_VARIANT_ID, pigOverlayVariantHolder);
    }
}
