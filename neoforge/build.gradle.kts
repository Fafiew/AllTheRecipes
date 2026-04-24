plugins {
    id("neoforge")
}

val minecraftVersion: String = "1.21.4"
val neoforgeVersion: String = "52.0.19"

dependencies {
    // Common core module - compile against it
    implementation(project(":common"))
    
    // NeoForge
    neoForge("net.neoforged:neoforge:${minecraftVersion}-${neoforgeVersion}")
    
    // Minecraft
    minecraft("com.mojang:minecraft:${minecraftVersion}")
    mappings(loom.officialMojangMappings())
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

minecraft {
    version = "${minecraftVersion}-${neoforgeVersion}"
    runDir = "run"
}

neoForge {
    version = "${minecraftVersion}-${neoforgeVersion}"
    minecraftVersion = minecraftVersion
}
