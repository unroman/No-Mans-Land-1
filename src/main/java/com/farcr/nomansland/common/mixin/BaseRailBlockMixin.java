package com.farcr.nomansland.common.mixin;

import com.farcr.nomansland.NMLConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.world.level.block.Block.canSupportRigidBlock;

@Mixin(BaseRailBlock.class)
public abstract class BaseRailBlockMixin extends BlockBehaviourMixin {

    @Shadow @Deprecated public abstract Property<RailShape> getShapeProperty();

    @Shadow protected abstract VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context);

    @Unique
    private static boolean nml$floatingRails(BlockPos pos, LevelReader level, RailShape shape, Direction direction) {
        for (int i = 1; i <= NMLConfig.MAX_FLOATING_RAILS.get(); i++) {
            BlockState state = level.getBlockState(pos.relative(direction, i));
            RailShape offsetShape;
            if (state.getBlock() instanceof BaseRailBlock) {
                offsetShape = state.getValue(((BaseRailBlock) state.getBlock()).getShapeProperty());
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
                case NORTH_SOUTH ->
                        nml$floatingRails(pos, level, shape, Direction.NORTH) && nml$floatingRails(pos, level, shape, Direction.SOUTH);
                case EAST_WEST ->
                        nml$floatingRails(pos, level, shape, Direction.EAST) && nml$floatingRails(pos, level, shape, Direction.WEST);
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

    @Override
    protected void getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        if (!canSupportRigidBlock(level, pos.below())) cir.setReturnValue(getShape(state, level, pos, context));
    }

    @Inject(method = "canSurvive", at = @At("HEAD"), cancellable = true)
    private void nml$canSurvive(BlockState state, LevelReader level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        RailShape railshape = null;
        if (state != null && state.hasProperty(this.getShapeProperty()))
            railshape = state.getValue(this.getShapeProperty());

        if (railshape != null)
            cir.setReturnValue(!nml$shouldBeRemovedOverride(pos, level, railshape));
    }

    @Inject(method = "shouldBeRemoved", at = @At("HEAD"), cancellable = true)
    private static void nml$shouldBeRemoved(BlockPos pos, Level level, RailShape shape, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(nml$shouldBeRemovedOverride(pos, level, shape));
    }
}
