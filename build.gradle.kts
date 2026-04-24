// Root build.gradle.kts - AllTheRecipes Multi-Loader Minecraft Mod

allprojects {
    group = "com.alltherecipes"
    version = "1.0.0"

    repositories {
        mavenCentral()
        maven("https://maven.fabricmc.net/")
        maven("https://maven.neoforged.net/")
        maven("https://maven.minecraftforge.net/")
    }
}

subprojects {
    apply(plugin = "java")

    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
}
