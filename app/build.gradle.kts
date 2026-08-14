plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.mikhaylenko.weatherdemo"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.mikhaylenko.weatherdemo"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
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
    buildFeatures {
        compose = true
    }
}

kotlin {
    jvmToolchain(21)
}

    dependencies {
        //ktx
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.lifecycle.runtime.ktx)
        //compose
        implementation(libs.androidx.activity.compose)
        implementation(libs.androidx.compose.ui)
        implementation(libs.androidx.compose.ui.graphics)
        implementation(libs.androidx.compose.ui.tooling.preview)
        implementation(libs.androidx.compose.material3)
        implementation(libs.androidx.compose.material.icons.extended)
        implementation(platform(libs.androidx.compose.bom))
        debugImplementation(libs.androidx.compose.ui.tooling)
        debugImplementation(libs.androidx.compose.ui.test.manifest)
        //image
        implementation(libs.coil.compose)
        //network
        implementation(libs.retrofit)
        implementation(libs.converter.gson)
        implementation(libs.okhttp.logging.interceptor)
        //di
        implementation(libs.hilt.android)
        ksp(libs.hilt.compiler)
        //test
        testImplementation(libs.junit)
    }