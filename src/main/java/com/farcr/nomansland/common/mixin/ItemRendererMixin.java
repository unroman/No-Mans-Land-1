package com.farcr.nomansland.common.mixin;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.registry.NMLMobVariants;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
//    @Shadow @Final private ItemModelShaper itemModelShaper;
//
//    @Inject(method = "getModel", at = @At("HEAD"))
//    private void vanity$captureModel(ItemStack stack, Level level, LivingEntity entity, int seed, CallbackInfoReturnable<BakedModel> cir) {
//        renderStack.set(itemStack);
//    }
//
//    @ModifyVariable(method = "getModel", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/renderer/ItemModelShaper;getItemModel(Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/client/resources/model/BakedModel;", shift = At.Shift.AFTER, ordinal = 0), argsOnly = true)
//    private ItemRenderer noMansLand$captureModel(BakedModel value, @Share("renderStack") LocalRef<ItemStack> renderStack) {
//        ItemStack stack = renderStack.get();
//        if (stack.is(Items.SALMON_BUCKET)) {
//            if (stack.get(DataComponents.BUCKET_ENTITY_DATA).contains("variant")) {
//                if (stack.get(DataComponents.BUCKET_ENTITY_DATA).getUnsafe().get("variant").equals("nomansland:ocean")) return
//            }
//        }
//
//        if (stack.is(Items.COD_BUCKET)) {
//            if (stack.get(DataComponents.BUCKET_ENTITY_DATA).contains("variant")) {
//                if (stack.get(DataComponents.BUCKET_ENTITY_DATA).getUnsafe().get("variant").equals("nomansland:freshwater")) return this.itemModelShaper.getModelManager().getModel(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, "item/freshwater_cod_bucket")));
//            }
//        }
//
//        return value;
//    }
}
