import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "ru.bysoft.build_logic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.gradleApiPlugin)
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
    compileOnly(libs.dependency.analysis.gradlePlugin)

}

gradlePlugin {
    plugins {
        register("androidLibraryBasic") {
            id = libs.plugins.bysoft.library.get().pluginId
            implementationClass = "ru.BaseLibraryPlugin"
        }
        register("androidLibraryCompose") {
            id = libs.plugins.bysoft.compose.get().pluginId
            implementationClass = "ru.ComposeLibraryPlugin"
        }
    }
}