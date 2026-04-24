package com.alltherecipes.fabric;

import com.alltherecipes.core.RecipeUnlockerCore;
import net.fabricmc.api.ModInitializer;

/**
 * Main entrypoint for the Fabric mod.
 * Initializes the recipe unlocker core with Fabric-specific helper.
 */
public class FabricRecipeUnlocker implements ModInitializer {
    
    @Override
    public void onInitialize() {
        System.out.println("[AllTheRecipes] Initializing Fabric mod...");
        
        // Initialize the core with Fabric-specific helper
        RecipeUnlockerCore.init(new FabricPlatformHelper());
        
        System.out.println("[AllTheRecipes] Fabric mod initialized successfully!");
    }
}
