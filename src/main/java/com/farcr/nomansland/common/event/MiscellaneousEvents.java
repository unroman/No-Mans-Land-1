package com.farcr.nomansland.common.event;

import com.farcr.nomansland.NMLConfig;
import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.block.torches.ExtinguishedTorchBlock;
import com.farcr.nomansland.common.entity.BillhookBass;
import com.farcr.nomansland.common.entity.deer.Deer;
import com.farcr.nomansland.common.registry.blocks.NMLBlocks;
import com.farcr.nomansland.common.registry.entities.NMLEntities;
import com.farcr.nomansland.common.registry.worldgen.NMLFeatures;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.camel.Camel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.BlockGrowFeatureEvent;
import net.neoforged.neoforge.event.level.ExplosionEvent;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.farcr.nomansland.common.block.FrostedGrassBlock.SNOWLOGGED;
import static net.minecraft.world.level.block.SnowyDirtBlock.SNOWY;
@SuppressWarnings("unused")
public class MiscellaneousEvents {
    @EventBusSubscriber(modid = NoMansLand.MODID)
    public static class NeoForgeEvents {
        @SubscribeEvent
        public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
            Level level = event.getLevel();
            BlockPos pos = event.getPos();
            BlockState state = level.getBlockState(pos);
            Player player = event.getEntity();
            ItemStack stack = event.getItemStack();

