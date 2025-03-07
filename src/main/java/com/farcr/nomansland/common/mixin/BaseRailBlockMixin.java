package com.farcr.nomansland.common.mixin;

import com.farcr.nomansland.NMLConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.RailShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BaseRailBlock.class)
public abstract class BaseRailBlockMixin extends Block {

    public BaseRailBlockMixin(Properties properties) {
        super(properties);
    }

    @Shadow @Deprecated public abstract Property<RailShape> getShapeProperty();

    @Unique
    private static boolean nml$floatingRails(BlockPos pos, LevelReader level, RailShape shape, Direction direction) {
        for (int i = 1; i <= NMLConfig.MAX_FLOATING_RAILS.get(); i++) {
            BlockState blockState = level.getBlockState(pos.relative(direction, i));
            RailShape offsetShape;
            if (blockState.getBlock() instanceof BaseRailBlock) {
                offsetShape = blockState.getValue(((BaseRailBlock)blockState.getBlock()).getShapeProperty());
            }
            else
                return true;
            if (canSupportRigidBlock(level, pos.relative(direction, i).below()))
                return false;
            if (offsetShape != shape)
                return true;
        }
        return true;
    }

    @Unique
    private static boolean nml$shouldBeRemovedOverride(BlockPos pos, LevelReader level, RailShape shape) {
        if (!canSupportRigidBlock(level, pos.below())) {
            return switch (shape) {
                case NORTH_SOUTH -> nml$floatingRails(pos, level, shape, Direction.NORTH) && nml$floatingRails(pos, level, shape, Direction.SOUTH);
                case EAST_WEST -> nml$floatingRails(pos, level, shape, Direction.EAST) && nml$floatingRails(pos, level, shape, Direction.WEST);
                default -> true;
            };
        } else {
            return switch (shape) {
                case ASCENDING_EAST -> !canSupportRigidBlock(level, pos.east());
                case ASCENDING_WEST -> !canSupportRigidBlock(level, pos.west());
                case ASCENDING_NORTH -> !canSupportRigidBlock(level, pos.north());
                case ASCENDING_SOUTH -> !canSupportRigidBlock(level, pos.south());
                default -> false;
            };
        }
    }

    @Inject(method = "canSurvive", at = @At("HEAD"), cancellable = true)
    public void nml$canSurvive(BlockState state, LevelReader level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        RailShape railshape = null;
        if (state != null && state.hasProperty(this.getShapeProperty())) {
            railshape = state.getValue(this.getShapeProperty());
        }
        cir.setReturnValue(!nml$shouldBeRemovedOverride(pos, level, railshape));
    }

    @Inject(method = "shouldBeRemoved", at = @At("HEAD"), cancellable = true)
    private static void nml$shouldBeRemoved(BlockPos pos, Level level, RailShape shape, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(nml$shouldBeRemovedOverride(pos, level, shape));
    }
}
