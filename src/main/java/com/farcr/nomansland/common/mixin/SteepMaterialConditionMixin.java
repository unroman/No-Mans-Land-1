package com.farcr.nomansland.common.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.SurfaceRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SurfaceRules.Context.SteepMaterialCondition.class)
public abstract class SteepMaterialConditionMixin extends SurfaceRules.LazyXZCondition {
    protected SteepMaterialConditionMixin(SurfaceRules.Context p_189622_) {
        super(p_189622_);
    }

    @ModifyReturnValue(method = "compute", at = @At(value = "TAIL"))
    private boolean fixMountainBug(boolean original)  {
        int i = this.context.blockX & 15;
        int j = this.context.blockZ & 15;
        int k = Math.max(j - 1, 0);
        int l = Math.min(j + 1, 15);
        ChunkAccess chunkaccess = this.context.chunk;
        int i1 = chunkaccess.getHeight(Heightmap.Types.WORLD_SURFACE_WG, i, k);
        int j1 = chunkaccess.getHeight(Heightmap.Types.WORLD_SURFACE_WG, i, l);
        if (i1 >= j1 + 4) {
            return true;
        }
        int k1 = Math.max(i - 1, 0);
        int l1 = Math.min(i + 1, 15);
        int i2 = chunkaccess.getHeight(Heightmap.Types.WORLD_SURFACE_WG, k1, j);
        int j2 = chunkaccess.getHeight(Heightmap.Types.WORLD_SURFACE_WG, l1, j);
        if (j2 >= i2 + 4) {
            return true;
        }
        return original;
    }
}
