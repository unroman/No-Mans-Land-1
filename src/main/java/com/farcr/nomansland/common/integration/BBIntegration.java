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

import static com.farcr.nomansland.common.registry.blocks.NMLBlocks.registerBlock;
import static net.minecraft.world.level.block.state.BlockBehaviour.Properties.ofFullCopy;

public class BBIntegration {

    public static final BlockDefinition<Block> STRIPPED_SPIKED_MAPLE_PALISADE = registerBlock("stripped_spiked_maple_palisade", () -> new SpikedPalisadeBlock(ModBlocks.PROPERTIES_PALISADE.mapColor(MapColor.WOOD).sound(SoundType.WOOD)));
    public static final BlockDefinition<Block> SPIKED_MAPLE_PALISADE = registerBlock("spiked_maple_palisade", () -> new SpikedPalisadeBlock(STRIPPED_SPIKED_MAPLE_PALISADE, ModBlocks.PROPERTIES_PALISADE.mapColor(MapColor.WOOD).sound(SoundType.WOOD)));
    public static final BlockDefinition<Block> STRIPPED_MAPLE_PALISADE = registerBlock("stripped_maple_palisade", () -> new PalisadeBlock(STRIPPED_SPIKED_MAPLE_PALISADE, ModBlocks.PROPERTIES_PALISADE.mapColor(MapColor.WOOD).sound(SoundType.WOOD)));
    public static final BlockDefinition<Block> MAPLE_PALISADE = registerBlock("maple_palisade", () -> new PalisadeBlock(SPIKED_MAPLE_PALISADE, STRIPPED_MAPLE_PALISADE, ModBlocks.PROPERTIES_PALISADE.mapColor(MapColor.WOOD).sound(SoundType.WOOD)));

    public static final BlockDefinition<Block> STRIPPED_SPIKED_PINE_PALISADE = registerBlock("stripped_spiked_pine_palisade", () -> new SpikedPalisadeBlock(ModBlocks.PROPERTIES_PALISADE.mapColor(MapColor.WOOD).sound(SoundType.WOOD)));
    public static final BlockDefinition<Block> SPIKED_PINE_PALISADE = registerBlock("spiked_pine_palisade", () -> new SpikedPalisadeBlock(STRIPPED_SPIKED_PINE_PALISADE, ModBlocks.PROPERTIES_PALISADE.mapColor(MapColor.WOOD).sound(SoundType.WOOD)));
    public static final BlockDefinition<Block> STRIPPED_PINE_PALISADE = registerBlock("stripped_pine_palisade", () -> new PalisadeBlock(STRIPPED_SPIKED_PINE_PALISADE, ModBlocks.PROPERTIES_PALISADE.mapColor(MapColor.WOOD).sound(SoundType.WOOD)));
    public static final BlockDefinition<Block> PINE_PALISADE = registerBlock("pine_palisade", () -> new PalisadeBlock(SPIKED_PINE_PALISADE, STRIPPED_PINE_PALISADE, ModBlocks.PROPERTIES_PALISADE.mapColor(MapColor.WOOD).sound(SoundType.WOOD)));

    public static final BlockDefinition<Block> STRIPPED_SPIKED_WALNUT_PALISADE = registerBlock("stripped_spiked_walnut_palisade", () -> new SpikedPalisadeBlock(ModBlocks.PROPERTIES_PALISADE.mapColor(MapColor.WOOD).sound(SoundType.WOOD)));
    public static final BlockDefinition<Block> SPIKED_WALNUT_PALISADE = registerBlock("spiked_walnut_palisade", () -> new SpikedPalisadeBlock(STRIPPED_SPIKED_WALNUT_PALISADE, ModBlocks.PROPERTIES_PALISADE.mapColor(MapColor.WOOD).sound(SoundType.WOOD)));
    public static final BlockDefinition<Block> STRIPPED_WALNUT_PALISADE = registerBlock("stripped_walnut_palisade", () -> new PalisadeBlock(STRIPPED_SPIKED_WALNUT_PALISADE, ModBlocks.PROPERTIES_PALISADE.mapColor(MapColor.WOOD).sound(SoundType.WOOD)));
    public static final BlockDefinition<Block> WALNUT_PALISADE = registerBlock("walnut_palisade", () -> new PalisadeBlock(SPIKED_WALNUT_PALISADE, STRIPPED_WALNUT_PALISADE, ModBlocks.PROPERTIES_PALISADE.mapColor(MapColor.WOOD).sound(SoundType.WOOD)));

    public static final BlockDefinition<Block> STRIPPED_SPIKED_WILLOW_PALISADE = registerBlock("stripped_spiked_willow_palisade", () -> new SpikedPalisadeBlock(ModBlocks.PROPERTIES_PALISADE.mapColor(MapColor.WOOD).sound(SoundType.WOOD)));
    public static final BlockDefinition<Block> SPIKED_WILLOW_PALISADE = registerBlock("spiked_willow_palisade", () -> new SpikedPalisadeBlock(STRIPPED_SPIKED_WILLOW_PALISADE, ModBlocks.PROPERTIES_PALISADE.mapColor(MapColor.WOOD).sound(SoundType.WOOD)));
    public static final BlockDefinition<Block> STRIPPED_WILLOW_PALISADE = registerBlock("stripped_willow_palisade", () -> new PalisadeBlock(STRIPPED_SPIKED_WILLOW_PALISADE, ModBlocks.PROPERTIES_PALISADE.mapColor(MapColor.WOOD).sound(SoundType.WOOD)));
    public static final BlockDefinition<Block> WILLOW_PALISADE = registerBlock("willow_palisade", () -> new PalisadeBlock(SPIKED_WILLOW_PALISADE, STRIPPED_WILLOW_PALISADE, ModBlocks.PROPERTIES_PALISADE.mapColor(MapColor.WOOD).sound(SoundType.WOOD)));

    public static final BlockDefinition<Block> MAPLE_SEAT = registerBlock("maple_seat", () -> new SeatBlock(ofFullCopy(NMLBlocks.MAPLE.planks().get())));
    public static final BlockDefinition<Block> PINE_SEAT = registerBlock("pine_seat", () -> new SeatBlock(ofFullCopy(NMLBlocks.PINE.planks().get())));
    public static final BlockDefinition<Block> WALNUT_SEAT = registerBlock("walnut_seat", () -> new SeatBlock(ofFullCopy(NMLBlocks.WALNUT.planks().get())));
    public static final BlockDefinition<Block> WILLOW_SEAT = registerBlock("willow_seat", () -> new SeatBlock(ofFullCopy(NMLBlocks.WILLOW.planks().get())));

    public static void register() {
    }
}
