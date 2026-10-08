plugins {
    alias(libs.plugins.android.application)
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
            storeFile = file("${project.rootDir}/signing_keys.jks")
            storePassword = System.getenv("STORE_PASSWORD")
        }
        create("upload") {
            keyAlias = System.getenv("KEY_UPLOAD_ALIAS")
            keyPassword = System.getenv("KEY_UPLOAD_PASSWORD")
            storeFile = file("${project.rootDir}/signing_keys.jks")
            storePassword = System.getenv("STORE_PASSWORD")
        }
        create("default") {
            keyAlias = "androiddebugkey"
            keyPassword = "android"
            storeFile = file("${project.rootDir}/debug.keystore.jks")
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

        // ключ exchangerate-api: exchangeRateApiKey в ~/.gradle/gradle.properties или переменная окружения
        val exchangeRateApiKey = providers.gradleProperty("exchangeRateApiKey")
            .orElse(providers.environmentVariable("EXCHANGE_RATE_API_KEY"))
            .getOrElse("")
        buildConfigField("String", "EXCHANGE_RATE_API_KEY", "\"$exchangeRateApiKey\"")
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.get("upload")
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            multiDexEnabled = true
        }
        debug {
            // в CI файла debug.keystore.jks нет — тогда подписываем стандартным отладочным ключом
            signingConfig = signingConfigs.get("default").takeIf { it.storeFile?.exists() == true }
                ?: signingConfigs.getByName("debug")
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

    buildFeatures {
        compose = true
        buildConfig = true
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
    implementation(libs.core.splashscreen)
    implementation(libs.accompanist.systemuicontroller)
//    implementation(libs.appcompat)
//    implementation(libs.material)
//    implementation(libs.constraintlayout)
//    implementation(libs.core.splashscreen)
//
    // DI (Koin)
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.android.compose)

    // Networking / Logging
    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlinx.serialization)
    implementation(libs.retrofit.logging)
    implementation(libs.kotlinx.json)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.play.services.auth)

    // Room
    implementation(libs.room.runtime)
//    ksp(libs.room.compiler)
//
    // Units
    testImplementation(libs.junit)
    testImplementation(libs.koin.test)

    // Instrumentals
//    androidTestImplementation(libs.junit.ext)
//    androidTestImplementation(libs.espresso.core)

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
