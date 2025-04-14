package com.farcr.nomansland.common.blockentity;

import com.farcr.nomansland.common.entity.bombs.ThrowableBombEntity;
import com.farcr.nomansland.common.item.ThrowableBombItem;
import com.farcr.nomansland.common.registry.NMLTags;
import com.farcr.nomansland.common.registry.blocks.NMLBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.phys.Vec3;

import static com.farcr.nomansland.common.event.BoneMealingEvents.*;
import static net.minecraft.world.level.block.VineBlock.getPropertyForFace;

public class BonemealDispenseBehavior extends OptionalDispenseItemBehavior {

    private final BoneMealItem boneMealItem;

    public BonemealDispenseBehavior(Item bomb) {
        if (bomb instanceof BoneMealItem boneMealItem) {
            this.boneMealItem = boneMealItem;
        } else {
            String name = String.valueOf(bomb);
            throw new IllegalArgumentException(name + " not instance of " + ThrowableBombItem.class.getSimpleName());
        }
    }

    public ItemStack execute(BlockSource source, ItemStack item) {
        Level level = source.level();
        Direction direction = source.state().getValue(DispenserBlock.FACING);
        BlockState state = level.getBlockState(source.pos().relative(direction));
        BlockPos pos = source.pos().relative(direction);

        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        // Bonemealing flowers and such #bonemeal_spreads
        if (state.is(NMLTags.BONEMEAL_SPREADS)) {
            level.playSound(null, pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1F, 1F);
            item.shrink(1);
            setSuccess(true);

            for (BlockPos blockPos : BlockPos.betweenClosed(x - 3, y - 1, z - 3, x + 3, y + 2, z + 3)) {
                if (level.random.nextFloat() < 0.3F && state.canSurvive(level, blockPos) && level.isEmptyBlock(blockPos)) {
                    BlockPos particlePosition = blockPos.above();
                    if (!level.isClientSide) level.setBlockAndUpdate(blockPos, state);
                    spawnParticles(level, particlePosition);
                }
            }
        }

        //Bone-Mealing things that grow upwards #bonemeal_spreads_above
        if (state.is(NMLTags.BONEMEAL_SPREADS_UPWARDS)) {
            while (!level.isEmptyBlock(pos.above())) {
                pos = pos.above();
            }
            pos = pos.above();
            if (level.isEmptyBlock(pos)) {
                level.playSound(null, pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1F, 1F);
                if (!level.isClientSide) {
                    item.shrink(1);
                    setSuccess(true);
                    level.setBlockAndUpdate(pos, state);
                } else {
                    spawnParticles(level, pos);
                }
            }
        }

        if (state.is(Blocks.VINE)) {
            for (BlockPos bp : BlockPos.betweenClosed(x - 3, y - 3, z - 3, x + 3, y + 3, z + 3)) {
                BlockState vineState = Blocks.VINE.defaultBlockState();
                if (level.getBlockState(bp).isEmpty() && level.random.nextBoolean()) {
                    for (Direction d : Direction.values()) {
                        if (d == Direction.DOWN) continue;
                        BooleanProperty booleanproperty = getPropertyForFace(d);
                        vineState = vineState.setValue(booleanproperty, canSupportAtFace(level, bp, d));
                    }
                    if (vineState != Blocks.VINE.defaultBlockState()) {
                        if (!level.isClientSide) level.setBlockAndUpdate(bp, vineState);
                        spawnParticles(level, bp);
                    }
                }
            }
            level.playSound(null, pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1F, 1F);
            item.shrink(1);
            setSuccess(true);
        }

        // Bonemealing dirt
        if (state.is(Blocks.DIRT) && !level.getBlockState(pos.above()).isSolid()) {
            // Ensure the dirt that is being right-clicked has a suitable block such as grass nearby
            for (Direction d : Direction.values()) {
                for (Direction d1 : Direction.values()) {
                    if (d == d1) break;
                    BlockPos newBlockPos = pos.relative(d);
                    if (level.getBlockState(newBlockPos).getBlock() instanceof SpreadingSnowyDirtBlock) {
                        item.shrink(1);
                        setSuccess(true);
                        bonemealDirt(level, pos, level.getBlockState(newBlockPos));
                        level.playSound(null, pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1F, 1F);
                    } else if (level.getBlockState(newBlockPos.relative(d1)).getBlock() instanceof SpreadingSnowyDirtBlock) {
                        item.shrink(1);
                        setSuccess(true);
                        bonemealDirt(level, pos, level.getBlockState(newBlockPos.relative(d1)));
                        level.playSound(null, pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1F, 1F);
                    }
                }
            }
        }

        if (state.is(NMLBlocks.SHELF_MUSHROOM)) {
            if (level instanceof  ServerLevel serverLevel) {
                Direction facing = state.getValue(BaseCoralWallFanBlock.FACING);
                BlockPos sidePos = level.random.nextBoolean() && level.isEmptyBlock(pos.relative(facing.getClockWise())) ?
                        pos.relative(facing.getClockWise()) : level.isEmptyBlock(pos.relative(facing.getCounterClockWise())) ?
                        pos.relative(facing.getCounterClockWise()) : level.isEmptyBlock(pos.relative(facing.getClockWise())) ?
                        pos.relative(facing.getClockWise()) : null;
                BlockState newState = NMLBlocks.SHELF_MUSHROOM_BLOCK.get().defaultBlockState()
                        .setValue(SlabBlock.TYPE, level.random.nextBoolean() ? SlabType.BOTTOM : SlabType.TOP);

                if (!level.isClientSide) level.setBlockAndUpdate(pos, newState);
                if (sidePos != null) {
                    if (!level.isClientSide) level.setBlockAndUpdate(sidePos, newState);
                    sendParticles(serverLevel, sidePos);
                    if (level.isEmptyBlock(sidePos.relative(facing.getOpposite()))) {
                        if (!level.isClientSide) level.setBlockAndUpdate(sidePos.relative(facing.getOpposite()), newState);
                        sendParticles(serverLevel, sidePos.relative(facing.getOpposite()));
                    }
                }
            }

            spawnParticles(level, pos);
            level.playSound(null, pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1, 1);
            item.shrink(1);
            setSuccess(true);
        }

        if (!BoneMealItem.growCrop(item, level, pos) && !BoneMealItem.growWaterPlant(item, level, pos, null))
            setSuccess(false);
        else if (!level.isClientSide) level.levelEvent(1505, pos, 15);

        return item;
    }

    protected void playSound(BlockSource source) {
        source.level().levelEvent(1002, source.pos(), 0);
    }
}
