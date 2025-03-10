package com.farcr.nomansland.common.mixin.mob_variants;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.entity.mob_variant.ChickenVariant;
import com.farcr.nomansland.common.entity.mob_variant.group.VariantGroupData;
import com.farcr.nomansland.common.mixin.MobMixin;
import com.farcr.nomansland.common.registry.entities.NMLDataSerializers;
import com.farcr.nomansland.common.registry.entities.NMLMobVariants;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Chicken.class)
public abstract class ChickenMixin extends MobMixin implements VariantHolder<Holder<ChickenVariant>> {
    @Unique
    private static final EntityDataAccessor<Holder<ChickenVariant>> DATA_VARIANT_ID = SynchedEntityData.defineId(Chicken.class, NMLDataSerializers.CHICKEN_VARIANT.get());
    @Unique
    private static final String VARIANT_KEY = "Variant";
    @Unique
    private static final ResourceKey<ChickenVariant> DEFAULT_VARIANT = ResourceKey.create(NMLMobVariants.CHICKEN_VARIANT_KEY, ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "default"));

    @Override
    protected void defineVariantData(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(DATA_VARIANT_ID, this.registryAccess().registryOrThrow(NMLMobVariants.CHICKEN_VARIANT_KEY).getHolderOrThrow(DEFAULT_VARIANT));
    }

    @Override
    protected void addVariantData(CompoundTag compound, CallbackInfo ci) {
        this.getVariant().unwrapKey().ifPresent((variant) -> {
            compound.putString(VARIANT_KEY, variant.location().toString());
        });
    }

    @Override
    protected void readVariantData(CompoundTag compound, CallbackInfo ci) {
        Optional.ofNullable(ResourceLocation.tryParse(compound.getString(VARIANT_KEY)))
                .map((string) -> ResourceKey.create(NMLMobVariants.CHICKEN_VARIANT_KEY, string))
                .flatMap((variant) -> this.registryAccess().registryOrThrow(NMLMobVariants.CHICKEN_VARIANT_KEY).getHolder(variant))
                .ifPresent(this::setVariant);
    }

    @Override
    protected void finalizeSpawnVariant(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, SpawnGroupData spawnGroupData, CallbackInfoReturnable<SpawnGroupData> cir) {
        if (this.getType() == EntityType.CHICKEN) {
            Holder<ChickenVariant> variant;
            if (spawnGroupData instanceof VariantGroupData variantGroupData) {
                variant = (Holder<ChickenVariant>) variantGroupData.variant;
            } else {
                variant = (Holder<ChickenVariant>) NMLMobVariants.getVariantForSpawn(((Chicken) (Object) this));
                spawnGroupData = new VariantGroupData(variant);
            }

            this.setVariant(variant);
        }
    }

    @Override
    public Holder<ChickenVariant> getVariant() {
        return this.entityData.get(DATA_VARIANT_ID);
    }

    @Override
    public void setVariant(Holder<ChickenVariant> variantHolder) {
        this.entityData.set(DATA_VARIANT_ID, variantHolder);
    }

    @Inject(method = "getBreedOffspring*", at = @At("RETURN"), cancellable = true)
    private void getOffspringVariant(ServerLevel level, AgeableMob otherParent, CallbackInfoReturnable<AgeableMob> cir) {
        AgeableMob entity = (AgeableMob) getType().create(level());

        if (entity.getType() == EntityType.CHICKEN) {
            ((VariantHolder<Holder<ChickenVariant>>) entity).setVariant((Holder<ChickenVariant>) NMLMobVariants.getOffspringWithVariant(((Chicken) (Object) this), otherParent));

            cir.setReturnValue(entity);
        }
    }
}
