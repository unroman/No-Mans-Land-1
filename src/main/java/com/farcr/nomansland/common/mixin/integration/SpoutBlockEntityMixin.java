package com.farcr.nomansland.common.mixin.integration;

import com.farcr.nomansland.common.registry.items.NMLItems;
import com.llamalad7.mixinextras.sugar.Local;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import com.simibubi.create.content.fluids.spout.SpoutBlockEntity;
import net.minecraft.world.item.ItemStack;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@IfModLoaded("create")
@Mixin(SpoutBlockEntity.class)
public abstract class SpoutBlockEntityMixin {
    @Accessor("createdHoneyApple")
    public abstract void setCreatedHoneyApple(boolean value);

    @Redirect(method = "whenItemHeld", at = @At(value = "FIELD", target = "Lcom/simibubi/create/content/fluids/spout/SpoutBlockEntity;createdHoneyApple:Z", opcode = Opcodes.PUTFIELD))
    private void countNMLHoneyedApple(SpoutBlockEntity instance, boolean value, @Local ItemStack out) {
        ((SpoutBlockEntityMixin) (Object) instance).setCreatedHoneyApple(out.is(NMLItems.HONEYED_APPLE) || value);
    }
}
