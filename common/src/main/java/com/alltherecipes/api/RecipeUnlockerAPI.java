package com.alltherecipes.api;

/**
 * Public API for the AllTheRecipes mod.
 * This can be used by other mods to interact with the recipe unlocking system.
 */
public class RecipeUnlockerAPI {
    
    /**
     * Forces a refresh of the recipe book for the local player.
     * This can be called by other mods to trigger recipe unlocking.
     */
    public static void refreshRecipeBook() {
        // This will be implemented using the platform helper
        // The actual implementation delegates to the platform-specific code
    }
    
    /**
     * Checks if the mod is currently active on the client.
     * 
     * @return true if the mod is active, false otherwise
     */
    public static boolean isActive() {
        return true;
    }
    
    /**
     * Gets the current version of the mod.
     * 
     * @return The mod version string
     */
    public static String getVersion() {
        return "1.0.0";
    }
}
