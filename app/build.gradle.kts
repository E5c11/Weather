import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.services)
    alias(libs.plugins.hilt.android.plugin)
    alias(libs.plugins.navigation.safeargs)
    alias(libs.plugins.kotlin.serialization)
}

val properties = Properties().apply {
    file("../local.properties").takeIf { it.canRead() }?.inputStream()?.use { load(it) }
}

android {
    namespace = "com.demo.weather"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.demo.weather"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.demo.weather.CustomTestRunner"

        buildConfigField("String", "API_KEY", "${properties["API_KEY"] ?: ""}")
        resValue("string", "MAPS_API_KEY", properties["MAP_KEY"]?.toString() ?: "")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    sourceSets {
        named("main") {
            jniLibs.srcDir("libs")
            res.srcDirs(
                "src/main/res/weather",
                "src/main/res/map",
                "src/main/res/history",
                "src/main/res"
            )
        }
    }
    packagingOptions {
        resources.excludes += "META-INF/*"
        jniLibs.useLegacyPackaging = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "2.0.0"
    }
}

dependencies {
    // AndroidX Libraries
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.splashscreen)
    implementation(libs.material)

    // Compose Libraries
    implementation(libs.compose.ui)
    implementation(libs.compose.material3)
    implementation(libs.compose.lifecycle.runtime)
    implementation(libs.compose.constraintlayout)
    implementation(libs.compose.hilt.navigation)
    debugImplementation(libs.compose.ui.tooling.preview)

    // Dependency Injection - Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // JSON Parsing and Serialization
    implementation(libs.gson)
    implementation(libs.kotlinx.serialization.json)

    // Networking
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp.logging.interceptor)

    // Navigation
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    // Image Loading with Coil
    implementation(libs.coil)
    implementation(libs.coil.svg)
    implementation(libs.coil.compose)

    // Coroutines
    implementation(libs.coroutines.play.services)
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    // Room for local database
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)

    // Google Play Services
    implementation(libs.play.services.location)
    implementation(libs.play.services.base)
    implementation(libs.play.services.maps)
    implementation(libs.google.places)

    // Unit Testing Libraries
    testImplementation(libs.junit)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.mockk)
    testImplementation(libs.mockk.agent.jvm)
    testImplementation(libs.kluent)

    // Android Instrumented Testing Libraries
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.mockk.android)
    androidTestImplementation(libs.kluent.android)
    androidTestImplementation(libs.uiautomator)
    androidTestImplementation(libs.core.testing)
    androidTestImplementation(libs.runner)
    androidTestImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.test.rules)

    // Additional Testing Libraries
    debugImplementation(libs.fragment.testing)
    testImplementation(libs.turbine)
    androidTestImplementation(libs.turbine)
}