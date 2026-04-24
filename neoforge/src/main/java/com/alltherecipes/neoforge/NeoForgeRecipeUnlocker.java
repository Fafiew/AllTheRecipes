package com.alltherecipes.neoforge;

import com.alltherecipes.core.RecipeUnlockerCore;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.IExtensionPoint;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.Mod.EventBusSubscriber;
import net.neoforged.fml.common.Mod.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkLoggingInEvent;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

/**
 * Main entrypoint for the NeoForge mod.
 * Initializes the recipe unlocker core with NeoForge-specific helper.
 */
@Mod("alltherecipes")
public class NeoForgeRecipeUnlocker {
    
    private static final String PROTOCOL_VERSION = "1";
    
    public NeoForgeRecipeUnlocker() {
        System.out.println("[AllTheRecipes] Initializing NeoForge mod...");
        
        // Register the platform helper
        RecipeUnlockerCore.init(new NeoForgePlatformHelper());
        
        // Configure display test - this is a client-only mod
        // We want it to work on both client and server, but only affect client
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> 
            new IExtensionPoint.DisplayTest(
                () -> PROTOCOL_VERSION,
                (s, b) -> true
            )
        );
        
        System.out.println("[AllTheRecipes] NeoForge mod initialized successfully!");
    }
    
    /**
     * Event subscriber for NeoForge events.
     * This handles player login and world join events.
     */
    @EventBusSubscriber(modid = "alltherecipes", bus = Bus.NEOFORGE, value = Dist.CLIENT)
    public static class NeoForgeEvents {
        
        /**
         * Called when player logs in (both singleplayer and multiplayer).
         */
        @net.neoforged.bus.api.SubscribeEvent
        public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
            // Only run on client side
            if (event.getEntity() != null && event.getEntity().level().isClientSide()) {
                RecipeUnlockerCore.onPlayerJoin(event.getEntity());
            }
        }
        
        /**
         * Called when client player logs in.
         */
        @net.neoforged.bus.api.SubscribeEvent
        public static void onClientPlayerLogin(ClientPlayerNetworkLoggingInEvent event) {
            if (event.getPlayer() != null) {
                RecipeUnlockerCore.onPlayerJoin(event.getPlayer());
            }
        }
        
        /**
         * Called each client tick - can be used as fallback.
         */
        @net.neoforged.bus.api.SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {
            if (event.getPhase() == TickEvent.ClientTickPhase.END) {
                // Could add logic here if needed
            }
        }
    }
}
