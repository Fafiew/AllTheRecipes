plugins {
    id("forge")
}

val minecraftVersion: String = "1.21.4"
val forgeVersion: String = "52.0.19"

dependencies {
    // Common core module - compile against it
    implementation(project(":common"))
    
    // Forge
    forge("net.minecraftforge:forge:${minecraftVersion}-${forgeVersion}")
    
    // Minecraft
    minecraft("com.mojang:minecraft:${minecraftVersion}")
    mappings(loom.officialMojangMappings())
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

minecraft {
    version = "${minecraftVersion}-${forgeVersion}"
    runDir = "run"
}

forge {
    version = "${minecraftVersion}-${forgeVersion}"
    minecraftVersion = minecraftVersion
}
