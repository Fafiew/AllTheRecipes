// neofore module - using placeholder until we confirm correct version
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
