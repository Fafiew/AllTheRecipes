package com.alltherecipes.forge;

import com.alltherecipes.core.RecipeUnlockerCore;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkLoggingInEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.network.ConnectionConstants;
import net.minecraftforge.network.simple.SimpleChannel;

/**
 * Main entrypoint for the Forge mod.
 * Initializes the recipe unlocker core with Forge-specific helper.
 */
@Mod("alltherecipes")
public class ForgeRecipeUnlocker {
    
    private static final String PROTOCOL_VERSION = "1";
    public static SimpleChannel CHANNEL;
    
    public ForgeRecipeUnlocker() {
        System.out.println("[AllTheRecipes] Initializing Forge mod...");
        
        // Register the platform helper
        RecipeUnlockerCore.init(new ForgePlatformHelper());
        
        // Configure display test - this is a client-only mod
        // We want it to work on both client and server, but only affect client
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> 
            new IExtensionPoint.DisplayTest(
                () -> PROTOCOL_VERSION,
                (s, b) -> true
            )
        );
        
        System.out.println("[AllTheRecipes] Forge mod initialized successfully!");
    }
    
    /**
     * Event subscriber for Forge events.
     * This handles player login and world join events.
     */
    @EventBusSubscriber(modid = "alltherecipes", bus = Bus.FORGE, value = Dist.CLIENT)
    public static class ForgeEvents {
        
        /**
         * Called when player logs in (both singleplayer and multiplayer).
         */
        @net.minecraftforge.eventbus.api.SubscribeEvent
        public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
            // Only run on client side
            if (event.getEntity() != null && event.getEntity().level().isClientSide()) {
                RecipeUnlockerCore.onPlayerJoin(event.getEntity());
            }
        }
        
        /**
         * Called when client player logs in.
         */
        @net.minecraftforge.eventbus.api.SubscribeEvent
        public static void onClientPlayerLogin(ClientPlayerNetworkLoggingInEvent event) {
            if (event.getPlayer() != null) {
                RecipeUnlockerCore.onPlayerJoin(event.getPlayer());
            }
        }
        
        /**
         * Called each client tick - can be used as fallback.
         */
        @net.minecraftforge.eventbus.api.SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {
            if (event.phase == TickEvent.Phase.END) {
                // Could add logic here if needed
            }
        }
    }
}
