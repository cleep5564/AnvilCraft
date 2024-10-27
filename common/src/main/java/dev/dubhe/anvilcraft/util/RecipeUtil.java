package dev.dubhe.anvilcraft.util;

import dev.dubhe.anvilcraft.data.recipe.RecipeInput;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RecipeUtil {

    /**
     * 配方原料相等
     */
    public static boolean isIngredientsEqual(Ingredient first, Ingredient second) {
        if (first == second) return true;
        ObjectArrayList<Ingredient.Value> firstValues = new ObjectArrayList<>(first.values);
        ObjectArrayList<Ingredient.Value> secondValues = new ObjectArrayList<>(second.values);

        if (firstValues.size() == secondValues.size()) {
            outer:
            for (int i = 0; i < firstValues.size(); i++) {
                var firstValue = firstValues.get(i);

                for (int j = 0; j < firstValues.size(); j++) {
                    if (isValuesEqual(firstValue, secondValues.get(j))) {
                        firstValues.remove(i);
                        secondValues.remove(j);
                        i--;

                        continue outer;
                    }
                }
                return false;
            }
            return true;
        }

        return false;
    }

    /**
     * 配方原料相等
     */
    private static boolean isValuesEqual(Ingredient.Value firstValue, Ingredient.Value secondValue) {
        Class<?> firstKlass = firstValue.getClass();
        Class<?> secondKlass = secondValue.getClass();

        if (firstKlass == secondKlass) {
            if (firstKlass == Ingredient.ItemValue.class) {
                return ItemStack.matches(
                    ((Ingredient.ItemValue) firstValue).item,
                    ((Ingredient.ItemValue) secondValue).item
                );
            } else if (firstKlass == Ingredient.TagValue.class) {
                return ((Ingredient.TagValue) firstValue).tag == ((Ingredient.TagValue) secondValue).tag;
            } else {
                var firstItems = firstValue.getItems();
                var secondItems = secondValue.getItems();
                var len = firstItems.size();

                if (len == secondItems.size()) {
                    Iterator<ItemStack> firstIter = firstItems.iterator();
                    Iterator<ItemStack> secondIter = secondItems.iterator();

                    while (firstIter.hasNext()) {
                        if (!ItemStack.matches(firstIter.next(), secondIter.next())) {
                            return false;
                        }
                    }
                } else {
                    return false;
                }
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * 合并配方原料
     */
    public static Object2IntMap<Ingredient> mergeIngredient(List<Ingredient> ingredients) {
        Object2IntMap<Ingredient> result = new Object2IntLinkedOpenHashMap<>();
        for (Ingredient ingredient : ingredients) {
            boolean flag = false;
            for (Ingredient key : result.keySet()) {
                if (isIngredientsEqual(ingredient, key)) {
                    result.put(key, result.getInt(key) + 1);
                    flag = true;
                }
            }
            if (!flag) {
                result.put(ingredient, 1);
            }
        }
        return result;
    }

    /**
     * 最大合成次数
     */
    public static int getMaxCraftTime(RecipeInput input, List<Ingredient> ingredients) {
        Object2IntMap<Item> contents = new Object2IntOpenHashMap<>();
        Object2BooleanMap<Ingredient> ingredientFlags = new Object2BooleanOpenHashMap<>();
        Object2BooleanMap<Item> flags = new Object2BooleanOpenHashMap<>();
        for (Ingredient ingredient : ingredients) {
            ingredientFlags.put(ingredient, false);
        }
        for (ItemStack stack : input.getItems()) {
            if (stack.isEmpty()) continue;
            contents.mergeInt(stack.getItem(), stack.getCount(), Integer::sum);
            flags.put(stack.getItem(), false);
        }
        int times = 0;
        while (true) {
            for (Ingredient ingredient : ingredients) {
                for (Item item : contents.keySet()) {
                    if (ingredient.test(new ItemStack(item))) {
                        contents.put(item, contents.getInt(item) - 1);
                        ingredientFlags.put(ingredient, true);
                        flags.put(item, true);
                    }
                }
            }
            if (ingredientFlags.values().stream().anyMatch(flag -> !flag)
                || flags.values().stream().anyMatch(flag -> !flag)) {
                return 0;
            }
            if (contents.values().intStream().allMatch(i -> i >= 0)) {
                times += 1;
            } else {
                return times;
            }
        }
    }
}
