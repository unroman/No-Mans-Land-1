package com.farcr.nomansland.common.blockentity;

import com.farcr.nomansland.common.registry.blocks.NMLBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class NMLHangingSignBlockEntity extends HangingSignBlockEntity {
    public NMLHangingSignBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    public BlockEntityType<?> getType() {
        return NMLBlockEntities.NML_HANGING_SIGN.get();
    }

    public boolean isValidBlockState(BlockState state) {
        return this.getType().isValid(state);
    }
}
