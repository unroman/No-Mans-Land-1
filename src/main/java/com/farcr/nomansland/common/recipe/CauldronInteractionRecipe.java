package com.farcr.nomansland.common.recipe;

import com.farcr.nomansland.common.block.cauldrons.FourLayeredCauldronBlock;
import com.farcr.nomansland.common.registry.NMLRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LayeredCauldronBlock;

public record CauldronInteractionRecipe(Block inputCauldron, Ingredient inputItem, int levelCost, ItemStack result) implements Recipe<CauldronInteractionInput> {

    @Override
    public boolean matches(CauldronInteractionInput input, Level level) {
        return this.inputCauldron == input.cauldron().getBlock() && this.levelCost <= (input.cauldron().getBlock() instanceof FourLayeredCauldronBlock ? input.cauldron().getValue(FourLayeredCauldronBlock.LEVEL) : input.cauldron().getValue(LayeredCauldronBlock.LEVEL)) && this.inputItem.test(input.stack());
    }

    @Override
    public ItemStack assemble(CauldronInteractionInput input, HolderLookup.Provider registries) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height > 0;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return result;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return NMLRecipeSerializers.CAULDRON_INTERACTION_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return NMLRecipeSerializers.CAULDRON_INTERACTION_RECIPE.get();
    }
}
