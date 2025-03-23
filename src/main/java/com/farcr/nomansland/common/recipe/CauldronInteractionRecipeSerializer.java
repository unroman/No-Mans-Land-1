package com.farcr.nomansland.common.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

// The generic parameter is our recipe class.
// Note: This assumes that simple CauldronInteractionRecipe#getInputState, #getInputItem and #getResult getters
// are available, which were omitted from the code above.
public class CauldronInteractionRecipeSerializer implements RecipeSerializer<CauldronInteractionRecipe> {
    public static final MapCodec<CauldronInteractionRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("cauldron").forGetter(CauldronInteractionRecipe::inputCauldron),
            Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(CauldronInteractionRecipe::inputItem),
            Codec.INT.fieldOf("cost").orElse(1).forGetter(CauldronInteractionRecipe::levelCost),
            ItemStack.CODEC.fieldOf("result").forGetter(CauldronInteractionRecipe::result)
    ).apply(inst, CauldronInteractionRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, CauldronInteractionRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.fromCodec(BuiltInRegistries.BLOCK.byNameCodec()), CauldronInteractionRecipe::inputCauldron,
                    Ingredient.CONTENTS_STREAM_CODEC, CauldronInteractionRecipe::inputItem,
                    ByteBufCodecs.INT, CauldronInteractionRecipe::levelCost,
                    ItemStack.STREAM_CODEC, CauldronInteractionRecipe::result,
                    CauldronInteractionRecipe::new
            );

    @Override
    public MapCodec<CauldronInteractionRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, CauldronInteractionRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
