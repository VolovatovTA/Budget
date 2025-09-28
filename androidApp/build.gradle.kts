import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile
import kotlin.jvm.java

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.gms)
    alias(libs.plugins.crashlitycs)
    alias(libs.plugins.serialization)
}

android {
    namespace = "ru.bysoft.android.budget"

    signingConfigs {
        create("release") {
            keyAlias = "signing_key_true"
            keyPassword = System.getenv("KEY_PASSWORD")
            storeFile = file("${project.rootDir}/signing_keys")
            storePassword = System.getenv("STORE_PASSWORD")
        }
        create("upload") {
            keyAlias = System.getenv("KEY_UPLOAD_ALIAS")
            keyPassword = System.getenv("KEY_UPLOAD_PASSWORD")
            storeFile = file("${project.rootDir}/signing_keys")
            storePassword = System.getenv("STORE_PASSWORD")
        }
        create("default") {
            keyAlias = "androiddebugkey"
            keyPassword = "android"
            storeFile = file("${project.rootDir}/debug.keystore")
            storePassword = "android"
        }
    }
    compileSdk = libs.versions.compile.sdk.get().toInt()

    defaultConfig {
        applicationId = "ru.it_bears.android.budget"
        minSdk = libs.versions.min.sdk.get().toInt()
        lint.targetSdk = libs.versions.target.sdk.get().toInt()
        versionCode = 3
        versionName = "1.0.0"

    }

    buildTypes {
        release {
            signingConfig = signingConfigs.get("upload")
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            multiDexEnabled = true
        }
        debug {
            signingConfig = signingConfigs.get("default")
            applicationIdSuffix = ".debug"
            isDebuggable = true
        }
    }
    // для объединения флаворов
    flavorDimensions.add("backType")
    productFlavors {
        create("remoteBack") {
            dimension = "backType"
            versionNameSuffix = "-remoteBack"
        }
        create("localMock") {
            dimension = "backType"
            versionNameSuffix = "-localMock"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    tasks.withType(KotlinJvmCompile::class.java).configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    // Compose
    implementation(libs.bundles.compose)
//    implementation(libs.compose.material.icons.core)
//    implementation(libs.compose.material.icons.extended)
    implementation(libs.compose.activity)
    implementation(libs.compose.navigation)

//    // Accompanist
//    implementation(libs.accompanist.systemuicontroller)
//    implementation(libs.accompanist.pager)
//    implementation(libs.accompanist.pager.indicators)

    // AndroidX & Material
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.36.0")
    implementation(libs.compose.navigation)
//    implementation(libs.appcompat)
//    implementation(libs.material)
//    implementation(libs.constraintlayout)
//    implementation(libs.core.splashscreen)
//
    // DI (Koin)
    implementation(libs.koin.core)
    implementation(libs.koin.android)

    // Networking / Logging
    implementation(libs.retrofit.logging)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.play.services.auth)

    // Room
    implementation(libs.room.runtime)
//    ksp(libs.room.compiller)
//
//    // Unit tests
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.junit.ext)
//    androidTestImplementation(libs.espresso.core)
//
    // Modules
    implementation(project(":shared"))
    implementation(project(":uikit"))
    implementation(project(":common"))
    implementation(project(":api"))
    implementation(project(":features:splash"))
    implementation(project(":features:auth"))
    implementation(project(":features:create-udate-category"))
    implementation(project(":features:create-update-delete-transactions"))
    implementation(project(":features:create-update-wallet"))
    implementation(project(":features:bottom-navigation:host"))
    implementation(project(":features:bottom-navigation:home"))
    implementation(project(":features:bottom-navigation:statistic"))
    implementation(project(":features:statistic-by-month"))
    implementation(project(":features:settings"))
    implementation(project(":features:currency-rates"))
}
