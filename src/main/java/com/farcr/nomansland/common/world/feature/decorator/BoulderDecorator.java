package com.farcr.nomansland.common.world.feature.decorator;

import com.farcr.nomansland.common.registry.NMLRegistries;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;

import java.util.Comparator;
import java.util.Set;
import java.util.function.BiConsumer;

public abstract class BoulderDecorator {
    public static final Codec<BoulderDecorator> CODEC;

    public BoulderDecorator() {}

    protected abstract BoulderDecoratorType<?> type();

    public abstract void place(Context var1);

    static {
        CODEC = NMLRegistries.BOULDER_DECORATOR_TYPE.byNameCodec().dispatch(BoulderDecorator::type, BoulderDecoratorType::codec);
    }

    public static final class Context {
        private final WorldGenLevel level;
        private final BiConsumer<BlockPos, BlockState> decorationSetter;
        private final RandomSource random;
        private final ObjectArrayList<BlockPos> stone;
        private final ChunkGenerator chunkGen;

        public Context(WorldGenLevel level, BiConsumer<BlockPos, BlockState> decorationSetter, RandomSource random, Set<BlockPos> stone, ChunkGenerator chunkGen) {
            this.level = level;
            this.decorationSetter = decorationSetter;
            this.random = random;
            this.stone = new ObjectArrayList<>(stone);
            this.stone.sort(Comparator.comparingInt(Vec3i::getY));
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

        public ObjectArrayList<BlockPos> stone() {
            return this.stone;
        }

        public ChunkGenerator chunkGenerator() { return this.chunkGen; }
    }
}
