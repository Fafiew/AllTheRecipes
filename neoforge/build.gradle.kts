plugins {
    id("net.neoforged.gradle.userdev") version "7.0.99"
}

val minecraftVersion: String = "1.21.4"
val neoforgeVersion: String = "7.0.99"

repositories {
    maven("https://maven.neoforged.net/releases")
}

base {
    archivesName = "alltherecipes-neoforge"
}

java.toolchain.languageVersion = JavaLanguageVersion.of(21)

dependencies {
    // Common core module
    implementation(project(":common"))
    
    // NeoForge
    implementation("net.neoforged:neoforge:${minecraftVersion}-${neoforgeVersion}")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
