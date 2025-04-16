package com.farcr.nomansland.common.integration;

import com.farcr.nomansland.common.definitions.BlockDefinition;
import com.farcr.nomansland.common.registry.blocks.NMLBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import vectorwing.blockbox.common.block.PalisadeBlock;
import vectorwing.blockbox.common.block.SeatBlock;
import vectorwing.blockbox.common.block.SpikedPalisadeBlock;
import vectorwing.blockbox.common.registry.ModBlocks;

import static net.minecraft.world.level.block.state.BlockBehaviour.Properties.ofFullCopy;

public class BBIntegration {

    public static final BlockDefinition<SpikedPalisadeBlock> STRIPPED_SPIKED_MAPLE_PALISADE = NMLBlocks.register("stripped_spiked_maple_palisade", () -> new SpikedPalisadeBlock(ModBlocks.PROPERTIES_PALISADE.mapColor(MapColor.WOOD).sound(SoundType.WOOD)));
    public static final BlockDefinition<SpikedPalisadeBlock> SPIKED_MAPLE_PALISADE = NMLBlocks.register("spiked_maple_palisade", () -> new SpikedPalisadeBlock(STRIPPED_SPIKED_MAPLE_PALISADE::block, ModBlocks.PROPERTIES_PALISADE.mapColor(MapColor.WOOD).sound(SoundType.WOOD)));
    public static final BlockDefinition<PalisadeBlock> STRIPPED_MAPLE_PALISADE = NMLBlocks.register("stripped_maple_palisade", () -> new PalisadeBlock(STRIPPED_SPIKED_MAPLE_PALISADE::block, ModBlocks.PROPERTIES_PALISADE.mapColor(MapColor.WOOD).sound(SoundType.WOOD)));
    public static final BlockDefinition<PalisadeBlock> MAPLE_PALISADE = NMLBlocks.register("maple_palisade", () -> new PalisadeBlock(SPIKED_MAPLE_PALISADE::block, STRIPPED_MAPLE_PALISADE::block, ModBlocks.PROPERTIES_PALISADE.mapColor(MapColor.WOOD).sound(SoundType.WOOD)));

    public static final BlockDefinition<SpikedPalisadeBlock> STRIPPED_SPIKED_PINE_PALISADE = NMLBlocks.register("stripped_spiked_pine_palisade", () -> new SpikedPalisadeBlock(ModBlocks.PROPERTIES_PALISADE.mapColor(MapColor.WOOD).sound(SoundType.WOOD)));
    public static final BlockDefinition<SpikedPalisadeBlock> SPIKED_PINE_PALISADE = NMLBlocks.register("spiked_pine_palisade", () -> new SpikedPalisadeBlock(STRIPPED_SPIKED_PINE_PALISADE::block, ModBlocks.PROPERTIES_PALISADE.mapColor(MapColor.WOOD).sound(SoundType.WOOD)));
    public static final BlockDefinition<PalisadeBlock> STRIPPED_PINE_PALISADE = NMLBlocks.register("stripped_pine_palisade", () -> new PalisadeBlock(STRIPPED_SPIKED_PINE_PALISADE::block, ModBlocks.PROPERTIES_PALISADE.mapColor(MapColor.WOOD).sound(SoundType.WOOD)));
    public static final BlockDefinition<PalisadeBlock> PINE_PALISADE = NMLBlocks.register("pine_palisade", () -> new PalisadeBlock(SPIKED_PINE_PALISADE::block, STRIPPED_PINE_PALISADE::block, ModBlocks.PROPERTIES_PALISADE.mapColor(MapColor.WOOD).sound(SoundType.WOOD)));

    public static final BlockDefinition<SpikedPalisadeBlock> STRIPPED_SPIKED_WALNUT_PALISADE = NMLBlocks.register("stripped_spiked_walnut_palisade", () -> new SpikedPalisadeBlock(ModBlocks.PROPERTIES_PALISADE.mapColor(MapColor.WOOD).sound(SoundType.WOOD)));
    public static final BlockDefinition<SpikedPalisadeBlock> SPIKED_WALNUT_PALISADE = NMLBlocks.register("spiked_walnut_palisade", () -> new SpikedPalisadeBlock(STRIPPED_SPIKED_WALNUT_PALISADE::block, ModBlocks.PROPERTIES_PALISADE.mapColor(MapColor.WOOD).sound(SoundType.WOOD)));
    public static final BlockDefinition<PalisadeBlock> STRIPPED_WALNUT_PALISADE = NMLBlocks.register("stripped_walnut_palisade", () -> new PalisadeBlock(STRIPPED_SPIKED_WALNUT_PALISADE::block, ModBlocks.PROPERTIES_PALISADE.mapColor(MapColor.WOOD).sound(SoundType.WOOD)));
    public static final BlockDefinition<PalisadeBlock> WALNUT_PALISADE = NMLBlocks.register("walnut_palisade", () -> new PalisadeBlock(SPIKED_WALNUT_PALISADE::block, STRIPPED_WALNUT_PALISADE::block, ModBlocks.PROPERTIES_PALISADE.mapColor(MapColor.WOOD).sound(SoundType.WOOD)));

    public static final BlockDefinition<SpikedPalisadeBlock> STRIPPED_SPIKED_WILLOW_PALISADE = NMLBlocks.register("stripped_spiked_willow_palisade", () -> new SpikedPalisadeBlock(ModBlocks.PROPERTIES_PALISADE.mapColor(MapColor.WOOD).sound(SoundType.WOOD)));
    public static final BlockDefinition<SpikedPalisadeBlock> SPIKED_WILLOW_PALISADE = NMLBlocks.register("spiked_willow_palisade", () -> new SpikedPalisadeBlock(STRIPPED_SPIKED_WILLOW_PALISADE::block, ModBlocks.PROPERTIES_PALISADE.mapColor(MapColor.WOOD).sound(SoundType.WOOD)));
    public static final BlockDefinition<PalisadeBlock> STRIPPED_WILLOW_PALISADE = NMLBlocks.register("stripped_willow_palisade", () -> new PalisadeBlock(STRIPPED_SPIKED_WILLOW_PALISADE::block, ModBlocks.PROPERTIES_PALISADE.mapColor(MapColor.WOOD).sound(SoundType.WOOD)));
    public static final BlockDefinition<PalisadeBlock> WILLOW_PALISADE = NMLBlocks.register("willow_palisade", () -> new PalisadeBlock(SPIKED_WILLOW_PALISADE::block, STRIPPED_WILLOW_PALISADE::block, ModBlocks.PROPERTIES_PALISADE.mapColor(MapColor.WOOD).sound(SoundType.WOOD)));

    public static final BlockDefinition<SeatBlock> MAPLE_SEAT = NMLBlocks.register("maple_seat", () -> new SeatBlock(ofFullCopy(NMLBlocks.MAPLE.planks().block())));
    public static final BlockDefinition<SeatBlock> PINE_SEAT = NMLBlocks.register("pine_seat", () -> new SeatBlock(ofFullCopy(NMLBlocks.PINE.planks().block())));
    public static final BlockDefinition<SeatBlock> WALNUT_SEAT = NMLBlocks.register("walnut_seat", () -> new SeatBlock(ofFullCopy(NMLBlocks.WALNUT.planks().block())));
    public static final BlockDefinition<SeatBlock> WILLOW_SEAT = NMLBlocks.register("willow_seat", () -> new SeatBlock(ofFullCopy(NMLBlocks.WILLOW.planks().block())));

    public static void register() {
    }
}
