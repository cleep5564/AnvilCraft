package dev.dubhe.anvilcraft.data.recipe.jewel;

import dev.dubhe.anvilcraft.data.recipe.RecipeInput;
import lombok.Getter;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Getter
public class JewelRecipeInput implements Container, RecipeInput {
    public final ItemStack sourceItem;
    public final List<ItemStack> inputItems;

    public JewelRecipeInput(ItemStack sourceItem, List<ItemStack> inputItems) {
        this.sourceItem = sourceItem;
        this.inputItems = inputItems;
    }

    @Override
    public int getContainerSize() {
        return 5;
    }

    @Override
    public boolean isEmpty() {
        return sourceItem.isEmpty() && inputItems.stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public ItemStack getItem(int slot) {
        return slot == 0 ? sourceItem : inputItems.get(slot - 1);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
    }

    @Override
    public void setChanged() {
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void clearContent() {
    }

    @Override
    public List<ItemStack> getItems() {
        return inputItems;
    }
}