            List<Block> torches = List.of(
                    Blocks.TORCH,
                    Blocks.WALL_TORCH,
                    Blocks.SOUL_TORCH,
                    Blocks.SOUL_WALL_TORCH,
                    NMLBlocks.SCONCE_TORCH.get(),
                    NMLBlocks.SCONCE_WALL_TORCH.get(),
                    NMLBlocks.SCONCE_SOUL_TORCH.get(),
                    NMLBlocks.SCONCE_SOUL_WALL_TORCH.get()
            );
            //Torch Extinguishing
            if (torches.contains(state.getBlock()) && stack.is(ItemTags.SHOVELS) && !player.isSpectator() && NMLConfig.TORCH_EXTINGUISHING.get()) {
                level.playSound(player, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.4F, 1.0F);
                if (!level.isClientSide) {
                    stack.hurtAndBreak(1, player, stack.getEquipmentSlot());

                    BlockState extinguishedTorchState = ImmutableMap.ofEntries(
                            Map.entry(Blocks.TORCH, NMLBlocks.EXTINGUISHED_TORCH),
                            Map.entry(Blocks.WALL_TORCH, NMLBlocks.EXTINGUISHED_WALL_TORCH),
                            Map.entry(Blocks.SOUL_TORCH, NMLBlocks.EXTINGUISHED_SOUL_TORCH),
                            Map.entry(Blocks.SOUL_WALL_TORCH, NMLBlocks.EXTINGUISHED_SOUL_WALL_TORCH),
                            Map.entry(NMLBlocks.SCONCE_TORCH.get(), NMLBlocks.EXTINGUISHED_SCONCE_TORCH),
                            Map.entry(NMLBlocks.SCONCE_WALL_TORCH.get(), NMLBlocks.EXTINGUISHED_SCONCE_WALL_TORCH),
                            Map.entry(NMLBlocks.SCONCE_SOUL_TORCH.get(), NMLBlocks.EXTINGUISHED_SCONCE_SOUL_TORCH),
                            Map.entry(NMLBlocks.SCONCE_SOUL_WALL_TORCH.get(), NMLBlocks.EXTINGUISHED_SCONCE_SOUL_WALL_TORCH)
                    ).get(state.getBlock()).value().withPropertiesOf(state);


                    level.setBlockAndUpdate(pos, extinguishedTorchState);
                }
                event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide()));
                event.setCanceled(true);
            }

            // Grass Frosting
            if (stack.is(Blocks.SNOW.asItem()) && !player.isSpectator() && state.is(Blocks.SHORT_GRASS)) {
                level.setBlockAndUpdate(pos, NMLBlocks.FROSTED_GRASS.get().defaultBlockState().setValue(SNOWLOGGED, true));
                stack.consume(1, player);
                level.playSound(player, pos, SoundEvents.SNOW_PLACE, SoundSource.PLAYERS, 1, (level.random.nextFloat() - level.random.nextFloat()) * 0.6F + 1.2F);
                BlockPos posUnder = pos.below();
                BlockState stateUnder = level.getBlockState(posUnder);
                if (stateUnder.getBlock() instanceof SnowyDirtBlock)
                    level.setBlockAndUpdate(posUnder, stateUnder.setValue(SNOWY, true));
                event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide()));
                event.setCanceled(true);
            }

            // Snow grassing?
            if ((stack.is(NMLBlocks.FROSTED_GRASS.asItem()) || stack.is(Blocks.SHORT_GRASS.asItem())) && !player.isSpectator() && state.is(Blocks.SNOW)) {
                BlockPos posUnder = pos.below();
                BlockState stateUnder = level.getBlockState(posUnder);
                if (stateUnder.getBlock() instanceof SnowyDirtBlock || stateUnder.getBlock() == Blocks.DIRT) {
                    level.setBlockAndUpdate(pos, NMLBlocks.FROSTED_GRASS.get().defaultBlockState().setValue(SNOWLOGGED, true));
                    stack.consume(1, player);
                    level.playSound(player, pos, SoundEvents.GRASS_PLACE, SoundSource.PLAYERS, 1, (level.random.nextFloat() - level.random.nextFloat()) * 0.2F + 1.2F);
                    if (stateUnder.getBlock() instanceof SnowyDirtBlock)
                        level.setBlockAndUpdate(posUnder, stateUnder.setValue(SNOWY, true));
                    event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide()));
                    event.setCanceled(true);
                }
            }

            // Ladder Placement
            if (stack.is(Items.LADDER) && state.is(Blocks.LADDER) && !player.isSpectator()) {
                Direction ladderFacing = state.getValue(LadderBlock.FACING);
                if (ladderFacing == event.getFace()) {
                    BlockPos.MutableBlockPos mutable = pos.below().mutable();
                    for (int i = 0; NMLConfig.MAX_LADDER_PLACEMENT_LENGTH.get() != 0 ? i < NMLConfig.MAX_LADDER_PLACEMENT_LENGTH.get() : mutable.getY() > level.getMinBuildHeight(); i++) {
                        BlockState state2 = level.getBlockState(mutable);
                        if (state2.is(BlockTags.REPLACEABLE)) {
                            if (state.canSurvive(level, mutable)) {
                                SoundType soundtype = state.getSoundType(level, pos, player);
                                level.playSound(player, mutable, soundtype.getPlaceSound(), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                                stack.consume(1, player);
                                level.setBlockAndUpdate(mutable, Blocks.LADDER.defaultBlockState().setValue(LadderBlock.FACING, ladderFacing));
                                event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide()));
                                event.setCanceled(true);
                                break;
                            }
                        } else if (!state2.is(Blocks.LADDER)) {
                            break;
                        }
                        mutable.move(Direction.DOWN);
                    }
                }
            }

            // Rail Placement
            // This code is terrible and fills me with regrets. Those who dare venture in do so at their own risk
            if (stack.is(ItemTags.RAILS) && state.is(BlockTags.RAILS) && !player.isSpectator()) {
                Direction playerDir = player.getDirection();
                RailShape railShape = null;
                if (state.hasProperty(RailBlock.SHAPE))
                    railShape = state.getValue(RailBlock.SHAPE);
                else if (state.hasProperty(PoweredRailBlock.SHAPE))
                    railShape = state.getValue(PoweredRailBlock.SHAPE);
                else if (state.hasProperty(DetectorRailBlock.SHAPE))
                    railShape = state.getValue(DetectorRailBlock.SHAPE);
                if (railShape != null) {
                    int railCount = 0;
                    for (Direction direction : Direction.Plane.HORIZONTAL) {
                        if (level.getBlockState(pos.relative(direction)).is(BlockTags.RAILS))
                            railCount++;
                    }
                    RailShape placedShape;
                    BlockPos.MutableBlockPos mutable = pos.mutable();
                    for (int i = 0; i < NMLConfig.MAX_RAIL_PLACMENT_LENGTH.get(); i++) {
                        BlockState state2 = level.getBlockState(mutable);
                        BlockState state4 = level.getBlockState(mutable.immutable().below());
                        BlockState state5 = level.getBlockState(mutable.immutable().above());
                        BlockState state6 = level.getBlockState(mutable.immutable().below(2));
                        BlockState state7 = level.getBlockState(mutable.immutable().below().relative(playerDir.getOpposite()));
                        if (state2.is(BlockTags.RAILS)) {
                            // Continue along the chain
                            RailShape offsetShape = null;
                            if (state2.hasProperty(RailBlock.SHAPE))
                                offsetShape = state2.getValue(RailBlock.SHAPE);
                            else if (state2.hasProperty(PoweredRailBlock.SHAPE))
                                offsetShape = state2.getValue(PoweredRailBlock.SHAPE);
                            else if (state2.hasProperty(DetectorRailBlock.SHAPE))
                                offsetShape = state2.getValue(DetectorRailBlock.SHAPE);

                            if (offsetShape == null)
                                break;

                            // The big if chain
                            // Straights are gone because of woke
                            // Curves
                            if (offsetShape == RailShape.NORTH_EAST) {
                                if (playerDir == Direction.SOUTH) playerDir = Direction.EAST;
                                else if (playerDir == Direction.WEST) playerDir = Direction.NORTH;
                            } else if (offsetShape == RailShape.NORTH_WEST) {
                                if (playerDir == Direction.SOUTH) playerDir = Direction.WEST;
                                else if (playerDir == Direction.EAST) playerDir = Direction.NORTH;
                            } else if (offsetShape == RailShape.SOUTH_EAST) {
                                if (playerDir == Direction.NORTH) playerDir = Direction.EAST;
                                else if (playerDir == Direction.WEST) playerDir = Direction.SOUTH;
                            } else if (offsetShape == RailShape.SOUTH_WEST) {
                                if (playerDir == Direction.NORTH) playerDir = Direction.WEST;
                                else if (playerDir == Direction.EAST) playerDir = Direction.SOUTH;
                            }
                            // Ramps
                            else if (offsetShape == RailShape.ASCENDING_NORTH) {
                                if (playerDir == Direction.NORTH) mutable.move(Direction.UP);
                                else if (playerDir != Direction.SOUTH) break;
                            } else if (offsetShape == RailShape.ASCENDING_SOUTH) {
                                if (playerDir == Direction.SOUTH) mutable.move(Direction.UP);
                                else if (playerDir != Direction.NORTH) break;
                            } else if (offsetShape == RailShape.ASCENDING_EAST) {
                                if (playerDir == Direction.EAST) mutable.move(Direction.UP);
                                else if (playerDir != Direction.WEST) break;
                            } else if (offsetShape == RailShape.ASCENDING_WEST) {
                                if (playerDir == Direction.WEST) mutable.move(Direction.UP);
                                else if (playerDir != Direction.EAST) break;
                            }
                            // Edge case
                            else if (offsetShape != RailShape.EAST_WEST && offsetShape != RailShape.NORTH_SOUTH){
                                break;
                            }

                            mutable.move(playerDir);
                        } else if (state4.is(BlockTags.RAILS)) {
                            boolean canGoDown = false;
                            RailShape offsetShape = null;
                            if (state4.hasProperty(RailBlock.SHAPE))
                                offsetShape = state4.getValue(RailBlock.SHAPE);
                            else if (state4.hasProperty(PoweredRailBlock.SHAPE))
                                offsetShape = state4.getValue(PoweredRailBlock.SHAPE);
                            else if (state4.hasProperty(DetectorRailBlock.SHAPE))
                                offsetShape = state4.getValue(DetectorRailBlock.SHAPE);

                            if (offsetShape == null) break;

                            if (offsetShape == RailShape.ASCENDING_NORTH) {
                                if (playerDir == Direction.SOUTH) {
                                    mutable.move(Direction.DOWN);
                                    canGoDown = true;
                                } else if (playerDir != Direction.NORTH) break;
                            } else if (offsetShape == RailShape.ASCENDING_SOUTH) {
                                if (playerDir == Direction.NORTH) {
                                    mutable.move(Direction.DOWN);
                                    canGoDown = true;
                                } else if (playerDir != Direction.SOUTH) break;
                            } else if (offsetShape == RailShape.ASCENDING_EAST) {
                                if (playerDir == Direction.WEST) {
                                    mutable.move(Direction.DOWN);
                                    canGoDown = true;
                                } else if (playerDir != Direction.EAST) break;
                            } else if (offsetShape == RailShape.ASCENDING_WEST) {
                                if (playerDir == Direction.EAST) {
                                    mutable.move(Direction.DOWN);
                                    canGoDown = true;
                                } else if (playerDir != Direction.WEST) break;
                            }

                            if (!canGoDown) {
                                // Lazily copied from below bc i SUCK
                                if (state2.is(BlockTags.REPLACEABLE)) {
                                    // Place a rail
                                    BlockState state3 = ((BlockItem) stack.getItem()).getBlock().defaultBlockState();
                                    placedShape = switch (playerDir) {
                                        case Direction.NORTH, Direction.SOUTH -> RailShape.NORTH_SOUTH;
                                        case Direction.EAST, Direction.WEST -> RailShape.EAST_WEST;
                                        default -> null;
                                    };
                                    if (state3.hasProperty(RailBlock.SHAPE))
                                        state3 = state3.setValue(RailBlock.SHAPE, placedShape);
                                    else if (state3.hasProperty(PoweredRailBlock.SHAPE))
                                        state3 = state3.setValue(PoweredRailBlock.SHAPE, placedShape);
                                    else if (state3.hasProperty(DetectorRailBlock.SHAPE))
                                        state3 = state3.setValue(DetectorRailBlock.SHAPE, placedShape);
                                    if (state3.canSurvive(level, mutable)) {
                                        SoundType soundtype = state3.getSoundType(level, mutable, player);
                                        level.playSound(player, mutable, soundtype.getPlaceSound(), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                                        stack.consume(1, player);
                                        level.setBlockAndUpdate(mutable, state3);
                                        event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide()));
                                        event.setCanceled(true);
                                        break;
                                    }
                                } else {
                                    break;
                                }
                            }
                        } else if (state2.isFaceSturdy(level, mutable, Direction.UP, SupportType.RIGID) && state5.is(BlockTags.REPLACEABLE)) {
                            // Place a rail above
                            BlockState state3 = ((BlockItem) stack.getItem()).getBlock().defaultBlockState();
                            placedShape = switch (playerDir) {
                                case Direction.NORTH, Direction.SOUTH -> RailShape.NORTH_SOUTH;
                                case Direction.EAST, Direction.WEST -> RailShape.EAST_WEST;
                                default -> null;
                            };
                            if (state3.hasProperty(RailBlock.SHAPE))
                                state3 = state3.setValue(RailBlock.SHAPE, placedShape);
                            else if (state3.hasProperty(PoweredRailBlock.SHAPE))
                                state3 = state3.setValue(PoweredRailBlock.SHAPE, placedShape);
                            else if (state3.hasProperty(DetectorRailBlock.SHAPE))
                                state3 = state3.setValue(DetectorRailBlock.SHAPE, placedShape);
                            if (state3.canSurvive(level, mutable.immutable().above())) {
                                SoundType soundtype = state3.getSoundType(level, mutable.immutable().above(), player);
                                level.playSound(player, mutable.immutable().above(), soundtype.getPlaceSound(), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                                stack.consume(1, player);
                                level.setBlockAndUpdate(mutable.immutable().above(), state3);
                                event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide()));
                                event.setCanceled(true);
                                break;
                            }
                        } else if (state6.isFaceSturdy(level, mutable.immutable().below(2), Direction.UP, SupportType.RIGID) && state7.isFaceSturdy(level, mutable.immutable().below().relative(playerDir.getOpposite()), playerDir, SupportType.RIGID) && state4.is(BlockTags.REPLACEABLE)) {
                            // Place a rail below
                            BlockState state3 = ((BlockItem) stack.getItem()).getBlock().defaultBlockState();
                            placedShape = switch (playerDir) {
                                case Direction.NORTH, Direction.SOUTH -> RailShape.NORTH_SOUTH;
                                case Direction.EAST, Direction.WEST -> RailShape.EAST_WEST;
                                default -> null;
                            };
                            if (state3.hasProperty(RailBlock.SHAPE))
                                state3 = state3.setValue(RailBlock.SHAPE, placedShape);
                            else if (state3.hasProperty(PoweredRailBlock.SHAPE))
                                state3 = state3.setValue(PoweredRailBlock.SHAPE, placedShape);
                            else if (state3.hasProperty(DetectorRailBlock.SHAPE))
                                state3 = state3.setValue(DetectorRailBlock.SHAPE, placedShape);
                            if (state3.canSurvive(level, mutable.immutable().below())) {
                                SoundType soundtype = state3.getSoundType(level, mutable.immutable().below(), player);
                                level.playSound(player, mutable.immutable().below(), soundtype.getPlaceSound(), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                                stack.consume(1, player);
                                level.setBlockAndUpdate(mutable.immutable().below(), state3);
                                event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide()));
                                event.setCanceled(true);
                                break;
                            }
                        } else if (state2.is(BlockTags.REPLACEABLE)) {
                            // Place a rail
                            BlockState state3 = ((BlockItem)stack.getItem()).getBlock().defaultBlockState();
                            placedShape = switch (playerDir) {
                                case Direction.NORTH, Direction.SOUTH -> RailShape.NORTH_SOUTH;
                                case Direction.EAST, Direction.WEST -> RailShape.EAST_WEST;
                                default -> null;
                            };
                            if (state3.hasProperty(RailBlock.SHAPE))
                                state3 = state3.setValue(RailBlock.SHAPE, placedShape);
                            else if (state3.hasProperty(PoweredRailBlock.SHAPE))
                                state3 = state3.setValue(PoweredRailBlock.SHAPE, placedShape);
                            else if (state3.hasProperty(DetectorRailBlock.SHAPE))
                                state3 = state3.setValue(DetectorRailBlock.SHAPE, placedShape);
                            if (state3.canSurvive(level, mutable)) {
                                SoundType soundtype = state3.getSoundType(level, mutable, player);
                                level.playSound(player, mutable, soundtype.getPlaceSound(), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                                stack.consume(1, player);
                                level.setBlockAndUpdate(mutable, state3);
                                event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide()));
                                event.setCanceled(true);
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
            }
        }

        @SubscribeEvent
        public static void onExplosion(ExplosionEvent.Detonate event) {
            Explosion explosion = event.getExplosion();
            Level level = event.getLevel();

            event.getAffectedBlocks().forEach(pos -> {
                BlockState state = level.getBlockState(pos);
                if (state.getBlock() instanceof TorchBlock && !(state.getBlock() instanceof ExtinguishedTorchBlock)) {
                    level.gameEvent(explosion.getDirectSourceEntity(), GameEvent.BLOCK_CHANGE, pos);
                    level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);

                    if (state.is(Blocks.TORCH)) level.setBlock(pos, NMLBlocks.EXTINGUISHED_TORCH.get().withPropertiesOf(state), 11);
                    if (state.is(Blocks.WALL_TORCH)) level.setBlock(pos, NMLBlocks.EXTINGUISHED_WALL_TORCH.get().withPropertiesOf(state), 11);
                    if (state.is(Blocks.SOUL_TORCH)) level.setBlock(pos, NMLBlocks.EXTINGUISHED_SOUL_TORCH.get().withPropertiesOf(state), 11);
                    if (state.is(Blocks.SOUL_WALL_TORCH)) level.setBlock(pos, NMLBlocks.EXTINGUISHED_SOUL_WALL_TORCH.get().withPropertiesOf(state), 11);
                }
            });
        }

        @SubscribeEvent
        public static void onFarmlandTrample(BlockEvent.FarmlandTrampleEvent event) {
            if (!NMLConfig.TRAMPLING.get()) event.setCanceled(true);
        }

        @SubscribeEvent
        public static void onBlockGrow(BlockGrowFeatureEvent event) {
            ResourceKey<ConfiguredFeature<?, ?>> feature = event.getFeature().getKey();
            List<ResourceKey<ConfiguredFeature<?, ?>>> regularOakFeatures = List.of(
                    TreeFeatures.OAK,
                    TreeFeatures.OAK_BEES_0002,
                    TreeFeatures.OAK_BEES_002,
                    TreeFeatures.OAK_BEES_005
            );
            List<ResourceKey<ConfiguredFeature<?, ?>>> fancyOakFeatures = List.of(
                    TreeFeatures.FANCY_OAK,
                    TreeFeatures.FANCY_OAK_BEES_0002,
                    TreeFeatures.FANCY_OAK_BEES_002,
                    TreeFeatures.FANCY_OAK_BEES_005
            );
            List<ResourceKey<ConfiguredFeature<?, ?>>> autumnalOakFeatures = List.of(
                    NMLFeatures.AUTUMNAL_OAK,
                    NMLFeatures.LARGE_AUTUMNAL_OAK
            );
            List<ResourceKey<ConfiguredFeature<?, ?>>> spruceFeatures = List.of(
                    TreeFeatures.SPRUCE,
                    TreeFeatures.MEGA_SPRUCE,
                    TreeFeatures.PINE,
                    TreeFeatures.MEGA_PINE
            );
            List<ResourceKey<ConfiguredFeature<?, ?>>> pineFeatures = List.of(
                    NMLFeatures.PINE,
                    NMLFeatures.LARGE_PINE
            );

            BlockPos pos = event.getPos();
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();
            LevelAccessor level = event.getLevel();
            int fruit = 0;
            RandomSource random = event.getRandom();

            boolean apple = regularOakFeatures.contains(feature) || fancyOakFeatures.contains(feature);
            boolean pear = autumnalOakFeatures.contains(feature);

            if (apple || pear) {
                Iterator<BlockPos> it = BlockPos.betweenClosedStream(x - 8, y - 12, z - 8, x + 8, y + 12, z + 8).iterator();
                while (it.hasNext()) {
                    BlockPos bp = it.next();
                    BlockState state = level.getBlockState(bp);
                    if (apple && state.is(NMLBlocks.APPLE_FRUIT)) fruit++;
                    if (pear && state.is(NMLBlocks.PEAR_FRUIT)) fruit++;
                }
                if (fruit >= 12) {
                    if (regularOakFeatures.contains(feature)) event.setFeature(NMLFeatures.OAK_APPLE_05);
                    if (fancyOakFeatures.contains(feature)) event.setFeature(NMLFeatures.FANCY_OAK_APPLE_05);
                    if (feature == NMLFeatures.AUTUMNAL_OAK) event.setFeature(NMLFeatures.AUTUMNAL_OAK_PEAR_05);
                    if (feature == NMLFeatures.LARGE_AUTUMNAL_OAK) event.setFeature(NMLFeatures.LARGE_AUTUMNAL_OAK_PEAR_05);
                }
                else if (fruit > 0) {
                    if (regularOakFeatures.contains(feature)) event.setFeature(NMLFeatures.OAK_APPLE_01);
                    if (fancyOakFeatures.contains(feature)) event.setFeature(NMLFeatures.FANCY_OAK_APPLE_01);
                }
            }

            if ((spruceFeatures.contains(feature) || pineFeatures.contains(feature)) && level.getLevelData().isRaining() && !level.getBiome(pos).value().warmEnoughToRain(pos)) {
                if (spruceFeatures.contains(feature)) {
                    if (feature == TreeFeatures.SPRUCE) event.setFeature(NMLFeatures.FROSTED_SPRUCE);
                    if (feature == TreeFeatures.MEGA_SPRUCE) event.setFeature(NMLFeatures.MEGA_FROSTED_SPRUCE);
                    if (feature == TreeFeatures.PINE) event.setFeature(NMLFeatures.FROSTED_SPRUCE_ALT);
                    if (feature == TreeFeatures.MEGA_PINE) event.setFeature(NMLFeatures.MEGA_FROSTED_SPRUCE_ALT);
                }
                if (pineFeatures.contains(feature)) event.setFeature(NMLFeatures.FROSTED_PINE);
            }
        }
    }

    @EventBusSubscriber(modid = NoMansLand.MODID, bus = EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents {
        @SubscribeEvent
        public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
            //    TODO: BURIED AND MOOSE
//            event.put(NMLEntities.BURIED.get(), BuriedEntity.createAttributes().build());
//            event.put(NMLEntities.MOOSE.get(), MooseEntity.createAttributes().build());
            event.put(NMLEntities.BILLHOOK_BASS.get(), BillhookBass.createAttributes().build());
            event.put(NMLEntities.DEER.get(), Deer.createAttributes().build());
        }

        @SubscribeEvent
        public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
            event.register(NMLEntities.BILLHOOK_BASS.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BillhookBass::checkSurfaceWaterAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
            event.register(NMLEntities.DEER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Deer::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
            event.register(EntityType.CAMEL, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Camel::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        }
    }
}
