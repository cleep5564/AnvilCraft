package dev.dubhe.anvilcraft.data.recipe;

import net.minecraft.world.item.ItemStack;

import java.util.List;

/**
 * 配方输入
 */
public interface RecipeInput {
    List<ItemStack> getItems();
}
