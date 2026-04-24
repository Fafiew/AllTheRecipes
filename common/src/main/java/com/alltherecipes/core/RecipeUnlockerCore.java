package com.alltherecipes.core;

/**
 * Core implementation of recipe unlocking logic.
 * This class is shared across all mod loaders.
 * 
 * The logic uses Minecraft's recipe system to discover and unlock all recipes
 * in the player's recipe book.
 * 
 * This is the API interface that platform-specific implementations must follow.
 */
public class RecipeUnlockerCore {
    
    private static IPlatformHelper platformHelper;
    
    /**
     * Initializes the core with the platform-specific helper.
     * @param helper The platform-specific implementation
     */
    public static void init(IPlatformHelper helper) {
        platformHelper = helper;
        if (platformHelper != null) {
            platformHelper.registerEventHandlers();
        }
    }
    
    /**
     * Called when a player joins the world.
     * This triggers the recipe unlocking for the player.
     * 
     * @param player The player who joined
     */
    public static void onPlayerJoin(Object player) {
        if (player == null) return;
        
        // Verify we're on client side
        if (!platformHelper.isClientSide()) {
            return;
        }
        
        // Unlock all recipes
        platformHelper.unlockAllRecipes(player);
        
        // Refresh UI
        platformHelper.refreshRecipeBookUI(player);
    }
    
    /**
     * Gets the platform helper.
     * @return The current platform helper
     */
    public static IPlatformHelper getPlatformHelper() {
        return platformHelper;
    }
}
