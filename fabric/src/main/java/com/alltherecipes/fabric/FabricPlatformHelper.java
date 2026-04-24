package com.alltherecipes.fabric;

import com.alltherecipes.core.IPlatformHelper;
import com.alltherecipes.core.RecipeUnlockerCore;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Collection;
import java.util.Collections;

/**
 * Fabric-specific implementation of the platform helper.
 * Uses reflection to access Minecraft classes to avoid direct mappings dependencies.
 */
public class FabricPlatformHelper implements IPlatformHelper {
    
    private static Method getInstanceMethod;
    private static Method getRecipeBookMethod;
    private static Method getRecipeManagerMethod;
    private static Field recipesField;
    private static Method isRecipeKnownMethod;
    private static Method onRecipeDiscoveredMethod;
    private static Method closeScreenMethod;
    
    static {
        try {
            // Get MinecraftClient class
            Class<?> minecraftClientClass = Class.forName("net.minecraft.client.MinecraftClient");
            getInstanceMethod = minecraftClientClass.getMethod("getInstance");
            
            // RecipeBook methods
            Class<?> recipeBookClass = Class.forName("net.minecraft.client.recipe.RecipeBook");
            getRecipeBookMethod = Class.forName("net.minecraft.client.network.ClientPlayerEntity").getMethod("getRecipeBook");
            isRecipeKnownMethod = recipeBookClass.getMethod("containsRecipe", Class.forName("net.minecraft.recipe.Recipe"));
            onRecipeDiscoveredMethod = recipeBookClass.getMethod("onRecipeDiscovered", 
                Class.forName("net.minecraft.recipe.Recipe"));
            
            // Get recipes from RecipeManager
            Class<?> recipeManagerClass = Class.forName("net.minecraft.recipe.RecipeManager");
            getRecipeManagerMethod = Class.forName("net.minecraft.server.MinecraftServer").getMethod("getRecipeManager");
            recipesField = recipeManagerClass.getField("recipes");
            
            // Screen methods
            Class<?> screenClass = Class.forName("net.minecraft.client.gui.screen.Screen");
            closeScreenMethod = screenClass.getMethod("close");
            
        } catch (Exception e) {
            System.err.println("[AllTheRecipes] Error initializing reflection: " + e.getMessage());
        }
    }
    
    @Override
    public void unlockAllRecipes(Object player) {
        if (player == null) return;
        
        try {
            Object recipeBook = getRecipeBookMethod.invoke(player);
            if (recipeBook == null) {
                System.err.println("[AllTheRecipes] Could not get recipe book for player");
                return;
            }
            
            Collection recipes = getAllRecipes();
            
            int unlockedCount = 0;
            for (Object recipe : recipes) {
                Boolean known = (Boolean) isRecipeKnownMethod.invoke(recipeBook, recipe);
                if (!known) {
                    onRecipeDiscoveredMethod.invoke(recipeBook, recipe);
                    unlockedCount++;
                }
            }
            
            System.out.println("[AllTheRecipes] Unlocked " + unlockedCount + " recipes for player");
            
        } catch (Exception e) {
            System.err.println("[AllTheRecipes] Error unlocking recipes: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Override
    public boolean isClientSide() {
        try {
            Object client = getInstanceMethod.invoke(null);
            return client != null;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public void registerEventHandlers() {
        net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            if (client.player != null) {
                RecipeUnlockerCore.onPlayerJoin(client.player);
            }
        });
    }
    
    @Override
    public void refreshRecipeBookUI(Object player) {
        try {
            Object client = getInstanceMethod.invoke(null);
            Field screenField = client.getClass().getField("currentScreen");
            Object screen = screenField.get(client);
            if (screen != null) {
                closeScreenMethod.invoke(screen);
            }
        } catch (Exception e) {
            System.err.println("[AllTheRecipes] Error refreshing UI: " + e.getMessage());
        }
    }
    
    @Override
    public String getPlatformName() {
        return "fabric";
    }
    
    @SuppressWarnings("unchecked")
    private Collection getAllRecipes() {
        try {
            Object client = getInstanceMethod.invoke(null);
            
            // Try integrated server first (singleplayer)
            Field serverField = client.getClass().getField("server");
            Object server = serverField.get(client);
            if (server != null) {
                Object recipeManager = getRecipeManagerMethod.invoke(server);
                Map recipes = (Map) recipesField.get(recipeManager);
                return (Collection) recipes.values();
            }
            
            // Try world server
            Field worldField = client.getClass().getField("world");
            Object world = worldField.get(client);
            if (world != null) {
                Field worldServerField = world.getClass().getField("server");
                Object worldServer = worldServerField.get(world);
                if (worldServer != null) {
                    Object recipeManager = getRecipeManagerMethod.invoke(worldServer);
                    Map recipes = (Map) recipesField.get(recipeManager);
                    return (Collection) recipes.values();
                }
            }
            
        } catch (Exception e) {
            System.err.println("[AllTheRecipes] Error getting recipes: " + e.getMessage());
        }
        
        return Collections.emptyList();
    }
}
