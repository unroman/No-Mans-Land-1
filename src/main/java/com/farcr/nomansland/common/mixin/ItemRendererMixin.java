package com.farcr.nomansland.common.mixin;

import net.minecraft.client.renderer.entity.ItemRenderer;
import org.spongepowered.asm.mixin.Mixin;

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
