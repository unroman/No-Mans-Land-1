package com.farcr.nomansland.common.registry;

import com.farcr.nomansland.NoMansLand;
import com.google.common.collect.Sets;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NMLCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, NoMansLand.MODID);

    public static Sets.SetView<DeferredHolder<Item, ? extends Item>> CREATIVE_TAB_ITEMS = Sets.union(NMLItems.CREATIVE_TAB_ITEMS, NMLBlocks.CREATIVE_TAB_ITEMS);


    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> NO_MANS_TAB = CREATIVE_TABS.register(NoMansLand.MODID,
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.nomansland"))
                    .icon(NMLItems.NO_MANS_GLOBE::toStack)
                    .displayItems((parameters, output) -> CREATIVE_TAB_ITEMS.forEach((item) -> output.accept(item.get())))
                    .build());
}
