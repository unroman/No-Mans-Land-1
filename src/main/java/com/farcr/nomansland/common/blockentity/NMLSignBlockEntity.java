package com.farcr.nomansland.common.blockentity;

import com.farcr.nomansland.common.registry.NMLBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import vectorwing.farmersdelight.common.registry.ModBlockEntityTypes;

public class NMLSignBlockEntity extends SignBlockEntity {
    public NMLSignBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    public BlockEntityType<?> getType() {
        return NMLBlockEntities.NML_SIGN.get();
    }

    public boolean isValidBlockState(BlockState state) {
        return this.getType().isValid(state);
    }
}
