package com.alltherecipes.forge;

import com.alltherecipes.core.IPlatformHelper;

/** Forge stub - needs ForgeGradle configuration */
public class ForgePlatformHelper implements IPlatformHelper {
    @Override
    public void unlockAllRecipes(Object player) {
        System.out.println("[AllTheRecipes] Forge: Placeholder - not implemented yet");
    }
    @Override
    public boolean isClientSide() { return false; }
    @Override
    public void registerEventHandlers() {}
    @Override
    public void refreshRecipeBookUI(Object player) {}
    @Override
    public String getPlatformName() { return "forge"; }
}
