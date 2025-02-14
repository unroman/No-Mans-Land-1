package com.farcr.nomansland.common.registry.worldgen;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.world.structure.processor.MossifyProcessor;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NMLStrucureProcessorTypes {
    public static final DeferredRegister<StructureProcessorType<?>> STRUCTURE_PROCESSOR_TYPES =
            DeferredRegister.create(Registries.STRUCTURE_PROCESSOR, NoMansLand.MODID);

    public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<MossifyProcessor>> MOSSIFY = register("mossify", MossifyProcessor.CODEC);

    private static <P extends StructureProcessor> DeferredHolder<StructureProcessorType<?>, StructureProcessorType<P>> register (String name, MapCodec<P> codec) {
        return STRUCTURE_PROCESSOR_TYPES.register(name, () -> () -> codec);
    }
}
