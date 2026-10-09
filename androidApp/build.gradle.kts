plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.gms)
    alias(libs.plugins.crashlitycs)
    alias(libs.plugins.serialization)
    alias(libs.plugins.dependency.analysis)
    alias(libs.plugins.kover)
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

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

kover {
    currentProject {
        createVariant("coverage") { add("localMockDebug") }
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(libs.versions.jdk.get().toInt())
    }
}

dependencies {
    // Compose
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling)
    implementation(libs.compose.activity)
    implementation(libs.compose.navigation)
    implementation(libs.core.splashscreen)
    implementation(libs.accompanist.systemuicontroller)

    // DI (Koin)
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    implementation(libs.koin.compose.viewmodel)

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

    // Units
    testImplementation(libs.junit)
    testImplementation(libs.koin.test)
    testImplementation(libs.coroutines.test)
    testImplementation(project(":core:model"))

    // Modules
    implementation(project(":shared"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:common"))
    implementation(project(":core:network"))
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
    implementation(project(":features:transaction-detail"))
}
