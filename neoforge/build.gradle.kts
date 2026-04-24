plugins {
    id("net.neoforged.gradle.userdev") version "7.1.25"
}

val minecraftVersion: String = "21.11.42"
val neoforgeVersion: String = "21.11.42"

repositories {
    maven("https://maven.neoforged.net/releases")
    maven("https://maven.minecraftforge.net/")
}

base {
    archivesName = "alltherecipes-neoforge"
}

java.toolchain.languageVersion = JavaLanguageVersion.of(21)

dependencies {
    // Common core module
    implementation(project(":common"))
    
    // NeoForge
    implementation("net.neoforged:neoforge:${minecraftVersion}")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
