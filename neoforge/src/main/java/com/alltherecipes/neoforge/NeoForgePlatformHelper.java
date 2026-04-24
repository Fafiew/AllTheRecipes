package com.alltherecipes.neoforge;

import com.alltherecipes.core.IPlatformHelper;

/** NeoForge stub - needs NeoGradle configuration */
public class NeoForgePlatformHelper implements IPlatformHelper {
    @Override
    public void unlockAllRecipes(Object player) {
        System.out.println("[AllTheRecipes] NeoForge: Placeholder - not implemented yet");
    }
    @Override
    public boolean isClientSide() { return false; }
    @Override
    public void registerEventHandlers() {}
    @Override
    public void refreshRecipeBookUI(Object player) {}
    @Override
    public String getPlatformName() { return "neoforge"; }
}
