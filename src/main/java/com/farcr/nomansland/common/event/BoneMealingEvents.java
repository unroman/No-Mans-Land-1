package com.farcr.nomansland.common.event;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.registry.NMLBlocks;
import com.farcr.nomansland.common.registry.NMLTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.Iterator;

import static net.minecraft.world.level.block.VineBlock.*;

@EventBusSubscriber(modid = NoMansLand.MODID)
@SuppressWarnings("unused")
public class BoneMealingEvents {

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);
        Player player = event.getEntity();
        ItemStack stack = event.getItemStack();

        //Sugarcane Cutting
        if (event.getFace() != Direction.DOWN && stack.is(Items.SHEARS) && state.is(Blocks.SUGAR_CANE) && !player.isSpectator()) {
            level.playSound(player, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!level.isClientSide()) {
                stack.hurtAndBreak(1, player, stack.getEquipmentSlot());

                level.setBlockAndUpdate(pos, NMLBlocks.CUT_SUGAR_CANE.get().defaultBlockState());
            }
            event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide()));
            event.setCanceled(true);

        }

        //Sugarcane Cutting
        if (stack.is(Items.SHEARS) && state.is(Blocks.VINE) && !player.isSpectator()) {
            level.playSound(player, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!level.isClientSide()) {
                stack.hurtAndBreak(1, player, stack.getEquipmentSlot());

                level.setBlockAndUpdate(pos, NMLBlocks.CUT_VINE.get().withPropertiesOf(state));
            }
            event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide()));
            event.setCanceled(true);

        }

        //Bone-Mealing
        if (stack.is(Items.BONE_MEAL) && !player.isSpectator()) {

            // Bonemealing flowers and such #bonemeal_spreads
            if (state.is(NMLTags.BONEMEAL_SPREADS)) {
                level.playSound(player, pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1F, 1F);
//                    level.addParticle();
                if (!level.isClientSide && !player.isCreative()) stack.shrink(1);

                int x = pos.getX();
                int y = pos.getY();
                int z = pos.getZ();

                Iterator<BlockPos> it = BlockPos.betweenClosedStream(x - 3, y - 1, z - 3, x + 3, y + 2, z + 3).iterator();
                while (it.hasNext()) {
                    BlockPos bp = it.next();
                    Block block = level.getBlockState(bp).getBlock();
                    if (level.random.nextFloat() <= 0.3F && state.canSurvive(level, bp) && level.isEmptyBlock(bp)) {
                        BlockPos particlePosition = bp.above();
                        if (!level.isClientSide) level.setBlockAndUpdate(bp, state);
                        else {
                            for (int i = 0; i <= 3; i++) {
                                level.addParticle(ParticleTypes.COMPOSTER, particlePosition.getX() + Math.random(), particlePosition.getY() + 0.2 + Math.random(), particlePosition.getZ() + Math.random(), 0, 0, 0);
                            }
                        }
                    }
                }
                event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide()));
                event.setCanceled(true);
            }

            //Bone-Mealing things that grow upwards #bonemeal_spreads_above
            if (state.is(NMLTags.BONEMEAL_SPREADS_UPWARDS)) {
                while (!level.isEmptyBlock(pos.above())) {
                    pos = pos.above();
                }
                pos = pos.above();
                if (level.isEmptyBlock(pos)) {
                    level.playSound(player, pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1F, 1F);
                    if (!level.isClientSide) {
                        if (!player.isCreative()) stack.shrink(1);
                        level.setBlockAndUpdate(pos, state);
                    } else {
                        for (int i = 0; i <= 3; i++) {
                            level.addParticle(ParticleTypes.COMPOSTER, pos.getX() + Math.random(), pos.getY() + 0.2 + Math.random(), pos.getZ() + Math.random(), 0, 0, 0);
                        }
                        event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide()));
                        event.setCanceled(true);
                    }
                }
            }

            if (state.is(Blocks.VINE)) {
                int i = 0;
                for (Direction direction : Direction.values()) {
                    if (level.random.nextBoolean()) i++;
                    if (i > 6) break;
                    BlockPos blockpos = pos.above();
                    BlockPos blockpos4 = pos;
                    BlockState blockstate;
                    Direction direction2;

                    if (!level.isEmptyBlock(pos.relative(direction))) continue;

                    if (direction.getAxis().isHorizontal() && !state.getValue(getPropertyForFace(direction))) {
                        if (canSpread(level, pos)) {
                            blockpos4 = pos.relative(direction);
                            blockstate = level.getBlockState(blockpos4);
                            if (blockstate.isAir()) {
                                direction2 = direction.getClockWise();
                                Direction direction4 = direction.getCounterClockWise();
                                boolean flag = state.getValue(getPropertyForFace(direction2));
                                boolean flag1 = state.getValue(getPropertyForFace(direction4));
                                BlockPos blockpos2 = blockpos4.relative(direction2);
                                BlockPos blockpos3 = blockpos4.relative(direction4);
                                if (flag && isAcceptableNeighbour(level, blockpos2, direction2)) {
                                    level.setBlock(blockpos4, state.setValue(getPropertyForFace(direction2), true), 2);
                                } else if (flag1 && isAcceptableNeighbour(level, blockpos3, direction4)) {
                                    level.setBlock(blockpos4, state.setValue(getPropertyForFace(direction4), true), 2);
                                } else {
                                    Direction direction1 = direction.getOpposite();
                                    if (flag && level.isEmptyBlock(blockpos2) && isAcceptableNeighbour(level, pos.relative(direction2), direction1)) {
                                        level.setBlock(blockpos2, state.setValue(getPropertyForFace(direction1), true), 2);
                                    } else if (flag1 && level.isEmptyBlock(blockpos3) && isAcceptableNeighbour(level, pos.relative(direction4), direction1)) {
                                        level.setBlock(blockpos3, state.setValue(getPropertyForFace(direction1), true), 2);
                                    } else if ((double) level.random.nextFloat() < 0.05 && isAcceptableNeighbour(level, blockpos4.above(), Direction.UP)) {
                                        level.setBlock(blockpos4, state.setValue(UP, true), 2);
                                    }
                                }
                            } else if (isAcceptableNeighbour(level, blockpos4, direction)) {
                                level.setBlock(pos, state.setValue(getPropertyForFace(direction), true), 2);
                            }
                        }
                    } else {
                        if (direction == Direction.UP && pos.getY() < level.getMaxBuildHeight() - 1) {
                            if (canSupportAtFace(level, pos, direction)) {
                                level.setBlock(pos, state.setValue(UP, true), 2);
                                return;
                            }

                            if (level.isEmptyBlock(blockpos)) {
                                if (!canSpread(level, pos)) {
                                    return;
                                }

                                BlockState blockstate3 = state;
                                Iterator<Direction> var17 = Direction.Plane.HORIZONTAL.iterator();

                                while (true) {
                                    do {
                                        if (!var17.hasNext()) {
                                            if (hasHorizontalConnection(blockstate3)) {
                                                level.setBlock(blockpos, blockstate3, 2);
                                            }
                                            level.playSound(player, pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1F, 1F);
                                            for (int p = 0; p <= 3; p++) {
                                                level.addParticle(ParticleTypes.COMPOSTER, blockpos.getX() + Math.random(), blockpos.getY() + 0.2 + Math.random(), blockpos.getZ() + Math.random(), 0, 0, 0);
                                            }
                                            return;
                                        }

                                        direction2 = var17.next();
                                    } while (!level.random.nextBoolean() && isAcceptableNeighbour(level, blockpos.relative(direction2), direction2));

                                    blockstate3 = blockstate3.setValue(getPropertyForFace(direction2), false);
                                }
                            }
                        }

                        if (pos.getY() > level.getMinBuildHeight()) {
                            blockpos4 = pos.below();
                            blockstate = level.getBlockState(blockpos4);
                            if (blockstate.isAir() || blockstate.is(Blocks.VINE)) {
                                BlockState blockstate1 = blockstate.isAir() ? state : blockstate;
                                BlockState blockstate2 = copyRandomFaces(state, blockstate1, level.random);
                                if (blockstate1 != blockstate2 && hasHorizontalConnection(blockstate2)) {
                                    level.setBlock(blockpos4, blockstate2, 2);
                                } else continue;
                            }
                        }
                    }
                    level.playSound(player, pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1F, 1F);
                    for (int p = 0; p <= 3; p++) {
                        level.addParticle(ParticleTypes.COMPOSTER, blockpos4.getX() + Math.random(), blockpos4.getY() + 0.2 + Math.random(), blockpos4.getZ() + Math.random(), 0, 0, 0);
                    }
                }
                event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide()));
                event.setCanceled(true);
            }

            // Bonemealing dirt
            if (state.getBlock().equals(Blocks.DIRT) && !level.getBlockState(pos.above()).isSolid()) {
                // Ensure the dirt that is being right-clicked has a suitable block such as grass nearby
                for (Direction d : Direction.values()) {
                    for (Direction d1 : Direction.values()) {
                        if (d == d1) break;
                        BlockPos newBlockPos = pos.relative(d);
                        if (level.getBlockState(newBlockPos).getBlock() instanceof SpreadingSnowyDirtBlock) {
                            if (!player.isCreative()) stack.shrink(1);
                            bonemealDirt(level, pos, level.getBlockState(newBlockPos));
                            level.playSound(player, pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1F, 1F);
                            event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide()));
                            event.setCanceled(true);
                            return;
                        } else if (level.getBlockState(newBlockPos.relative(d1)).getBlock() instanceof SpreadingSnowyDirtBlock) {
                            if (!player.isCreative()) stack.shrink(1);
                            bonemealDirt(level, pos, level.getBlockState(newBlockPos.relative(d1)));
                            level.playSound(player, pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1F, 1F);
                            event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide()));
                            event.setCanceled(true);
                            return;
                        }
                    }
                }
            }
        }
    }

    public static void bonemealDirt(Level level, BlockPos pos, BlockState state) {
        if (!level.isClientSide) level.setBlockAndUpdate(pos, state);

        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        // Iterate through a cube and find suitable dirt blocks that can be turned into the new block
        Iterator<BlockPos> it = BlockPos.betweenClosedStream(x - 3, y - 1, z - 3, x + 3, y + 2, z + 3).iterator();
        while (it.hasNext()) {
            BlockPos bp = it.next();
            BlockState block = level.getBlockState(bp);
            for (Direction d : Direction.values()) {
                Block newBlock = level.getBlockState(bp.relative(d)).getBlock();
                if (level.random.nextFloat() <= 0.3F && block == Blocks.DIRT.defaultBlockState() && newBlock instanceof SpreadingSnowyDirtBlock && !level.getBlockState(bp.above()).isSolid()) {
                    BlockPos particlePosition = bp.above();
                    if (!level.isClientSide) level.setBlockAndUpdate(bp, state);
                    else {
                        for (int i = 0; i <= 3; i++) {
                            level.addParticle(ParticleTypes.COMPOSTER, particlePosition.getX() + Math.random(), particlePosition.getY() + 0.2 + Math.random(), particlePosition.getZ() + Math.random(), 0, 0, 0);
                        }
                    }
                }
            }
        }
    }

    private static boolean canSpread(BlockGetter blockReader, BlockPos pos) {
        boolean i = true;
        Iterable<BlockPos> iterable = BlockPos.betweenClosed(pos.getX() - 4, pos.getY() - 1, pos.getZ() - 4, pos.getX() + 4, pos.getY() + 1, pos.getZ() + 4);
        int j = 5;

        for (BlockPos blockpos : iterable) {
            if (blockReader.getBlockState(blockpos).is(Blocks.VINE)) {
                --j;
                if (j <= 0) {
                    return false;
                }
            }
        }

        return true;
    }

    private static BlockState copyRandomFaces(BlockState sourceState, BlockState spreadState, RandomSource random) {

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            if (random.nextBoolean()) {
                BooleanProperty booleanproperty = getPropertyForFace(direction);
                if (sourceState.getValue(booleanproperty)) {
                    spreadState = spreadState.setValue(booleanproperty, true);
                }
            }
        }

        return spreadState;
    }

    private static boolean hasHorizontalConnection(BlockState state) {
        return state.getValue(NORTH) || state.getValue(EAST) || state.getValue(SOUTH) || state.getValue(WEST);
    }

    private static boolean canSupportAtFace(BlockGetter level, BlockPos pos, Direction direction) {
        if (direction == Direction.DOWN) {
            return false;
        } else {
            BlockPos blockpos = pos.relative(direction);
            if (isAcceptableNeighbour(level, blockpos, direction)) {
                return true;
            } else if (direction.getAxis() == Direction.Axis.Y) {
                return false;
            } else {
                BooleanProperty booleanproperty = PROPERTY_BY_DIRECTION.get(direction);
                BlockState blockstate = level.getBlockState(pos.above());
                return blockstate.is(Blocks.VINE) && blockstate.getValue(booleanproperty);
            }
        }
    }
}
