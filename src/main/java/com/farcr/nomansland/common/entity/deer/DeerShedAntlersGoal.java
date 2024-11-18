package com.farcr.nomansland.common.entity.deer;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class DeerShedAntlersGoal  extends Goal {

    private final Deer mob;
    
    @Nullable
    protected Path path;
    private final Level level;
    private int shedAnimationTick;

    public DeerShedAntlersGoal(Deer mob) {
        this.mob = mob;
        level = mob.level();
        setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
    }

    public boolean canUse() {
        if (mob.getAntlersLifetime() > mob.getRandom().nextInt(70000, 80000) && mob.hasAntlers()) {
            Iterable<BlockPos> iterable = BlockPos.betweenClosed(Mth.floor(mob.getX() - 10), Mth.floor(mob.getY() - 10), Mth.floor(mob.getZ() - 10), Mth.floor(mob.getX() + 10), Mth.floor(mob.getY() + 10), Mth.floor(mob.getZ() + 10));
            BlockPos blockpos = null;

            for (BlockPos blockpos1 : iterable) {
                if (level.getBlockState(blockpos1).is(BlockTags.LOGS)) {
                    for (Direction direction : Direction.Plane.HORIZONTAL) {
                        if (level.getBlockState(blockpos1.below(2).relative(direction)).isPathfindable(PathComputationType.LAND) && mob.getRandom().nextBoolean()) {
                            blockpos = blockpos1.relative(direction).above();
                            break;
                        }
                    }
                    if (blockpos != null) {
                        path = mob.getNavigation().createPath(blockpos.getX(), blockpos.getY(), blockpos.getZ(), 0);
                        break;
                    }
                }
            }
        }
        
        return path != null;
    }

    public void start() {
        shedAnimationTick = adjustedTickDelay(mob.getRandom().nextInt(40, 120));
        level.broadcastEntityEvent(mob, (byte)10);
        mob.getNavigation().moveTo(path, 1);
    }

    public boolean canContinueToUse() {
        return mob.hasAntlers() && shedAnimationTick > 0;
    }

    public void tick() {
        if (!mob.isPathFinding()) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                if (level.getBlockState(mob.blockPosition().above().relative(direction)).is(BlockTags.LOGS)) {
                    shedAnimationTick = Math.max(0, shedAnimationTick - 1);
                    if (shedAnimationTick == adjustedTickDelay(4)) mob.setHasAntlers(false);
                }
            }
        }
    }

    public void stop() {
        shedAnimationTick = 0;
    }

    public int getShedAnimationTick() {
        return shedAnimationTick;
    }
}
