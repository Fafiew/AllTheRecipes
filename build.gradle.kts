plugins {
    id("fabric") version "0.15.11" apply false
    id("neoforge") version "1.21.4-52.0.19" apply false
    id("forge") version "1.21.4-52.0.19" apply false
    id("fabric-loom-basic") version "0.15.11" apply false
}

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

    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
}
