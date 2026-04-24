// forge module not configured yet - using neoforge instead
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
