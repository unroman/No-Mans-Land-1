package com.farcr.nomansland.common.world.structure.processor;

import com.farcr.nomansland.common.registry.worldgen.NMLStructureProcessorTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class PrefixProcessor extends StructureProcessor {

    public static final MapCodec<PrefixProcessor> CODEC = RecordCodecBuilder.mapCodec((instance) ->
            instance.group(
                    Codec.STRING.fieldOf("prefix").forGetter((prefixProcessor -> prefixProcessor.prefix)),
                    Codec.floatRange(0.0F, 1.0F).fieldOf("chance").forGetter((prefixProcessor) -> prefixProcessor.chance)
                    )
                    .apply(instance, PrefixProcessor::new));

    private final float chance;
    private final String prefix;

    @Override
    protected StructureProcessorType<?> getType() {
        return NMLStructureProcessorTypes.PREFIX.get();
    }

    public PrefixProcessor(String prefix, float chance) {
        this.prefix = prefix;
        this.chance = chance;
    }

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo process(LevelReader levelReader, BlockPos offset, BlockPos pos, StructureTemplate.StructureBlockInfo blockInfo, StructureTemplate.StructureBlockInfo relativeBlockInfo, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
        BlockState state = relativeBlockInfo.state();

        Registry<Block> blockRegistry = levelReader.registryAccess().registryOrThrow(Registries.BLOCK);
        Optional<Block> block = blockRegistry.getOptional(blockRegistry.getKey(state.getBlock()).withPrefix(prefix + "_"));

        if (block.isPresent() && settings.getRandom(relativeBlockInfo.pos()).nextFloat() < chance) {
            BlockState prefixedBlockState = block.get().withPropertiesOf(state);
            return new StructureTemplate.StructureBlockInfo(relativeBlockInfo.pos(), prefixedBlockState, relativeBlockInfo.nbt());
        }

        return relativeBlockInfo;
    }
}
