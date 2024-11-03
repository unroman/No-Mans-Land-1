package com.farcr.nomansland.common.mixin.mob_variants;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.entity.mob_variant.CowVariant;
import com.farcr.nomansland.common.entity.mob_variant.MooshroomVariant;
import com.farcr.nomansland.common.mixin.MobMixin;
import com.farcr.nomansland.common.mixinduck.MooshroomDuck;
import com.farcr.nomansland.common.registry.NMLBlocks;
import com.farcr.nomansland.common.registry.NMLDataSerializers;
import com.farcr.nomansland.common.registry.NMLMobVariants;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;

@Mixin(Cow.class)
public abstract class CowMixin extends MobMixin implements VariantHolder<Holder<CowVariant>>, MooshroomDuck {
    @Unique
    private static final EntityDataAccessor<Holder<CowVariant>> DATA_VARIANT_ID = SynchedEntityData.defineId(Cow.class, NMLDataSerializers.COW_VARIANT.get());
    @Unique
    private static final EntityDataAccessor<Holder<MooshroomVariant>> DATA_MOOSHROOM_VARIANT_ID = SynchedEntityData.defineId(Cow.class, NMLDataSerializers.MOOSHROOM_VARIANT.get());
    @Unique
    private static final String VARIANT_KEY = "variant";
    @Unique
    private static final String MOOSHROOM_VARIANT_KEY = "variant";
    @Unique
    private static final ResourceKey<CowVariant> DEFAULT_VARIANT = ResourceKey.create(NMLMobVariants.COW_VARIANT_KEY, ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "default"));
    @Unique
    private static final ResourceKey<MooshroomVariant> DEFAULT_MOOSHROOM_VARIANT = ResourceKey.create(NMLMobVariants.MOOSHROOM_VARIANT_KEY, ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "default"));
    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(DATA_VARIANT_ID, this.registryAccess().registryOrThrow(NMLMobVariants.COW_VARIANT_KEY).getHolderOrThrow(DEFAULT_VARIANT));
        builder.define(DATA_MOOSHROOM_VARIANT_ID, this.registryAccess().registryOrThrow(NMLMobVariants.MOOSHROOM_VARIANT_KEY).getHolderOrThrow(DEFAULT_MOOSHROOM_VARIANT));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
        this.getVariant().unwrapKey().ifPresent((variant) -> {
            compound.putString(VARIANT_KEY, variant.location().toString());
        });
        this.noMansLand$getMooshroomVariant().unwrapKey().ifPresent((variant) -> {
            compound.putString(MOOSHROOM_VARIANT_KEY, variant.location().toString());
        });
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
        Optional.ofNullable(ResourceLocation.tryParse(compound.getString(VARIANT_KEY)))
                .map((string) -> ResourceKey.create(NMLMobVariants.COW_VARIANT_KEY, string))
                .flatMap((variant) -> this.registryAccess().registryOrThrow(NMLMobVariants.COW_VARIANT_KEY).getHolder(variant))
                .ifPresent(this::setVariant);
        Optional.ofNullable(ResourceLocation.tryParse(compound.getString(MOOSHROOM_VARIANT_KEY)))
                .map((string) -> ResourceKey.create(NMLMobVariants.MOOSHROOM_VARIANT_KEY, string))
                .flatMap((variant) -> this.registryAccess().registryOrThrow(NMLMobVariants.MOOSHROOM_VARIANT_KEY).getHolder(variant))
                .ifPresent(this::noMansLand$setMooshroomVariant);
    }

    @Override
    protected void finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, SpawnGroupData spawnGroupData, CallbackInfoReturnable<SpawnGroupData> cir) {
        if (this.getType() == EntityType.MOOSHROOM)
            this.noMansLand$setMooshroomVariant((Holder<MooshroomVariant>) NMLMobVariants.getVariantForSpawn(((MushroomCow) (Object) this)));
        else
            this.setVariant((Holder<CowVariant>) NMLMobVariants.getVariantForSpawn(((Cow) (Object) this)));
    }

    @Override
    public Holder<CowVariant> getVariant() {
        return this.entityData.get(DATA_VARIANT_ID);
    }

    @Override
    public void setVariant(Holder<CowVariant> variantHolder) {
        this.entityData.set(DATA_VARIANT_ID, variantHolder);
    }

    @Override
    public BlockState noMansLand$getMushroomBlock(Holder<MooshroomVariant> mooshroomVariantHolder) {
        return switch(mooshroomVariantHolder.unwrapKey().get().location().toString()) {
            default -> Blocks.AIR.defaultBlockState();
            case "nomansland:red_shroom" -> Blocks.RED_MUSHROOM.defaultBlockState();
            case "nomansland:brown_shroom" -> Blocks.BROWN_MUSHROOM.defaultBlockState();
            case "nomansland:field_shroom" -> NMLBlocks.FIELD_MUSHROOM.get().defaultBlockState();
        };
    }

    @Inject(method = "getBreedOffspring*", at = @At("RETURN"), cancellable = true)
    protected void getBreedOffspring(ServerLevel level, AgeableMob otherParent, CallbackInfoReturnable<AgeableMob> cir) {
        AgeableMob entity = (AgeableMob) this.getType().create(this.level());
        if (entity.getType() == EntityType.COW)
            ((VariantHolder<Holder<CowVariant>>) entity).setVariant((Holder<CowVariant>) NMLMobVariants.getOffspringWithVariant(((Cow) (Object) this), otherParent));
        else ((MooshroomDuck) entity).noMansLand$setMooshroomVariant((Holder<MooshroomVariant>) NMLMobVariants.getOffspringWithVariant(((MushroomCow) (Object) this), otherParent));
        cir.setReturnValue(entity);
    }

    @Inject(method = "getDefaultDimensions", at = @At("HEAD"), cancellable = true)
    private void getDefaultDimensions(Pose pose, CallbackInfoReturnable<EntityDimensions> cir) {
        cir.setReturnValue(((AgeableMob) (Object) this).isBaby() ? EntityType.COW.getDimensions().scale(0.65F).withEyeHeight(0.8F) : EntityType.COW.getDimensions().scale(1.25F, 1.1F).withEyeHeight(3F));
    }

    @Override
    public Holder<MooshroomVariant> noMansLand$getMooshroomVariant() {
        return this.entityData.get(DATA_MOOSHROOM_VARIANT_ID);
    }

    @Override
    public void noMansLand$setMooshroomVariant(Holder<MooshroomVariant> mooshroomVariantHolder) {
        this.entityData.set(DATA_MOOSHROOM_VARIANT_ID, mooshroomVariantHolder);
    }
}
