package com.farcr.nomansland.common.registry;

import com.farcr.nomansland.integration.Mods;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Items;
import vectorwing.farmersdelight.common.effect.NourishmentEffect;
import vectorwing.farmersdelight.common.registry.ModEffects;

public class NMLFoods {
    public static final FoodProperties MASHED_POTATOES_WITH_MUSHROOMS = new FoodProperties.Builder().nutrition(10).saturationModifier(0.9f).usingConvertsTo(Items.BOWL).build();
    public static final FoodProperties GRILLED_MUSHROOMS = new FoodProperties.Builder().nutrition(4).saturationModifier(0.5f).build();

    public static final FoodProperties FROG_LEG = new FoodProperties.Builder().nutrition(2).saturationModifier(0.2f).build();
    public static final FoodProperties COOKED_FROG_LEG = new FoodProperties.Builder().nutrition(6).saturationModifier(0.7f).build();

    public static final FoodProperties RAW_HORSE = new FoodProperties.Builder().nutrition(4).saturationModifier(0.3f).build();
    public static final FoodProperties HORSE_STEAK = new FoodProperties.Builder().nutrition(9).saturationModifier(0.8f).build();

    public static final FoodProperties RAW_VENISON = new FoodProperties.Builder().nutrition(3).saturationModifier(0.3f).build();
    public static final FoodProperties COOKED_VENISON = new FoodProperties.Builder().nutrition(6).saturationModifier(0.8f).build();
//TODO: GIVE NOURISHMENT
    public static final FoodProperties SEARED_VENISON = Mods.FARMERSDELIGHT.isLoaded() ? new FoodProperties.Builder().nutrition(12).saturationModifier(0.9f).usingConvertsTo(Items.BOWL)
        .effect(new MobEffectInstance(ModEffects.NOURISHMENT, 3600, 0), 1.0f).build(): null;

    public static final FoodProperties BILLHOOK_BASS = new FoodProperties.Builder().nutrition(3).saturationModifier(0.1F).build();
    public static final FoodProperties COOKED_BILLHOOK_BASS = new FoodProperties.Builder().nutrition(7).saturationModifier(0.8F).build();
    public static final FoodProperties CAVE_CARP = new FoodProperties.Builder().nutrition(3).saturationModifier(0.2F).build();

    public static final FoodProperties MAPLE_SYRUP_BOTTLE = new FoodProperties.Builder().nutrition(5).saturationModifier(0.2f).alwaysEdible().build();

    public static final FoodProperties PEAR = new FoodProperties.Builder().nutrition(4).saturationModifier(0.3f).build();
    public static final FoodProperties SYRUPED_PEAR = new FoodProperties.Builder().nutrition(6).saturationModifier(0.4f).build();
    public static final FoodProperties PEAR_COBBLER = new FoodProperties.Builder().nutrition(8).saturationModifier(0.5f).build();
    public static final FoodProperties PANCAKE = new FoodProperties.Builder().nutrition(8).saturationModifier(0.5f).build();

    public static final FoodProperties HONEYED_APPLE = new FoodProperties.Builder().nutrition(6).saturationModifier(0.4f).build();

    public static final FoodProperties WALNUTS = new FoodProperties.Builder().nutrition(2).saturationModifier(0.3f).fast().build();
}
