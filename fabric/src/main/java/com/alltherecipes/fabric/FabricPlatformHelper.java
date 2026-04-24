package com.alltherecipes.fabric;

import com.alltherecipes.core.IPlatformHelper;
import com.alltherecipes.core.RecipeUnlockerCore;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.book.RecipeBook;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Collection;

/**
 * Fabric-specific implementation of the platform helper.
 * Handles Fabric-specific API for events and player interaction.
 */
public class FabricPlatformHelper implements IPlatformHelper {
    
    @Override
    public void unlockAllRecipes(Object player) {
        if (player == null) return;
        
        try {
            RecipeBook recipeBook = getRecipeBook(player);
            if (recipeBook == null) {
                System.err.println("[AllTheRecipes] Could not get recipe book for player");
                return;
            }
            
            Collection<Recipe<?>> recipes = getAllRecipesFromServer();
            
            int unlockedCount = 0;
            for (Recipe<?> recipe : recipes) {
                if (!recipeBook.containsRecipe(recipe)) {
                    recipeBook.onRecipeDiscovered(recipe);
                    unlockedCount++;
                }
            }
            
            System.out.println("[AllTheRecipes] Unlocked " + unlockedCount + " recipes for " + 
                (player instanceof ClientPlayerEntity ? "client" : "server") + " player");
            
        } catch (Exception e) {
            System.err.println("[AllTheRecipes] Error unlocking recipes: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Override
    public boolean isClientSide() {
        // We're on the client if MinecraftClient instance exists and we're in a game
        return MinecraftClient.getInstance().isInGame();
    }
    
    @Override
    public void registerEventHandlers() {
        // Register client-side player join event
        net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            ClientPlayerEntity player = client.player;
            if (player != null) {
                RecipeUnlockerCore.onPlayerJoin(player);
            }
        });
    }
    
    @Override
    public void refreshRecipeBookUI(Object player) {
        try {
            // Trigger recipe book GUI update if open
            MinecraftClient client = MinecraftClient.getInstance();
            
            if (client.player != null) {
                // If the recipe book GUI is open, trigger a refresh
                if (client.currentScreen instanceof net.minecraft.screen.RecipeScreen) {
                    // Close and reopen to refresh
                    client.currentScreen.close();
                }
            }
        } catch (Exception e) {
            System.err.println("[AllTheRecipes] Error refreshing UI: " + e.getMessage());
        }
    }
    
    @Override
    public String getPlatformName() {
        return "fabric";
    }
    
    /**
     * Gets all recipes from the server.
     */
    @SuppressWarnings("unchecked")
    private Collection<Recipe<?>> getAllRecipesFromServer() {
        try {
            MinecraftClient client = MinecraftClient.getInstance();
            
            // Try integrated server first (singleplayer)
            if (client.getServer() != null) {
                MinecraftServer server = client.getServer();
                RecipeManager recipeManager = server.getRecipeManager();
                return (Collection<Recipe<?>>) recipeManager.values();
            }
            
            // Fallback: try world server
            if (client.world != null && client.world.getServer() != null) {
                RecipeManager recipeManager = client.world.getServer().getRecipeManager();
                return (Collection<Recipe<?>>) recipeManager.values();
            }
            
        } catch (Exception e) {
            System.err.println("[AllTheRecipes] Error getting recipes: " + e.getMessage());
        }
        
        return java.util.Collections.emptyList();
    }
    
    /**
     * Gets the recipe book from a player object.
     */
    private RecipeBook getRecipeBook(Object player) {
        if (player instanceof ClientPlayerEntity) {
            return ((ClientPlayerEntity) player).getRecipeBook();
        }
        if (player instanceof ServerPlayerEntity) {
            return ((ServerPlayerEntity) player).getRecipeBook();
        }
        return null;
    }
}
