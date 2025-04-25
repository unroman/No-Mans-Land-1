package com.farcr.nomansland.common.entity;

import com.farcr.nomansland.common.registry.NMLTags;
import com.farcr.nomansland.common.registry.entities.NMLEntities;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.UUID;

public class Moose extends Animal implements NeutralMob {
    private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(Moose.class, EntityDataSerializers.INT);
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(2*20*60, 2*20*60); // figure out how to make this dynamically take day length?
    private static final EntityDataAccessor<Integer> DATA_PACIFICATION_STAGE = SynchedEntityData.defineId(Moose.class, EntityDataSerializers.INT); // number of carrots fed, or 5 if pacified
    @Nullable
    private UUID persistentAngerTarget;

    public Moose(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 40D)
                .add(Attributes.FOLLOW_RANGE, 20D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ARMOR_TOUGHNESS, 0.1f)
                .add(Attributes.ATTACK_KNOCKBACK, 0.5f)
                .add(Attributes.ATTACK_DAMAGE, 2f);
    }

    @Override
    protected void sendDebugPackets() {
        super.sendDebugPackets();
        DebugPackets.sendEntityBrain(this);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_REMAINING_ANGER_TIME, 0);
        builder.define(DATA_PACIFICATION_STAGE, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        this.addPersistentAngerSaveData(compound);
        compound.putInt("PacificationStage", getPacificationStage());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.readPersistentAngerSaveData(this.level(), compound);
        if (compound.contains("PacificationStage")) {
            setPacificationStage(compound.getInt("PacificationStage"));
        }
    }

    @Override
    protected Brain.Provider<Moose> brainProvider() {
        return MooseAI.brainProvider();
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> dynamic) {
        return MooseAI.makeBrain(this.brainProvider().makeBrain(dynamic));
    }

    @Override
    protected void customServerAiStep() {
        this.level().getProfiler().push("mooseBrain");
        ((Brain<Moose>) this.brain).tick((ServerLevel) this.level(), this);
        this.level().getProfiler().pop();
        this.level().getProfiler().push("mooseActivityUpdate");
        MooseAI.updateActivity(this);
        this.level().getProfiler().pop();

        super.customServerAiStep();
    }

    /*@Override
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide) {
            this.updatePersistentAnger((ServerLevel) this.level(), true);
        }
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));

        this.goalSelector.addGoal(1, new HurtByTargetGoal(this));

        this.goalSelector.addGoal(2, new NearestAttackableTargetGoal<>(this,  Player.class, 10, true, false, this::isAngryAt));
        this.goalSelector.addGoal(3, new MooseChargeAttackGoal(this, 1.0, 1.5, 16, 4));

        this.goalSelector.addGoal(4, new BreedGoal(this, 1.15D));
        this.goalSelector.addGoal(5, new TemptGoal(this, 1.2D, Ingredient.of(NMLTags.MOOSE_FOOD), false));

        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1.1D));


        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.1D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 3f));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new ResetUniversalAngerTargetGoal<>(this, false));

    }*/

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        Moose baby = NMLEntities.MOOSE.get().create(pLevel);
        if (baby != null && isPacified() && ((Moose) pOtherParent).isPacified()) {
            baby.setPacificationStage(5);
        }
        return baby;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();
        if (!this.level().isClientSide || this.isBaby() && this.isFood(itemstack)) {
            if (itemstack.is(Items.GOLDEN_CARROT) && getPacificationStage() < 5)  {
                int pacificationStage = getPacificationStage();
                itemstack.consume(1, player);
                if (pacificationStage < 4) {
                    setPacificationStage(pacificationStage + 1);
                    this.level().broadcastEntityEvent(this, (byte) 6);
                } else if (random.nextInt(3) == 0) {
                    setPacificationStage(5);
                    this.navigation.stop();
                    this.setTarget(null);
                    this.setPersistentAngerTarget(null);
                    this.level().broadcastEntityEvent(this, (byte) 7);
                } else {
                    this.level().broadcastEntityEvent(this, (byte) 6);
                }
                return InteractionResult.SUCCESS;
            } else {
                return super.mobInteract(player, hand);
            }
        } else {
            boolean flag = itemstack.is(Items.GOLDEN_CARROT) && getPacificationStage() < 5;
            return flag ? InteractionResult.CONSUME : InteractionResult.PASS;
        }
    }

    protected void spawnTamingParticles(boolean tamed) {
        ParticleOptions particleoptions = ParticleTypes.HEART;
        if (!tamed) {
            particleoptions = ParticleTypes.SMOKE;
        }

        for(int i = 0; i < 7; ++i) {
            double d0 = this.random.nextGaussian() * 0.02;
            double d1 = this.random.nextGaussian() * 0.02;
            double d2 = this.random.nextGaussian() * 0.02;
            this.level().addParticle(particleoptions, this.getRandomX((double)1.0F), this.getRandomY() + (double)0.5F, this.getRandomZ((double)1.0F), d0, d1, d2);
        }

    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 7) {
            this.spawnTamingParticles(true);
        } else if (id == 6) {
            this.spawnTamingParticles(false);
        } else {
            super.handleEntityEvent(id);
        }
    }

    public int getPacificationStage() {
        return this.entityData.get(DATA_PACIFICATION_STAGE);
    }

    public void setPacificationStage(int stage) {
        this.entityData.set(DATA_PACIFICATION_STAGE, stage);
    }

    public boolean isPacified() {
        return getPacificationStage() == 5;
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(NMLTags.MOOSE_FOOD);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.HOGLIN_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.RAVAGER_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.DOLPHIN_DEATH;
    }

    @Override
    public float getAgeScale() {
        return this.isBaby() ? 0.75f : 1.0f;
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.entityData.get(DATA_REMAINING_ANGER_TIME);
    }

    @Override
    public void setRemainingPersistentAngerTime(int time) {
        this.entityData.set(DATA_REMAINING_ANGER_TIME, time);
    }

    @Override
    public @Nullable UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID target) {
        this.persistentAngerTarget = target;
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    @Override
    public @Nullable LivingEntity getTarget() {
        if (isPacified()) return null;
        return this.getTargetFromBrain();
    }

    public class MooseChargeAttackGoal extends Goal {
        protected final PathfinderMob mob;
        private final double speedModifierFlee;
        private final double speedModifierCharge;
        private final int fleeDistance;
        private final int extraChargeDistance;
        private Path path;
        private Vec3 pathNormalized;
        private long lastCanUseCheck;
        private ChargeState chargeState;
        private enum ChargeState {
            FLEE,
            CHARGE,
            POSTCHARGE
        }

        public MooseChargeAttackGoal(PathfinderMob mob, double speedModifierFlee, double speedModifierCharge, int fleeDistance, int extraChargeDistance) {
            this.mob = mob;
            this.speedModifierFlee = speedModifierFlee;
            this.speedModifierCharge = speedModifierCharge;
            this.fleeDistance = fleeDistance;
            this.extraChargeDistance = extraChargeDistance;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        public boolean canUse() {
            long i = this.mob.level().getGameTime();
            if (i - this.lastCanUseCheck < 20L) {
                return false;
            } else {
                this.lastCanUseCheck = i;
                LivingEntity livingentity = this.mob.getTarget();
                if (livingentity == null) {
                    return false;
                } else if (!livingentity.isAlive()) {
                    return false;
                } else {
                    Vec3 vec3 = DefaultRandomPos.getPosAway(this.mob, fleeDistance, 7, livingentity.getPosition(0.0f));
                    if (vec3 == null) {
                        return false;
                    }
                    this.path = this.mob.getNavigation().createPath(vec3.x, vec3.y, vec3.z, 0);
                    return this.path != null || this.mob.isWithinMeleeAttackRange(livingentity);
                }
            }
        }

        public boolean canContinueToUse() {
            LivingEntity livingentity = this.mob.getTarget();
            if (livingentity == null) {
                return false;
            } else if (!livingentity.isAlive()) {
                return false;
            } else {
                if (this.mob.getPosition(0.0f).subtract(livingentity.getPosition(0.0f)).horizontalDistance() > ((float)fleeDistance) * 0.75f && chargeState == ChargeState.FLEE) {
                    this.chargeState = ChargeState.CHARGE;
                    this.mob.getLookControl().setLookAt(livingentity, 360.0F, 360.0F);
                    Vec3 pathNormalized = livingentity.getPosition(0.0f).subtract(this.mob.getPosition(0.0f)).normalize();
                    Vec3 newLocation = livingentity.getPosition(0.0f).add(pathNormalized.scale(extraChargeDistance));
                    this.path = this.mob.getNavigation().createPath(newLocation.x, newLocation.y, newLocation.z, 0);
                    this.mob.getNavigation().moveTo(this.path, this.speedModifierCharge);
                }
                return true;
            }
        }

        public void start() {
            this.mob.getNavigation().moveTo(this.path, this.speedModifierFlee);
            this.mob.setAggressive(true);
            this.chargeState = ChargeState.FLEE;
        }

        public void stop() {
            LivingEntity livingentity = this.mob.getTarget();
            if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingentity)) {
                this.mob.setTarget((LivingEntity)null);
            }

            this.mob.setAggressive(false);
            this.mob.getNavigation().stop();
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            LivingEntity livingentity = this.mob.getTarget();
            if (livingentity != null) {
                if (this.chargeState == ChargeState.CHARGE) {
                    this.mob.getLookControl().setLookAt(livingentity, 360.0F, 360.0F);
                    this.checkAndPerformAttack(livingentity);
                    if (this.mob.getNavigation().isDone()) {
                        this.chargeState = ChargeState.FLEE;
                    }
                } else if (this.chargeState == ChargeState.FLEE) {
                    if (this.mob.getNavigation().isDone() && this.mob.getPosition(0.0f).subtract(livingentity.getPosition(0.0f)).horizontalDistance() <= ((float)fleeDistance) * 0.75f) {
                        Vec3 vec3 = DefaultRandomPos.getPosAway(this.mob, this.fleeDistance, 7, livingentity.getPosition(0.0f));
                        if (vec3 == null)
                            return;
                        this.path = this.mob.getNavigation().createPath(vec3.x, vec3.y, vec3.z, 0);
                        this.mob.getNavigation().moveTo(this.path, this.speedModifierFlee);
                    }
                }
            }
        }

        protected void checkAndPerformAttack(LivingEntity target) {
            if (this.canPerformAttack(target)) {
                this.mob.swing(InteractionHand.MAIN_HAND);
                this.mob.doHurtTarget(target);
                this.chargeState = ChargeState.FLEE;
                int i = 0;
                Vec3 vec3 = DefaultRandomPos.getPosAway(this.mob, this.fleeDistance, 7, target.getPosition(0.0f));
                if (vec3 == null)
                    return;
                this.path = this.mob.getNavigation().createPath(vec3.x, vec3.y, vec3.z, 0);
                this.mob.getNavigation().moveTo(this.path, this.speedModifierFlee);
            }
        }

        protected boolean canPerformAttack(LivingEntity entity) {
            return this.mob.isWithinMeleeAttackRange(entity) && this.mob.getSensing().hasLineOfSight(entity);
        }
    }
}