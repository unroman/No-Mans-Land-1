package com.farcr.nomansland.common.block.cauldrons;

import com.farcr.nomansland.common.integration.FDIntegration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import vectorwing.farmersdelight.common.registry.ModSounds;

public class WitchStewCauldron extends FourLayeredCauldronBlock {

    public WitchStewCauldron() {
        super(null);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        double d0 = pos.getX() + 0.5 + random.nextInt(-40, 40) * 0.01;
        double d1 = pos.getY() + random.nextInt(-10, 40) * 0.001 + getContentHeight(state);
        double d2 = pos.getZ() + 0.5 + random.nextInt(-40, 40) * 0.01;
        level.addParticle(ParticleTypes.EFFECT, d0, d1, d2, 0.0, 0.0, 0.0);

        if (random.nextInt(10) == 0) {
            level.playLocalSound(d0, d1, d2, ModSounds.BLOCK_COOKING_POT_BOIL_SOUP.get(), SoundSource.BLOCKS, 0.5F, random.nextFloat() * 0.2F + 0.9F, false);
        }
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        boolean interacted = false;
        if (stack.is(Items.BOWL)) {
            interacted = true;
            player.getItemInHand(hand).shrink(1);
            player.addItem(new ItemStack(FDIntegration.WITCH_STEW_ITEM.get()));
            level.playSound(null, pos, SoundEvents.ARMOR_EQUIP_GENERIC.value(), SoundSource.BLOCKS);
            if (state.getValue(LEVEL) > 1) {
                lowerFillLevel(state, level, pos);
            } else {
                level.setBlockAndUpdate(pos, FDIntegration.EMPTY_WITCH_STEW.block().defaultBlockState());
            }
        }

        if (interacted) {
            player.awardStat(Stats.USE_CAULDRON);
            player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
        }
    }
}
