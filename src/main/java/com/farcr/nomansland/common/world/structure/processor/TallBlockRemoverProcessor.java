package com.farcr.nomansland.common.world.structure.processor;

import com.farcr.nomansland.common.registry.worldgen.NMLStructureProcessorTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
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
import java.util.List;

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
        return NMLStructureProcessorTypes.TALL_BLOCK_REMOVER.get();
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
        List<StructureTemplate.StructureBlockInfo> finalBlockInfos = new ArrayList<>(List.copyOf(processedBlockInfos));

        for (StructureTemplate.StructureBlockInfo blockInfo : processedBlockInfos) {
            BlockState state = blockInfo.state();
            if (state.is(block) && state.hasProperty(DoublePlantBlock.HALF) && state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER) {
                for (StructureTemplate.StructureBlockInfo blockInfo1 : processedBlockInfos) {
                    if (blockInfo.pos().equals(blockInfo1.pos().above()) && !blockInfo1.state().is(block)) {
                        finalBlockInfos.remove(blockInfo);
                        finalBlockInfos.add(new StructureTemplate.StructureBlockInfo(blockInfo.pos(), Blocks.AIR.defaultBlockState(), blockInfo.nbt()));
                        break;
                    }
                }
            }
        }

        return finalBlockInfos;
    }
}
