package com.farcr.nomansland.common.entity;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.entity.mob_variant.BillhookBassVariant;
import com.farcr.nomansland.common.mixinduck.LivingEntityDuck;
import com.farcr.nomansland.common.registry.NMLDataSerializers;
import com.farcr.nomansland.common.registry.NMLEntities;
import com.farcr.nomansland.common.registry.NMLItems;
import com.farcr.nomansland.common.registry.NMLMobVariants;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class BillhookBass extends AbstractFish implements VariantHolder<Holder<BillhookBassVariant>>, NeutralMob {

    private static final EntityDataAccessor<Holder<BillhookBassVariant>> DATA_VARIANT_ID = SynchedEntityData.defineId(BillhookBass.class, NMLDataSerializers.BASS_VARIANT.get());
    private static final String VARIANT_KEY = "variant";
    private static final ResourceKey<BillhookBassVariant> DEFAULT_VARIANT = ResourceKey.create(NMLMobVariants.BILLHOOK_BASS_VARIANT_KEY, ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "default"));

    private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(BillhookBass.class, EntityDataSerializers.INT);

    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    
    @Nullable
    private UUID persistentAngerTarget;
    
    public BillhookBass(EntityType<? extends AbstractFish> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_VARIANT_ID, registryAccess().registryOrThrow(NMLMobVariants.BILLHOOK_BASS_VARIANT_KEY).getHolderOrThrow(DEFAULT_VARIANT));
        builder.define(DATA_REMAINING_ANGER_TIME, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        getVariant().unwrapKey().ifPresent((variant) -> {
            compound.putString(VARIANT_KEY, variant.location().toString());
        });
        addPersistentAngerSaveData(compound);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        Optional.ofNullable(ResourceLocation.tryParse(compound.getString(VARIANT_KEY)))
                .map((string) -> ResourceKey.create(NMLMobVariants.BILLHOOK_BASS_VARIANT_KEY, string))
                .flatMap((variant) -> registryAccess().registryOrThrow(NMLMobVariants.BILLHOOK_BASS_VARIANT_KEY).getHolder(variant))
                .ifPresent(this::setVariant);
        readPersistentAngerSaveData(level(), compound);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        if (spawnType != MobSpawnType.BUCKET) {
            setVariant((Holder<BillhookBassVariant>) NMLMobVariants.getVariantForSpawn((this)));
        }
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.removeAllGoals(goal -> goal instanceof PanicGoal);
        goalSelector.removeAllGoals(goal -> goal instanceof AvoidEntityGoal<?>);
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.3, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal<>(this, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractFish.class, 10, true, true, target -> target.getType() != NMLEntities.BILLHOOK_BASS.get() && distanceTo(target) < 2));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));

    }

    @Override
    protected void customServerAiStep() {
        if (!level().isClientSide) {
            updatePersistentAnger((ServerLevel) level(), false);
            if (getTarget() != null && distanceTo(getTarget()) > 5)
                stopBeingAngry();
        }
        super.customServerAiStep();
    }

    @Override
    public boolean killedEntity(ServerLevel level, LivingEntity entity) {
        if (entity instanceof AbstractFish) ((LivingEntityDuck) entity).noMansLand$skipDroppingDeathLoot();
        if (entity.getType() == EntityType.PUFFERFISH) addEffect(new MobEffectInstance(MobEffects.POISON, 12000));
        return super.killedEntity(level, entity);
    }

    public SoundEvent getAmbientSound() {
        return SoundEvents.SALMON_AMBIENT;
    }

    public SoundEvent getDeathSound() {
        return SoundEvents.SALMON_DEATH;
    }

    public SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.SALMON_HURT;
    }

    public SoundEvent getFlopSound() {
        return SoundEvents.SALMON_FLOP;
    }

    public ItemStack getBucketItemStack() {
        return NMLItems.BILLHOOK_BASS_BUCKET.toStack();
    }

    public void setVariant(Holder<BillhookBassVariant> bassVariantHolder) {
        entityData.set(DATA_VARIANT_ID, bassVariantHolder);
    }

    public Holder<BillhookBassVariant> getVariant() {
        return entityData.get(DATA_VARIANT_ID);
    }

    public EntityType<?> getType() {
        return NMLEntities.BILLHOOK_BASS.get();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8)
                .add(Attributes.ATTACK_DAMAGE, 4);
    }

    public int getRemainingPersistentAngerTime() {
        return entityData.get(DATA_REMAINING_ANGER_TIME);
    }

    public void setRemainingPersistentAngerTime(int time) {
        entityData.set(DATA_REMAINING_ANGER_TIME, time);
    }

    @Nullable
    public UUID getPersistentAngerTarget() {
        return persistentAngerTarget;
    }

    public void setPersistentAngerTarget(@Nullable UUID target) {
        persistentAngerTarget = target;
    }

    public void startPersistentAngerTimer() {
        setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(random));
    }
}
