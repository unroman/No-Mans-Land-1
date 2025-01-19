package com.farcr.nomansland.common.blockentity;

import com.farcr.nomansland.common.registry.NMLBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import vectorwing.farmersdelight.common.registry.ModBlockEntityTypes;

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
