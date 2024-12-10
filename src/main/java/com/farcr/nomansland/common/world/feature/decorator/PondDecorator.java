//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.farcr.nomansland.common.world.feature.decorator;

import com.farcr.nomansland.common.registry.NMLRegistries;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Comparator;
import java.util.Set;
import java.util.function.BiConsumer;

public abstract class PondDecorator {
    public static final Codec<PondDecorator> CODEC;

    public PondDecorator() {
    }

    protected abstract PondDecoratorType<?> type();

    public abstract void place(Context var1);

    static {
        CODEC = NMLRegistries.POND_DECORATOR_TYPE.byNameCodec().dispatch(PondDecorator::type, PondDecoratorType::codec);
    }

    public static final class Context {
        private final LevelReader level;
        private final BiConsumer<BlockPos, BlockState> decorationSetter;
        private final RandomSource random;
        private final ObjectArrayList<BlockPos> water;

        public Context(LevelReader level, BiConsumer<BlockPos, BlockState> decorationSetter, RandomSource random, Set<BlockPos> water) {
            this.level = level;
            this.decorationSetter = decorationSetter;
            this.random = random;
            this.water = new ObjectArrayList(water);
            this.water.sort(Comparator.comparingInt(Vec3i::getY));
        }

        public void setBlock(BlockPos pos, BlockState state) {
            this.decorationSetter.accept(pos, state);
        }

        public boolean isAir(BlockPos pos) {
            return simulated().isStateAtPosition(pos, BlockBehaviour.BlockStateBase::isAir);
        }

        public LevelReader level() {
            return this.level;
        }

        public LevelSimulatedReader simulated() { return (LevelSimulatedReader) this.level; }

        public RandomSource random() {
            return this.random;
        }

        public ObjectArrayList<BlockPos> water() {
            return this.water;
        }
    }
}
