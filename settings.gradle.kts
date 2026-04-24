pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/")
        maven("https://maven.neoforged.net/releases")
        maven("https://maven.minecraftforge.net/")
        gradlePluginPortal()
    }
    
    plugins {
        id("fabric") version "0.15.11"
        id("neoforge") version "1.21.4-52.0.19"
        id("forge") version "1.21.4-52.0.19"
    }
}

rootProject.name = "AllTheRecipes"

include("common")
include("fabric")
include("forge")
include("neoforge")
