package com.farcr.nomansland.common.entity.goose;

import com.farcr.nomansland.common.registry.NMLSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.jetbrains.annotations.Nullable;

public class Goose extends PathfinderMob {
    public Goose(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10)
                .add(Attributes.ATTACK_DAMAGE, 2)
                .add(Attributes.MOVEMENT_SPEED, 0.2);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(1, new PanicGoal(this, 1.65));
        goalSelector.addGoal(2, new RandomStrollGoal(this, 1.0, 60));
        goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        goalSelector.addGoal(4, new GooseGoToWaterGoal(this, 1.0));
        goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        goalSelector.addGoal(6, new LookAtPlayerGoal(this, Goose.class, 8.0F));
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return NMLSounds.GOOSE_AMBIENT.get();
    }
    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return NMLSounds.GOOSE_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return NMLSounds.GOOSE_DEATH.get();
    }

    protected void playStepSound(BlockPos pos, BlockState block) {
        this.playSound(NMLSounds.GOOSE_STEP.get(), 0.15F, 1.0F);
    }

    @Override
    public void tick() {
        super.tick();
        floatGoose();
    }

    @Override
    public boolean canStandOnFluid(FluidState fluidState) {
        return fluidState.is(FluidTags.WATER);
    }

    private void floatGoose() {
        if (this.isInWater()) {
            CollisionContext collisioncontext = CollisionContext.of(this);
            if (collisioncontext.isAbove(LiquidBlock.STABLE_SHAPE, this.blockPosition(), true) && !level().getFluidState(blockPosition().above()).is(FluidTags.WATER)) {
                this.setOnGround(true);
            } else {
                this.setDeltaMovement(getDeltaMovement().scale(0.5).add(0.0, 0.05, 0.0));
            }
        }

    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new GoosePathNavigation(this, level);
    }

    @Override
    public PathNavigation getNavigation() {
        return super.getNavigation();
    }

    public static class GooseGoToWaterGoal extends MoveToBlockGoal {
        private final Goose goose;

        GooseGoToWaterGoal(Goose goose, double speedModifier) {
            super(goose, speedModifier, 8, 2);
            this.goose = goose;
        }

        public BlockPos getMoveToTarget() {
            return this.blockPos;
        }

        public boolean canContinueToUse() {
            return !this.goose.isInWater() && this.isValidTarget(this.goose.level(), this.blockPos);
        }

        public boolean canUse() {
            return !this.goose.isInWater() && super.canUse();
        }

        public boolean shouldRecalculatePath() {
            return this.tryTicks % 20 == 0;
        }

        protected boolean isValidTarget(LevelReader level, BlockPos pos) {
            return level.getBlockState(pos).is(Blocks.WATER) && level.getBlockState(pos.above()).isPathfindable(PathComputationType.LAND);
        }
    }
    
    public static class GoosePathNavigation extends GroundPathNavigation {
        GoosePathNavigation(Goose goose, Level level) {
            super(goose, level);
        }

        protected PathFinder createPathFinder(int maxVisitedNodes) {
            this.nodeEvaluator = new WalkNodeEvaluator();
            this.nodeEvaluator.setCanPassDoors(true);
            return new PathFinder(this.nodeEvaluator, maxVisitedNodes);
        }

        protected boolean hasValidPathType(PathType pathType) {
            return pathType == PathType.WATER || super.hasValidPathType(pathType);
        }

        public boolean isStableDestination(BlockPos pos) {
            return this.level.getBlockState(pos).is(Blocks.WATER) || super.isStableDestination(pos);
        }
    }
}
