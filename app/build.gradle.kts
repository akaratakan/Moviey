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
        viewBinding = true
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
    implementation(libs.appcompat)
    implementation(libs.core.ktx)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)

    implementation(libs.coroutines)

    implementation(libs.timber.log)
    implementation(libs.moshi)

    implementation(libs.dagger)
    implementation(libs.hilt.navigation)
    kapt(libs.dagger.compiler)
}