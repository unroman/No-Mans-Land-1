package com.farcr.nomansland.common.mixin;

import com.farcr.nomansland.common.entity.billhook_bass.BillhookBass;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.animal.AbstractFish;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFish.class)
public abstract class AbstractFishMixin extends EntityMixin {

    @Inject(method = "registerGoals", at = @At("TAIL"))
    private void registerGoals(CallbackInfo ci) {
        ((AbstractFish) (Object) this).goalSelector.addGoal(3, new AvoidEntityGoal<>(((AbstractFish) (Object) this), BillhookBass.class, 5.0F, 1.6, 1.4));
    }

//    @Inject(method = "saveToBucketTag", at = @At("TAIL"))
//    private void saveToBucketTag(ItemStack stack, CallbackInfo ci) {
//        if (this.getTags().contains("Variant")) {
//            CustomData.update(DataComponents.BUCKET_ENTITY_DATA, stack, (data) -> {
//                this.getVariant().unwrapKey().ifPresent((variant) -> {
//                    data.putString("Variant", variant.location().toString());
//                });
//            });
//        }
//    }
//
//    @Inject(method = "loadFromBucketTag", at = @At("TAIL"))
//    private void loadFromBucketTag(CompoundTag tag, CallbackInfo ci) {
//            if (tag.contains("Variant")) {
//                Optional<Registry<MobVariant>> optionalRegistry = this.registryAccess().registry(NMLMobVariants.getVariantOfType(this.getType()));
//                if (optionalRegistry.isPresent()) {
//                    Registry<? extends MobVariant> registry = optionalRegistry.get();
//                    this.setVariant(registry.holders().filter(v -> v.unwrapKey().get().location().toString().equals(tag.getString("Variant"))).findAny().get());
//                }
//            }
//    }
}
