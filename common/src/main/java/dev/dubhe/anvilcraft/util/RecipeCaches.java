package dev.dubhe.anvilcraft.util;

import dev.dubhe.anvilcraft.data.recipe.jewel.JewelCraftingRecipe;
import dev.dubhe.anvilcraft.init.ModRecipeTypes;
import dev.dubhe.anvilcraft.network.ClientboundSyncRecipeCachePacket;
import lombok.Getter;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class RecipeCaches {
    public static final RecipeCaches INSTANCE = new RecipeCaches();
    private final Map<Item, JewelCraftingRecipe> jewelRecipes = new HashMap<>();

    public static List<Item> getAllJewelResultItem() {
        return INSTANCE.jewelRecipes.keySet().stream().toList();
    }

    public static @Nullable JewelCraftingRecipe getJewelRecipeByResult(ItemStack stack) {
        return INSTANCE.jewelRecipes.get(stack.getItem());
    }

    /**
     * 更新配方缓存
     */
    public void update(List<JewelCraftingRecipe> recipes) {
        jewelRecipes.clear();
        recipes.forEach(it -> {
            jewelRecipes.put(it.getResult().getItem(), it);
        });
    }

    public void reload(RecipeManager manager) {
        update(manager.getAllRecipesFor(ModRecipeTypes.JEWEL_CRAFTING));
    }

    public void fromNetwork(List<JewelCraftingRecipe> jewelRecipes) {
        update(jewelRecipes);
    }

    public void syncToPlayer(ServerPlayer player) {
        new ClientboundSyncRecipeCachePacket(jewelRecipes.values().stream().toList()).send(player);
    }
}
