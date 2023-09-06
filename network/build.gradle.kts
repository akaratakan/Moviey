plugins {
    alias(libs.plugins.kotlinAndroid)
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.kapt.get().pluginId)
    id(libs.plugins.hilt.plugin.get().pluginId)
}

android {
    compileSdk = 34
    namespace = "com.aa.network"

    defaultConfig {
        minSdk = 24
        buildConfigField("String","URL","\"http://www.omdbapi.com/\"")
        buildConfigField("String","API_KEY","\"c38aff30\"")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation(project(":model"))

    implementation(libs.retrofit)
    implementation(libs.timber.log)
    implementation(libs.okhttp)
    implementation(libs.okhttp.intercepter)
    implementation(libs.moshi)
    implementation(libs.moshi.converter)

    implementation(libs.dagger)
    kapt(libs.dagger.compiler)
}