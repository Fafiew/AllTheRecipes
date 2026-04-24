plugins {
    id("fabric-loom")
}

val minecraftVersion: String = "1.21.4"
val yarnVersion: String = "1.21.4+build.8"
val loaderVersion: String = "0.16.9"
val fabricApiVersion: String = "0.108.0+1.21.4"

repositories {
    maven("https://maven.fabricmc.net/")
}

dependencies {
    // Common core module
    implementation(project(":common"))
    
    // Minecraft and mappings
    minecraft("com.mojang:minecraft:${minecraftVersion}")
    mappings("net.fabricmc:yarn:${yarnVersion}:v2")
    
    // Fabric Loader
    modImplementation("net.fabricmc:fabric-loader:${loaderVersion}")
    
    // Fabric API for client events
    modApi("net.fabricmc.fabric-api:fabric-api:${fabricApiVersion}")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.processResources {
    inputs.property("version", project.version)
    filesMatching("fabric.mod.json") {
        expand("version" to project.version)
    }
}
