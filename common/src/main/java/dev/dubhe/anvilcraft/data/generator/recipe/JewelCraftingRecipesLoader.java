package dev.dubhe.anvilcraft.data.generator.recipe;

import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import dev.dubhe.anvilcraft.AnvilCraft;
import dev.dubhe.anvilcraft.data.recipe.jewel.JewelCraftingRecipe;
import dev.dubhe.anvilcraft.init.ModBlocks;
import dev.dubhe.anvilcraft.init.ModItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class JewelCraftingRecipesLoader {
    /**
     *
     */
    public static void init(RegistrateRecipeProvider provider) {
        JewelCraftingRecipe.of(AnvilCraft.of("enchanted_golden_apple"))
            .requires(Items.EXPERIENCE_BOTTLE, 16)
            .requires(Items.GOLD_BLOCK, 8)
            .requires(Items.GOLDEN_APPLE)
            .result(new ItemStack(Items.ENCHANTED_GOLDEN_APPLE))
            .accept(provider);

        JewelCraftingRecipe.of(AnvilCraft.of("totem_of_undying"))
            .requires(Items.EMERALD_BLOCK, 2)
            .requires(Items.ENCHANTED_GOLDEN_APPLE)
            .requires(ModBlocks.ROYAL_STEEL_BLOCK)
            .result(new ItemStack(Items.TOTEM_OF_UNDYING))
            .accept(provider);

        JewelCraftingRecipe.of(AnvilCraft.of("elytra"))
            .requires(Items.PHANTOM_MEMBRANE, 8)
            .requires(Items.BAMBOO, 4)
            .requires(ModItemTags.TITANIUM_INGOTS)
            .result(new ItemStack(Items.ELYTRA))
            .accept(provider);
    }
}
