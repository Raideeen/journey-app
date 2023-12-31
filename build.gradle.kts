// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    //id("com.android.application") version "8.1.2" apply false
    id("com.android.application") version "8.1.4" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
    // Add KSP Gradle plugin for Room
    id("com.google.devtools.ksp") version "1.9.21-1.0.15" apply false
}

// Add the Safe Args Gradle plugin from the Navigation component
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.1.4")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.21")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.5")
    }
}
