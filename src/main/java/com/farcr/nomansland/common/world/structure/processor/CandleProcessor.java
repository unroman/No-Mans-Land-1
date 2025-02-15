package com.farcr.nomansland.common.world.structure.processor;

import com.farcr.nomansland.common.registry.worldgen.NMLStructureProcessorTypes;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

public class CandleProcessor extends StructureProcessor {

    public static final MapCodec<CandleProcessor> CODEC = MapCodec.unit(CandleProcessor::new);

    @Override
    protected StructureProcessorType<?> getType() {
        return NMLStructureProcessorTypes.CANDLES.get();
    }

    public CandleProcessor() {
    }

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo process(LevelReader levelReader, BlockPos offset, BlockPos pos, StructureTemplate.StructureBlockInfo blockInfo, StructureTemplate.StructureBlockInfo relativeBlockInfo, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
        RandomSource random = settings.getRandom(relativeBlockInfo.pos());
        BlockState state = relativeBlockInfo.state();

        if (state.hasProperty(CandleBlock.CANDLES)) {
            return new StructureTemplate.StructureBlockInfo(relativeBlockInfo.pos(), state.setValue(CandleBlock.CANDLES, random.nextIntBetweenInclusive(1, 4)), relativeBlockInfo.nbt());
        }

        return relativeBlockInfo;
    }
}
