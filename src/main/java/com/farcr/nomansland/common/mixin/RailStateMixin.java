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

    @Shadow @Final private BlockPos pos;

    @Shadow @Final private List<BlockPos> connections;

    @Shadow @Nullable protected abstract RailState getRail(BlockPos pos);

    @Shadow protected abstract boolean hasNeighborRail(BlockPos pos);

    @Shadow public abstract boolean hasConnection(BlockPos pos);

    @Inject(method = "place", at = @At("HEAD"), cancellable = true)
    public void injected(boolean powered, boolean alwaysPlace, RailShape shape, CallbackInfoReturnable<RailState> cir) {
        BlockPos blockpos = this.pos.north();
        BlockPos blockpos1 = this.pos.south();
        BlockPos blockpos2 = this.pos.west();
        BlockPos blockpos3 = this.pos.east();
        boolean flag = this.hasNeighborRail(blockpos);
        boolean flag1 = this.hasNeighborRail(blockpos1);
        boolean flag2 = this.hasNeighborRail(blockpos2);
        boolean flag3 = this.hasNeighborRail(blockpos3);
        RailShape railshape = null;
        boolean flag4 = flag || flag1;
        boolean flag5 = flag2 || flag3;
        boolean supported = level.getBlockState(this.pos.below()).isFaceSturdy(level, this.pos.below(), Direction.UP, SupportType.RIGID);
        if (flag4 && !supported) {
            railshape = RailShape.NORTH_SOUTH;
        }

        if (flag5 && !supported) {
            railshape = RailShape.EAST_WEST;
        }

        if (railshape != null) {
            this.updateConnections(railshape);
            this.state = this.state.setValue(this.block.getShapeProperty(), railshape);
            if (alwaysPlace || this.level.getBlockState(this.pos) != this.state) {
                this.level.setBlock(this.pos, this.state, 3);

                for (int i = 0; i < this.connections.size(); i++) {
                    RailState railstate = this.getRail(this.connections.get(i));
                    if (railstate != null) {
                        railstate.removeSoftConnections();
                        if (railstate.canConnectTo((RailState)(Object)this)) {
                            railstate.connectTo((RailState)(Object)this);
                        }
                    }
                }
            }

            cir.setReturnValue((RailState)(Object)this);
        }

    }

    @Inject(method = "connectTo", at = @At("HEAD"), cancellable = true)
    public void injected2(RailState state, CallbackInfo ci) {
        this.connections.add(state.pos);
        BlockPos blockpos = this.pos.north();
        BlockPos blockpos1 = this.pos.south();
        BlockPos blockpos2 = this.pos.west();
        BlockPos blockpos3 = this.pos.east();
        boolean flag = this.hasConnection(blockpos);
        boolean flag1 = this.hasConnection(blockpos1);
        boolean flag2 = this.hasConnection(blockpos2);
        boolean flag3 = this.hasConnection(blockpos3);
        RailShape railshape = null;
        if (flag || flag1) {
            railshape = RailShape.NORTH_SOUTH;
        }

        if (flag2 || flag3) {
            railshape = RailShape.EAST_WEST;
        }

        boolean supported = level.getBlockState(this.pos.below()).isFaceSturdy(level, this.pos.below(), Direction.UP, SupportType.RIGID);
        if (!supported) {
            if (railshape == null) {
                railshape = RailShape.NORTH_SOUTH;
            }

            if (!this.block.isValidRailShape(railshape)) { // Forge: allow rail block to decide if the new shape is valid
                this.connections.remove(state.pos);
                ci.cancel();
            }
            this.state = this.state.setValue(this.block.getShapeProperty(), railshape);
            this.level.setBlock(this.pos, this.state, 3);
            ci.cancel();;
        }
    }
}
