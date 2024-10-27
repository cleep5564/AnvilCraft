package dev.dubhe.anvilcraft.client.gui.screen.inventory;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import dev.dubhe.anvilcraft.AnvilCraft;
import dev.dubhe.anvilcraft.inventory.JewelCraftingMenu;
import dev.dubhe.anvilcraft.inventory.component.jewel.JewelInputSlot;
import dev.dubhe.anvilcraft.inventory.component.jewel.JewelResultSlot;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class JewelCraftingScreen extends AbstractContainerScreen<JewelCraftingMenu> {

    private static final ResourceLocation CONTAINER_LOCATION =
        AnvilCraft.of("textures/gui/container/smithing/background/jewelcrafting_table.png");


    public JewelCraftingScreen(JewelCraftingMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(CONTAINER_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderHintItemSlot(guiGraphics);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    private void renderHintItemSlot(GuiGraphics guiGraphics) {
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        poseStack.translate(leftPos, topPos, 0);

        for (int i = JewelCraftingMenu.CRAFT_SLOT_START; i <= JewelCraftingMenu.CRAFT_SLOT_END; i++) {
            Slot slot = menu.getSlot(i);
            if (!slot.hasItem() && slot instanceof JewelInputSlot inputSlot) {
                int count = inputSlot.getHintCount();
                ItemStack @Nullable [] ingredientItems = inputSlot.getIngredientItems();
                if (ingredientItems != null) {
                    int index = (int) ((System.currentTimeMillis() / 1000) % ingredientItems.length);
                    ItemStack stack = ingredientItems[index];
                    guiGraphics.renderItem(stack, slot.x, slot.y);
                    guiGraphics.fill(
                        RenderType.guiGhostRecipeOverlay(),
                        slot.x,
                        slot.y,
                        slot.x + 16,
                        slot.y + 16,
                        0x808B8B8B
                    );
                    guiGraphics.renderItemDecorations(font, stack.copyWithCount(count), slot.x, slot.y);
                }
            }
        }

        poseStack.popPose();
    }

    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, int x, int y) {
        if (this.menu.getCarried().isEmpty() && this.hoveredSlot != null) {
            ItemStack itemstack = null;
            if (this.hoveredSlot.hasItem()) {
                itemstack = this.hoveredSlot.getItem();
            } else if (this.hoveredSlot instanceof JewelInputSlot inputSlot) {
                ItemStack @Nullable [] ingredientItems = inputSlot.getIngredientItems();
                if (ingredientItems != null) {
                    int index = (int) ((System.currentTimeMillis() / 1000) % ingredientItems.length);
                    itemstack = ingredientItems[index];
                }
            }
            if (itemstack != null) {
                guiGraphics.renderTooltip(
                    this.font,
                    this.getTooltipFromContainerItem(itemstack),
                    itemstack.getTooltipImage(),
                    x,
                    y
                );
            }
        }
    }

    @Override
    protected void renderSlot(GuiGraphics guiGraphics, Slot slot) {
        final int i = slot.x;
        final int j = slot.y;
        ItemStack itemStack = slot.getItem();
        ItemStack itemStack2 = this.menu.getCarried();
        String string = null;
        boolean bl = false;
        if (slot == this.clickedSlot && !this.draggingItem.isEmpty() && this.isSplittingStack && !itemStack.isEmpty()) {
            itemStack = itemStack.copyWithCount(itemStack.getCount() / 2);
        } else if (this.isQuickCrafting && this.quickCraftSlots.contains(slot) && !itemStack2.isEmpty()) {
            if (this.quickCraftSlots.size() == 1) {
                return;
            }

            if (AbstractContainerMenu.canItemQuickReplace(slot, itemStack2, true) && this.menu.canDragTo(slot)) {
                bl = true;
                int k = Math.min(itemStack2.getMaxStackSize(), slot.getMaxStackSize(itemStack2));
                int l = slot.getItem().isEmpty() ? 0 : slot.getItem().getCount();
                int m = AbstractContainerMenu.getQuickCraftPlaceCount(
                    this.quickCraftSlots,
                    this.quickCraftingType,
                    itemStack2
                ) + l;
                if (m > k) {
                    m = k;
                    string = ChatFormatting.YELLOW.toString() + k;
                }

                itemStack = itemStack2.copyWithCount(m);
            } else {
                this.quickCraftSlots.remove(slot);
                this.recalculateQuickCraftRemaining();
            }
        }
        boolean bl2 = slot == this.clickedSlot && !this.draggingItem.isEmpty() && !this.isSplittingStack;
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0.0F, 0.0F, 100.0F);
        if (itemStack.isEmpty() && slot.isActive()) {
            Pair<ResourceLocation, ResourceLocation> pair = slot.getNoItemIcon();
            if (pair != null) {
                TextureAtlasSprite textureAtlasSprite = this.minecraft.getTextureAtlas(pair.getFirst())
                    .apply(pair.getSecond());
                guiGraphics.blit(i, j, 0, 16, 16, textureAtlasSprite);
                bl2 = true;
            }
        }

        if (!bl2) {
            if (bl) {
                guiGraphics.fill(i, j, i + 16, j + 16, -2130706433);
            }

            renderSlotContents(guiGraphics, itemStack, slot, string);
        }

        guiGraphics.pose().popPose();
    }


    protected void renderSlotContents(
        GuiGraphics guiGraphics,
        ItemStack itemstack,
        Slot slot,
        @Nullable String countString
    ) {
        if (slot instanceof JewelInputSlot inputSlot) {
            if (itemstack.getCount() < inputSlot.getHintCount()) {
                int seed = slot.x + slot.y * imageWidth;
                guiGraphics.renderItem(itemstack, slot.x, slot.y, seed);
                if (!itemstack.isEmpty()) {
                    guiGraphics.pose().pushPose();
                    String s = String.valueOf(itemstack.getCount());
                    guiGraphics.pose().translate(0.0F, 0.0F, 200.0F);
                    guiGraphics.drawString(font, s, slot.x + 19 - 2 - font.width(s), slot.y + 6 + 3, 0xFFFF5555, true);
                    guiGraphics.pose().popPose();
                }
                return;
            }
        }

        int i = slot.x;
        int j = slot.y;
        guiGraphics.renderItem(itemstack, i, j, slot.x + slot.y * this.imageWidth);
        guiGraphics.renderItemDecorations(this.font, itemstack, i, j, countString);
    }
}
