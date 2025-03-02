package com.farcr.nomansland.common.mixin;

import com.farcr.nomansland.NMLConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
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
    private static boolean no_Mans_Land$floatingRails(BlockPos pos, LevelReader level, RailShape shape, Direction direction) {
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
    private static boolean no_Mans_Land$shouldBeRemovedOverride(BlockPos pos, LevelReader level, RailShape shape) {
        if (!canSupportRigidBlock(level, pos.below())) {
            return switch (shape) {
                case NORTH_SOUTH -> no_Mans_Land$floatingRails(pos, level, shape, Direction.NORTH) && no_Mans_Land$floatingRails(pos, level, shape, Direction.SOUTH);
                case EAST_WEST -> no_Mans_Land$floatingRails(pos, level, shape, Direction.EAST) && no_Mans_Land$floatingRails(pos, level, shape, Direction.WEST);
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
    public void injected(BlockState state, LevelReader level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        RailShape railshape = null;
        if (state != null && state.hasProperty(this.getShapeProperty())) {
            railshape = (RailShape) state.getValue(this.getShapeProperty());
        }
        cir.setReturnValue(!no_Mans_Land$shouldBeRemovedOverride(pos, level, railshape));
    }

    @Inject(method = "shouldBeRemoved", at = @At("HEAD"), cancellable = true)
    private static void injected2(BlockPos pos, Level level, RailShape shape, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(no_Mans_Land$shouldBeRemovedOverride(pos, level, shape));
    }
}
