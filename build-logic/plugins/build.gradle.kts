plugins {
    `kotlin-dsl`
}

group = "ru.bysoft.build_logic"

kotlin {
    jvmToolchain(libs.versions.jdk.get().toInt())
}

dependencies {
    compileOnly(libs.android.gradleApiPlugin)
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
    compileOnly(libs.dependency.analysis.gradlePlugin)
    compileOnly(libs.kover.gradlePlugin)

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