buildscript {
    dependencies {
        classpath(libs.google.services)
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    // Add the Google services Gradle plugin
    id("com.google.gms.google-services") version "4.4.2" apply false
}