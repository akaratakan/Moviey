plugins {
    alias(libs.plugins.kotlinAndroid)
    id(libs.plugins.android.library.get().pluginId)
}

android {
    compileSdk = 33
    namespace = "com.aa.model"

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

    implementation(libs.moshi)
    implementation(libs.moshi.converter)
}