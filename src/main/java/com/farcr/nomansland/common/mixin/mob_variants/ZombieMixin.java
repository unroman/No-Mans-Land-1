package com.farcr.nomansland.common.mixin.mob_variants;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.entity.mob_variant.DrownedVariant;
import com.farcr.nomansland.common.entity.mob_variant.HuskVariant;
import com.farcr.nomansland.common.entity.mob_variant.ZombieVariant;
import com.farcr.nomansland.common.entity.mob_variant.group.VariantGroupData;
import com.farcr.nomansland.common.mixin.MobMixin;
import com.farcr.nomansland.common.mixinduck.DrownedDuck;
import com.farcr.nomansland.common.mixinduck.HuskDuck;
import com.farcr.nomansland.common.registry.entities.NMLDataSerializers;
import com.farcr.nomansland.common.registry.entities.NMLMobVariants;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Zombie.class)
public abstract class ZombieMixin extends MobMixin implements VariantHolder<Holder<ZombieVariant>>, HuskDuck, DrownedDuck {

    @Unique
    private static final EntityDataAccessor<Holder<ZombieVariant>> DATA_VARIANT_ID = SynchedEntityData.defineId(Zombie.class, NMLDataSerializers.ZOMBIE_VARIANT.get());
    @Unique
    private static final EntityDataAccessor<Holder<HuskVariant>> DATA_HUSK_VARIANT_ID = SynchedEntityData.defineId(Zombie.class, NMLDataSerializers.HUSK_VARIANT.get());
    @Unique
    private static final EntityDataAccessor<Holder<DrownedVariant>> DATA_DROWNED_VARIANT_ID = SynchedEntityData.defineId(Zombie.class, NMLDataSerializers.DROWNED_VARIANT.get());

    @Unique
    private static final String VARIANT_KEY = "Variant";
    @Unique
    private static final String HUSK_VARIANT_KEY = "HuskVariant";
    @Unique
    private static final String DROWNED_VARIANT_KEY = "DrownedVariant";

