plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    id(libs.plugins.kotlin.kapt.get().pluginId)
    id(libs.plugins.hilt.plugin.get().pluginId)
}

android {
    namespace = "com.aa.base"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.aa.base"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
//        kotlinCompilerExtensionVersion = "${libs.versions.kotlin.ext}"
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    hilt {
        enableAggregatingTask = true
    }

    kotlin {
        jvmToolchain(17)
        sourceSets.configureEach {
            kotlin.srcDir("$buildDir/generated/ksp/$name/kotlin/")
        }
    }
    allprojects {
        tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_17.toString()
            }
        }
    }
}

dependencies {

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(project(":model"))
    implementation(project(":usecase"))
    implementation(project(":local"))


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

    implementation(libs.coil.compose)

    implementation(libs.appcompat)
    implementation(libs.core.ktx)

    implementation(libs.lifecycle.viewmodel.ktx)


    implementation(libs.coroutines)

    implementation(libs.timber.log)
    implementation(libs.moshi)

    implementation(libs.dagger)
    kapt(libs.dagger.compiler)
}