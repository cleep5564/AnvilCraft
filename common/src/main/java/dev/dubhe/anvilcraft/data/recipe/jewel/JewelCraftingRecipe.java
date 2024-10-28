package dev.dubhe.anvilcraft.data.recipe.jewel;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import dev.dubhe.anvilcraft.AnvilCraft;
import dev.dubhe.anvilcraft.init.ModRecipeTypes;
import dev.dubhe.anvilcraft.util.RecipeUtil;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.function.Consumer;

import static net.minecraft.data.recipes.RecipeBuilder.ROOT_RECIPE_ADVANCEMENT;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Getter
public class JewelCraftingRecipe implements Recipe<JewelRecipeInput> {
    public static final Serializer SERIALIZER = new Serializer();
    public static final RecipeType<JewelCraftingRecipe> TYPE = new Type();

    private final ResourceLocation id;
    private final NonNullList<Ingredient> ingredients;
    private final ItemStack result;
    private final Object2IntMap<Ingredient> mergedIngredients;
    private final List<Object2IntMap.Entry<Ingredient>> mergedIngredientsList;

    /**
     *
     */
    public JewelCraftingRecipe(NonNullList<Ingredient> ingredients, ItemStack result, ResourceLocation id) {
        this.ingredients = ingredients;
        this.result = result;
        this.id = id;
        mergedIngredients = RecipeUtil.mergeIngredient(ingredients);
        if (mergedIngredients.size() > 4) {
            throw new IllegalArgumentException("Expect at most 4 different ingredients, got "
                + mergedIngredients.size()
            );
        }
        mergedIngredientsList = mergedIngredients.object2IntEntrySet().stream().toList();
    }

    public boolean matches(JewelRecipeInput container, Level level) {
        return ItemStack.isSameItemSameTags(container.sourceItem, result)
            && RecipeUtil.getMaxCraftTime(container, ingredients) > 0;
    }

    @Override
    public ItemStack assemble(JewelRecipeInput container, RegistryAccess registryAccess) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return result.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.JEWEL_CRAFTING;
    }

    public static final class Serializer implements RecipeSerializer<JewelCraftingRecipe> {
        /**
         *
         */
        @Override
        public JewelCraftingRecipe fromJson(ResourceLocation recipeId, JsonObject serializedRecipe) {
            JsonArray ingredientsJsonArray = serializedRecipe.getAsJsonArray("ingredients");
            NonNullList<Ingredient> ingredients = NonNullList.create();
            ingredientsJsonArray.asList().stream()
                .map(Ingredient::fromJson)
                .forEach(ingredients::add);
            JsonObject resultJsonObject = serializedRecipe.getAsJsonObject("result");
            ItemStack result = ItemStack.CODEC.decode(JsonOps.INSTANCE, resultJsonObject)
                .getOrThrow(false, AnvilCraft.LOGGER::error)
                .getFirst();
            return new JewelCraftingRecipe(ingredients, result, recipeId);
        }

        /**
         *
         */
        @Override
        public JewelCraftingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            return new JewelCraftingRecipe(
                buffer.readCollection(
                    NonNullList::createWithCapacity,
                    Ingredient::fromNetwork
                ),
                buffer.readItem(),
                recipeId
            );
        }

        /**
         *
         */
        @Override
        public void toNetwork(FriendlyByteBuf buffer, JewelCraftingRecipe recipe) {
            buffer.writeCollection(
                recipe.ingredients,
                (buf, ingredient) -> ingredient.toNetwork(buf)
            );
            buffer.writeItem(recipe.result);
        }

        /**
         *
         */
        public JsonObject toJson(JewelCraftingRecipe recipe) {
            JsonObject res = new JsonObject();
            encodeStart(recipe, res);
            return res;
        }

        /**
         *
         */
        public void encodeStart(JewelCraftingRecipe recipe, JsonObject res) {
            JsonArray ingredientArray = new JsonArray();
            recipe.ingredients.forEach(it -> ingredientArray.add(it.toJson()));
            res.add("ingredients", ingredientArray);
            res.add(
                "result",
                ItemStack.CODEC.encodeStart(JsonOps.INSTANCE, recipe.result)
                    .getOrThrow(false, AnvilCraft.LOGGER::error)
            );
        }
    }

    public static class Type implements RecipeType<JewelCraftingRecipe> {
    }

    public static Builder of(ResourceLocation id) {
        return new Builder(id);
    }

    @Setter
    @Accessors(fluent = true, chain = true)
    public static class Builder {
        private final ResourceLocation id;
        private Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();
        private RecipeCategory category = RecipeCategory.MISC;
        private NonNullList<Ingredient> ingredients = NonNullList.create();
        private ItemStack result = ItemStack.EMPTY;

        /**
         *
         */
        public Builder requires(Ingredient ingredient, int count) {
            for (int i = 0; i < count; i++) {
                this.ingredients.add(ingredient);
            }
            return this;
        }

        /**
         *
         */
        public Builder requires(Ingredient ingredient) {
            return requires(ingredient, 1);
        }

        public Builder requires(ItemLike itemLike, int count) {
            return requires(Ingredient.of(itemLike), count);
        }

        public Builder requires(ItemLike item) {
            return requires(item, 1);
        }

        public Builder requires(TagKey<Item> tag, int count) {
            return requires(Ingredient.of(tag), count);
        }

        public Builder requires(TagKey<Item> tagKey) {
            return requires(tagKey, 1);
        }

        /**
         *
         */
        Builder(ResourceLocation id) {
            this.id = id;
        }

        /**
         *
         */
        public JewelCraftingRecipe create() {
            return new JewelCraftingRecipe(
                ingredients,
                result,
                id
            );
        }

        /**
         *
         */
        public FinishedJewelCraftingRecipe finish() {
            this.advancement
                .parent(ROOT_RECIPE_ADVANCEMENT)
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(RequirementsStrategy.OR);
            return new FinishedJewelCraftingRecipe(
                create(),
                advancement,
                id.withPrefix("recipes/" + this.category.getFolderName() + "/")
            );
        }

        public void accept(Consumer<FinishedRecipe> prov) {
            prov.accept(finish());
        }
    }
}
