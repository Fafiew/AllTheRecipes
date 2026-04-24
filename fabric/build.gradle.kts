plugins {
    id("fabric")
}

val minecraftVersion: String = "1.21.4"

dependencies {
    // Common core module - compile against it
    implementation(project(":common"))
    
    // Fabric API
    modImplementation("net.fabricmc:fabric-api:fabric-api")
    
    // Minecraft and mappings
    minecraft("com.mojang:minecraft:${minecraftVersion}")
    mappings(loom.yarn())
    
    // Fabric loader
    modImplementation("net.fabricmc:fabric-loader")
}

fabric {
    version = "0.15.11"
}
