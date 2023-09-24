plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.dagger.compiler) apply false
}
buildscript {
    repositories {
        mavenCentral()
        google()
    }
}