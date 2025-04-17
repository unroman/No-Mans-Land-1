package com.farcr.nomansland.common.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.RailState;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RailShape;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(RailState.class)
public abstract class RailStateMixin {

    @Shadow @Final private Level level;

    @Shadow private BlockState state;

    @Shadow protected abstract void updateConnections(RailShape shape);

    @Shadow @Final private BaseRailBlock block;

    @Shadow @Final public BlockPos pos;

    @Shadow @Final private List<BlockPos> connections;

    @Shadow @Nullable protected abstract RailState getRail(BlockPos pos);

    @Shadow protected abstract boolean hasNeighborRail(BlockPos pos);

    @Shadow public abstract boolean hasConnection(BlockPos pos);

    @Inject(method = "place", at = @At("HEAD"), cancellable = true)
    public void nml$place(boolean powered, boolean alwaysPlace, RailShape shape, CallbackInfoReturnable<RailState> cir) {
        BlockPos blockpos = pos.north();
        BlockPos blockpos1 = pos.south();
        BlockPos blockpos2 = pos.west();
        BlockPos blockpos3 = pos.east();
        boolean flag = hasNeighborRail(blockpos);
        boolean flag1 = hasNeighborRail(blockpos1);
        boolean flag2 = hasNeighborRail(blockpos2);
        boolean flag3 = hasNeighborRail(blockpos3);
        RailShape railshape = null;
        boolean flag4 = flag || flag1;
        boolean flag5 = flag2 || flag3;
        boolean supported = level.getBlockState(pos.below()).isFaceSturdy(level, pos.below(), Direction.UP, SupportType.RIGID);
        if (flag4 && !supported) {
            railshape = RailShape.NORTH_SOUTH;
        }

        if (flag5 && !supported) {
            railshape = RailShape.EAST_WEST;
        }

        if (railshape != null) {
            updateConnections(railshape);
            state = state.setValue(block.getShapeProperty(), railshape);
            if (alwaysPlace || level.getBlockState(pos) != state) {
                level.setBlock(pos, state, 3);

                for (BlockPos connection : connections) {
                    RailState railstate = getRail(connection);
                    if (railstate != null) {
                        railstate.removeSoftConnections();
                        if (railstate.canConnectTo((RailState) (Object) this)) {
                            railstate.connectTo((RailState) (Object) this);
                        }
                    }
                }
            }

            cir.setReturnValue((RailState)(Object)this);
        }

    }

    @Inject(method = "connectTo", at = @At("HEAD"), cancellable = true)
    public void nml$connectTo(RailState state, CallbackInfo ci) {
        connections.add(state.pos);
        BlockPos blockpos = pos.north();
        BlockPos blockpos1 = pos.south();
        BlockPos blockpos2 = pos.west();
        BlockPos blockpos3 = pos.east();
        boolean flag = hasConnection(blockpos);
        boolean flag1 = hasConnection(blockpos1);
        boolean flag2 = hasConnection(blockpos2);
        boolean flag3 = hasConnection(blockpos3);
        RailShape railshape = null;
        if (flag || flag1) {
            railshape = RailShape.NORTH_SOUTH;
        }

        if (flag2 || flag3) {
            railshape = RailShape.EAST_WEST;
        }

        boolean supported = level.getBlockState(pos.below()).isFaceSturdy(level, pos.below(), Direction.UP, SupportType.RIGID);
        if (!supported) {
            if (railshape == null) {
                railshape = RailShape.NORTH_SOUTH;
            }

            if (!block.isValidRailShape(railshape)) { // Forge: allow rail block to decide if the new shape is valid
                connections.remove(state.pos);
                ci.cancel();
            }
            this.state = this.state.setValue(block.getShapeProperty(), railshape);
            level.setBlock(pos, this.state, 3);
            ci.cancel();;
        }
    }
}
