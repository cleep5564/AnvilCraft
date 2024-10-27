package dev.dubhe.anvilcraft.network;

import dev.anvilcraft.lib.network.Packet;
import dev.dubhe.anvilcraft.data.recipe.jewel.JewelCraftingRecipe;
import dev.dubhe.anvilcraft.init.ModNetworks;
import dev.dubhe.anvilcraft.util.RecipeCaches;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ClientboundSyncRecipeCachePacket implements Packet {
    private final List<JewelCraftingRecipe> recipes;

    public ClientboundSyncRecipeCachePacket(List<JewelCraftingRecipe> recipes) {
        this.recipes = recipes;
    }

    /**
     * decoder
     *
     */
    public ClientboundSyncRecipeCachePacket(FriendlyByteBuf buf) {
        recipes = buf.readList(it -> {
            ResourceLocation id = it.readResourceLocation();
            return JewelCraftingRecipe.SERIALIZER.fromNetwork(id, it);
        });
    }

    @Override
    public ResourceLocation getType() {
        return ModNetworks.CLIENT_SYNC_RECIPE_CACHE;
    }

    @Override
    public void encode(@NotNull FriendlyByteBuf buf) {
        buf.writeCollection(recipes, (buffer, recipe) -> {
            buffer.writeResourceLocation(recipe.getId());
            JewelCraftingRecipe.SERIALIZER.toNetwork(buffer, recipe);
        });
    }

    @Override
    public void handler() {
        RecipeCaches.INSTANCE.fromNetwork(recipes);
    }
}
