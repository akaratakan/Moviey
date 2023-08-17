plugins {
    alias(libs.plugins.kotlinAndroid)
    id(libs.plugins.kotlin.kapt.get().pluginId)
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.hilt.plugin.get().pluginId)
}

android {
    compileSdk = 33
    namespace = "com.aa.local"

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

    // coroutines
//    implementation(libs.coroutines)
//    testImplementation(libs.coroutines)
//    testImplementation(libs.coroutines.test)

    // database
    implementation(libs.room.ktx)
    implementation(libs.room)
    kapt(libs.room.ksp)

    // json parsing
    implementation(libs.moshi)

    // di
    implementation(libs.dagger)
    implementation(libs.hilt.navigation)
    kapt(libs.dagger.compiler)
}