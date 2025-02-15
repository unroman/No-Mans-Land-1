package com.farcr.nomansland.common.world.structure.processor;

import com.farcr.nomansland.common.registry.worldgen.NMLStructureProcessorTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.calculateRelativePosition;

public class TallBlockRemoverProcessor extends StructureProcessor {

    public static final MapCodec<TallBlockRemoverProcessor> CODEC = RecordCodecBuilder.mapCodec((instance) ->
            instance.group(
                    BuiltInRegistries.BLOCK.byNameCodec().fieldOf("block").forGetter((prefixProcessor -> prefixProcessor.block)),
                    Codec.floatRange(0.0F, 1.0F).fieldOf("chance").forGetter((prefixProcessor) -> prefixProcessor.chance)
                    )
                    .apply(instance, TallBlockRemoverProcessor::new));

    private final float chance;
    private final Block block;

    @Override
    protected StructureProcessorType<?> getType() {
        return NMLStructureProcessorTypes.PREFIX.get();
    }

    public TallBlockRemoverProcessor(Block block, float chance) {
        this.block = block;
        this.chance = chance;
    }

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo process(LevelReader levelReader, BlockPos offset, BlockPos pos, StructureTemplate.StructureBlockInfo blockInfo, StructureTemplate.StructureBlockInfo relativeBlockInfo, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
        BlockState state = relativeBlockInfo.state();

        if (state.is(block) && state.hasProperty(DoublePlantBlock.HALF) && state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER && settings.getRandom(relativeBlockInfo.pos()).nextFloat() < chance) 
            return new StructureTemplate.StructureBlockInfo(relativeBlockInfo.pos(), state.getValue(BlockStateProperties.WATERLOGGED) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState(), relativeBlockInfo.nbt());

        return relativeBlockInfo;
    }

    @Override
    public List<StructureTemplate.StructureBlockInfo> finalizeProcessing(ServerLevelAccessor serverLevel, BlockPos offset, BlockPos pos, List<StructureTemplate.StructureBlockInfo> originalBlockInfos, List<StructureTemplate.StructureBlockInfo> processedBlockInfos, StructurePlaceSettings settings) {

//        for (StructureTemplate.StructureBlockInfo processedBlockinfo : processedBlockInfos) {
//            BlockState state = processedBlockinfo.state();
//            if (state.is(block) && state.hasProperty(DoublePlantBlock.HALF)) {
//                for (StructureTemplate.StructureBlockInfo processedBlockinfo1 : processedBlockInfos) {
//                    if (processedBlockinfo1.pos() == processedBlockinfo.pos().relative(state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER ? Direction.UP : Direction.DOWN, 1) && !processedBlockinfo1.state().is(block)) {
//                        finalBlockInfos.remove(processedBlockinfo);
//                        finalBlockInfos.add(new StructureTemplate.StructureBlockInfo(processedBlockinfo.pos(), state.getValue(BlockStateProperties.WATERLOGGED) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState(), processedBlockinfo.nbt()));
//                        break;
//                    }
//                }
//            }
//        }

        for (StructureTemplate.StructureBlockInfo originalBlockInfo : originalBlockInfos) {
//            for (StructureTemplate.StructureBlockInfo processedBlockinfo : processedBlockInfos) {
//                if (originalBlockInfo.equals(processedBlockinfo)) continue;

                BlockState state = originalBlockInfo.state();
                if (!state.is(block) || (state.hasProperty(DoublePlantBlock.HALF) && state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER))
                    continue;


                if (!serverLevel.getBlockState(originalBlockInfo.pos().offset(offset).below()).is(block)) {
                    processedBlockInfos.remove(originalBlockInfo);
                    processedBlockInfos.add(new StructureTemplate.StructureBlockInfo(originalBlockInfo.pos(), Blocks.RED_CONCRETE.defaultBlockState(), originalBlockInfo.nbt()));
                    break;
                }
//            }
        }

        return processedBlockInfos;
    }
}
