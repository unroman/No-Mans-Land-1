package com.farcr.nomansland.common.blockentity;

import com.farcr.nomansland.common.registry.NMLBlockEntities;
import com.farcr.nomansland.integration.FDIntegration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import vectorwing.farmersdelight.common.block.entity.CabinetBlockEntity;

public class NMLCabinetBlockEntity extends CabinetBlockEntity {

    public NMLCabinetBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> getType() {
        return FDIntegration.CABINET.get();
    }
}
