package com.farcr.nomansland.common.event.listener;

import com.farcr.nomansland.NMLConfig;
import com.farcr.nomansland.common.blockentity.MonsterAnchorBlockEntity;
import com.farcr.nomansland.common.mixinduck.LivingEntityDuck;
import com.farcr.nomansland.common.registry.NMLCriteriaTriggers;
import com.farcr.nomansland.common.registry.NMLParticleTypes;
import com.farcr.nomansland.common.registry.NMLTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class AnchorListener implements GameEventListener {

    private final BlockState state;
    private final PositionSource positionSource;

    public AnchorListener(BlockState state, PositionSource positionSource) {
        this.state = state;
        this.positionSource = positionSource;
    }

    public static List<Vec3> processPoints(AABB boundingBox, double step) {
        double minX = boundingBox.minX;
        double minY = boundingBox.minY;
        double minZ = boundingBox.minZ;
        double maxX = boundingBox.maxX;
        double maxY = boundingBox.maxY;
        double maxZ = boundingBox.maxZ;

        List<Vec3> pointList = new ArrayList<>();

        // Front face (minZ face)
        for (double x = minX; x <= maxX; x += step) {
            for (double y = minY; y <= maxY; y += step) {
                Vec3 point = new Vec3(x, y, minZ);
                pointList.add(point);
            }
        }

        // Back face (maxZ face)
        for (double x = minX; x <= maxX; x += step) {
            for (double y = minY; y <= maxY; y += step) {
                Vec3 point = new Vec3(x, y, maxZ);
                pointList.add(point);
            }
        }

        // Left face (minX face)
        for (double z = minZ; z <= maxZ; z += step) {
            for (double y = minY; y <= maxY; y += step) {
                Vec3 point = new Vec3(minX, y, z);
                pointList.add(point);
            }
        }

        // Right face (maxX face)
        for (double z = minZ; z <= maxZ; z += step) {
            for (double y = minY; y <= maxY; y += step) {
                Vec3 point = new Vec3(maxX, y, z);
                pointList.add(point);
            }
        }

        // Top face (maxY face)
        for (double x = minX; x <= maxX; x += step) {
            for (double z = minZ; z <= maxZ; z += step) {
                Vec3 point = new Vec3(x, maxY, z);
                pointList.add(point);
            }
        }

        // Bottom face (minY face)
        for (double x = minX; x <= maxX; x += step) {
            for (double z = minZ; z <= maxZ; z += step) {
                Vec3 point = new Vec3(x, minY, z);
                pointList.add(point);
            }
        }
        return pointList;
    }

    public PositionSource getListenerSource() {
        return this.positionSource;
    }

    public int getListenerRadius() {
        return NMLConfig.RESURRECTION_RADIUS.get();
    }

    @Override
    public boolean handleGameEvent(ServerLevel level, Holder<GameEvent> gameEvent, GameEvent.Context context, Vec3 pos) {
        if (GameEvent.ENTITY_DIE.is(gameEvent) && context.sourceEntity() instanceof Monster monster) {
            if (!(monster.getType().getTags().toList().contains(NMLTags.ANCHOR_BLACKLIST))) {
                if (!monster.wasExperienceConsumed()) {
                    // Add the entity to the dead entity list
                    this.positionSource.getPosition(level).ifPresent((sourcePos) -> {
                        MonsterAnchorBlockEntity monsterAnchorBlockEntity = (MonsterAnchorBlockEntity) level.getBlockEntity(BlockPos.containing(sourcePos));
                        monsterAnchorBlockEntity.entityQueue.put(monster, monster.getPosition(0));
                    });

                    // Stop the mob from dropping experience and lootType
                    monster.skipDropExperience();
                    ((LivingEntityDuck) monster).nml$skipDroppingDeathLoot();

                    AABB boundingBox = monster.getBoundingBox();
                    processPoints(boundingBox, 0.2).forEach(point -> {
                        level.sendParticles((ParticleOptions) NMLParticleTypes.MALEVOLENT_EMBERS.get(), point.x, point.y, point.z, 1, 0, 0, 0, 0);
                    });

                    tryAwardAdvancement(level, monster);
                }
            }
            return true;
        }
        return false;
    }

    public DeliveryMode getDeliveryMode() {
        return DeliveryMode.BY_DISTANCE;
    }

    private static void tryAwardAdvancement(Level level, Monster monster) {
        if (monster.getLastHurtByMob() instanceof ServerPlayer serverplayer) {
            DamageSource damagesource = monster.getLastDamageSource() == null
                    ? level.damageSources().playerAttack(serverplayer)
                    : monster.getLastDamageSource();
            NMLCriteriaTriggers.KILL_MOB_NEAR_MONSTER_ANCHOR.get().trigger(serverplayer, monster, damagesource);
        }
    }
}
