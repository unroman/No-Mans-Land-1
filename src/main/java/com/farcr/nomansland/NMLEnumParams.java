package com.farcr.nomansland;

import com.farcr.nomansland.common.registry.blocks.NMLBlocks;
import com.farcr.nomansland.common.registry.items.NMLItems;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Items;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

public class NMLEnumParams {
    public static final EnumProxy<Boat.Type> MAPLE_BOAT_TYPE = new EnumProxy<>(
            Boat.Type.class, NMLBlocks.MAPLE.planks(), NoMansLand.MODID + ":maple", NMLItems.MAPLE_BOAT, NMLItems.MAPLE_CHEST_BOAT, Items.STICK, false
    );

    public static final EnumProxy<Boat.Type> PINE_BOAT_TYPE = new EnumProxy<>(
            Boat.Type.class, NMLBlocks.PINE.planks(), NoMansLand.MODID + ":pine", NMLItems.PINE_BOAT, NMLItems.PINE_CHEST_BOAT, Items.STICK, false
    );

    public static final EnumProxy<Boat.Type> WALNUT_BOAT_TYPE = new EnumProxy<>(
            Boat.Type.class, NMLBlocks.WALNUT.planks(), NoMansLand.MODID + ":walnut", NMLItems.WALNUT_BOAT, NMLItems.WALNUT_CHEST_BOAT, Items.STICK, false
    );

    public static final EnumProxy<Boat.Type> WILLOW_BOAT_TYPE = new EnumProxy<>(
            Boat.Type.class, NMLBlocks.WILLOW.planks(), NoMansLand.MODID + ":willow", NMLItems.WILLOW_BOAT, NMLItems.WILLOW_CHEST_BOAT, Items.STICK, false
    );
}
