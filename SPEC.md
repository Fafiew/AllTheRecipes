# AllTheRecipes Mod - Specification

## 1. Project Overview

**Project Name:** AllTheRecipes  
**Project Type:** Client-side Minecraft Mod  
**Core Functionality:** Automatically unlocks all crafting recipes in the recipe book for the player on client-side, working in both singleplayer and multiplayer without server installation.

## 2. Technical Architecture

### 2.1 Multi-Loader Structure

```
/common (shared core)
  /src/main/java/com/alltherecipes/
    /core/
      RecipeUnlockerCore.java       # Main entry for core logic
      IPlatformHelper.java         # Platform abstraction interface
      PlatformHelpers.java         # Helper accessor
    /api/
      RecipeUnlockerAPI.java        # Public API for other mods

/fabric (Fabric mod)
  /src/main/java/com/alltherecipes/
    /fabric/
      FabricRecipeUnlocker.java     # Fabric entrypoint
      FabricPlatformHelper.java     # Fabric-specific implementation
  /resources/
    fabric.mod.json

/forge (Forge mod)
  /src/main/java/com/alltherecipes/
    /forge/
      ForgeRecipeUnlocker.java      # Forge entrypoint
      ForgePlatformHelper.java      # Forge-specific implementation
  /resources/
    META-INF/
      mods.toml

/neoforge (NeoForge mod)
  /src/main/java/com/alltherecipes/
    /neoforge/
      NeoForgeRecipeUnlocker.java   # NeoForge entrypoint
      NeoForgePlatformHelper.java   # NeoForge-specific implementation
  /resources/
    META-INF/
      mods.toml
```

### 2.2 Build System

- **Gradle** with Kotlin DSL
- **Version Catalog** for dependency management
- **Multi-source-set** architecture for loader-specific code

## 3. Functionality Specification

### 3.1 Core Features

1. **Automatic Recipe Unlocking**
   - On player login/join, automatically discover all crafting recipes
   - Mark all recipes as "known" in the player's recipe book
   - Trigger UI refresh to show unlocked recipes immediately

2. **Trigger Conditions**
   - Player login event (first time spawn in world)
   - World join event
   - Recipe book reload (if available in API)

3. **Platform Abstraction**
   - Abstract interfaces for:
     - Recipe manager access
     - Player recipe book manipulation
     - Event subscriptions
     - Client-side execution checks

### 3.2 Supported Minecraft Versions

| Version | Support Level |
|---------|--------------|
| 1.19.x  | Backward compatibility |
| 1.20.x  | Backward compatibility |
| 1.21.x  | Primary support |
| 1.21.1+ | Primary support (26.1.x format) |

### 3.3 Mod Loaders

- **Fabric** - Uses Fabric's entrypoint system and mixins if needed
- **Forge** - Uses Forge's event subscriber system
- **NeoForge** - Uses NeoForge's event subscriber system

## 4. Implementation Details

### 4.1 Core Logic (Common Module)

```java
// IPlatformHelper - Platform abstraction
public interface IPlatformHelper {
    void unlockAllRecipes(Player player);
    boolean isClientSide();
    void registerEventHandlers();
    void refreshRecipeBookUI(Player player);
}
```

### 4.2 Recipe Unlocking Algorithm

1. Get the RecipeManager from the server/game instance
2. Get all recipes from all recipe types (crafting, smelting, etc.)
3. Get the player's RecipeBook
4. For each recipe:
   - Check if already known
   - If not known, add to known recipes
5. Trigger recipe book packet update if needed
6. Call GUI refresh if on client

### 4.3 Version Compatibility

- Use reflection or version-aware code for API differences
- Handle:
  - RecipeBook API changes between versions
  - RecipeManager access differences
  - Event API changes

### 4.4 Multiplayer Safety

- Always verify `isClientSide()` before modifying state
- Never send packets to server
- Only modify local client Player object
- Use client-side execution guards

## 5. Build Configuration

### 5.1 Version Catalog (libs.versions.toml)

```
minecraft = "1.21.4"
fabric-loader = "0.15.11"
fabric-api = "0.100.3+1.21.4"
forge = "1.21.4-52.0.19"
neoforge = "1.21.4-52.0.19"
```

### 5.2 Gradle Structure

- Root `build.gradle.kts` - Common configuration
- `build.gradle.kts` per subproject
- Source set configuration for each loader

## 6. Metadata

### 6.1 Fabric (fabric.mod.json)

```json
{
  "schemaVersion": 1,
  "id": "alltherecipes",
  "version": "1.0.0",
  "name": "AllTheRecipes",
  "description": "Unlock all recipes in the recipe book",
  "authors": ["AllTheRecipes"],
  "license": "MIT",
  "entrypoints": {
    "main": "com.alltherecipes.fabric.FabricRecipeUnlocker"
  },
  "depends": {
    "fabricloader": ">=0.15.0",
    "minecraft": ">=1.19"
  }
}
```

### 6.2 Forge/NeoForge (mods.toml)

```toml
modLoader="javafml"
loaderVersion="[52,)"
license="MIT"

[[mods]]
modId="alltherecipes"
version="1.0.0"
displayName="AllTheRecipes"
description="Unlock all recipes in the recipe book"
```

## 7. Acceptance Criteria

1. **Build Success** - All three mod variants compile without errors
2. **Client-Side Only** - No server dependencies, works without server mods
3. **Multiplayer Compatible** - Works on multiplayer servers without affecting server state
4. **Multi-Loader** - Works on Fabric, Forge, and NeoForge
5. **Version Support** - Compatible with 1.19.x through 1.21.x+
6. **Recipe Unlocking** - All recipes appear as unlocked in the recipe book UI
7. **No Desync** - Does not cause any desynchronization with server

## 8. Optional Enhancements

- Per-world persistence of unlocked state using WorldSavedData
- Configurable recipe categories to unlock
- Keybind to manually refresh recipes