    @Unique
    private static final ResourceKey<ZombieVariant> DEFAULT_VARIANT = ResourceKey.create(NMLMobVariants.ZOMBIE_VARIANT_KEY, ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "default"));
    @Unique
    private static final ResourceKey<HuskVariant> DEFAULT_HUSK_VARIANT = ResourceKey.create(NMLMobVariants.HUSK_VARIANT_KEY, ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "default"));
    @Unique
    private static final ResourceKey<DrownedVariant> DEFAULT_DROWNED_VARIANT = ResourceKey.create(NMLMobVariants.DROWNED_VARIANT_KEY, ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "default"));

    @Override
    protected void nml$defineSynchedData(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(DATA_VARIANT_ID, this.registryAccess().registryOrThrow(NMLMobVariants.ZOMBIE_VARIANT_KEY).getHolderOrThrow(DEFAULT_VARIANT));
        builder.define(DATA_HUSK_VARIANT_ID, this.registryAccess().registryOrThrow(NMLMobVariants.HUSK_VARIANT_KEY).getHolderOrThrow(DEFAULT_HUSK_VARIANT));
        builder.define(DATA_DROWNED_VARIANT_ID, this.registryAccess().registryOrThrow(NMLMobVariants.DROWNED_VARIANT_KEY).getHolderOrThrow(DEFAULT_DROWNED_VARIANT));

    }

    @Override
    protected void nml$addAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
        this.getVariant().unwrapKey().ifPresent((variant) -> compound.putString(VARIANT_KEY, variant.location().toString()));
        this.noMansLand$getHuskVariant().unwrapKey().ifPresent((variant) -> compound.putString(HUSK_VARIANT_KEY, variant.location().toString()));
        this.noMansLand$getDrownedVariant().unwrapKey().ifPresent((variant) -> compound.putString(DROWNED_VARIANT_KEY, variant.location().toString()));
    }

    @Override
    protected void nml$readAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
        Optional.ofNullable(ResourceLocation.tryParse(compound.getString(VARIANT_KEY)))
                .map((string) -> ResourceKey.create(NMLMobVariants.ZOMBIE_VARIANT_KEY, string))
                .flatMap((variant) -> this.registryAccess().registryOrThrow(NMLMobVariants.ZOMBIE_VARIANT_KEY).getHolder(variant))
                .ifPresent(this::setVariant);
        Optional.ofNullable(ResourceLocation.tryParse(compound.getString(HUSK_VARIANT_KEY)))
                .map((string) -> ResourceKey.create(NMLMobVariants.HUSK_VARIANT_KEY, string))
                .flatMap((variant) -> this.registryAccess().registryOrThrow(NMLMobVariants.HUSK_VARIANT_KEY).getHolder(variant))
                .ifPresent(this::noMansLand$setHuskVariant);
        Optional.ofNullable(ResourceLocation.tryParse(compound.getString(DROWNED_VARIANT_KEY)))
                .map((string) -> ResourceKey.create(NMLMobVariants.DROWNED_VARIANT_KEY, string))
                .flatMap((variant) -> this.registryAccess().registryOrThrow(NMLMobVariants.DROWNED_VARIANT_KEY).getHolder(variant))
                .ifPresent(this::noMansLand$setDrownedVariant);
    }

    @Override
    protected void nml$finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, SpawnGroupData spawnGroupData, CallbackInfoReturnable<SpawnGroupData> cir) {
        if (this.getType() == EntityType.HUSK) {
            Holder<HuskVariant> variant;
            if (spawnGroupData instanceof VariantGroupData variantGroupData) {
                variant = (Holder<HuskVariant>) variantGroupData.variant;
            } else {
                variant = (Holder<HuskVariant>) NMLMobVariants.getVariantForSpawn(((Husk) (Object) this));
                spawnGroupData = new VariantGroupData(variant);
            }

            this.noMansLand$setHuskVariant(variant);
        } else if (this.getType() == EntityType.DROWNED) {
            Holder<DrownedVariant> variant;
            if (spawnGroupData instanceof VariantGroupData variantGroupData) {
                variant = (Holder<DrownedVariant>) variantGroupData.variant;
            } else {
                variant = (Holder<DrownedVariant>) NMLMobVariants.getVariantForSpawn(((Drowned) (Object) this));
                spawnGroupData = new VariantGroupData(variant);
            }

            this.noMansLand$setDrownedVariant(variant);
        } else if (this.getType() == EntityType.ZOMBIE) {
            Holder<ZombieVariant> variant;
            if (spawnGroupData instanceof VariantGroupData variantGroupData) {
                variant = (Holder<ZombieVariant>) variantGroupData.variant;
            } else {
                variant = (Holder<ZombieVariant>) NMLMobVariants.getVariantForSpawn(((Zombie) (Object) this));
                spawnGroupData = new VariantGroupData(variant);
            }

            this.setVariant(variant);
        }
    }

    @Override
    public Holder<ZombieVariant> getVariant() {
        return this.entityData.get(DATA_VARIANT_ID);
    }

    @Override
    public void setVariant(Holder<ZombieVariant> variantHolder) {
        this.entityData.set(DATA_VARIANT_ID, variantHolder);
    }

    @Override
    public Holder<HuskVariant> noMansLand$getHuskVariant() {
        return this.entityData.get(DATA_HUSK_VARIANT_ID);
    }

    @Override
    public void noMansLand$setHuskVariant(Holder<HuskVariant> huskVariantHolder) {
        this.entityData.set(DATA_HUSK_VARIANT_ID, huskVariantHolder);
    }

    @Override
    public Holder<DrownedVariant> noMansLand$getDrownedVariant() {
        return this.entityData.get(DATA_DROWNED_VARIANT_ID);
    }

    @Override
    public void noMansLand$setDrownedVariant(Holder<DrownedVariant> drownedVariantHolder) {
        this.entityData.set(DATA_DROWNED_VARIANT_ID, drownedVariantHolder);
    }
}
