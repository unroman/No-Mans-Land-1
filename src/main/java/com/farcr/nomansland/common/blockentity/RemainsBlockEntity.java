package com.farcr.nomansland.common.blockentity;

import com.farcr.nomansland.NMLConfig;
import com.farcr.nomansland.common.registry.NMLBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class RemainsBlockEntity extends BrushableBlockEntity {
    public RemainsBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public boolean isValidBlockState(BlockState blockState) {
        return NMLBlockEntities.REMAINS.get().isValid(blockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return super.getType();
    }

    @Override
    public void brushingCompleted(Player player) {
        if (level != null && level.getServer() != null) {
            Block remainsBlockState = this.getBlockState().getBlock();
            if (remainsBlockState instanceof BrushableBlock) {
                if (level.random.nextFloat() < NMLConfig.BURIED_SPAWNING_CHANCE.get()) {
                    //TODO: When Buried is readded/worked on, replace SKELETON with BURIED
                    Skeleton buried = EntityType.SKELETON.create(level);
                    if (buried != null) {
                        BlockPos spawningPosition = worldPosition.relative(this.getHitDirection());
                        if (level.getBlockState(spawningPosition) == Blocks.AIR.defaultBlockState())
                            buried.moveTo(spawningPosition.getCenter());
                        else
                            buried.moveTo((player.getX() + worldPosition.getX()) / 2, (player.getY() + worldPosition.getY()) / 2, (player.getZ() + worldPosition.getZ()) / 2);
                        level.addFreshEntity(buried);
                        buried.spawnAnim();
                    }
                }
            }
        }

        super.brushingCompleted(player);
    }
}
