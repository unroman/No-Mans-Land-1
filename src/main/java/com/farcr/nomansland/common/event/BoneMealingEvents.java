package com.farcr.nomansland.common.event;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.registry.NMLBlocks;
import com.farcr.nomansland.common.registry.NMLTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

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
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

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

        //Vine Cutting
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

                for (BlockPos bp : BlockPos.betweenClosed(x - 3, y - 1, z - 3, x + 3, y + 2, z + 3)) {
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
                for (BlockPos bp : BlockPos.betweenClosed(x - 3, y - 3, z - 3, x + 3, y + 3, z + 3)) {
                    BlockState vineState = Blocks.VINE.defaultBlockState();
                    if (level.getBlockState(bp).isEmpty() && level.random.nextBoolean()) {
                        for (Direction d : Direction.values()) {
                            if (d == Direction.DOWN) continue;
                            BooleanProperty booleanproperty = getPropertyForFace(d);
                            vineState = vineState.setValue(booleanproperty, canSupportAtFace(level, bp, d));
                        }
                        if (vineState != Blocks.VINE.defaultBlockState()) {
                            level.setBlockAndUpdate(bp, vineState);
                            for (int i = 0; i <= 3; i++) {
                                level.addParticle(ParticleTypes.COMPOSTER, bp.getX() + Math.random(), bp.getY() + 0.2 + Math.random(), bp.getZ() + Math.random(), 0, 0, 0);
                            }
                        }
                    }
                }
                level.playSound(player, pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1F, 1F);
                if (!player.isCreative()) stack.shrink(1);
                event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide()));
                event.setCanceled(true);
            }

            // Bonemealing dirt
            if (state.is(Blocks.DIRT) && !level.getBlockState(pos.above()).isSolid()) {
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
        for (BlockPos bp : BlockPos.betweenClosed(x - 3, y - 1, z - 3, x + 3, y + 2, z + 3)) {
            BlockState block = level.getBlockState(bp);
            for (Direction d : Direction.values()) {
                Block newBlock = level.getBlockState(bp.relative(d)).getBlock();
                if (level.random.nextFloat() < 0.3F && block == Blocks.DIRT.defaultBlockState() && newBlock instanceof SpreadingSnowyDirtBlock && !level.getBlockState(bp.above()).isSolid()) {
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

    public static boolean canSupportAtFace(BlockGetter level, BlockPos pos, Direction direction) {
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
