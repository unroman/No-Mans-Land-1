package com.farcr.nomansland.common.mixin.mob_variants;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.entity.mob_variant.CowVariant;
import com.farcr.nomansland.common.mixinduck.MooshroomDuck;
import com.farcr.nomansland.common.registry.NMLMobVariants;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.EventHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Mixin(MushroomCow.class)
public abstract class MushroomCowMixin extends CowMixin {
    @Shadow @Nullable private UUID lastLightningBoltUUID;

    @Inject(method = "thunderHit", at = @At("HEAD"), cancellable = true)
    private void thunderHit(ServerLevel level, LightningBolt lightning, CallbackInfo ci) {
        UUID uuid = lightning.getUUID();
        if (!uuid.equals(this.lastLightningBoltUUID)) {
            Registry<CowVariant> registry = this.registryAccess().registryOrThrow(NMLMobVariants.COW_VARIANT_KEY);
            List<Holder.Reference<CowVariant>> possibleMooshroomVariants = registry.holders()
                    .filter((v) -> noMansLand$isMooshroomVariant(v) && v.value().biomes().isPresent() && v.value().biomes().get().contains(level.getBiome(this.blockPosition())))
                    .toList();
            List<Holder.Reference<CowVariant>> defaultMooshroomVariants = registry.holders()
                    .filter((v) -> noMansLand$isMooshroomVariant(v) && v.value().biomes().isEmpty())
                    .toList();
            this.noMansLand$setVariant(possibleMooshroomVariants.isEmpty() ? defaultMooshroomVariants.get(random.nextInt(defaultMooshroomVariants.size())) : possibleMooshroomVariants.get(random.nextInt(possibleMooshroomVariants.size())));
            this.lastLightningBoltUUID = uuid;
            this.playSound(SoundEvents.MOOSHROOM_CONVERT, 2.0F, 1.0F);
        }
        ci.cancel();
    }

    @Inject(method = "shear", at = @At("HEAD"), cancellable = true)
    private void shear(SoundSource category, CallbackInfo ci) {
        this.level().playSound(null, (LivingEntity)(Object)this, SoundEvents.MOOSHROOM_SHEAR, category, 1.0F, 1.0F);
        if (!this.level().isClientSide()) {
            if (!EventHooks.canLivingConvert((LivingEntity)(Object)this, EntityType.COW, (timer) -> {
            })) {
                return;
            }

            Cow cow = EntityType.COW.create(this.level());
            if (cow != null) {
                EventHooks.onLivingConvert((LivingEntity)(Object)this, cow);
                ((ServerLevel)this.level()).sendParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY(0.5), this.getZ(), 2, 0.0, 0.0, 0.0, 0.1);
                this.discard();
                cow.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
                cow.setHealth(this.getHealth());
                cow.yBodyRot = this.yBodyRot;
                if (this.hasCustomName()) {
                    cow.setCustomName(this.getCustomName());
                    cow.setCustomNameVisible(this.isCustomNameVisible());
                }

                Registry<CowVariant> registry = this.registryAccess().registryOrThrow(NMLMobVariants.COW_VARIANT_KEY);
                Stream<Holder.Reference<CowVariant>> defaultVariants = registry.holders()
                        .filter((v) -> !noMansLand$isMooshroomVariant(v));

                switch (this.noMansLand$getVariant().unwrapKey().get().location().toString()) {
                    case "nomansland:mycelial_shroom" -> ((VariantHolder<Holder<CowVariant>>)cow).setVariant(defaultVariants.filter(v -> v.is(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID,"highland"))).findFirst().get());
                    case "nomansland:field_shroom" -> ((VariantHolder<Holder<CowVariant>>)cow).setVariant(defaultVariants.filter(v -> v.is(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID,"base"))).findFirst().get());
                    case "nomansland:red_shroom" -> ((VariantHolder<Holder<CowVariant>>)cow).setVariant(defaultVariants.filter(v -> v.is(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID,"calico_splotches"))).findFirst().get());
                    case "nomansland:brown_shroom" ->  ((VariantHolder<Holder<CowVariant>>)cow).setVariant(defaultVariants.filter(v -> v.is(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID,"brown_splotches"))).findFirst().get());
                }

                if (this.isPersistenceRequired()) {
                    cow.setPersistenceRequired();
                }

                cow.setInvulnerable(this.isInvulnerable());
                this.level().addFreshEntity(cow);

                for(int i = 0; i < random.nextInt(3, 7); ++i) {
                    ItemEntity item = this.spawnAtLocation(new ItemStack(this.noMansLand$getMushroomBlock(this.noMansLand$getVariant()).getBlock()), this.getBbHeight());
                    if (item != null) {
                        item.setNoPickUpDelay();
                    }
                }
            }
        }
        ci.cancel();
    }

    @Inject(method = "getBreedOffspring*", at = @At("RETURN"), cancellable = true)
    protected void getBreedOffspring(ServerLevel level, AgeableMob otherParent, CallbackInfoReturnable<AgeableMob> cir) {
        MushroomCow mooshroom = EntityType.MOOSHROOM.create(level);
        if (mooshroom != null) {
            ((MooshroomDuck)mooshroom).noMansLand$setVariant(this.random.nextBoolean() ?  this.noMansLand$getVariant() : ((MooshroomDuck)otherParent).noMansLand$getVariant());
        }
        cir.setReturnValue(mooshroom);
    }
}
