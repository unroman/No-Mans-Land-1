package com.farcr.nomansland.common.mixin.mob_variants;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.entity.mob_variant.RabbitVariant;
import com.farcr.nomansland.common.entity.mob_variant.group.VariantGroupData;
import com.farcr.nomansland.common.mixin.MobMixin;
import com.farcr.nomansland.common.mixinduck.RabbitDuck;
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
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
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
    protected void defineVariantData(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(DATA_VARIANT_ID, this.registryAccess().registryOrThrow(NMLMobVariants.RABBIT_VARIANT_KEY).getHolderOrThrow(DEFAULT_VARIANT));
    }

    @Override
    protected void addVariantData(CompoundTag compound, CallbackInfo ci) {
        this.nml$getRabbitVariant().unwrapKey().ifPresent((variant) -> {
            compound.putString(VARIANT_KEY, variant.location().toString());
        });
    }

    @Override
    protected void readVariantData(CompoundTag compound, CallbackInfo ci) {
        Optional.ofNullable(ResourceLocation.tryParse(compound.getString(VARIANT_KEY)))
                .map((string) -> ResourceKey.create(NMLMobVariants.RABBIT_VARIANT_KEY, string))
                .flatMap((variant) -> this.registryAccess().registryOrThrow(NMLMobVariants.RABBIT_VARIANT_KEY).getHolder(variant))
                .ifPresent(this::nml$setRabbitVariant);
    }

    @Override
    protected void finalizeSpawnVariant(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, SpawnGroupData spawnGroupData, CallbackInfoReturnable<SpawnGroupData> cir) {
        if (this.getType() == EntityType.RABBIT) {
            Holder<RabbitVariant> variant;
            if (spawnGroupData instanceof VariantGroupData variantGroupData) {
                variant = (Holder<RabbitVariant>) variantGroupData.variant;
            } else {
                variant = (Holder<RabbitVariant>) NMLMobVariants.getVariantForSpawn(((Rabbit) (Object) this));
                spawnGroupData = new VariantGroupData(variant);
            }

            this.nml$setRabbitVariant(variant);
        }
    }

    @Override
    public Holder<RabbitVariant> nml$getRabbitVariant() {
        return this.entityData.get(DATA_VARIANT_ID);
    }

    @Override
    public void nml$setRabbitVariant(Holder<RabbitVariant> rabbitVariantHolder) {
        this.entityData.set(DATA_VARIANT_ID, rabbitVariantHolder);
    }

    @Inject(method = "getBreedOffspring*", at = @At("RETURN"), cancellable = true)
    private void getOffspringVariant(ServerLevel level, AgeableMob otherParent, CallbackInfoReturnable<AgeableMob> cir) {
        AgeableMob entity = (AgeableMob) getType().create(level());

        if (entity.getType() == EntityType.RABBIT) {
            ((RabbitDuck) entity).nml$setRabbitVariant(
                    random.nextBoolean() ?
                            ((RabbitDuck) this).nml$getRabbitVariant() :
                            ((RabbitDuck) otherParent).nml$getRabbitVariant());

            cir.setReturnValue(entity);
        }
    }
}
