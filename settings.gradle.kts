pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/") {
            content {
                includeGroup("net.fabricmc")
                includeGroup("fabric-loom")
            }
        }
    }
}

plugins {
    id("fabric-loom") version "1.8.11" apply false
}

rootProject.name = "AllTheRecipes"

include("common")
include("fabric")