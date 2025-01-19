package com.farcr.nomansland.common.world.feature.decorator;

import com.farcr.nomansland.common.registry.NMLRegistries;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;

import java.util.Set;
import java.util.function.BiConsumer;

public abstract class FallenTreeDecorator {
    public static final Codec<FallenTreeDecorator> CODEC;

    public FallenTreeDecorator() {}

    protected abstract FallenTreeDecoratorType<?> type();

    public abstract void place(Context context);

    static {
        CODEC = NMLRegistries.FALLEN_TREE_DECORATOR_TYPE.byNameCodec().dispatch(FallenTreeDecorator::type, FallenTreeDecoratorType::codec);
    }

    public static final class Context {
        private final WorldGenLevel level;
        private final BiConsumer<BlockPos, BlockState> decorationSetter;
        private final RandomSource random;
        private final ObjectArrayList<BlockPos> logs;
        private final ChunkGenerator chunkGen;

        public Context(WorldGenLevel level, BiConsumer<BlockPos, BlockState> decorationSetter, RandomSource random, Set<BlockPos> logs, ChunkGenerator chunkGen) {
            this.level = level;
            this.decorationSetter = decorationSetter;
            this.random = random;
            this.logs = new ObjectArrayList<>(logs);
            this.chunkGen = chunkGen;
        }

        public void setBlock(BlockPos pos, BlockState state) {
            this.decorationSetter.accept(pos, state);
        }

        public boolean isAir(BlockPos pos) {
            return levelSimulatedReader().isStateAtPosition(pos, BlockBehaviour.BlockStateBase::isAir);
        }

        public WorldGenLevel level() {
            return this.level;
        }
        public LevelReader levelReader() {
            return this.level;
        }

        public LevelSimulatedReader levelSimulatedReader() { return this.level; }

        public RandomSource random() {
            return this.random;
        }

        public ObjectArrayList<BlockPos> logs() {
            return this.logs;
        }

        public ChunkGenerator chunkGenerator() { return this.chunkGen; }
    }
}
