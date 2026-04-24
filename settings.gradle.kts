pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.neoforged.net/releases") {
            content {
                includeGroup("net.neoforged")
                includeGroupByRegex("net\\.neoforged\\..*")
            }
        }
        maven("https://maven.minecraftforge.net/") {
            content {
                includeGroup("net.minecraftforge")
                includeGroupByRegex("net\\.minecraftforge\\..*")
            }
        }
    }
}

plugins {
    id("net.neoforged.gradle.userdev") version "7.1.25" apply false
}

rootProject.name = "AllTheRecipes"

include("common")
include("neoforge")