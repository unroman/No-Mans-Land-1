package com.farcr.nomansland.common.world.feature.decorator;

import com.farcr.nomansland.common.registry.blocks.NMLBlocks;
import com.farcr.nomansland.common.registry.worldgen.NMLTreeDecoratorTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class HugeShelfMushroomDecorator extends TreeDecorator {
    public static final MapCodec<HugeShelfMushroomDecorator> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter(f -> f.probability),
                    Codec.intRange(0, 16).fieldOf("limit").forGetter(f -> f.limit),
                    Codec.intRange(0, 16).fieldOf("exclusion_bottom").forGetter(f -> f.exclusionBottom),
                    Codec.intRange(0, 16).fieldOf("exclusion_top").forGetter(f -> f.exclusionTop)
            ).apply(instance, HugeShelfMushroomDecorator::new)
    );
    protected final float probability;
    protected final int limit;
    protected final int exclusionBottom;
    protected final int exclusionTop;

    public HugeShelfMushroomDecorator(float probability, int limit, int exclusionBottom, int exclusionTop) {
        this.probability = probability;
        this.limit =  limit;
        this.exclusionBottom = exclusionBottom;
        this.exclusionTop = exclusionTop;
    }

    @Override
    protected TreeDecoratorType<?> type() { return NMLTreeDecoratorTypes.HUGE_SHELF_MUSHROOM.get(); }

    @Override
    public void place(Context context) {
        Set<BlockPos> excludedPositions = new HashSet<>();
        int logs = 0;
        RandomSource random = context.random();
        Optional<BlockPos> bottomLog = (context.logs().stream().min(Comparator.comparingInt(Vec3i::getY)));
        Optional<BlockPos> topLog = (context.logs().stream().max(Comparator.comparingInt(Vec3i::getY)));
        int minY = bottomLog.map(Vec3i::getY).orElse(-65);
        int maxY = topLog.map(Vec3i::getY).orElse(-65);

        for (BlockPos pos : Util.shuffledCopy(context.logs(), random)) {
            if (pos.getY() - minY >= exclusionBottom && maxY - pos.getY() >= exclusionTop && random.nextFloat() < this.probability) {
                if (!excludedPositions.contains(pos)) {
                    Direction dir = null;
                    for (Direction dirTemp : Direction.Plane.HORIZONTAL.shuffledCopy(random)) {
                        if (context.isAir(pos.relative(dirTemp)) && context.isAir(pos.relative(dirTemp.getClockWise()))
                        && context.isAir(pos.relative(dirTemp).relative(dirTemp.getClockWise()))) {
                            dir = dirTemp;
                            break;
                        }
                    }
                    if (dir != null) {
                        context.setBlock(pos.relative(dir), NMLBlocks.SHELF_MUSHROOM_BLOCK.get().defaultBlockState());
                        context.setBlock(pos.relative(dir.getClockWise()), NMLBlocks.SHELF_MUSHROOM_BLOCK.get().defaultBlockState());
                        context.setBlock(pos.relative(dir).relative(dir.getClockWise()), NMLBlocks.SHELF_MUSHROOM_BLOCK.get().defaultBlockState());
                        excludedPositions.add(pos);
                        logs++;
                    }
                }
            }
            if (logs >= limit) break;
        }
    }
}
