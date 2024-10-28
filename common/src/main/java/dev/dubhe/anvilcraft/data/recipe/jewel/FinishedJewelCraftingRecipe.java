package dev.dubhe.anvilcraft.data.recipe.jewel;

import com.google.gson.JsonObject;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FinishedJewelCraftingRecipe implements FinishedRecipe {
    private final JewelCraftingRecipe recipe;
    private final Advancement.Builder advancement;
    private final ResourceLocation advancementId;

    /**
     * constructor
     */
    public FinishedJewelCraftingRecipe(
        JewelCraftingRecipe recipe,
        Advancement.Builder advancement,
        ResourceLocation advancementId
    ) {
        this.recipe = recipe;
        this.advancement = advancement;
        this.advancementId = advancementId;
    }

    @Override
    public void serializeRecipeData(JsonObject json) {
        JewelCraftingRecipe.SERIALIZER.encodeStart(recipe, json);
    }

    @Override
    public ResourceLocation getId() {
        return recipe.getId();
    }

    @Override
    public RecipeSerializer<?> getType() {
        return JewelCraftingRecipe.SERIALIZER;
    }

    @Override
    public @Nullable JsonObject serializeAdvancement() {
        return advancement.serializeToJson();
    }

    @Override
    public @Nullable ResourceLocation getAdvancementId() {
        return advancementId;
    }
}
