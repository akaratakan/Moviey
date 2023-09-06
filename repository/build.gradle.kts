plugins {
    alias(libs.plugins.kotlinAndroid)
    id(libs.plugins.kotlin.kapt.get().pluginId)
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.hilt.plugin.get().pluginId)
}

android {
    compileSdk = 34
    namespace = "com.aa.repository"

    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {

    implementation(project(":network"))
    implementation(project(":local"))
    implementation(project(":model"))

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(libs.timber.log)

    implementation(libs.dagger)
    kapt(libs.dagger.compiler)
}