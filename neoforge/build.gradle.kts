// NeoForge module - placeholder
// For production build, requires:
// - NeoGradle 7.1.25+
// - Gradle 8.14+
// - Correct version format (see NeoForge docs)
plugins {
    id("java-library")
}

dependencies {
    implementation(project(":common"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
