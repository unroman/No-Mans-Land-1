package com.farcr.nomansland.common.world.structure.processor;

import com.farcr.nomansland.common.registry.worldgen.NMLStructureProcessorTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.ArrayList;
import java.util.List;

public class BlockRemoverProcessor extends StructureProcessor {

    public static final MapCodec<BlockRemoverProcessor> CODEC = RecordCodecBuilder.mapCodec((instance) ->
            instance.group(
                    BuiltInRegistries.BLOCK.byNameCodec().fieldOf("block").forGetter((prefixProcessor -> prefixProcessor.block)),
                    Codec.floatRange(0.0F, 1.0F).fieldOf("chance").forGetter((prefixProcessor) -> prefixProcessor.chance)
                    )
                    .apply(instance, BlockRemoverProcessor::new));

    private final float chance;
    private final Block block;

    @Override
    protected StructureProcessorType<?> getType() {
        return NMLStructureProcessorTypes.PREFIX.get();
    }

    public BlockRemoverProcessor(Block block, float chance) {
        this.block = block;
        this.chance = chance;
    }

    @Override
    public List<StructureTemplate.StructureBlockInfo> finalizeProcessing(ServerLevelAccessor serverLevel, BlockPos offset, BlockPos pos, List<StructureTemplate.StructureBlockInfo> originalBlockInfos, List<StructureTemplate.StructureBlockInfo> processedBlockInfos, StructurePlaceSettings settings) {

        List<StructureTemplate.StructureBlockInfo> finalBlockInfos = new ArrayList<>(List.copyOf(processedBlockInfos));

        if (settings.getRandom(pos).nextFloat() < chance) {
            for (StructureTemplate.StructureBlockInfo blockInfo : processedBlockInfos) {
                if (block == Blocks.WATER && blockInfo.state().hasProperty(BlockStateProperties.WATERLOGGED)) {
                    finalBlockInfos.remove(blockInfo);
                    finalBlockInfos.add(new StructureTemplate.StructureBlockInfo(blockInfo.pos(), blockInfo.state().setValue(BlockStateProperties.WATERLOGGED, false), blockInfo.nbt()));
                }

                if (blockInfo.state().is(block)) {
                    finalBlockInfos.remove(blockInfo);
                    finalBlockInfos.add(new StructureTemplate.StructureBlockInfo(blockInfo.pos(), Blocks.AIR.defaultBlockState(), blockInfo.nbt()));
                }
            }
        }

        return finalBlockInfos;
    }
}
