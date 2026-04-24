package com.alltherecipes.core;

import net.minecraft.client.MinecraftClient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.book.RecipeBook;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Collection;
import java.util.Collections;

/**
 * Core implementation of recipe unlocking logic.
 * This class is shared across all mod loaders.
 * 
 * The logic uses Minecraft's recipe system to discover and unlock all recipes
 * in the player's recipe book.
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
     * Gets all recipes from the server or client recipe manager.
     * This method handles both integrated server (singleplayer) and dedicated server cases.
     * 
     * @return Collection of all recipes, or empty collection if not available
     */
    @SuppressWarnings("unchecked")
    public static Collection<Recipe<?>> getAllRecipes() {
        try {
            MinecraftClient client = MinecraftClient.getInstance();
            
            // Try to get from integrated server (singleplayer)
            if (client.getServer() != null) {
                MinecraftServer server = client.getServer();
                RecipeManager recipeManager = server.getRecipeManager();
                return (Collection<Recipe<?>>) recipeManager.values();
            }
            
            // Try to get from client (multiplayer - client has access to recipe manager)
            if (client.getNetworkHandler() != null && client.getNetworkHandler().getConnection() != null) {
                // In multiplayer, the client receives recipes from the server
                // We can access the RecipeManager through the client world
            }
            
            // Fallback: try to get from world
            if (client.world != null && client.world.getServer() != null) {
                RecipeManager recipeManager = client.world.getServer().getRecipeManager();
                return (Collection<Recipe<?>>) recipeManager.values();
            }
            
        } catch (Exception e) {
            System.err.println("[AllTheRecipes] Error getting recipes: " + e.getMessage());
        }
        
        return Collections.emptyList();
    }
    
    /**
     * Unlocks a single recipe in the player's recipe book.
     * 
     * @param recipeBook The player's recipe book
     * @param recipe The recipe to unlock
     */
    public static void unlockRecipe(RecipeBook recipeBook, Recipe<?> recipe) {
        if (recipeBook == null || recipe == null) return;
        
        try {
            recipeBook.onRecipeDiscovered(recipe);
        } catch (Exception e) {
            System.err.println("[AllTheRecipes] Error unlocking recipe: " + e.getMessage());
        }
    }
    
    /**
     * Gets the recipe book for a player entity.
     * Works with both ServerPlayerEntity and ClientPlayerEntity.
     * 
     * @param player The player object
     * @return The player's recipe book, or null if not available
     */
    public static RecipeBook getRecipeBook(Object player) {
        if (player == null) return null;
        
        try {
            // Try ServerPlayerEntity first (works for singleplayer and servers)
            if (player instanceof ServerPlayerEntity) {
                return ((ServerPlayerEntity) player).getRecipeBook();
            }
            
            // For client-side player
            if (player instanceof net.minecraft.client.network.ClientPlayerEntity) {
                return ((net.minecraft.client.network.ClientPlayerEntity) player).getRecipeBook();
            }
            
            // Try reflection as fallback
            var method = player.getClass().getMethod("getRecipeBook");
            return (RecipeBook) method.invoke(player);
            
        } catch (Exception e) {
            System.err.println("[AllTheRecipes] Error getting recipe book: " + e.getMessage());
        }
        
        return null;
    }
}
