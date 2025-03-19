package com.farcr.nomansland.common.entity.deer;

import com.farcr.nomansland.common.registry.NMLSounds;
import com.farcr.nomansland.common.registry.NMLTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

public class Deer extends Animal {

    private static final String VARIANT_KEY = "Variant";
    private static final String ANTLER_VARIANT_KEY = "AntlersVariant";
    private static final String PATTERN_VARIANT_KEY = "PatternVariant";

    private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(Deer.class, EntityDataSerializers.BYTE);
    private static final int FLAG_DRINKING = 1;

    private static final int FLAG_ANTLERS = 1;

//    private static final EntityDataAccessor<Integer> DATA_HYDRATION = SynchedEntityData.defineId(Deer.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_ANTLERS_LIFETIME = SynchedEntityData.defineId(Deer.class, EntityDataSerializers.INT);
    private int drinkAnimationTick;
    private DeerDrinkWaterGoal drinkGoal;

    public Deer(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_FLAGS_ID, (byte) 0);
//        builder.define(DATA_HYDRATION, 0);
        builder.define(DATA_ANTLERS_LIFETIME, 0);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setFlag(FLAG_ANTLERS, compound.getBoolean("HasAntlers"));
//        entityData.set(DATA_ANTLERS_LIFETIME, compound.getInt("AntlersLifetime"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);

        compound.putBoolean("HasAntlers", getFlag(FLAG_ANTLERS));
        compound.putInt("AntlersLifetime", entityData.get(DATA_ANTLERS_LIFETIME));
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
//        drinkGoal = new DeerDrinkWaterGoal(this);
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(0, new PanicGoal(this, 1.75));
        goalSelector.addGoal(2, new BreedGoal(this, 1.25));
        goalSelector.addGoal(3, new FollowParentGoal(this, 1.5));
//        goalSelector.addGoal(4, new DeerShedAntlersGoal(this));
//        goalSelector.addGoal(4, drinkGoal);
        goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0));
        goalSelector.addGoal(5, new AvoidEntityGoal<>(this, Monster.class, 12, 1.5, 1.75));
        goalSelector.addGoal(5, new AvoidEntityGoal<>(this, Player.class, 12, 1.5, 1.75, player -> !player.isDiscrete() && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(player)));
        goalSelector.addGoal(5, new AvoidEntityGoal<>(this, Villager.class, 12, 1.5, 1.75));
        goalSelector.addGoal(5, new AvoidEntityGoal<>(this, LivingEntity.class, 12, 1.5, 1.75, livingEntity -> livingEntity instanceof  NeutralMob));
        goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 12));
        goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10)
                .add(Attributes.JUMP_STRENGTH, 2)
                .add(Attributes.STEP_HEIGHT, 1 )
                .add(Attributes.MOVEMENT_SPEED, 0.22);
    }

    @Override
    protected void customServerAiStep() {
//        handleHydration(getHydration());
        handleAntlers(getAntlersLifetime());
//        drinkAnimationTick = drinkGoal.getDrinkAnimationTick();

        super.customServerAiStep();
    }

    @Override
    public void aiStep() {
        if (level().isClientSide) drinkAnimationTick = Math.max(0, drinkAnimationTick - 1);
        
        super.aiStep();
    }

    private void handleHydration(int hydration) {
        if (isAlive() && isDrinking()) {
            if (hydration < 0) setHydration(0);
            else setHydration(hydration + 1);
        } else if (hydration > -1) setHydration(hydration - 1);
    }

    private void handleAntlers(int antlersLifeTime) {
        if (!isBaby()) setAntlersLifetime(getAntlersLifetime() + 1);
        if (antlersLifeTime > random.nextInt(70000, 80000) && !hasAntlers()) {
            setHasAntlers(true);
        }
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        if (!isBaby() && random.nextFloat() < 0.8) {
            setHasAntlers(true);
            setAntlersLifetime(random.nextInt(0, 36000));
        }

        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob otherParent) {
        Deer deer = (Deer) getType().create(level());
        Deer otherDeer = (Deer) otherParent;
        deer.setHasAntlers(false);
        deer.setAntlersLifetime(0);
        return deer;
    }

    public float getHeadDrinkPositionScale(float partialTick) {
        if (drinkAnimationTick <= 0) {
            return 0.0F;
        } else if (drinkAnimationTick >= 4 && drinkAnimationTick <= 36) {
            return 1.0F;
        } else {
            return drinkAnimationTick < 4 ? (drinkAnimationTick - partialTick) / 4.0F : -((drinkAnimationTick - 40) - partialTick) / 4.0F;
        }
    }

    public float getHeadDrinkAngleScale(float partialTick) {
        if (drinkAnimationTick > 4 && drinkAnimationTick <= 36) {
            float f = ((drinkAnimationTick - 4) - partialTick) / 32.0F;
            return 0.62831855F + 0.21991149F * Mth.sin(f * 28.7F);
        } else {
            return drinkAnimationTick > 0 ? 0.62831855F : getXRot() * 0.017453292F;
        }
    }
    
    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(NMLTags.DEER_FOOD);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return NMLSounds.DEER_AMBIENT.get();
    }
    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return NMLSounds.DEER_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return NMLSounds.DEER_DEATH.get();
    }

//    protected void playStepSound(BlockPos pos, BlockState block) {
//        this.playSound(NMLSounds.DEER_STEP.get(), 0.15F, 1.0F);
//    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    private void setFlag(int flagId, boolean value) {
        if (value) {
            entityData.set(DATA_FLAGS_ID, (byte) (entityData.get(DATA_FLAGS_ID) | flagId));
        } else {
            entityData.set(DATA_FLAGS_ID, (byte) (entityData.get(DATA_FLAGS_ID) & ~flagId));
        }
    }

    private boolean getFlag(int flagId) {
        return (entityData.get(DATA_FLAGS_ID) & flagId) != 0;
    }

    public void setHasAntlers(boolean hasAntlers) {
        setFlag(FLAG_ANTLERS, hasAntlers);
    }

    public boolean hasAntlers() {
        return getFlag(FLAG_ANTLERS);
    }

    public void setIsDrinking(boolean isDrinking) {
        setFlag(FLAG_DRINKING, isDrinking);
    }

    public boolean isDrinking() {
        return getFlag(FLAG_DRINKING);
    }

    public int getHydration() {
        return 0;
//        return entityData.get(DATA_HYDRATION);
    }

    public void setHydration(int hydration) {
//        entityData.set(DATA_HYDRATION, hydration);
    }

    public int getAntlersLifetime() {
        return entityData.get(DATA_ANTLERS_LIFETIME);
    }

    public void setAntlersLifetime(int antlersLifetime) {
        entityData.set(DATA_ANTLERS_LIFETIME, antlersLifetime);
    }
}
