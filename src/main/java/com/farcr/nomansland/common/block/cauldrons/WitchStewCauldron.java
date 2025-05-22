package com.farcr.nomansland.common.block.cauldrons;

import com.farcr.nomansland.common.integration.FDIntegration;
import com.farcr.nomansland.common.registry.blocks.NMLBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.function.Supplier;

public class WitchStewCauldron extends FourLayeredCauldronBlock {

    public WitchStewCauldron() {
        super(null);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        int fillLevel = state.getValue(LEVEL);

        if (fillLevel == 1) {
            level.setBlockAndUpdate(pos, FDIntegration.EMPTY_WITCH_STEW.block().defaultBlockState());
        } else {
            level.setBlockAndUpdate(pos, FDIntegration.WITCH_STEW.block().withPropertiesOf(state).setValue(LEVEL, fillLevel - 1));
        }

        return InteractionResult.SUCCESS;
    }
}
