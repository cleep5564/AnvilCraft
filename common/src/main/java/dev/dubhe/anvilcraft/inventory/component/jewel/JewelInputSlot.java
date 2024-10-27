package dev.dubhe.anvilcraft.inventory.component.jewel;

import dev.dubhe.anvilcraft.data.recipe.jewel.JewelCraftingRecipe;
import dev.dubhe.anvilcraft.inventory.container.JewelSourceContainer;
import lombok.Getter;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

public class JewelInputSlot extends Slot {
    private final JewelSourceContainer sourceContainer;
    @Getter
    @Nullable
    private Ingredient ingredient;
    @Getter
    private ItemStack @Nullable [] ingredientItems;
    @Getter
    private int hintCount;

    /**
     *
     */
    public JewelInputSlot(JewelSourceContainer sourceContainer, Container container, int slot, int x, int y) {
        super(container, slot, x, y);
        this.sourceContainer = sourceContainer;

        updateIngredient();
    }

    /**
     *
     */
    @Override
    public boolean mayPlace(ItemStack stack) {
        if (ingredient == null) {
            return false;
        }
        if (!ingredient.test(stack)) {
            return false;
        }
        return super.mayPlace(stack);
    }

    /**
     *
     */
    public void updateIngredient() {
        JewelCraftingRecipe recipe = sourceContainer.getRecipe();
        if (recipe != null) {
            var mergedIngredients = sourceContainer.getRecipe().getMergedIngredientsList();
            if (getContainerSlot() > mergedIngredients.size() - 1) {
                ingredient = null;
                ingredientItems = null;
            } else {
                var entry = mergedIngredients.get(getContainerSlot());
                ingredient = entry.getKey();
                ingredientItems = ingredient.getItems();
                hintCount = entry.getIntValue();
            }
        } else {
            ingredient = null;
            ingredientItems = null;
        }
    }
}
