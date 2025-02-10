package com.farcr.nomansland.common.mixin.mob_variants;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.entity.mob_variant.FoxVariant;
import com.farcr.nomansland.common.entity.mob_variant.RabbitVariant;
import com.farcr.nomansland.common.entity.mob_variant.RabbitVariant;
import com.farcr.nomansland.common.entity.mob_variant.group.VariantGroupData;
import com.farcr.nomansland.common.mixin.MobMixin;
import com.farcr.nomansland.common.mixinduck.FoxDuck;
import com.farcr.nomansland.common.mixinduck.RabbitDuck;
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
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Rabbit.class)
public abstract class RabbitMixin extends MobMixin implements RabbitDuck {
    @Unique
    private static final EntityDataAccessor<Holder<RabbitVariant>> DATA_VARIANT_ID = SynchedEntityData.defineId(Rabbit.class, NMLDataSerializers.RABBIT_VARIANT.get());
    @Unique
    private static final String VARIANT_KEY = "Variant";
    @Unique
    private static final ResourceKey<RabbitVariant> DEFAULT_VARIANT = ResourceKey.create(NMLMobVariants.RABBIT_VARIANT_KEY, ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "default"));

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(DATA_VARIANT_ID, this.registryAccess().registryOrThrow(NMLMobVariants.RABBIT_VARIANT_KEY).getHolderOrThrow(DEFAULT_VARIANT));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
        this.noMansLand$getRabbitVariant().unwrapKey().ifPresent((variant) -> {
            compound.putString(VARIANT_KEY, variant.location().toString());
        });
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
        Optional.ofNullable(ResourceLocation.tryParse(compound.getString(VARIANT_KEY)))
                .map((string) -> ResourceKey.create(NMLMobVariants.RABBIT_VARIANT_KEY, string))
                .flatMap((variant) -> this.registryAccess().registryOrThrow(NMLMobVariants.RABBIT_VARIANT_KEY).getHolder(variant))
                .ifPresent(this::noMansLand$setRabbitVariant);
    }

    @Override
    protected void finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, SpawnGroupData spawnGroupData, CallbackInfoReturnable<SpawnGroupData> cir) {
        Holder<RabbitVariant> variant;
        if (spawnGroupData instanceof VariantGroupData variantGroupData) {
            variant = (Holder<RabbitVariant>) variantGroupData.variant;
        } else {
            variant = (Holder<RabbitVariant>) NMLMobVariants.getVariantForSpawn(((Rabbit) (Object) this));
            spawnGroupData = new VariantGroupData(variant);
        }

        this.noMansLand$setRabbitVariant(variant);
    }

    @Override
    public Holder<RabbitVariant> noMansLand$getRabbitVariant() {
        return this.entityData.get(DATA_VARIANT_ID);
    }

    @Override
    public void noMansLand$setRabbitVariant(Holder<RabbitVariant> rabbitVariantHolder) {
        this.entityData.set(DATA_VARIANT_ID, rabbitVariantHolder);
    }

    @Inject(method = "getBreedOffspring*", at = @At("RETURN"), cancellable = true)
    private void getBreedOffspring(ServerLevel level, AgeableMob otherParent, CallbackInfoReturnable<AgeableMob> cir) {
        AgeableMob entity = (AgeableMob) this.getType().create(this.level());
        ((RabbitDuck) entity).noMansLand$setRabbitVariant(
                random.nextBoolean() ?
                        ((RabbitDuck) this).noMansLand$getRabbitVariant() :
                        ((RabbitDuck) otherParent).noMansLand$getRabbitVariant());
        cir.setReturnValue(entity);
    }
}
