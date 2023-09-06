plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
}

android {
    compileSdk = 34
    namespace = "com.aa.common"

    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(libs.compose.runtime)
    implementation(libs.compose.lifecycle.runtime)
    implementation(libs.compose.runtime.livedata)
    implementation(libs.compose.material)
    implementation(libs.compose.material.size)
    implementation(libs.compose.animation)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.controller)
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.navigation)
    implementation(libs.compose.hilt)

    implementation(libs.compose.icons)
    implementation(libs.compose.foundation)
    implementation(libs.compose.foundation.layout)
}