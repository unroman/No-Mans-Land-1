package com.farcr.nomansland.common.world.feature;

import com.farcr.nomansland.common.world.feature.decorator.BoulderDecorator;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
import java.util.function.BiConsumer;

public class BoulderFeature extends Feature<BoulderFeatureConfiguration> {
    public BoulderFeature(Codec<BoulderFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<BoulderFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();
        BoulderFeatureConfiguration config = context.config();

        int numCubes = config.numCubes().sample(random);

        ArrayList<BlockPos> stonePos = new ArrayList<>();
        ArrayList<BlockPos> decoPos = new ArrayList<>();

        BlockPos.MutableBlockPos cube_origin_pos = origin.mutable();
        BlockPos.MutableBlockPos pos = origin.mutable();

        for (int i = 0; i < numCubes; i++) {
            if (i > 0) {
                // set cube origin to a random stone block
                cube_origin_pos.set(stonePos.get(random.nextInt(stonePos.size())));
            }

            // find cube size, move cube randomly so that at least one block intersects the existing cubes
            int cubeSize = config.cubeSize().sample(random);
            if (random.nextFloat() < config.percentLargerCube()) cubeSize++;
            cube_origin_pos.move(random.nextInt(cubeSize - 1), random.nextInt(cubeSize - 1), random.nextInt(cubeSize - 1));
            if (cube_origin_pos.getY() < origin.getY() + config.heightMin()) {
                cube_origin_pos.setY(origin.getY() + config.heightMin());
            }
            if (cube_origin_pos.getY() > origin.getY() + config.heightMax() - cubeSize) {
                cube_origin_pos.setY(origin.getY() + config.heightMax() - cubeSize);
            }

            // make the cube
            ArrayList<BlockPos> cubePos = new ArrayList<>();
            for (int x = 0; x < cubeSize; x++) {
                for (int y = 0; y < cubeSize; y++) {
                    for (int z = 0; z < cubeSize; z++) {
                        pos.set(cube_origin_pos);
                        pos.move(x, y, z);
                        cubePos.add(pos.immutable());
                    }
                }
            }

            // erode random blocks from the cube with at least 3 adj air blocks
            int cubeErosion = config.cubeErosion().sample(random);
            if (config.cubeErosionSizeMultiplier() > 0) cubeErosion = (int) (cubeErosion * config.cubeErosionSizeMultiplier() * cubeSize * cubeSize * cubeSize);
            int erodedBlocks = 0;
            BlockPos posToRemove;
            // I really should refactor this into its own method since it's used 3 times
            // However I am lazy ¯\_(ツ)_/¯
            do {
                Util.shuffle(cubePos, random);
                posToRemove = null;
                for (BlockPos cpos : cubePos) {
                    int directionsWithoutBlocks = 0;
                    for (Direction d : Direction.values()) {
                        if (!cubePos.contains(cpos.relative(d))) {
                            directionsWithoutBlocks++;
                        } else if (d == Direction.UP) {
                            // at this point in generation don't erode stuff with blocks above, we're just shaving off the top
                            directionsWithoutBlocks = -10;
                        }
                    }
                    if (directionsWithoutBlocks >= 3) {
                        posToRemove = cpos;
                        break;
                    }
                }
                if (posToRemove != null) {
                    cubePos.remove(posToRemove);
                    erodedBlocks++;
                }
            } while (posToRemove != null && erodedBlocks < cubeErosion);

            // add the cube to the final block positions
            stonePos.addAll(cubePos);
        }

        // sort stonePos from lowest to highest y for gravity purposes
        stonePos.sort(Comparator.comparingInt(Vec3i::getY));

        // check all blocks for placeability & apply gravity
        ArrayList<BlockPos> stonePosPlaced = new ArrayList<>();
        for (BlockPos pos1 : stonePos) {
            if (level.getBlockState(pos1).is(BlockTags.REPLACEABLE)) {
                int i = 0;
                while (level.getBlockState(pos1.below(i)).is(BlockTags.REPLACEABLE) && !stonePosPlaced.contains(pos1.below(i)) && i < 10) {
                    i++;
                }
                stonePosPlaced.add(pos1.below(i - 1));
            }
        }

        // extra bit of final erosion
        BlockPos posToRemove;
        int erodedBlocks = 0;
        int erosion = config.extraErosion().sample(random);
        do {
            posToRemove = null;
            for (BlockPos cpos : stonePosPlaced) {
                int directionsWithoutBlocks = 0;
                for (Direction d : Direction.values()) {
                    if (!stonePosPlaced.contains(cpos.relative(d))) {
                        directionsWithoutBlocks++;
                    }
                }
                if (directionsWithoutBlocks >= 3) {
                    posToRemove = cpos;
                    break;
                }
            }
            if (posToRemove != null) {
                stonePosPlaced.remove(posToRemove);
                erodedBlocks++;
            }
        } while (posToRemove != null && erodedBlocks < erosion);

        // erode sharp edges
        do {
            posToRemove = null;
            for (BlockPos cpos : stonePosPlaced) {
                int directionsWithoutBlocks = 0;
                for (Direction d : Direction.values()) {
                    if (!stonePosPlaced.contains(cpos.relative(d))) {
                        directionsWithoutBlocks++;
                    }
                }
                if (directionsWithoutBlocks >= 5) {
                    posToRemove = cpos;
                    break;
                }
            }
            if (posToRemove != null) {
                stonePosPlaced.remove(posToRemove);
            }
        } while (posToRemove != null);

        if (stonePosPlaced.size() <= config.minimumSize()) {
            return false;
        }

        // place all blocks
        Set<BlockPos> stonePosSet = Sets.newHashSet();
        for (BlockPos pos1 : stonePosPlaced) {
            level.setBlock(pos1, config.blockProvider().getState(random, pos1), 2);
            stonePosSet.add(pos1);
        }


        BiConsumer<BlockPos, BlockState> decoratorConsumer = (pos1, state1) -> {
            decoPos.add(pos1.immutable());
            level.setBlock(pos1, state1, 19);
        };
        if (!config.decorators().isEmpty()) {
            BoulderDecorator.Context boulderdecorator$context = new BoulderDecorator.Context(level, decoratorConsumer, random, stonePosSet, context.chunkGenerator());
            config.decorators().forEach((deco) -> deco.place(boulderdecorator$context));
        }

        return true;
    }
}
