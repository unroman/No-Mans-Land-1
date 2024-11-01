package com.farcr.nomansland.common.mixin.mob_variants;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.entity.mob_variant.CowVariant;
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
    private static final String VARIANT_KEY = "variant";
    @Unique
    private static final ResourceKey<CowVariant> DEFAULT_VARIANT = ResourceKey.create(NMLMobVariants.COW_VARIANT_KEY, ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "default"));

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(DATA_VARIANT_ID, this.registryAccess().registryOrThrow(NMLMobVariants.COW_VARIANT_KEY).getHolderOrThrow(DEFAULT_VARIANT));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
        this.getVariant().unwrapKey().ifPresent((variant) -> {
            compound.putString(VARIANT_KEY, variant.location().toString());
        });
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
        Optional.ofNullable(ResourceLocation.tryParse(compound.getString(VARIANT_KEY)))
                .map((string) -> ResourceKey.create(NMLMobVariants.COW_VARIANT_KEY, string))
                .flatMap((variant) -> this.registryAccess().registryOrThrow(NMLMobVariants.COW_VARIANT_KEY).getHolder(variant))
                .ifPresent(this::setVariant);
    }

    @Override
    protected void finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, SpawnGroupData spawnGroupData, CallbackInfoReturnable<SpawnGroupData> cir) {
        if (this.getType() == EntityType.MOOSHROOM) {
            Registry<CowVariant> registry = this.registryAccess().registryOrThrow(NMLMobVariants.COW_VARIANT_KEY);
            List<Holder.Reference<CowVariant>> possibleMooshroomVariants = registry.holders()
                    .filter((v) -> noMansLand$isMooshroomVariant(v) && v.value().biomes().isPresent() && v.value().biomes().get().contains(level.getBiome(this.blockPosition())))
                    .toList();
            List<Holder.Reference<CowVariant>> defaultMooshroomVariants = registry.holders()
                    .filter((v) -> noMansLand$isMooshroomVariant(v) && v.value().biomes().isEmpty())
                    .toList();
            this.setVariant(possibleMooshroomVariants.isEmpty() ? defaultMooshroomVariants.get(random.nextInt(defaultMooshroomVariants.size())) : possibleMooshroomVariants.get(random.nextInt(possibleMooshroomVariants.size())));
        } else {
            this.setVariant((Holder<CowVariant>) NMLMobVariants.getVariantForSpawn(((Cow) (Object) this)));
        }
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
    public boolean noMansLand$isMooshroomVariant(Holder<CowVariant> cowVariantHolder) {
        return cowVariantHolder.value().mooshroom().isPresent() && cowVariantHolder.value().mooshroom().get();
    }

    @Override
    public BlockState noMansLand$getMushroomBlock(Holder<CowVariant> mooshroomVariantHolder) {
        return switch(mooshroomVariantHolder.unwrapKey().get().location().toString()) {
            default -> Blocks.AIR.defaultBlockState();
            case "nomansland:red_shroom" -> Blocks.RED_MUSHROOM.defaultBlockState();
            case "nomansland:brown_shroom" -> Blocks.BROWN_MUSHROOM.defaultBlockState();
            case "nomansland:field_shroom" -> NMLBlocks.FIELD_MUSHROOM.get().defaultBlockState();
        };
    }

    @Inject(method = "getBreedOffspring*", at = @At("RETURN"), cancellable = true)
    protected void getBreedOffspring(ServerLevel level, AgeableMob otherParent, CallbackInfoReturnable<AgeableMob> cir) {
        cir.setReturnValue(NMLMobVariants.getOffspringWithVariant(((Cow) (Object) this), otherParent));
    }

    @Inject(method = "getDefaultDimensions", at = @At("HEAD"), cancellable = true)
    private void getDefaultDimensions(Pose pose, CallbackInfoReturnable<EntityDimensions> cir) {
        cir.setReturnValue(((Cow)(Object)this).isBaby() ? EntityType.COW.getDimensions().scale(0.65F).withEyeHeight(0.8F) : EntityType.COW.getDimensions().scale(1.25F, 1.1F).withEyeHeight(3F));
    }

    @Override
    public Holder<CowVariant> noMansLand$getVariant() {
        return this.entityData.get(DATA_VARIANT_ID);
    }

    @Override
    public void noMansLand$setVariant(Holder<CowVariant> mooshroomVariantHolder) {
        this.entityData.set(DATA_VARIANT_ID, mooshroomVariantHolder);
    }
}
