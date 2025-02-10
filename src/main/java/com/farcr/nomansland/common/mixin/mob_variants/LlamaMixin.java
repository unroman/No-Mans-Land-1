package com.farcr.nomansland.common.mixin.mob_variants;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.entity.mob_variant.LlamaVariant;
import com.farcr.nomansland.common.entity.mob_variant.group.VariantGroupData;
import com.farcr.nomansland.common.mixin.MobMixin;
import com.farcr.nomansland.common.mixinduck.LlamaDuck;
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
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Llama.class)
public abstract class LlamaMixin extends MobMixin implements LlamaDuck {
    @Shadow public abstract boolean isTraderLlama();

    @Unique
    private static final EntityDataAccessor<Holder<LlamaVariant>> DATA_VARIANT_ID = SynchedEntityData.defineId(Llama.class, NMLDataSerializers.LLAMA_VARIANT.get());
    @Unique
    private static final String VARIANT_KEY = "Variant";
    @Unique
    private static final ResourceKey<LlamaVariant> DEFAULT_VARIANT = ResourceKey.create(NMLMobVariants.LLAMA_VARIANT_KEY, ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "default"));

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(DATA_VARIANT_ID, this.registryAccess().registryOrThrow(NMLMobVariants.LLAMA_VARIANT_KEY).getHolderOrThrow(DEFAULT_VARIANT));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
        this.noMansLand$getLlamaVariant().unwrapKey().ifPresent((variant) -> {
            compound.putString(VARIANT_KEY, variant.location().toString());
        });
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
        Optional.ofNullable(ResourceLocation.tryParse(compound.getString(VARIANT_KEY)))
                .map((string) -> ResourceKey.create(NMLMobVariants.LLAMA_VARIANT_KEY, string))
                .flatMap((variant) -> this.registryAccess().registryOrThrow(NMLMobVariants.LLAMA_VARIANT_KEY).getHolder(variant))
                .ifPresent(this::noMansLand$setLlamaVariant);
    }

    @Override
    protected void finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, SpawnGroupData spawnGroupData, CallbackInfoReturnable<SpawnGroupData> cir) {
        Holder<LlamaVariant> llama$variant;
        if (spawnGroupData instanceof VariantGroupData variantGroupData) {
            llama$variant = (Holder<LlamaVariant>) variantGroupData.variant;
        } else {
            llama$variant = (Holder<LlamaVariant>) NMLMobVariants.getVariantForSpawn(((Llama) (Object) this), NMLMobVariants.getVariantKey("llama"));
            spawnGroupData = new VariantGroupData(llama$variant);
        }

        this.noMansLand$setLlamaVariant(llama$variant);
    }

    @Inject(method = "getBreedOffspring*", at = @At("RETURN"), cancellable = true)
    private void getBreedOffspring(ServerLevel level, AgeableMob otherParent, CallbackInfoReturnable<AgeableMob> cir) {
        AgeableMob entity = (AgeableMob) this.getType().create(this.level());
        ((LlamaDuck) entity).noMansLand$setLlamaVariant((Holder<LlamaVariant>) NMLMobVariants.getOffspringWithVariant(((Llama) (Object) this), otherParent));
        cir.setReturnValue(entity);
    }

    @Override
    public Holder<LlamaVariant> noMansLand$getLlamaVariant() {
        return this.entityData.get(DATA_VARIANT_ID);
    }

    @Override
    public void noMansLand$setLlamaVariant(Holder<LlamaVariant> llamaVariantHolder) {
        this.entityData.set(DATA_VARIANT_ID, llamaVariantHolder);
    }
}
