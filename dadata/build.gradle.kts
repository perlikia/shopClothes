plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)

    kotlin("plugin.serialization") version "2.0.0"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies{
    implementation(libs.okhttp)
    implementation(libs.kotlinx.serialization.json)
}