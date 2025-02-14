package com.farcr.nomansland.common.world.structure.processor;

import com.farcr.nomansland.common.registry.worldgen.NMLStrucureProcessorTypes;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class MossifyProcessor extends StructureProcessor {

    public static final MapCodec<MossifyProcessor> CODEC = RecordCodecBuilder.mapCodec((instance) ->
            instance.group(Codec.floatRange(0.0F, 1.0F).fieldOf("integrity").forGetter((mossifyProcessor) -> mossifyProcessor.chance))
                    .apply(instance, MossifyProcessor::new));

    private final float chance;
    private final Map<Block, Block> replacements = Util.make(Maps.newHashMap(), map -> {
        map.put(Blocks.COBBLESTONE, Blocks.MOSSY_COBBLESTONE);
        map.put(Blocks.COBBLESTONE_SLAB, Blocks.MOSSY_COBBLESTONE_SLAB);
        map.put(Blocks.COBBLESTONE_STAIRS, Blocks.MOSSY_COBBLESTONE_STAIRS);
        map.put(Blocks.COBBLESTONE_WALL, Blocks.MOSSY_COBBLESTONE_WALL);
        map.put(Blocks.STONE_BRICKS, Blocks.MOSSY_STONE_BRICKS);
        map.put(Blocks.STONE_BRICK_SLAB, Blocks.MOSSY_STONE_BRICK_SLAB);
        map.put(Blocks.STONE_BRICK_STAIRS, Blocks.MOSSY_STONE_BRICK_STAIRS);
        map.put(Blocks.STONE_BRICK_WALL, Blocks.MOSSY_STONE_BRICK_WALL);

    });

    @Override
    protected StructureProcessorType<?> getType() {
        return NMLStrucureProcessorTypes.MOSSIFY.get();
    }

    public MossifyProcessor(float chance) {
        this.chance = chance;
    }

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo process(LevelReader levelReader, BlockPos offset, BlockPos pos, StructureTemplate.StructureBlockInfo blockInfo, StructureTemplate.StructureBlockInfo relativeBlockInfo, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
        BlockState blockstate = relativeBlockInfo.state();
        Block block = this.replacements.get(blockstate.getBlock());

        if (block != null && settings.getRandom(relativeBlockInfo.pos()).nextFloat() < chance) {
            BlockState mossyBlockState = block.withPropertiesOf(blockstate);
            return new StructureTemplate.StructureBlockInfo(relativeBlockInfo.pos(), mossyBlockState, relativeBlockInfo.nbt());

        }

        return relativeBlockInfo;
    }
}
