package dev.dubhe.anvilcraft.forge;

import dev.dubhe.anvilcraft.AnvilCraft;
import dev.dubhe.anvilcraft.init.ModCommands;
import dev.dubhe.anvilcraft.init.forge.ModRecipeTypesForge;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.NotNull;

@Mod(AnvilCraft.MOD_ID)
public class AnvilCraftForge {
    public AnvilCraftForge() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        AnvilCraft.init();
        bus.register(new ModRecipeTypesForge());
        MinecraftForge.EVENT_BUS.addListener(AnvilCraftForge::registerCommand);
    }

    public static void registerCommand(@NotNull RegisterCommandsEvent event) {
        ModCommands.register(event.getDispatcher());
    }
}