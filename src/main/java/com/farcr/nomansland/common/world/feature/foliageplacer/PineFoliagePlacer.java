package com.farcr.nomansland.common.world.feature.foliageplacer;

import com.farcr.nomansland.common.registry.worldgen.NMLFoliagePlacerTypes;
import com.google.common.collect.Sets;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class PineFoliagePlacer extends FoliagePlacer {
    public static final MapCodec<PineFoliagePlacer> CODEC;

    private final IntProvider offsetIncrease;
    private final IntProvider numSmallCanopies;
    private final FloatProvider leafProbability;


    public PineFoliagePlacer(IntProvider radius, IntProvider offset, IntProvider offsetIncrease, IntProvider numSmallCanopies, FloatProvider leafProbability) {
        super(radius, offset);
        this.offsetIncrease = offsetIncrease;
        this.numSmallCanopies = numSmallCanopies;
        this.leafProbability = leafProbability;
    }

    protected @NotNull FoliagePlacerType<?> type() {
        return NMLFoliagePlacerTypes.PINE_FOLIAGE_PLACER.get();
    }

    protected void createFoliage(@NotNull LevelSimulatedReader level, @NotNull FoliageSetter blockSetter, @NotNull RandomSource random, @NotNull TreeConfiguration config, int maxFreeTreeHeight, @NotNull FoliageAttachment attachment, int foliageHeight, int foliageRadius, int foliageOffset) {
        Set<BlockPos> leafPositions = Sets.newHashSet();
        Set<BlockPos> probLeafPositions = Sets.newHashSet();

        int numCanopies = (foliageHeight - 5) / 2;
        // Add tree topper
        for (int di = 0; di < 4; di++) {
            Direction d = Direction.from2DDataValue(di);
            leafPositions.add(attachment.pos().below(2).relative(d));
            leafPositions.add(attachment.pos().below(2).relative(d).relative(d.getClockWise()));
            leafPositions.add(attachment.pos().below().relative(d));
            probLeafPositions.add(attachment.pos().below().relative(d).relative(d.getClockWise()));
            leafPositions.add(attachment.pos().relative(d));
        }
        leafPositions.add(attachment.pos());
        leafPositions.add(attachment.pos().above());
        leafPositions.add(attachment.pos().above(2));
        probLeafPositions.add(attachment.pos().above(3));

        int numSmallCanopiesSampled = numSmallCanopies.sample(random);
        for (int i = 0; i < (Math.min(numCanopies, numSmallCanopiesSampled)); i++) {
            Set<BlockPos> layerPositions = placeLayer(attachment.pos().below(4 + 2*i), foliageRadius);
            leafPositions.addAll(layerPositions);
        }
        if (numCanopies >= numSmallCanopiesSampled) {
            for (int i = 0; i < numCanopies - numSmallCanopiesSampled; i++) {
                Set<BlockPos> layerPositions = placeLayer(attachment.pos().below(4 + 2*numSmallCanopiesSampled + 2*i), foliageRadius + offsetIncrease.sample(random));
                leafPositions.addAll(layerPositions);
            }
        }

        leafPositions.add(attachment.pos().below(3 + 2*numCanopies).north());
        leafPositions.add(attachment.pos().below(3 + 2*numCanopies).east());
        leafPositions.add(attachment.pos().below(3 + 2*numCanopies).south());
        leafPositions.add(attachment.pos().below(3 + 2*numCanopies).west());

        // Place the foliage
        for (BlockPos leafPos : leafPositions) {
            tryPlaceLeaf(level, blockSetter, random, config, leafPos);
        }
        float leafProbabilitySampled = leafProbability.sample(random);
        for (BlockPos leafPos : probLeafPositions) {
            if (random.nextFloat() > leafProbabilitySampled)
                tryPlaceLeaf(level, blockSetter, random, config, leafPos);
        }
    }



    private Set<BlockPos> placeLayer(BlockPos localOrigin, int size) {
        Set<BlockPos> leafPositions = Sets.newHashSet();
        for (int x = -size; x <= size; x++) {
            for (int z = -size; z <= size; z++) {
                if (Math.abs(x) + Math.abs(z) < size + 2) {
                    leafPositions.add(localOrigin.offset(x, 0, z));
                }
                if (Math.abs(x) + Math.abs(z) < size) {
                    leafPositions.add(localOrigin.offset(x, 1, z));
                }
            }
        }
        return leafPositions;
    }

    public int foliageHeight(@NotNull RandomSource random, int height, @NotNull TreeConfiguration config) {
        return height;
    }

    protected boolean shouldSkipLocation(@NotNull RandomSource random, int localX, int localY, int localZ, int range, boolean large) {
        return false;
    }

    static {
        CODEC = RecordCodecBuilder.mapCodec((instance) -> {
            return foliagePlacerParts(instance).and(instance.group(IntProvider.codec(-4, 4).fieldOf("offset_increase").forGetter((tree) -> {
                return tree.offsetIncrease;
            }), IntProvider.codec(1, 8).fieldOf("num_small_canopies").forGetter((tree) -> {
                return tree.numSmallCanopies;
            }), FloatProvider.codec(0.0F, 1.0F).fieldOf("leaf_probability").forGetter((tree) -> {
                return tree.leafProbability;
            }))).apply(instance, PineFoliagePlacer::new);
        });
    }
}
