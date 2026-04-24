package com.alltherecipes.core;

/**
 * Platform abstraction interface for multi-loader support.
 * Each mod loader (Fabric, Forge, NeoForge) provides its own implementation.
 */
public interface IPlatformHelper {
    
    /**
     * Unlocks all recipes for the given player.
     * @param player The player whose recipes should be unlocked
     */
    void unlockAllRecipes(Object player);
    
    /**
     * Checks if the current execution is on the client side.
     * @return true if running on client, false if on server
     */
    boolean isClientSide();
    
    /**
     * Registers event handlers for player login/join events.
     * Called during mod initialization.
     */
    void registerEventHandlers();
    
    /**
     * Refreshes the recipe book UI for the player.
     * @param player The player whose UI should be refreshed
     */
    void refreshRecipeBookUI(Object player);
    
    /**
     * Gets the mod loader identifier.
     * @return "fabric", "forge", or "neoforge"
     */
    String getPlatformName();
}
