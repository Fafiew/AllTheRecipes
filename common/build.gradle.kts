/*
 * AllTheRecipes - Minecraft Mod
 * Copyright (c) 2024 AllTheRecipes
 * 
 * This is a client-side only mod that unlocks all recipes
 * Works on Fabric, Forge, and NeoForge
 */

plugins {
    id("fabric-loom-basic")
}

val minecraftVersion: String = "1.21.4"

dependencies {
    compileOnly("com.mojang:minecraft:${minecraftVersion}")
    compileOnly("net.fabricmc:yarn:1.21.4+build.8-v2")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

loom {
    officialMojangMappings()
}
